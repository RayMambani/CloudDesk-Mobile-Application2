package com.example.cloudesk

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.sun.net.httpserver.HttpServer
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.net.InetSocketAddress

/** Runs ApiClient against a tiny local HTTP server that imitates api.php. */
@RunWith(RobolectricTestRunner::class)
class ApiClientTest {
    private lateinit var server: HttpServer
    private lateinit var context: Context
    private var lastBody = ""
    private var lastToken: String? = null
    private var reply = """{"ok":true}"""
    private var status = 200

    private val url get() = "http://127.0.0.1:${server.address.port}/api.php"

    @Before fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        server = HttpServer.create(InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/api.php") { ex ->
            lastBody = ex.requestBody.readBytes().toString(Charsets.UTF_8)
            lastToken = ex.requestHeaders.getFirst("X-Auth-Token")
            val bytes = reply.toByteArray()
            ex.sendResponseHeaders(status, bytes.size.toLong())
            ex.responseBody.use { it.write(bytes) }
        }
        server.start()
    }

    @After fun tearDown() { server.stop(0); SessionManager.clearSession(context) }

    @Test fun sendsActionAndToken() {
        SessionManager.saveToken(context, "abc123")
        val r = ApiClient.call(context, "sync", baseUrl = url)
        assertTrue(r.ok)
        assertEquals("sync", JSONObject(lastBody).getString("action"))
        assertEquals("abc123", lastToken)
    }

    @Test fun noTokenWhenNotAuthenticated() {
        SessionManager.saveToken(context, "abc123")
        ApiClient.call(context, "login", authenticated = false, baseUrl = url)
        assertNull(lastToken)
    }

    @Test fun mapsServerError() {
        reply = """{"ok":false,"error":"invalid_credentials"}"""; status = 401
        val r = ApiClient.call(context, "login", authenticated = false, baseUrl = url)
        assertFalse(r.ok)
        assertEquals("invalid_credentials", r.error)
        assertEquals(401, r.httpCode)
    }

    @Test fun badJsonIsBadResponse() {
        reply = "<html>oops</html>"
        assertEquals("bad_response", ApiClient.call(context, "ping", baseUrl = url).error)
    }

    @Test fun networkErrorWhenServerDown() {
        val dead = url; server.stop(0)
        val r = ApiClient.call(context, "ping", baseUrl = dead)
        assertTrue(r.networkError)
    }

    @Test fun notConfiguredByDefault() {
        assertEquals("not_configured", ApiClient.call(context, "ping").error)
    }
}
