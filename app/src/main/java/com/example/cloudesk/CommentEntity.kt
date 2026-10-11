package com.example.cloudesk

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ticketId: String,
    val userId: Int,
    val userName: String,
    val content: String,
    val created_at: String
)
