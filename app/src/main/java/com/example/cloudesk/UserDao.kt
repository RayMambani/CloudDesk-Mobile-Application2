package com.example.cloudesk

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE lower(email) = lower(:email) LIMIT 1")
    fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    fun getUserById(id: Int): UserEntity?

    @Query("SELECT * FROM users WHERE role = :role")
    fun getUsersByRole(role: String): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity): Long

    @Update
    fun updateUser(user: UserEntity)

    @Query("DELETE FROM users")
    fun deleteAll()

    @Query("SELECT * FROM users ORDER BY role, firstName")
    fun getAllUsers(): List<UserEntity>
}
