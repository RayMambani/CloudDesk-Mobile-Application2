package com.example.cloudesk

import android.content.Context

/**
 * Bridges between the app's canonical, always-English stored values (used in
 * every comparison throughout the app: sorting, filtering, AI scoring,
 * report grouping) and what gets displayed to the user in their chosen
 * language.
 *
 * IMPORTANT: status, priority, and category are stored in the database as
 * fixed English keys ("open", "high", "network", etc.) because business logic
 * elsewhere compares against those exact strings (e.g. TicketPriorityAI,
 * PerformanceReportActivity's resolved/closed checks, AI queue sorting).
 * Never store a localized display string in the database — always store the
 * canonical key from *_KEYS below, and only translate for display via the
 * *Display() functions.
 */
object TicketVocabulary {

    val PRIORITY_KEYS = listOf("low", "medium", "high", "critical")
    val CATEGORY_KEYS = listOf("hardware", "software", "network", "account/access", "other")
    val STATUS_KEYS = listOf("open", "progress", "resolved", "closed")

    fun priorityDisplay(context: Context, key: String): String = when (key.lowercase()) {
        "low" -> context.getString(R.string.priority_low)
        "medium" -> context.getString(R.string.priority_medium)
        "high" -> context.getString(R.string.priority_high)
        "critical" -> context.getString(R.string.priority_critical)
        else -> key
    }

    fun statusDisplay(context: Context, key: String): String = when (key.lowercase()) {
        "open" -> context.getString(R.string.status_open)
        "progress" -> context.getString(R.string.status_progress)
        "resolved" -> context.getString(R.string.status_resolved)
        "closed" -> context.getString(R.string.status_closed)
        else -> key
    }

    fun categoryDisplay(context: Context, key: String): String = when (key.lowercase()) {
        "hardware" -> context.getString(R.string.category_hardware)
        "software" -> context.getString(R.string.category_software)
        "network" -> context.getString(R.string.category_network)
        "account/access" -> context.getString(R.string.category_account_access)
        "other" -> context.getString(R.string.category_other)
        else -> key
    }

    fun priorityDisplayList(context: Context): Array<String> =
        PRIORITY_KEYS.map { priorityDisplay(context, it) }.toTypedArray()

    fun categoryDisplayList(context: Context): Array<String> =
        CATEGORY_KEYS.map { categoryDisplay(context, it) }.toTypedArray()

    fun statusDisplayList(context: Context): Array<String> =
        STATUS_KEYS.map { statusDisplay(context, it) }.toTypedArray()

    /** Reverse lookup: what the user sees in a dropdown -> the canonical English key to store. */
    fun priorityKeyFromDisplay(context: Context, display: String): String =
        PRIORITY_KEYS.firstOrNull { priorityDisplay(context, it).equals(display, ignoreCase = true) }
            ?: display.lowercase()

    fun categoryKeyFromDisplay(context: Context, display: String): String =
        CATEGORY_KEYS.firstOrNull { categoryDisplay(context, it).equals(display, ignoreCase = true) }
            ?: display.lowercase()

    fun statusKeyFromDisplay(context: Context, display: String): String =
        STATUS_KEYS.firstOrNull { statusDisplay(context, it).equals(display, ignoreCase = true) }
            ?: display.lowercase()
}
