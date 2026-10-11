package com.example.cloudesk

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments WHERE ticketId = :ticketId ORDER BY created_at ASC")
    fun getCommentsForTicket(ticketId: String): List<CommentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(comment: CommentEntity)
}
