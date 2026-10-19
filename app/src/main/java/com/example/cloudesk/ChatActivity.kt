package com.example.cloudesk

import org.json.JSONObject
import android.widget.Toast

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Live Chat Support screen.
 *
 * Replies come from Google's Gemini API (see GeminiClient) once
 * GeminiConfig.API_KEY is filled in, giving real, context-aware answers.
 * If the key isn't configured yet, or a request fails for any reason, this
 * falls back to a small local canned-response bot so chat never just breaks.
 * Messages are persisted to Room so the conversation survives navigating
 * away and back.
 */
class ChatActivity : BaseActivity() {

    private lateinit var adapter: ChatAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var typingIndicator: TextView
    private val handler = Handler(Looper.getMainLooper())
    private var userId: Int = -1
    private var userName: String = "User"
    private val messages = mutableListOf<ChatMessageEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_chat)

        userId = intent.getIntExtra("USER_ID", -1)
        userName = intent.getStringExtra("USER_NAME") ?: "User"

        findViewById<ImageButton>(R.id.backButton).setOnClickListener { finish() }

        recyclerView = findViewById(R.id.chatRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this).apply { stackFromEnd = true }
        adapter = ChatAdapter(messages)
        recyclerView.adapter = adapter

        typingIndicator = findViewById(R.id.typingIndicator)

        val chatInput = findViewById<EditText>(R.id.chatInput)
        val sendButton = findViewById<ImageButton>(R.id.sendButton)

        loadConversation()

        sendButton.setOnClickListener {
            val text = chatInput.text.toString().trim()
            if (text.isNotEmpty()) {
                sendUserMessage(text)
                chatInput.text?.clear()
            }
        }
    }

    private fun loadConversation() {
        val dao = AppDatabase.getDatabase(this).chatMessageDao()
        val existing = dao.getConversation(userId)
        if (existing.isEmpty()) {
            // First time: show a greeting. (The server records its own greeting with the first message.)
            messages.add(
                ChatMessageEntity(
                    userId = userId,
                    senderName = getString(R.string.chat_support_name),
                    message = getString(R.string.chat_greeting_format, userName),
                    fromSupport = true,
                    timestamp = timeNow()
                )
            )
        } else {
            messages.addAll(existing)
        }
        adapter.notifyDataSetChanged()
        scrollToBottom()

        // Bring the conversation up to date from the online database.
        ServerSync.refreshAsync(this) { ok ->
            if (ok) reloadMessages()
        }
    }

    /** Re-reads the conversation from the local copy of the online database. */
    private fun reloadMessages() {
        val stored = AppDatabase.getDatabase(this).chatMessageDao().getConversation(userId)
        if (stored.isEmpty()) return
        messages.clear()
        messages.addAll(stored)
        adapter.notifyDataSetChanged()
        scrollToBottom()
    }

    private fun sendUserMessage(text: String) {
        // Show the message straight away, then ask the server. Chat lives in the online
        // database; replies come from the server: Gemini for admins and technicians only,
        // the built-in default replies for everybody else.
        val pending = ChatMessageEntity(
            userId = userId,
            senderName = userName,
            message = text,
            fromSupport = false,
            timestamp = timeNow()
        )
        messages.add(pending)
        adapter.notifyItemInserted(messages.size - 1)
        scrollToBottom()
        typingIndicator.visibility = View.VISIBLE

        ApiClient.callAsync(this, "chat_send", JSONObject().put("message", text)) { r ->
            typingIndicator.visibility = View.INVISIBLE
            if (r.ok) {
                reloadMessages()
            } else {
                messages.remove(pending)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, ApiClient.errorText(this, r), Toast.LENGTH_SHORT).show()
            }
        }
    }

    // NOTE: keyword detection here deliberately checks English words regardless of
    // the app's display language — this fallback only runs when Gemini isn't
    // configured. Once GeminiConfig.API_KEY is set, users can type in any
    // language and get a genuinely understood, localized AI reply instead.
    private fun generateSupportReply(userText: String): String {
        val text = userText.lowercase()
        return when {
            "password" in text || "log in" in text || "login" in text ->
                getString(R.string.chat_reply_login)
            "urgent" in text || "asap" in text || "critical" in text || "down" in text ->
                getString(R.string.chat_reply_urgent)
            "ticket" in text && ("status" in text || "update" in text) ->
                getString(R.string.chat_reply_ticket_status)
            "thank" in text ->
                getString(R.string.chat_reply_thanks)
            "hi" == text.trim() || "hello" in text || "hey" in text ->
                getString(R.string.chat_reply_greeting)
            else ->
                getString(R.string.chat_reply_default)
        }
    }

    private fun scrollToBottom() {
        recyclerView.post { recyclerView.scrollToPosition(messages.size - 1) }
    }

    private fun timeNow(): String =
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
}
