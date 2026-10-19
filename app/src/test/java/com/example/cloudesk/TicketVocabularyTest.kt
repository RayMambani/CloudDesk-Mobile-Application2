package com.example.cloudesk

import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/** Tickets store English keys; the UI translates them. These must stay in sync. */
@RunWith(RobolectricTestRunner::class)
class TicketVocabularyTest {

    private val context = ApplicationProvider.getApplicationContext<android.content.Context>()

    @Test fun keysAreTheCanonicalEnglishValues() {
        assertEquals(listOf("low", "medium", "high", "critical"), TicketVocabulary.PRIORITY_KEYS)
        assertEquals(listOf("open", "progress", "resolved", "closed"), TicketVocabulary.STATUS_KEYS)
        assertEquals(listOf("hardware", "software", "network", "account/access", "other"), TicketVocabulary.CATEGORY_KEYS)
    }

    @Test fun everyKeyHasATranslation() {
        TicketVocabulary.PRIORITY_KEYS.forEach { assertTrue(TicketVocabulary.priorityDisplay(context, it).isNotBlank()) }
        TicketVocabulary.STATUS_KEYS.forEach { assertTrue(TicketVocabulary.statusDisplay(context, it).isNotBlank()) }
        TicketVocabulary.CATEGORY_KEYS.forEach { assertTrue(TicketVocabulary.categoryDisplay(context, it).isNotBlank()) }
    }

    @Test fun displayAndReverseLookupRoundTrip() {
        TicketVocabulary.PRIORITY_KEYS.forEach {
            assertEquals(it, TicketVocabulary.priorityKeyFromDisplay(context, TicketVocabulary.priorityDisplay(context, it)))
        }
        TicketVocabulary.STATUS_KEYS.forEach {
            assertEquals(it, TicketVocabulary.statusKeyFromDisplay(context, TicketVocabulary.statusDisplay(context, it)))
        }
        TicketVocabulary.CATEGORY_KEYS.forEach {
            assertEquals(it, TicketVocabulary.categoryKeyFromDisplay(context, TicketVocabulary.categoryDisplay(context, it)))
        }
    }

    @Test fun unknownValuesPassThrough() {
        assertEquals("weird", TicketVocabulary.statusDisplay(context, "weird"))
    }

    @Test fun dropdownListsMatchKeyCounts() {
        assertEquals(4, TicketVocabulary.priorityDisplayList(context).size)
        assertEquals(4, TicketVocabulary.statusDisplayList(context).size)
        assertEquals(5, TicketVocabulary.categoryDisplayList(context).size)
    }

    @Test fun aiPrioritiesAndSlaCoverTheSameKeys() {
        assertEquals(TicketVocabulary.PRIORITY_KEYS, TicketPriorityAI.Priority.values().map { it.label })
        TicketVocabulary.PRIORITY_KEYS.forEach { assertTrue(SlaHelper.slaHours(it) > 0) }
    }
}
