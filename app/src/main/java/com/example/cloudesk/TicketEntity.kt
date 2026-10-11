package com.example.cloudesk

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tickets")
data class TicketEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val status: String,
    val priority: String,
    val category: String,
    val created_at: String,
    val userId: Int,
    val assignedTo: String? = null,
    val attachmentUri: String? = null,
    val attachmentName: String? = null,
    val resolvedAt: String? = null,
    val rating: Int? = null,            // 1..5, given by the ticket owner after resolution
    val ratingComment: String? = null
)
