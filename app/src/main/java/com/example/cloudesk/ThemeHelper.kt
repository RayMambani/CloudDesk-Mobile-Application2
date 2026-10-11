package com.example.cloudesk

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

/** Stores and applies the user's Light/Dark choice. Default is Dark (the original blue look). */
object ThemeHelper {
    private const val PREFS = "cloudesk_theme"
    private const val KEY = "theme_mode"
    const val MODE_LIGHT = "light"
    const val MODE_DARK = "dark"

    fun getThemeMode(context: Context): String =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY, MODE_DARK) ?: MODE_DARK

    fun setThemeMode(context: Context, mode: String) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putString(KEY, mode).apply()
        applyTheme(context)
    }

    fun applyTheme(context: Context) {
        AppCompatDelegate.setDefaultNightMode(
            if (getThemeMode(context) == MODE_LIGHT) AppCompatDelegate.MODE_NIGHT_NO
            else AppCompatDelegate.MODE_NIGHT_YES
        )
    }
}
