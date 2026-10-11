package com.example.cloudesk

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,          // the end-user this support conversation belongs to
    val senderName: String,
    val message: String,
    val fromSupport: Boolean, // true = support agent/bot, false = the user
    val timestamp: String
)
