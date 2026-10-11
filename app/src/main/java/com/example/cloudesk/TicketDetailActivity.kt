package com.example.cloudesk

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TicketDetailActivity : BaseActivity() {

    private lateinit var commentsRecyclerView: RecyclerView
    private lateinit var commentEditText: TextInputEditText
    private lateinit var addCommentButton: Button
    private var ticketId: String = ""
    private var userId: Int = -1
    private var userRole: String = "user"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_detail)

        ticketId = intent.getStringExtra("TKT_ID") ?: ""
        userId = intent.getIntExtra("USER_ID", -1)
        userRole = intent.getStringExtra("USER_ROLE") ?: "user"

        if (ticketId.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_invalid_ticket_id), Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val db = AppDatabase.getDatabase(this)
        val ticketDao = db.ticketDao()
        val commentDao = db.commentDao()
        val userDao = db.userDao()

        // Always prefer the live database row; fall back to what the list screen passed in.
        val base = ticketDao.getTicketById(ticketId) ?: TicketEntity(
            id = ticketId,
            title = intent.getStringExtra("TKT_TITLE") ?: "",
            description = intent.getStringExtra("TKT_DESC") ?: "",
            status = intent.getStringExtra("TKT_STATUS") ?: "open",
            priority = intent.getStringExtra("TKT_PRIORITY") ?: "medium",
            category = intent.getStringExtra("TKT_CATEGORY") ?: "other",
            created_at = intent.getStringExtra("TKT_DATE") ?: "",
            userId = intent.getIntExtra("TICKET_USER_ID", -1),
            attachmentUri = intent.getStringExtra("TKT_ATTACHMENT_URI"),
            attachmentName = intent.getStringExtra("TKT_ATTACHMENT_NAME")
        )

        val currentUser = userDao.getUserById(userId)
        val currentUserName = currentUser?.let { "${it.firstName} ${it.lastName}" } ?: "User $userId"

        // Bind Views
        findViewById<TextView>(R.id.ticketIdText).text = base.id
        findViewById<TextView>(R.id.ticketTitleText).text = base.title
        findViewById<TextView>(R.id.descriptionText).text = base.description
        findViewById<TextView>(R.id.dateText).text = getString(R.string.ticket_detail_created_on, base.created_at)

        // SLA / due date
        val slaText = findViewById<TextView>(R.id.slaText)
        if (base.status == "open" || base.status == "progress") {
            val due = SlaHelper.dueLabel(base)
            if (due != null) {
                slaText.visibility = View.VISIBLE
                if (SlaHelper.isOverdue(base)) {
                    slaText.text = getString(R.string.sla_overdue_format, due)
                    slaText.setTextColor(getColor(R.color.danger))
                } else {
                    slaText.text = getString(R.string.sla_due_format, due)
                }
            }
        }

        // Attachment
        val attachmentRow = findViewById<LinearLayout>(R.id.attachmentRow)
        val attachmentUriString = base.attachmentUri
        if (!attachmentUriString.isNullOrEmpty()) {
            attachmentRow.visibility = View.VISIBLE
            findViewById<TextView>(R.id.attachmentNameText).text = base.attachmentName ?: ticketId
            attachmentRow.setOnClickListener {
                try {
                    val uri = Uri.parse(attachmentUriString)
                    val viewIntent = Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(uri, contentResolver.getType(uri) ?: "*/*")
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    startActivity(viewIntent)
                } catch (e: Exception) {
                    Toast.makeText(this, getString(R.string.toast_couldnt_open_attachment, e.message ?: ""), Toast.LENGTH_SHORT).show()
                }
            }
        }

        val statusBadge = findViewById<TextView>(R.id.statusBadge)
        statusBadge.text = TicketVocabulary.statusDisplay(this, base.status).uppercase()
        val colorRes = when (base.status.lowercase()) {
            "open" -> R.color.info
            "progress" -> R.color.warning
            "resolved" -> R.color.success
            else -> R.color.text_muted
        }
        statusBadge.backgroundTintList = getColorStateList(colorRes)

        findViewById<TextView>(R.id.priorityBadge).text = TicketVocabulary.priorityDisplay(this, base.priority).uppercase()
        findViewById<TextView>(R.id.categoryBadge).text = TicketVocabulary.categoryDisplay(this, base.category)

        commentsRecyclerView = findViewById(R.id.commentsRecyclerView)
        commentsRecyclerView.layoutManager = LinearLayoutManager(this)
        commentEditText = findViewById(R.id.commentEditText)
        addCommentButton = findViewById(R.id.addCommentButton)

        // Role based UI
        val actionsCard = findViewById<MaterialCardView>(R.id.actionsCard)
        val assignLayout = findViewById<TextInputLayout>(R.id.assignLayout)
        val statusLayout = findViewById<TextInputLayout>(R.id.statusLayout)
        val technicianDropdown = findViewById<AutoCompleteTextView>(R.id.technicianDropdown)
        val statusDropdown = findViewById<AutoCompleteTextView>(R.id.statusDropdown)
        val updateButton = findViewById<Button>(R.id.updateButton)
        val cannedRepliesButton = findViewById<Button>(R.id.cannedRepliesButton)

        if (userRole == "admin" || userRole == "technician") {
            actionsCard.visibility = View.VISIBLE
            statusLayout.visibility = View.VISIBLE
            cannedRepliesButton.visibility = View.VISIBLE

            val statusAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, TicketVocabulary.statusDisplayList(this))
            statusDropdown.setAdapter(statusAdapter)
            statusDropdown.setText(TicketVocabulary.statusDisplay(this, base.status), false)

            if (userRole == "admin") {
                assignLayout.visibility = View.VISIBLE
                val technicians = userDao.getUsersByRole("technician").filter { it.active }
                    .map { "${it.firstName} ${it.lastName}" }.toTypedArray()
                technicianDropdown.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, technicians))
                technicianDropdown.setText(base.assignedTo ?: "", false)
            }
        }

        loadComments()
        loadHistory()
        refreshRatingCard(base)

        // ---- Canned replies (staff only) ----
        cannedRepliesButton.setOnClickListener {
            val replies = arrayOf(
                getString(R.string.canned_1), getString(R.string.canned_2), getString(R.string.canned_3),
                getString(R.string.canned_4), getString(R.string.canned_5), getString(R.string.canned_6)
            )
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.canned_replies_title))
                .setItems(replies) { _, which ->
                    commentEditText.setText(replies[which])
                    commentEditText.setSelection(commentEditText.text?.length ?: 0)
                }
                .show()
        }

        // ---- Notify the ticket owner via the device's own email/SMS app ----
        findViewById<Button>(R.id.notifyEmailButton).setOnClickListener {
            val owner = userDao.getUserById(base.userId)
            val currentStatusKey = if (statusDropdown.text.toString().isNotEmpty())
                TicketVocabulary.statusKeyFromDisplay(this, statusDropdown.text.toString()) else base.status
            val statusLine = getString(R.string.label_status, TicketVocabulary.statusDisplay(this, currentStatusKey))
            val body = "Hi ${owner?.firstName ?: "there"},\n\nAn update on your ticket $ticketId ('${base.title}'):\n" +
                "$statusLine\n\n— CloudDesk Support"
            NotificationHelper.sendEmail(this, owner?.email, "Update on ticket $ticketId", body)
        }
        findViewById<Button>(R.id.notifySmsButton).setOnClickListener {
            val owner = userDao.getUserById(base.userId)
            if (owner?.phone.isNullOrBlank()) {
                Toast.makeText(this, getString(R.string.toast_no_phone_on_file), Toast.LENGTH_SHORT).show()
            } else {
                val currentStatusKey = if (statusDropdown.text.toString().isNotEmpty())
                    TicketVocabulary.statusKeyFromDisplay(this, statusDropdown.text.toString()) else base.status
                val statusLine = getString(R.string.label_status, TicketVocabulary.statusDisplay(this, currentStatusKey))
                val body = "CloudDesk: update on ticket $ticketId ('${base.title}') — $statusLine"
                NotificationHelper.sendSms(this, owner?.phone, body)
            }
        }

        // ---- Comments ----
        addCommentButton.setOnClickListener {
            val content = commentEditText.text.toString()
            if (content.isNotEmpty() && userId != -1) {
                val createdAt = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
                commentDao.insertComment(
                    CommentEntity(
                        ticketId = ticketId,
                        userId = userId,
                        userName = currentUserName,
                        content = content,
                        created_at = createdAt
                    )
                )
                Toast.makeText(this@TicketDetailActivity, getString(R.string.toast_comment_added), Toast.LENGTH_SHORT).show()
                commentEditText.text?.clear()
                loadComments()
            }
        }

        // ---- Update status / assignment (staff) ----
        updateButton.setOnClickListener {
            val latest = ticketDao.getTicketById(ticketId) ?: base

            val statusText = statusDropdown.text.toString()
            val newStatus = if (statusText.isBlank()) latest.status
                            else TicketVocabulary.statusKeyFromDisplay(this, statusText)

            // Only admins can reassign; a technician updating status keeps the current assignee.
            val newAssignee: String? = if (userRole == "admin") {
                technicianDropdown.text.toString().trim().ifEmpty { null }
            } else latest.assignedTo

            val now = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
            val updated = latest.copy(
                status = newStatus,
                assignedTo = newAssignee,
                resolvedAt = if (newStatus == "resolved" || newStatus == "closed") (latest.resolvedAt ?: now) else null
            )
            ticketDao.insertTicket(updated)

            // History log
            if (latest.status != newStatus) {
                TicketHistoryLogger.log(db, ticketId, currentUserName, "status", latest.status, newStatus)
            }
            if ((latest.assignedTo ?: "") != (newAssignee ?: "")) {
                TicketHistoryLogger.log(db, ticketId, currentUserName, "assignee", latest.assignedTo, newAssignee)
            }

            Toast.makeText(this, getString(R.string.toast_ticket_updated), Toast.LENGTH_SHORT).show()
            finish()
        }

        // ---- Rating (ticket owner, after resolution) ----
        findViewById<Button>(R.id.submitRatingButton).setOnClickListener {
            val stars = findViewById<RatingBar>(R.id.ratingBar).rating.toInt()
            if (stars < 1) {
                Toast.makeText(this, getString(R.string.toast_pick_rating), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val comment = findViewById<TextInputEditText>(R.id.ratingCommentInput).text.toString().trim()
            val latest = ticketDao.getTicketById(ticketId) ?: base
            val rated = latest.copy(rating = stars, ratingComment = comment.ifEmpty { null })
            ticketDao.insertTicket(rated)
            TicketHistoryLogger.log(db, ticketId, currentUserName, "rating", null, stars.toString())
            Toast.makeText(this, getString(R.string.toast_rating_saved), Toast.LENGTH_SHORT).show()
            refreshRatingCard(rated)
            loadHistory()
        }
    }

    /** Shows the rating form to the ticket owner once resolved; shows the result to everyone after. */
    private fun refreshRatingCard(ticket: TicketEntity) {
        val card = findViewById<MaterialCardView>(R.id.ratingCard)
        val summary = findViewById<TextView>(R.id.ratingSummaryText)
        val inputSection = findViewById<LinearLayout>(R.id.ratingInputSection)
        val isFinished = ticket.status == "resolved" || ticket.status == "closed"

        when {
            ticket.rating != null -> {
                card.visibility = View.VISIBLE
                inputSection.visibility = View.GONE
                summary.visibility = View.VISIBLE
                val stars = "★".repeat(ticket.rating) + "☆".repeat(5 - ticket.rating)
                summary.text = if (ticket.ratingComment.isNullOrBlank()) stars else "$stars\n${ticket.ratingComment}"
            }
            isFinished && userRole == "user" && userId == ticket.userId -> {
                card.visibility = View.VISIBLE
                inputSection.visibility = View.VISIBLE
                summary.visibility = View.GONE
            }
            else -> card.visibility = View.GONE
        }
    }

    private fun loadHistory() {
        val container = findViewById<LinearLayout>(R.id.historyContainer)
        container.removeAllViews()
        val entries = AppDatabase.getDatabase(this).ticketHistoryDao().getForTicket(ticketId)
        if (entries.isEmpty()) {
            container.addView(TextView(this).apply {
                text = getString(R.string.history_empty)
                setTextColor(getColor(R.color.text_muted))
                textSize = 13f
            })
            return
        }
        entries.forEach { entry ->
            container.addView(TextView(this).apply {
                text = "${entry.createdAt} · ${entry.actorName}\n${TicketHistoryLogger.describe(this@TicketDetailActivity, entry)}"
                setTextColor(getColor(R.color.text_muted))
                textSize = 13f
                setPadding(0, 0, 0, 14)
            })
        }
    }

    private fun loadComments() {
        val db = AppDatabase.getDatabase(this)
        val commentsFromDb = db.commentDao().getCommentsForTicket(ticketId)
        val displayComments = commentsFromDb.map { comment ->
            Comment(comment.id, comment.ticketId, comment.userId, comment.userName, comment.content, comment.created_at)
        }
        commentsRecyclerView.adapter = CommentAdapter(displayComments)
    }
}
