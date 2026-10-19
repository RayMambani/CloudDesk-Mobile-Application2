package com.example.cloudesk

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatMessageDao {
    @Query("SELECT * FROM chat_messages WHERE userId = :userId ORDER BY id ASC")
    fun getConversation(userId: Int): List<ChatMessageEntity>

    @Query("DELETE FROM chat_messages")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: ChatMessageEntity): Long
}
