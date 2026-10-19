package com.example.cloudesk

import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/** Who is signed in: saved on login, wiped on logout / switch user. */
@RunWith(RobolectricTestRunner::class)
class SessionManagerTest {

    private val context = ApplicationProvider.getApplicationContext<android.content.Context>()

    @Test fun startsLoggedOutWithSafeDefaults() {
        SessionManager.clearSession(context)
        assertFalse(SessionManager.isLoggedIn(context))
        assertEquals(-1, SessionManager.getUserId(context))
        assertEquals("user", SessionManager.getUserRole(context))
    }

    @Test fun saveThenReadBack() {
        SessionManager.saveSession(context, 7, "Sarah Naidoo", "admin", "admin@clouddesk.com")
        assertTrue(SessionManager.isLoggedIn(context))
        assertEquals(7, SessionManager.getUserId(context))
        assertEquals("Sarah Naidoo", SessionManager.getUserName(context))
        assertEquals("admin", SessionManager.getUserRole(context))
        assertEquals("admin@clouddesk.com", SessionManager.getUserEmail(context))
    }

    @Test fun logoutClearsEverything() {
        SessionManager.saveSession(context, 7, "Sarah Naidoo", "admin", "admin@clouddesk.com")
        SessionManager.clearSession(context)
        assertFalse(SessionManager.isLoggedIn(context))
        assertEquals("", SessionManager.getUserEmail(context))
    }
}
