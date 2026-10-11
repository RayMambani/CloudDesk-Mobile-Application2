package com.example.cloudesk

/**
 * Populates the database with simulated demo data the first time the app runs
 * (i.e. only if the users table is still empty), so the app has realistic
 * content to demonstrate every feature — role-based dashboards, AI priority
 * sorting, assigned tickets, comment threads, and chat history — without
 * needing to manually register accounts and create tickets by hand first.
 *
 * All seeded accounts use the password "password123" (see the printed login
 * list this function logs on first run). This never overwrites or touches
 * existing data — it only runs when every table is empty.
 */
object SeedData {

    private const val PASSWORD = "password123"
    private const val SECURITY_ANSWER = "blue" // answer to the seeded "favourite colour" question

    private fun daysAgo(days: Int, time: String = "09:00"): String {
        val cal = java.util.Calendar.getInstance()
        cal.add(java.util.Calendar.DAY_OF_YEAR, -days)
        return java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(cal.time) + " " + time
    }

    fun seedIfEmpty(db: AppDatabase) {
        val userDao = db.userDao()
        if (userDao.getAllUsers().isNotEmpty()) return // Already seeded (or real data exists) — do nothing.

        // ---------- USERS (12) ----------
        // Hash once and reuse for all demo accounts (hashing is deliberately slow).
        val seedHash = PasswordHasher.hash(PASSWORD)
        val seedAnswerHash = SecurityQuestions.hashAnswer(SECURITY_ANSWER)
        val users = listOf(
            UserEntity(firstName = "Shantel", lastName = "Mshabi", email = "admin@clouddesk.com", password = seedHash, department = "IT", role = "admin", phone = "0821234567"),
            UserEntity(firstName = "Ompha", lastName = "Khumalo", email = "omph.admin@clouddesk.com", password = seedHash, department = "IT", role = "admin", phone = "0821234568"),
            UserEntity(firstName = "John", lastName = "Mokoena", email = "john.tech@clouddesk.com", password = seedHash, department = "IT Support", role = "technician", phone = "0731234561"),
            UserEntity(firstName = "Lisa", lastName = "Mabatha", email = "lisa.tech@clouddesk.com", password = seedHash, department = "IT Support", role = "technician", phone = "0731234562"),
            UserEntity(firstName = "Michael", lastName = "Dlamini", email = "michael.tech@clouddesk.com", password = seedHash, department = "Network Ops", role = "technician", phone = "0731234563"),
            UserEntity(firstName = "Grace", lastName = "Mahlangu", email = "grace@company.com", password = seedHash, department = "Finance", role = "user", phone = "0611112222"),
            UserEntity(firstName = "Thabo", lastName = "Sithole", email = "thabo@company.com", password = seedHash, department = "Sales", role = "user", phone = "0611112223"),
            UserEntity(firstName = "Amanda", lastName = "Botha", email = "amanda@company.com", password = seedHash, department = "HR", role = "user"),
            UserEntity(firstName = "Sipho", lastName = "Ngcobo", email = "sipho@company.com", password = seedHash, department = "Marketing", role = "user", phone = "0611112225"),
            UserEntity(firstName = "Emma", lastName = "Msane", email = "emma@company.com", password = seedHash, department = "Operations", role = "user"),
            UserEntity(firstName = "Kagiso", lastName = "Molefe", email = "kagiso@company.com", password = seedHash, department = "Legal", role = "user", phone = "0611112227"),
            UserEntity(firstName = "Zanele", lastName = "Cele", email = "zanele@company.com", password = seedHash, department = "Design", role = "user"),
        )
        val userIds = users.map {
            userDao.insertUser(it.copy(securityQuestion = "colour", securityAnswerHash = seedAnswerHash)).toInt()
        }
        // Index map for readability below (matches declaration order above).
        val admin1 = userIds[0]; val admin2 = userIds[1]
        val tech1 = "John Mokoena"; val tech2 = "Lisa Mabatha"; val tech3 = "Michael Dlamini"
        val grace = userIds[5]; val thabo = userIds[6]; val amanda = userIds[7]
        val sipho = userIds[8]; val emma = userIds[9]; val kagiso = userIds[10]; val zanele = userIds[11]

        // ---------- TICKETS (12) ----------
        val ticketDao = db.ticketDao()
        data class Seed(
            val id: String, val title: String, val description: String, val status: String,
            val priority: String, val category: String, val createdAt: String, val userId: Int,
            val assignedTo: String? = null, val resolvedAt: String? = null
        )
        val tickets = listOf(
            Seed("TCK-1001", "Cannot access shared drive", "I can't open the Finance shared drive since this morning, getting an access denied error.", "open", "high", "Network", daysAgo(1, "08:30"), grace),
            Seed("TCK-1002", "Laptop won't turn on", "My laptop is completely dead, tried charging overnight and still nothing happens when I press power.", "open", "critical", "Hardware", daysAgo(0, "07:15"), thabo),
            Seed("TCK-1003", "Outlook keeps crashing", "Outlook closes itself every few minutes, losing unsent emails. Started after the last update.", "progress", "high", "Software", daysAgo(6, "10:00"), amanda, tech1),
            Seed("TCK-1004", "Request for new monitor", "Could I please get a second monitor for my desk to improve productivity.", "open", "low", "Hardware", daysAgo(2, "13:20"), sipho),
            Seed("TCK-1005", "VPN disconnects randomly", "The VPN drops every 10-15 minutes while working from home, have to keep reconnecting.", "progress", "medium", "Network", daysAgo(4, "09:45"), emma, tech3),
            Seed("TCK-1006", "Password reset not working", "The forgot password email never arrives, checked spam folder already.", "resolved", "high", "Account/Access", daysAgo(9, "08:00"), kagiso, tech2, daysAgo(8, "10:15")),
            Seed("TCK-1007", "Printer offline in Marketing", "The floor printer shows offline for the whole team since yesterday.", "resolved", "medium", "Hardware", daysAgo(11, "11:30"), zanele, tech1, daysAgo(10, "14:30")),
            Seed("TCK-1008", "Server down urgent - all users affected", "Production server appears to be down, nobody in the department can log in, this is urgent.", "closed", "critical", "Backend", daysAgo(19, "15:00"), grace, tech3, daysAgo(19, "16:45")),
            Seed("TCK-1009", "Software license expired", "Adobe license shows expired, cannot export any client files today.", "closed", "medium", "Software", daysAgo(24, "10:10"), thabo, tech2, daysAgo(23, "09:00")),
            Seed("TCK-1010", "How do I submit expense reports", "New employee here, not sure how to submit my travel expense claim through the system.", "closed", "low", "Other", daysAgo(30, "14:00"), amanda, tech1, daysAgo(30, "15:20")),
            Seed("TCK-1011", "Two-factor authentication locked me out", "Lost my phone so I can't approve the 2FA prompt, completely locked out of my account.", "open", "critical", "Account/Access", daysAgo(0, "06:30"), sipho),
            Seed("TCK-1012", "Slow internet connection all week", "Internet has been extremely slow all week, pages take a long time to load across the whole office.", "progress", "medium", "Network", daysAgo(1, "08:30"), emma, tech3),
        )
        // Ratings the ticket owners gave once their tickets were resolved/closed.
        val ratings = mapOf(
            "TCK-1006" to Pair(5, "Quick and friendly, thank you!"),
            "TCK-1007" to Pair(4, "Fixed the same day."),
            "TCK-1008" to Pair(5, "Handled very professionally under pressure."),
            "TCK-1009" to Pair(3, "Worked in the end but took a while."),
            "TCK-1010" to Pair(4, "Clear explanation.")
        )
        tickets.forEach {
            ticketDao.insertTicket(
                TicketEntity(
                    id = it.id, title = it.title, description = it.description, status = it.status,
                    priority = it.priority, category = it.category, created_at = it.createdAt,
                    userId = it.userId, assignedTo = it.assignedTo, resolvedAt = it.resolvedAt,
                    rating = ratings[it.id]?.first, ratingComment = ratings[it.id]?.second
                )
            )
        }

        // ---------- COMMENTS (12) ----------
        val commentDao = db.commentDao()
        val comments = listOf(
            CommentEntity(ticketId = "TCK-1003", userId = userIds[2], userName = tech1, content = "Looking into this now — can you tell me which Outlook version you're on?", created_at = "2026-08-28 14:10"),
            CommentEntity(ticketId = "TCK-1003", userId = amanda, userName = "Amanda Botha", content = "It's the latest one from the Office 365 update this week.", created_at = "2026-08-28 14:25"),
            CommentEntity(ticketId = "TCK-1005", userId = userIds[4], userName = tech3, content = "Can you share your router model? Might be a firmware issue on your end.", created_at = "2026-08-30 11:00"),
            CommentEntity(ticketId = "TCK-1005", userId = emma, userName = "Emma Msane", content = "It's a TP-Link Archer, bought about a year ago.", created_at = "2026-08-30 11:20"),
            CommentEntity(ticketId = "TCK-1006", userId = userIds[3], userName = tech2, content = "Reset the account manually on our end — please try logging in again.", created_at = "2026-08-21 10:10"),
            CommentEntity(ticketId = "TCK-1006", userId = kagiso, userName = "Kagiso Molefe", content = "That worked, thank you so much!", created_at = daysAgo(8, "10:15")),
            CommentEntity(ticketId = "TCK-1007", userId = userIds[2], userName = tech1, content = "Replaced the network cable on the printer, should be back online now.", created_at = "2026-08-19 14:25"),
            CommentEntity(ticketId = "TCK-1007", userId = zanele, userName = "Zanele Cele", content = "Confirmed, it's printing again. Thanks!", created_at = daysAgo(10, "14:30")),
            CommentEntity(ticketId = "TCK-1008", userId = userIds[4], userName = tech3, content = "Identified a failed disk on the primary server, switching over to backup now.", created_at = "2026-08-10 16:10"),
            CommentEntity(ticketId = "TCK-1008", userId = admin1, userName = "Shantel Mshabi", content = "Please prioritise this, whole department is blocked.", created_at = "2026-08-10 16:20"),
            CommentEntity(ticketId = "TCK-1008", userId = userIds[4], userName = tech3, content = "Failover complete, all users should have access again.", created_at = daysAgo(19, "16:45")),
            CommentEntity(ticketId = "TCK-1009", userId = userIds[3], userName = tech2, content = "Renewed the license, should reflect within a few minutes.", created_at = daysAgo(23, "09:00")),
        )
        comments.forEach { commentDao.insertComment(it) }

        // ---------- TICKET HISTORY (>= 12) ----------
        val historyDao = db.ticketHistoryDao()
        val nameById = userIds.zip(users).associate { (id, u) -> id to "${u.firstName} ${u.lastName}" }
        val adminName = "${users[0].firstName} ${users[0].lastName}"
        tickets.forEach { t ->
            historyDao.insert(TicketHistoryEntity(ticketId = t.id, actorName = nameById[t.userId] ?: "User", field = "created", createdAt = t.createdAt))
            val assignee = t.assignedTo
            if (!assignee.isNullOrBlank()) {
                historyDao.insert(TicketHistoryEntity(ticketId = t.id, actorName = adminName, field = "assignee", oldValue = null, newValue = assignee, createdAt = t.createdAt))
                if (t.status != "open") {
                    historyDao.insert(TicketHistoryEntity(ticketId = t.id, actorName = assignee, field = "status", oldValue = "open", newValue = "progress", createdAt = t.createdAt))
                }
                if (t.status == "resolved" || t.status == "closed") {
                    historyDao.insert(TicketHistoryEntity(ticketId = t.id, actorName = assignee, field = "status", oldValue = "progress", newValue = t.status, createdAt = t.resolvedAt ?: t.createdAt))
                }
            }
        }

        // ---------- CHAT MESSAGES (12) ----------
        val chatDao = db.chatMessageDao()
        val chatMessages = listOf(
            ChatMessageEntity(userId = grace, senderName = "CloudDesk Support", message = "Hi Grace! 👋 I'm your CloudDesk support assistant. Tell me what you're running into and I'll help.", fromSupport = true, timestamp = "09:00"),
            ChatMessageEntity(userId = grace, senderName = "Grace Mahlangu", message = "Hi, my password isn't working today", fromSupport = false, timestamp = "09:01"),
            ChatMessageEntity(userId = grace, senderName = "CloudDesk Support", message = "For login issues, try the 'Forgot Password' link on the sign-in screen first. If that doesn't work, I can raise a ticket for our Auth team — want me to do that?", fromSupport = true, timestamp = "09:01"),
            ChatMessageEntity(userId = grace, senderName = "Grace Mahlangu", message = "Yes please, that would help", fromSupport = false, timestamp = "09:02"),
            ChatMessageEntity(userId = grace, senderName = "CloudDesk Support", message = "Got it, thanks for the details. A technician will follow up shortly. In the meantime, feel free to submit a ticket from the dashboard so we can track this properly.", fromSupport = true, timestamp = "09:02"),
            ChatMessageEntity(userId = grace, senderName = "Grace Mahlangu", message = "Thank you!", fromSupport = false, timestamp = "09:03"),
            ChatMessageEntity(userId = thabo, senderName = "CloudDesk Support", message = "Hi Thabo! 👋 I'm your CloudDesk support assistant. Tell me what you're running into and I'll help.", fromSupport = true, timestamp = "13:40"),
            ChatMessageEntity(userId = thabo, senderName = "Thabo Sithole", message = "my laptop won't switch on at all", fromSupport = false, timestamp = "13:41"),
            ChatMessageEntity(userId = thabo, senderName = "CloudDesk Support", message = "That sounds urgent — I'd recommend submitting a ticket right away so it gets flagged as high priority automatically. I'm also looping in a technician now.", fromSupport = true, timestamp = "13:41"),
            ChatMessageEntity(userId = thabo, senderName = "Thabo Sithole", message = "already did, ticket TCK-1002", fromSupport = false, timestamp = "13:42"),
            ChatMessageEntity(userId = thabo, senderName = "CloudDesk Support", message = "Perfect, I can see it in the queue. A technician will be in touch shortly.", fromSupport = true, timestamp = "13:42"),
            ChatMessageEntity(userId = thabo, senderName = "Thabo Sithole", message = "thanks for the quick help", fromSupport = false, timestamp = "13:43"),
        )
        chatMessages.forEach { chatDao.insertMessage(it) }

        android.util.Log.i(
            "SeedData",
            "Seeded ${users.size} users, ${tickets.size} tickets, ${comments.size} comments, " +
                "${chatMessages.size} chat messages. All seeded accounts use password: $PASSWORD"
        )
    }
}
