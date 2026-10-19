package com.example.cloudesk

/**
 * Where the online backend lives. CloudDesk's accounts, tickets, comments, history and
 * chat are stored in the ONLINE database behind the website's api.php (hosted on Render).
 * The app keeps a local copy (Room) only so screens load instantly and work read-only offline.
 *
 * 1. Deploy the website to Render (see the website README) and paste its address below.
 * 2. Paste the Google *Web application* OAuth client ID (the same one the website uses)
 *    so "Sign in with Google" can be verified by the server.
 *
 * Testing on the Android emulator against XAMPP on your PC: http://10.0.2.2/cloudDeskWeb/
 */
object ApiConfig {
    const val BASE_URL = "https://clouddesk-web-application.onrender.com/"
    const val GOOGLE_WEB_CLIENT_ID = "350970218855-954p280oiad65e2mltpgkhhmfgjtlefb.apps.googleusercontent.com.apps.googleusercontent.comapps.googleusercontent.com.apps.googleusercontent.com"

    val API_URL: String get() = BASE_URL.trimEnd('/') + "/api.php"

    fun isConfigured(): Boolean = !BASE_URL.contains("YOUR-SERVICE")
    fun isGoogleConfigured(): Boolean = !GOOGLE_WEB_CLIENT_ID.startsWith("YOUR_WEB_CLIENT_ID")
}
