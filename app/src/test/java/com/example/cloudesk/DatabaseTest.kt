package com.example.cloudesk

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/** The Room database behind accounts, tickets, comments, history and chat (in-memory copy). */
@RunWith(RobolectricTestRunner::class)
class DatabaseTest {

    private lateinit var db: AppDatabase

    @Before fun open() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
    }

    @After fun close() { db.close() }

    private fun newUser(first: String, email: String, role: String = "user", active: Boolean = true) = UserEntity(
        firstName = first, lastName = "Test", email = email,
        password = PasswordHasher.hash("password123"), department = "IT", role = role, active = active
    )

    private fun newTicket(id: String, userId: Int, created: String = "2026-01-01 08:00", status: String = "open") = TicketEntity(
        id = id, title = "Title $id", description = "Desc", status = status, priority = "high",
        category = "software", created_at = created, userId = userId
    )

    // ---------- accounts ----------

    @Test fun registerThenLoginLookupIsCaseInsensitive() {
        db.userDao().insertUser(newUser("Grace", "Grace@Company.com"))
        val found = db.userDao().getUserByEmail("grace@company.COM")
        assertNotNull(found)
        assertTrue(PasswordHasher.verify("password123", found!!.password))
        assertNull(db.userDao().getUserByEmail("nobody@company.com"))
    }

    @Test fun usersCanBeFilteredByRole() {
        db.userDao().insertUser(newUser("Ann", "ann@x.com", "admin"))
        db.userDao().insertUser(newUser("Tom", "tom@x.com", "technician"))
        db.userDao().insertUser(newUser("Una", "una@x.com", "user"))
        assertEquals(listOf("Tom"), db.userDao().getUsersByRole("technician").map { it.firstName })
        assertEquals(3, db.userDao().getAllUsers().size)
    }

    @Test fun adminCanDeactivateAndChangeRoleAndResetPassword() {
        val id = db.userDao().insertUser(newUser("Una", "una@x.com")).toInt()
        val user = db.userDao().getUserById(id)!!
        assertTrue(user.active)
        db.userDao().updateUser(user.copy(active = false, role = "technician", password = PasswordHasher.hash("NewTemp99")))
        val updated = db.userDao().getUserById(id)!!
        assertFalse(updated.active)
        assertEquals("technician", updated.role)
        assertTrue(PasswordHasher.verify("NewTemp99", updated.password))
        assertFalse(PasswordHasher.verify("password123", updated.password))
    }

    @Test fun securityQuestionAnswerIsStoredHashed() {
        val id = db.userDao().insertUser(
            newUser("Una", "una@x.com").copy(securityQuestion = "pet", securityAnswerHash = SecurityQuestions.hashAnswer("Rex"))
        ).toInt()
        val u = db.userDao().getUserById(id)!!
        assertTrue(SecurityQuestions.checkAnswer("rex", u.securityAnswerHash))
        assertFalse(SecurityQuestions.checkAnswer("max", u.securityAnswerHash))
    }

    // ---------- tickets ----------

    @Test fun submittedTicketCanBeReadBack() {
        db.ticketDao().insertTicket(newTicket("TCK-1", userId = 1))
        val t = db.ticketDao().getTicketById("TCK-1")
        assertEquals("Title TCK-1", t?.title)
        assertNull(db.ticketDao().getTicketById("TCK-404"))
    }

    @Test fun newestTicketsComeFirst() {
        db.ticketDao().insertTicket(newTicket("TCK-1", 1, "2026-01-01 08:00"))
        db.ticketDao().insertTicket(newTicket("TCK-2", 1, "2026-03-01 08:00"))
        db.ticketDao().insertTicket(newTicket("TCK-3", 1, "2026-02-01 08:00"))
        assertEquals(listOf("TCK-2", "TCK-3", "TCK-1"), db.ticketDao().getAllTickets().map { it.id })
    }

    @Test fun technicianUpdateReplacesTheTicket() {
        db.ticketDao().insertTicket(newTicket("TCK-1", 1))
        val t = db.ticketDao().getTicketById("TCK-1")!!
        db.ticketDao().insertTicket(t.copy(status = "resolved", assignedTo = "John Mokoena", resolvedAt = "2026-01-02 09:00"))
        val after = db.ticketDao().getTicketById("TCK-1")!!
        assertEquals("resolved", after.status)
        assertEquals("John Mokoena", after.assignedTo)
        assertEquals(1, db.ticketDao().getAllTickets().size)
    }

    @Test fun ownerCanRateAResolvedTicket() {
        db.ticketDao().insertTicket(newTicket("TCK-1", 1, status = "resolved"))
        val t = db.ticketDao().getTicketById("TCK-1")!!
        db.ticketDao().insertTicket(t.copy(rating = 5, ratingComment = "Fast fix"))
        assertEquals(5, db.ticketDao().getTicketById("TCK-1")?.rating)
    }

    // ---------- comments, history, chat ----------

    @Test fun commentsAreScopedToTheirTicketAndOldestFirst() {
        db.commentDao().insertComment(CommentEntity(ticketId = "TCK-1", userId = 1, userName = "A", content = "second", created_at = "2026-01-01 10:00"))
        db.commentDao().insertComment(CommentEntity(ticketId = "TCK-1", userId = 2, userName = "B", content = "first", created_at = "2026-01-01 09:00"))
        db.commentDao().insertComment(CommentEntity(ticketId = "TCK-2", userId = 2, userName = "B", content = "other ticket", created_at = "2026-01-01 09:30"))
        assertEquals(listOf("first", "second"), db.commentDao().getCommentsForTicket("TCK-1").map { it.content })
        assertEquals(1, db.commentDao().getCommentsForTicket("TCK-2").size)
    }

    @Test fun historyRecordsChangesNewestFirst() {
        TicketHistoryLogger.log(db, "TCK-1", "Grace", "created")
        TicketHistoryLogger.log(db, "TCK-1", "John", "status", "open", "progress")
        TicketHistoryLogger.log(db, "TCK-2", "John", "created")
        val entries = db.ticketHistoryDao().getForTicket("TCK-1")
        assertEquals(listOf("status", "created"), entries.map { it.field })
        assertEquals("progress", entries.first().newValue)
        assertEquals(3, db.ticketHistoryDao().count())
    }

    @Test fun historyDescriptionsAreReadable() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val e = TicketHistoryEntity(ticketId = "TCK-1", actorName = "John", field = "assignee", newValue = "Lisa", createdAt = "2026-01-01 08:00")
        assertTrue(TicketHistoryLogger.describe(context, e).contains("Lisa"))
    }

    @Test fun chatConversationsAreSeparatePerUserAndInOrder() {
        db.chatMessageDao().insertMessage(ChatMessageEntity(userId = 1, senderName = "Grace", message = "hi", fromSupport = false, timestamp = "09:00"))
        db.chatMessageDao().insertMessage(ChatMessageEntity(userId = 1, senderName = "Support", message = "hello", fromSupport = true, timestamp = "09:01"))
        db.chatMessageDao().insertMessage(ChatMessageEntity(userId = 2, senderName = "Thabo", message = "other", fromSupport = false, timestamp = "09:02"))
        val convo = db.chatMessageDao().getConversation(1)
        assertEquals(listOf("hi", "hello"), convo.map { it.message })
        assertTrue(convo[1].fromSupport)
    }

    // ---------- demo data ----------

    @Test fun seedCreatesDemoAccountsTicketsAndIsIdempotent() {
        SeedData.seedIfEmpty(db)
        assertEquals(12, db.userDao().getAllUsers().size)
        assertEquals(2, db.userDao().getUsersByRole("admin").size)
        assertEquals(3, db.userDao().getUsersByRole("technician").size)
        assertEquals(12, db.ticketDao().getAllTickets().size)
        val admin = db.userDao().getUserByEmail("admin@clouddesk.com")!!
        assertTrue(PasswordHasher.verify("password123", admin.password))
        assertTrue(SecurityQuestions.checkAnswer("blue", admin.securityAnswerHash))
        SeedData.seedIfEmpty(db)
        assertEquals(12, db.userDao().getAllUsers().size)
    }
}
