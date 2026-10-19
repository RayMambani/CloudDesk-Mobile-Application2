package com.example.cloudesk

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/** The AI triage engine: keyword scoring decides the suggested ticket priority. */
class TicketPriorityAITest {

    @Test fun outageIsCritical() {
        val r = TicketPriorityAI.analyze("Server outage", "production server down for all users", "network")
        assertEquals(TicketPriorityAI.Priority.CRITICAL, r.priority)
        assertTrue(r.matchedSignals.contains("outage"))
    }

    @Test fun cannotLoginIsHigh() {
        val r = TicketPriorityAI.analyze("Cannot log in", "unable to open email", "software")
        assertEquals(TicketPriorityAI.Priority.HIGH, r.priority)
    }

    @Test fun urgentCategoryBoostsScore() {
        val r = TicketPriorityAI.analyze("Network issue", "intermittent bug", "network")
        assertEquals(TicketPriorityAI.Priority.HIGH, r.priority)
    }

    @Test fun minorGlitchIsMedium() {
        val r = TicketPriorityAI.analyze("Printer glitch", "wrong colours show up", "hardware")
        assertEquals(TicketPriorityAI.Priority.MEDIUM, r.priority)
    }

    @Test fun questionIsLow() {
        val r = TicketPriorityAI.analyze("How to change wallpaper", "just a question, no rush", "other")
        assertEquals(TicketPriorityAI.Priority.LOW, r.priority)
    }

    @Test fun matchingIgnoresCase() {
        val r = TicketPriorityAI.analyze("URGENT", "", "other")
        assertTrue(r.matchedSignals.contains("urgent"))
    }

    @Test fun urgentToneAddsSignal() {
        val r = TicketPriorityAI.analyze("Printer jam", "Please help!!", "hardware")
        assertTrue(r.matchedSignals.contains("! ! (urgent tone)"))
        assertEquals(TicketPriorityAI.Priority.MEDIUM, r.priority)
    }

    @Test fun allCapsTitleAddsSignal() {
        val r = TicketPriorityAI.analyze("PRINTER JAMMED AGAIN", "", "hardware")
        assertTrue(r.matchedSignals.contains("ALL CAPS TITLE"))
    }

    @Test fun confidenceStaysInRange() {
        val none = TicketPriorityAI.analyze("hello", "there", "other")
        assertEquals(55, none.confidence)
        val many = TicketPriorityAI.analyze(
            "outage", "down breach hacked security leak production urgent asap emergency crashed unusable locked out", "network"
        )
        assertEquals(96, many.confidence)
    }

    @Test fun rankOrdersByUrgency() {
        assertTrue(TicketPriorityAI.priorityRank("critical") > TicketPriorityAI.priorityRank("high"))
        assertTrue(TicketPriorityAI.priorityRank("high") > TicketPriorityAI.priorityRank("medium"))
        assertTrue(TicketPriorityAI.priorityRank("medium") > TicketPriorityAI.priorityRank("low"))
        assertEquals(1, TicketPriorityAI.priorityRank("something-else"))
        assertEquals(3, TicketPriorityAI.priorityRank("CRITICAL"))
    }
}
