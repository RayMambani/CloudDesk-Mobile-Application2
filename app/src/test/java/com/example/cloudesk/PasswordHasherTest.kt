package com.example.cloudesk

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/** Runs under Robolectric because the hasher uses android.util.Base64. */
@RunWith(RobolectricTestRunner::class)
class PasswordHasherTest {

    @Test fun hashHasExpectedFormat() {
        val h = PasswordHasher.hash("secret123")
        assertTrue(h.startsWith("pbkdf2\$10000\$"))
        assertEquals(4, h.split("\$").size)
        assertTrue(PasswordHasher.isHashed(h))
        assertFalse(PasswordHasher.needsUpgrade(h))
    }

    @Test fun correctPasswordVerifies() {
        assertTrue(PasswordHasher.verify("secret123", PasswordHasher.hash("secret123")))
    }

    @Test fun wrongPasswordFails() {
        assertFalse(PasswordHasher.verify("secret124", PasswordHasher.hash("secret123")))
        assertFalse(PasswordHasher.verify("", PasswordHasher.hash("secret123")))
    }

    @Test fun sameInputGivesDifferentHashesBecauseOfSalt() {
        assertNotEquals(PasswordHasher.hash("secret123"), PasswordHasher.hash("secret123"))
    }

    @Test fun legacyPlainTextAccountsStillWorkAndAreFlagged() {
        assertTrue(PasswordHasher.needsUpgrade("oldpassword"))
        assertTrue(PasswordHasher.verify("oldpassword", "oldpassword"))
        assertFalse(PasswordHasher.verify("other", "oldpassword"))
    }

    @Test fun malformedHashesNeverVerify() {
        assertFalse(PasswordHasher.verify("x", "pbkdf2\$only-two"))
        assertFalse(PasswordHasher.verify("x", "pbkdf2\$notanumber\$abc\$def"))
    }
}
