package com.example.cloudesk

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.TimeUnit

/** Runs ApiClient against a tiny local HTTP server that imitates api.php. */
@RunWith(RobolectricTestRunner::class)
class ApiClientTest {
    private lateinit var server: MockWebServer
    private lateinit var context: Context

    private val url get() = server.url("/api.php").toString()

    @Before fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        server = MockWebServer()
        server.start()
    }

    @After fun tearDown() {
        runCatching { server.shutdown() } // some tests shut it down themselves
        SessionManager.clearSession(context)
    }

    private fun reply(body: String, code: Int = 200) {
        server.enqueue(MockResponse().setResponseCode(code).setBody(body))
    }

    @Test fun sendsActionAndToken() {
        reply("""{"ok":true}""")
        SessionManager.saveToken(context, "abc123")
        val r = ApiClient.call(context, "sync", baseUrl = url)
        assertTrue(r.ok)
        val request = server.takeRequest(5, TimeUnit.SECONDS)!!
        assertEquals("sync", JSONObject(request.body.readUtf8()).getString("action"))
        assertEquals("abc123", request.getHeader("X-Auth-Token"))
    }

    @Test fun noTokenWhenNotAuthenticated() {
        reply("""{"ok":true}""")
        SessionManager.saveToken(context, "abc123")
        ApiClient.call(context, "login", authenticated = false, baseUrl = url)
        val request = server.takeRequest(5, TimeUnit.SECONDS)!!
        assertNull(request.getHeader("X-Auth-Token"))
    }

    @Test fun mapsServerError() {
        reply("""{"ok":false,"error":"invalid_credentials"}""", 401)
        val r = ApiClient.call(context, "login", authenticated = false, baseUrl = url)
        assertFalse(r.ok)
        assertEquals("invalid_credentials", r.error)
        assertEquals(401, r.httpCode)
    }

    @Test fun badJsonIsBadResponse() {
        reply("<html>oops</html>")
        assertEquals("bad_response", ApiClient.call(context, "ping", baseUrl = url).error)
    }

    @Test fun networkErrorWhenServerDown() {
        val dead = url
        server.shutdown()
        val r = ApiClient.call(context, "ping", baseUrl = dead)
        assertTrue(r.networkError)
    }

    @Test fun placeholderUrlCountsAsNotConfigured() {
        assertFalse(ApiConfig.isConfigured("https://clouddesk-web-application.onrender.com/"))
        assertTrue(ApiConfig.isConfigured())
    }
}