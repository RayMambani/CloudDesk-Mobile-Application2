package com.example.cloudesk

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

/**
 * Persists the user's chosen app language and applies it to any Context.
 *
 * Usage: every Activity should extend BaseActivity (which calls wrapContext in
 * attachBaseContext) so the saved language is applied before that screen's views
 * inflate. Change the language via setLocale() + recreate()/restart the activity.
 */
object LocaleHelper {
    private const val PREFS_NAME = "cloudesk_locale"
    private const val KEY_LANGUAGE = "language"
    const val DEFAULT_LANGUAGE = "en"

    // Supported languages. Codes match the values-<code> resource folders.
    // Sepedi has no ISO 639-1 two-letter code, so its folder uses the BCP-47
    // qualifier form: values-b+nso (supported since minSdk 24 here).
    val SUPPORTED_LANGUAGES = linkedMapOf(
        "en" to "English",
        "nso" to "Sepedi",
        "ve" to "Tshivenda",
        "ts" to "Xitsonga"
    )

    fun getLanguage(context: Context): String {
        val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
    }

    fun setLocale(context: Context, languageCode: String) {
        val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LANGUAGE, languageCode).apply()
    }

    /** Wraps a Context so its resources resolve using the saved language. */
    fun wrapContext(context: Context): Context {
        val languageCode = getLanguage(context)
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
            context.createConfigurationContext(config)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
            @Suppress("DEPRECATION")
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            context
        }
    }
}
