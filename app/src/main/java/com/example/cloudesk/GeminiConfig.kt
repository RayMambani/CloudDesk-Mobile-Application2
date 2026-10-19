package com.example.cloudesk

/**
 * Gemini API configuration.
 *
 * IMPORTANT — different security situation than the website version: this is a
 * compiled Android app, not a server. Anything in here (including this API key)
 * gets bundled into the APK and CAN be extracted by anyone who decompiles it —
 * there's no way to truly hide a secret inside a client-side app. That's fine
 * for a student/demo project, but if this app were ever published publicly,
 * the right approach is to route Gemini calls through your own small backend
 * (so the key lives on a server you control, never on the device) rather than
 * calling Gemini directly from the app as done here.
 *
 * Get a free key: https://aistudio.google.com/apikey
 */
object GeminiConfig {
    const val API_KEY = "AQ.Ab8RN6Is_PJtVw7I8MYLXRHb8_zCGB2RRUvoSM2ZpxL8dDYKBg"

    // If you ever get a "model not found" error, Google has likely renamed or
    // retired this one — check https://ai.google.dev/gemini-api/docs/models
    // and update this value.
    const val MODEL = "gemini-3.8-flash"
}
