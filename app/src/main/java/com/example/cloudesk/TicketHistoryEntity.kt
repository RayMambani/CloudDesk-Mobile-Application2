package com.example.cloudesk

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Audit trail of what happened to a ticket. `field` is a fixed key ("created", "status",
 * "assignee", "rating") and the values are canonical keys, so the screen can show them in
 * whichever language the user has chosen.
 */
@Entity(tableName = "ticket_history")
data class TicketHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ticketId: String,
    val actorName: String,
    val field: String,
    val oldValue: String? = null,
    val newValue: String? = null,
    val createdAt: String
)
