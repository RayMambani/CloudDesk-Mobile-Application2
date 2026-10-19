package com.example.cloudesk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * Talks to the online backend (api.php). Every call is a JSON POST:
 *   {"action": "...", ...}   with the header  X-Auth-Token: <token>  once signed in.
 *
 * call() blocks, so it must run off the main thread; callAsync() does that for you and
 * delivers the result on the main thread. Successful calls that return the user's data
 * refresh the local copy (ServerSync) automatically.
 */
object ApiClient {

    data class Result(
        val ok: Boolean,
        val json: JSONObject?,
        val error: String? = null,
        val networkError: Boolean = false,
        val httpCode: Int = 0
    )

    /** Blocking request. [baseUrl] is only overridden by tests. */
    fun call(
        context: Context,
        action: String,
        body: JSONObject = JSONObject(),
        authenticated: Boolean = true,
        baseUrl: String = ApiConfig.API_URL
    ): Result {
        if (baseUrl == ApiConfig.API_URL && !ApiConfig.isConfigured()) {
            return Result(false, null, "not_configured")
        }
        val payload = JSONObject(body.toString())
        payload.put("action", action)
        payload.put("lang", LocaleHelper.getLanguage(context))

        var connection: HttpURLConnection? = null
        return try {
            connection = (URL(baseUrl).openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                connectTimeout = 15000
                readTimeout = 45000 // the server may be waking up (free hosting) or waiting for Gemini
                doOutput = true
                setRequestProperty("Content-Type", "application/json; charset=utf-8")
                setRequestProperty("Accept", "application/json")
                if (authenticated) {
                    SessionManager.getToken(context)?.let { setRequestProperty("X-Auth-Token", it) }
                }
            }
            connection.outputStream.use { it.write(payload.toString().toByteArray(Charsets.UTF_8)) }

            val code = connection.responseCode
            val stream = if (code in 200..299) connection.inputStream else connection.errorStream
            val text = stream?.bufferedReader(Charsets.UTF_8)?.use { it.readText() } ?: ""
            parse(text, code)
        } catch (e: IOException) {
            Result(false, null, "network", networkError = true)
        } catch (e: Exception) {
            Result(false, null, "bad_response", httpCode = 0)
        } finally {
            connection?.disconnect()
        }
    }

    internal fun parse(text: String, httpCode: Int): Result {
        val json = try { JSONObject(text) } catch (e: Exception) { null }
            ?: return Result(false, null, "bad_response", httpCode = httpCode)
        val ok = json.optBoolean("ok", false)
        val error = if (ok || json.isNull("error")) null else json.getString("error")
        return Result(ok, json, error, httpCode = httpCode)
    }

    /**
     * Runs [call] on a background thread and delivers the result on the main thread.
     * If the server says the session is no longer valid (token expired, account deactivated,
     * password reset by an admin) the user is sent back to the login screen.
     */
    fun callAsync(
        activity: Activity,
        action: String,
        body: JSONObject = JSONObject(),
        authenticated: Boolean = true,
        onResult: (Result) -> Unit
    ) {
        Thread {
            val result = call(activity, action, body, authenticated)
            val data = result.json
            if (authenticated && result.ok && data != null && data.has("me")) {
                try { ServerSync.apply(activity, data) } catch (e: Exception) { e.printStackTrace() }
            }
            activity.runOnUiThread {
                if (activity.isFinishing || activity.isDestroyed) return@runOnUiThread
                if (authenticated && !result.ok && result.error == "unauthorized") {
                    ServerSync.clearLocal(activity)
                    Toast.makeText(activity, activity.getString(R.string.toast_session_expired), Toast.LENGTH_LONG).show()
                    val i = Intent(activity, LoginActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    activity.startActivity(i)
                    activity.finish()
                } else {
                    onResult(result)
                }
            }
        }.start()
    }

    /** A message the user can read for a failed call. */
    fun errorText(context: Context, r: Result): String = context.getString(
        when {
            r.networkError -> R.string.toast_server_unreachable
            r.error == "not_configured" -> R.string.toast_server_not_configured
            r.error == "invalid_credentials" -> R.string.toast_invalid_credentials
            r.error == "deactivated" -> R.string.toast_account_deactivated
            r.error == "exists" -> R.string.toast_user_exists
            r.error == "weak_password" -> R.string.toast_password_too_short
            r.error == "throttled" -> R.string.toast_too_many_attempts
            r.error == "wrong_answer" -> R.string.toast_wrong_security_answer
            r.error == "wrong_current_password" -> R.string.toast_current_password_incorrect
            r.error == "not_found" -> R.string.toast_account_not_found
            r.error == "no_security_question" -> R.string.toast_no_security_question
            r.error == "security_required" -> R.string.toast_security_required
            r.error == "missing_fields" -> R.string.toast_fill_all_fields
            r.error == "invalid_email" -> R.string.toast_invalid_email
            r.error == "google_not_configured" || r.error == "google_invalid" -> R.string.toast_google_failed
            r.error == "attachment_too_large" -> R.string.toast_attachment_too_large
            r.error == "attachment_type_not_allowed" -> R.string.toast_attachment_type_not_allowed
            r.error == "cannot_change_self" -> R.string.toast_cant_change_self
            r.error == "forbidden" -> R.string.toast_admin_only
            else -> R.string.toast_something_went_wrong
        }
    )
}
