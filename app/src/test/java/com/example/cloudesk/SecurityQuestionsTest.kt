package com.example.cloudesk

import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/** "Forgot password": the answer is normalised, hashed, and checked case-insensitively. */
@RunWith(RobolectricTestRunner::class)
class SecurityQuestionsTest {

    private val context = ApplicationProvider.getApplicationContext<android.content.Context>()

    @Test fun fourQuestionsAvailable() {
        assertEquals(listOf("colour", "pet", "city", "school"), SecurityQuestions.KEYS)
        assertEquals(4, SecurityQuestions.displayList(context).size)
    }

    @Test fun answersAreNormalised() {
        assertEquals("blue", SecurityQuestions.normalizeAnswer("  BLUE "))
    }

    @Test fun answerCheckIgnoresCaseAndSpaces() {
        val stored = SecurityQuestions.hashAnswer("blue")
        assertTrue(SecurityQuestions.checkAnswer("Blue", stored))
        assertTrue(SecurityQuestions.checkAnswer("  BLUE  ", stored))
        assertFalse(SecurityQuestions.checkAnswer("green", stored))
    }

    @Test fun missingStoredAnswerNeverMatches() {
        assertFalse(SecurityQuestions.checkAnswer("blue", null))
    }

    @Test fun storedValueIsHashedNotPlain() {
        assertTrue(PasswordHasher.isHashed(SecurityQuestions.hashAnswer("blue")))
    }

    @Test fun displayTextMapsBackToTheKey() {
        for (key in SecurityQuestions.KEYS) {
            val text = SecurityQuestions.display(context, key)
            assertEquals(key, SecurityQuestions.keyFromDisplay(context, text.uppercase()))
        }
        assertNull(SecurityQuestions.keyFromDisplay(context, "no such question"))
    }
}
