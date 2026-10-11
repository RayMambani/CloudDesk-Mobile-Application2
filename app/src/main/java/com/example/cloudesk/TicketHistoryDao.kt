package com.example.cloudesk

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TicketHistoryDao {
    @Query("SELECT * FROM ticket_history WHERE ticketId = :ticketId ORDER BY id DESC")
    fun getForTicket(ticketId: String): List<TicketHistoryEntity>

    @Insert
    fun insert(entry: TicketHistoryEntity)

    @Query("SELECT COUNT(*) FROM ticket_history")
    fun count(): Int
}
