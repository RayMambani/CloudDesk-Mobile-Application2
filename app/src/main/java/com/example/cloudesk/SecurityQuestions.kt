package com.example.cloudesk

import android.content.Context

/** Security questions used by "Forgot password". The key is stored, the text is localized. */
object SecurityQuestions {
    val KEYS = listOf("colour", "pet", "city", "school")

    fun display(context: Context, key: String): String = when (key) {
        "colour" -> context.getString(R.string.sq_colour)
        "pet" -> context.getString(R.string.sq_pet)
        "city" -> context.getString(R.string.sq_city)
        "school" -> context.getString(R.string.sq_school)
        else -> key
    }

    fun displayList(context: Context): Array<String> = KEYS.map { display(context, it) }.toTypedArray()

    fun keyFromDisplay(context: Context, text: String): String? =
        KEYS.firstOrNull { display(context, it).equals(text.trim(), ignoreCase = true) }

    fun normalizeAnswer(answer: String): String = answer.trim().lowercase()

    fun hashAnswer(answer: String): String = PasswordHasher.hash(normalizeAnswer(answer))

    fun checkAnswer(answer: String, stored: String?): Boolean =
        stored != null && PasswordHasher.verify(normalizeAnswer(answer), stored)
}
