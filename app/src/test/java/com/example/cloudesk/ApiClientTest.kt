package com.example.cloudesk

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.ByteArrayOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import kotlin.concurrent.thread

/** Runs ApiClient against a tiny local HTTP server (plain sockets) that imitates api.php. */
@RunWith(RobolectricTestRunner::class)
class ApiClientTest {
    private lateinit var server: ServerSocket
    private lateinit var context: Context
    @Volatile private var lastBody = ""
    @Volatile private var lastToken: String? = null
    @Volatile private var reply = """{"ok":true}"""
    @Volatile private var status = 200

    private val url get() = "http://127.0.0.1:${server.localPort}/api.php"

    @Before fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        server = ServerSocket(0, 50, InetAddress.getByName("127.0.0.1"))
        thread(isDaemon = true) {
            while (!server.isClosed) {
                try {
                    val socket = server.accept()
                    socket.use { s ->
                        val input = s.getInputStream()
                        val head = ByteArrayOutputStream()
                        var last4 = 0
                        while (true) {
                            val b = input.read()
                            if (b < 0) break
                            head.write(b)
                            last4 = (last4 shl 8) or b
                            if (last4 == 0x0D0A0D0A) break
                        }
                        var length = 0
                        lastToken = null
                        for (line in head.toString("UTF-8").split("\r\n")) {
                            val i = line.indexOf(':')
                            if (i > 0) {
                                val name = line.substring(0, i).trim().lowercase()
                                val value = line.substring(i + 1).trim()
                                if (name == "content-length") length = value.toIntOrNull() ?: 0
                                if (name == "x-auth-token") lastToken = value
                            }
                        }
                        val body = ByteArray(length)
                        var read = 0
                        while (read < length) {
                            val n = input.read(body, read, length - read)
                            if (n < 0) break
                            read += n
                        }
                        lastBody = String(body, 0, read, Charsets.UTF_8)
                        val bytes = reply.toByteArray(Charsets.UTF_8)
                        val out = s.getOutputStream()
                        out.write("HTTP/1.1 $status X\r\nContent-Type: application/json\r\nContent-Length: ${bytes.size}\r\nConnection: close\r\n\r\n".toByteArray(Charsets.UTF_8))
                        out.write(bytes)
                        out.flush()
                    }
                } catch (e: Exception) {
                    // the server was closed, or a connection was dropped
                }
            }
        }
    }

    @After fun tearDown() {
        try { server.close() } catch (e: Exception) { }
        SessionManager.clearSession(context)
    }

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
        reply = """{"ok":false,"error":"invalid_credentials"}"""
        status = 401
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
        val dead = url
        server.close()
        val r = ApiClient.call(context, "ping", baseUrl = dead)
        assertTrue(r.networkError)
    }
}