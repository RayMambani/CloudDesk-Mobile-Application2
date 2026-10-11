package com.example.cloudesk

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TicketEntity::class, UserEntity::class, CommentEntity::class, ChatMessageEntity::class, TicketHistoryEntity::class], version = 8)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
    abstract fun userDao(): UserDao
    abstract fun commentDao(): CommentDao
    abstract fun chatMessageDao(): ChatMessageDao
    abstract fun ticketHistoryDao(): TicketHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cloudesk_database"
                )
                .allowMainThreadQueries() // Allowed for simplicity in this generated project
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
