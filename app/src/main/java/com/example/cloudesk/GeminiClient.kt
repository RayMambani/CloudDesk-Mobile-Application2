package com.example.cloudesk

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Google Gemini integration for Live Chat Support — mirrors the web system's
 * chat integration (same system prompt, same fallback behavior, same lessons
 * learned the hard way while wiring up the website's version):
 *   - Gemini 3.x Flash can't fully disable "thinking", and the older
 *     thinkingBudget/thinkingConfig parameters caused HTTP 400s on this model,
 *     so this deliberately leaves generationConfig minimal.
 *   - The conversation sent to Gemini must start on a "user" turn, so any
 *     leading support-bot messages (like the initial greeting) are dropped
 *     before building the request.
 *   - Network calls must never run on the main thread on Android — call
 *     callGeminiForReply() from a background thread (ChatActivity does this).
 *
 * Returns null on any failure (missing key, network error, bad response) so
 * the caller can fall back to the local canned-response bot — the chat
 * feature should never simply break if Gemini is unavailable.
 */
object GeminiClient {

    private const val SYSTEM_PROMPT = """
You are "CloudDesk Support", a helpful assistant embedded in the CloudDesk IT
helpdesk ticketing app's live chat.

Guidelines:
- Answer clearly and briefly — 2 to 4 short sentences, no long essays.
- You can help with general troubleshooting guidance (login issues, slow
  performance, common software/network/hardware problems) and questions
  about how to use CloudDesk itself (submitting a ticket, checking ticket
  status, attaching a file, categories, priority levels).
- You do NOT have access to the user's actual account, ticket history, or
  live system status — never invent specific ticket numbers, statuses, or
  account details. If asked about a specific ticket, tell them to check
  their dashboard or open that ticket's detail page.
- If an issue sounds urgent, serious, or needs hands-on technician work,
  recommend they submit a ticket from the dashboard so it gets tracked and
  routed properly, rather than trying to fully resolve it yourself in chat.
- Stay on topic: IT support and using CloudDesk. Politely redirect anything
  unrelated back to those topics.

Knowledge base (use these facts when relevant to the question):
- Forgot password: on the sign-in screen tap "Forgot password?", enter your
  email, answer your security question and choose a new password. If the
  account has no security question, ask an administrator to reset it.
- Submitting a ticket: tap the + button on the dashboard, enter a title,
  description, category and priority (the app suggests a priority), and
  optionally attach a file.
- Response targets by priority: Critical 4 hours, High 24 hours, Medium
  72 hours, Low 120 hours. Overdue tickets are flagged for the support team.
- Wi-Fi/VPN dropping: restart the device, forget and re-join the network,
  update the VPN app, then submit a Network ticket if it continues.
- Slow computer: restart, close unused apps, free up storage, install
  pending updates; submit a Hardware or Software ticket if it persists.
- After a ticket is Resolved, the person who opened it can rate it from the
  ticket page. Ticket status and history are shown on the ticket page.
"""

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    /**
     * @param history Prior messages in this conversation, oldest first.
     * @param newUserMessage The message the user just sent.
     * @return Gemini's reply text, or null if the call couldn't be completed.
     */
    fun callGeminiForReply(history: List<ChatMessageEntity>, newUserMessage: String): String? {
        if (GeminiConfig.API_KEY.isBlank() || GeminiConfig.API_KEY == "PASTE_YOUR_GEMINI_API_KEY_HERE") {
            return null // Not configured yet — caller falls back to the canned bot.
        }

        return try {
            val recent = history.takeLast(10).toMutableList()
            // Gemini expects the conversation to start on a "user" turn.
            while (recent.isNotEmpty() && recent.first().fromSupport) {
                recent.removeAt(0)
            }

            val contents = JSONArray()
            for (m in recent) {
                contents.put(JSONObject().apply {
                    put("role", if (m.fromSupport) "model" else "user")
                    put("parts", JSONArray().put(JSONObject().put("text", m.message)))
                })
            }
            contents.put(JSONObject().apply {
                put("role", "user")
                put("parts", JSONArray().put(JSONObject().put("text", newUserMessage)))
            })

            val payload = JSONObject().apply {
                put("system_instruction", JSONObject().apply {
                    put("parts", JSONArray().put(JSONObject().put("text", SYSTEM_PROMPT)))
                })
                put("contents", contents)
                put("generationConfig", JSONObject().apply {
                    put("maxOutputTokens", 2048)
                })
            }

            val url = "https://generativelanguage.googleapis.com/v1beta/models/" +
                "${GeminiConfig.MODEL}:generateContent"

            val body = payload.toString().toRequestBody("application/json".toMediaType())
            val request = Request.Builder()
                .url(url)
                .addHeader("x-goog-api-key", GeminiConfig.API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build()

            httpClient.newCall(request).execute().use { response ->
                val responseBody = response.body?.string()
                if (!response.isSuccessful || responseBody.isNullOrBlank()) {
                    android.util.Log.w("GeminiClient", "Gemini API call failed (HTTP ${response.code}): $responseBody")
                    return null
                }
                val json = JSONObject(responseBody)
                json.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text")
                    .trim()
            }
        } catch (e: IOException) {
            android.util.Log.w("GeminiClient", "Gemini network error: ${e.message}")
            null
        } catch (e: Exception) {
            android.util.Log.w("GeminiClient", "Gemini call failed: ${e.message}")
            null
        }
    }
}
