package com.example.cloudesk

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,          // PBKDF2 hash (see PasswordHasher)
    val department: String,
    val role: String,
    val phone: String? = null,
    val active: Boolean = true,    // admins can deactivate accounts
    val securityQuestion: String? = null,      // key, see SecurityQuestions
    val securityAnswerHash: String? = null     // hashed, lower-cased answer
)
