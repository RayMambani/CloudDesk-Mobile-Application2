package com.example.cloudesk

import java.util.Date
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/** Service-level deadlines: critical 4h, high 24h, medium 72h, low 120h. */
class SlaHelperTest {

    private fun ticket(priority: String, status: String = "open", created: String = "2026-01-01 08:00") = TicketEntity(
        id = "TCK-T", title = "t", description = "d", status = status, priority = priority,
        category = "other", created_at = created, userId = 1
    )

    private fun hoursAfterCreation(t: TicketEntity, hours: Long) =
        Date(SlaHelper.parseCreated(t.created_at)!!.time + hours * 3_600_000L)

    @Test fun targetsPerPriority() {
        assertEquals(4, SlaHelper.slaHours("critical"))
        assertEquals(24, SlaHelper.slaHours("high"))
        assertEquals(72, SlaHelper.slaHours("medium"))
        assertEquals(120, SlaHelper.slaHours("low"))
        assertEquals(120, SlaHelper.slaHours("unknown"))
        assertEquals(4, SlaHelper.slaHours("CRITICAL"))
    }

    @Test fun parsesBothDateFormats() {
        assertNotNull(SlaHelper.parseCreated("2026-01-01 08:00"))
        assertNotNull(SlaHelper.parseCreated("2026-01-01"))
        assertNull(SlaHelper.parseCreated("not a date"))
    }

    @Test fun dueLabelAddsTheTarget() {
        assertEquals("2026-01-01 12:00", SlaHelper.dueLabel(ticket("critical")))
        assertEquals("2026-01-02 08:00", SlaHelper.dueLabel(ticket("high")))
    }

    @Test fun openTicketBecomesOverdueAfterDeadline() {
        val t = ticket("critical")
        assertFalse(SlaHelper.isOverdue(t, hoursAfterCreation(t, 3)))
        assertTrue(SlaHelper.isOverdue(t, hoursAfterCreation(t, 5)))
    }

    @Test fun inProgressCanBeOverdue() {
        val t = ticket("high", status = "progress")
        assertTrue(SlaHelper.isOverdue(t, hoursAfterCreation(t, 25)))
    }

    @Test fun resolvedAndClosedAreNeverOverdue() {
        for (status in listOf("resolved", "closed")) {
            val t = ticket("critical", status = status)
            assertFalse(SlaHelper.isOverdue(t, hoursAfterCreation(t, 500)))
        }
    }

    @Test fun badDateIsNotOverdue() {
        val t = ticket("critical", created = "garbage")
        assertNull(SlaHelper.dueDate(t))
        assertNull(SlaHelper.dueLabel(t))
        assertFalse(SlaHelper.isOverdue(t))
    }
}
