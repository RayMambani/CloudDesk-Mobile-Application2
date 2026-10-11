package com.example.cloudesk

import android.content.Context

/** Helpers for writing and describing ticket history entries. */
object TicketHistoryLogger {
    fun now(): String =
        java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(java.util.Date())

    fun log(db: AppDatabase, ticketId: String, actor: String, field: String, old: String? = null, new: String? = null) {
        db.ticketHistoryDao().insert(
            TicketHistoryEntity(ticketId = ticketId, actorName = actor, field = field, oldValue = old, newValue = new, createdAt = now())
        )
    }

    /** Human-readable, localized description of one history entry. */
    fun describe(context: Context, e: TicketHistoryEntity): String = when (e.field) {
        "created" -> context.getString(R.string.history_created)
        "status" -> context.getString(
            R.string.history_status_format,
            TicketVocabulary.statusDisplay(context, e.oldValue ?: ""),
            TicketVocabulary.statusDisplay(context, e.newValue ?: "")
        )
        "assignee" -> if (e.newValue.isNullOrBlank()) context.getString(R.string.history_unassigned)
                      else context.getString(R.string.history_assigned_format, e.newValue)
        "rating" -> context.getString(R.string.history_rated_format, e.newValue ?: "")
        else -> e.field
    }
}
