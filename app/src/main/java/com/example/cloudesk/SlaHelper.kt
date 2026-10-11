package com.example.cloudesk

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Service-level targets: how long each priority is allowed to stay unresolved.
 * Critical 4h, High 24h, Medium 72h, Low 120h.
 */
object SlaHelper {
    fun slaHours(priority: String): Int = when (priority.lowercase()) {
        "critical" -> 4
        "high" -> 24
        "medium" -> 72
        else -> 120
    }

    fun parseCreated(created: String): Date? {
        for (pattern in listOf("yyyy-MM-dd HH:mm", "yyyy-MM-dd")) {
            try {
                return SimpleDateFormat(pattern, Locale.getDefault()).parse(created)
            } catch (e: Exception) { /* try next format */ }
        }
        return null
    }

    fun dueDate(ticket: TicketEntity): Date? {
        val created = parseCreated(ticket.created_at) ?: return null
        return Date(created.time + slaHours(ticket.priority) * 3_600_000L)
    }

    fun isOverdue(ticket: TicketEntity, now: Date = Date()): Boolean {
        if (ticket.status != "open" && ticket.status != "progress") return false
        val due = dueDate(ticket) ?: return false
        return now.after(due)
    }

    fun dueLabel(ticket: TicketEntity): String? {
        val due = dueDate(ticket) ?: return null
        return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(due)
    }
}
