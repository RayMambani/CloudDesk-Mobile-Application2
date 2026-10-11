package com.example.cloudesk

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
        val db = AppDatabase.getDatabase(this)
        val dao = db.chatMessageDao()
        val existing = dao.getConversation(userId)

        if (existing.isEmpty()) {
            // Greet the user the first time they open chat.
            val greeting = ChatMessageEntity(
                userId = userId,
                senderName = getString(R.string.chat_support_name),
                message = getString(R.string.chat_greeting_format, userName),
                fromSupport = true,
                timestamp = timeNow()
            )
            dao.insertMessage(greeting)
            messages.add(greeting)
        } else {
            messages.addAll(existing)
        }
        adapter.notifyDataSetChanged()
        scrollToBottom()
    }

    private fun sendUserMessage(text: String) {
        val db = AppDatabase.getDatabase(this)
        val dao = db.chatMessageDao()

        // Snapshot history BEFORE inserting the new message, for Gemini context.
        val historyBeforeThisMessage = messages.toList()

        val userMsg = ChatMessageEntity(
            userId = userId,
            senderName = userName,
            message = text,
            fromSupport = false,
            timestamp = timeNow()
        )
        dao.insertMessage(userMsg)
        messages.add(userMsg)
        adapter.notifyItemInserted(messages.size - 1)
        scrollToBottom()

        typingIndicator.visibility = View.VISIBLE

        // Network calls can't run on the main thread — ask Gemini in the
        // background, then hop back to the main thread to update the UI/DB.
        Thread {
            // Gemini answers are for staff only. Regular users always get the built-in
            // default responses (generateSupportReply) — Gemini is never called for them.
            val role = SessionManager.getUserRole(this)
            val aiReply = if (role == "admin" || role == "technician")
                GeminiClient.callGeminiForReply(historyBeforeThisMessage, text)
            else null
            handler.post {
                typingIndicator.visibility = View.INVISIBLE
                val reply = aiReply ?: generateSupportReply(text)
                val replyMsg = ChatMessageEntity(
                    userId = userId,
                    senderName = getString(R.string.chat_support_name),
                    message = reply,
                    fromSupport = true,
                    timestamp = timeNow()
                )
                dao.insertMessage(replyMsg)
                messages.add(replyMsg)
                adapter.notifyItemInserted(messages.size - 1)
                scrollToBottom()
            }
        }.start()
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