package com.example.cloudesk

import android.content.Context
import android.content.SharedPreferences

/**
 * Small wrapper around SharedPreferences that tracks who is currently logged in.
 * Used so Logout / Switch User can fully clear state and always land back on
 * the Login screen, regardless of which Activity the user is currently on.
 */
object SessionManager {
    private const val PREFS_NAME = "cloudesk_session"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_ROLE = "user_role"
    private const val KEY_USER_EMAIL = "user_email"

    private fun prefs(context: Context): SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveSession(context: Context, userId: Int, userName: String, userRole: String, email: String) {
        prefs(context).edit()
            .putInt(KEY_USER_ID, userId)
            .putString(KEY_USER_NAME, userName)
            .putString(KEY_USER_ROLE, userRole)
            .putString(KEY_USER_EMAIL, email)
            .apply()
    }

    fun isLoggedIn(context: Context): Boolean = getUserId(context) != -1

    fun getUserId(context: Context): Int = prefs(context).getInt(KEY_USER_ID, -1)

    fun getUserName(context: Context): String = prefs(context).getString(KEY_USER_NAME, "User") ?: "User"

    fun getUserRole(context: Context): String = prefs(context).getString(KEY_USER_ROLE, "user") ?: "user"

    fun getUserEmail(context: Context): String = prefs(context).getString(KEY_USER_EMAIL, "") ?: ""

    /** Clears the active session. Used by both Logout and Switch User. */
    fun clearSession(context: Context) {
        prefs(context).edit().clear().apply()
    }
}
