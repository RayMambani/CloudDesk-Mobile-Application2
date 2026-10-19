package com.example.cloudesk

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TicketDao {
    @Query("SELECT * FROM tickets ORDER BY created_at DESC")
    fun getAllTickets(): List<TicketEntity>

    @Query("SELECT * FROM tickets WHERE id = :id LIMIT 1")
    fun getTicketById(id: String): TicketEntity?

    @Query("DELETE FROM tickets")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTickets(tickets: List<TicketEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTicket(ticket: TicketEntity)
}
