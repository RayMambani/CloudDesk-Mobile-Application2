package com.example.cloudesk

import android.app.Activity
import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

/**
 * Keeps the local database (Room) in step with the online one.
 *
 * The ONLINE database is the source of truth. After sign-in and after every change the
 * server sends back everything this user may see (staff: all tickets and users; a normal
 * user: only their own tickets). [apply] replaces the local copy with that data, so every
 * screen keeps reading from Room exactly as before.
 */
object ServerSync {

    /** Pulls the latest data from the server; [onDone] gets true when it worked. */
    fun refreshAsync(activity: Activity, onDone: (Boolean) -> Unit = {}) {
        ApiClient.callAsync(activity, "sync") { r -> onDone(r.ok) }
    }

    @Synchronized
    fun apply(context: Context, json: JSONObject) {
        val db = AppDatabase.getDatabase(context)

        // A file the user attached on THIS phone keeps opening from the phone's copy.
        val localAttachments = db.ticketDao().getAllTickets()
            .mapNotNull { t -> t.attachmentUri?.takeIf { it.startsWith("content:") || it.startsWith("file:") }?.let { t.id to it } }
            .toMap()
        val token = SessionManager.getToken(context)

        val users = json.optJSONArray("users").mapObjects { parseUser(it) }
        val tickets = json.optJSONArray("tickets").mapObjects { parseTicket(it, localAttachments, token) }
        val comments = json.optJSONArray("comments").mapObjects { parseComment(it) }
        val history = json.optJSONArray("history").mapObjects { parseHistory(it) }
        val chat = json.optJSONArray("chat").mapObjects { parseChat(it) }

        db.runInTransaction(Runnable {
            db.ticketHistoryDao().deleteAll()
            db.commentDao().deleteAll()
            db.chatMessageDao().deleteAll()
            db.ticketDao().deleteAll()
            db.userDao().deleteAll()
            users.forEach { db.userDao().insertUser(it) }
            db.ticketDao().insertTickets(tickets)
            comments.forEach { db.commentDao().insertComment(it) }
            history.forEach { db.ticketHistoryDao().insert(it) }
            chat.forEach { db.chatMessageDao().insertMessage(it) }
        })
    }

    /** Forget everything about the signed-in person (logout / expired session). */
    @Synchronized
    fun clearLocal(context: Context) {
        val db = AppDatabase.getDatabase(context)
        db.runInTransaction(Runnable {
            db.ticketHistoryDao().deleteAll()
            db.commentDao().deleteAll()
            db.chatMessageDao().deleteAll()
            db.ticketDao().deleteAll()
            db.userDao().deleteAll()
        })
        SessionManager.clearSession(context)
    }

    // ---------------------------------------------------------------- JSON -> entities

    private fun JSONObject.str(name: String): String? = if (isNull(name)) null else getString(name)

    private inline fun <T> JSONArray?.mapObjects(transform: (JSONObject) -> T): List<T> {
        if (this == null) return emptyList()
        val out = ArrayList<T>(length())
        for (i in 0 until length()) out.add(transform(getJSONObject(i)))
        return out
    }

    /** The password never leaves the server, so the local row holds no password. */
    fun parseUser(o: JSONObject) = UserEntity(
        id = o.getInt("id"),
        firstName = o.getString("firstName"),
        lastName = o.getString("lastName"),
        email = o.getString("email"),
        password = "",
        department = o.str("department") ?: "General",
        role = o.getString("role"),
        phone = o.str("phone"),
        active = o.optBoolean("active", true),
        securityQuestion = o.str("securityQuestion"),
        securityAnswerHash = null
    )

    private fun parseTicket(o: JSONObject, localAttachments: Map<String, String>, token: String?): TicketEntity {
        val id = o.getString("id")
        val attachmentName = o.str("attachmentName")
        val attachmentUri = localAttachments[id]
            ?: if (o.optBoolean("hasAttachment", false) && token != null) {
                ApiConfig.API_URL + "?action=attachment&id=" + java.net.URLEncoder.encode(id, "UTF-8") +
                    "&token=" + java.net.URLEncoder.encode(token, "UTF-8")
            } else null
        return TicketEntity(
            id = id,
            title = o.getString("title"),
            description = o.getString("description"),
            status = o.getString("status"),
            priority = o.getString("priority"),
            category = o.getString("category"),
            created_at = o.str("created_at") ?: "",
            userId = o.getInt("userId"),
            assignedTo = o.str("assignedTo"),
            attachmentUri = attachmentUri,
            attachmentName = attachmentName,
            resolvedAt = o.str("resolvedAt"),
            rating = if (o.isNull("rating")) null else o.getInt("rating"),
            ratingComment = o.str("ratingComment")
        )
    }

    private fun parseComment(o: JSONObject) = CommentEntity(
        id = o.getInt("id"),
        ticketId = o.getString("ticketId"),
        userId = o.getInt("userId"),
        userName = o.getString("userName"),
        content = o.getString("content"),
        created_at = o.str("created_at") ?: ""
    )

    private fun parseHistory(o: JSONObject) = TicketHistoryEntity(
        id = o.getInt("id"),
        ticketId = o.getString("ticketId"),
        actorName = o.getString("actorName"),
        field = o.getString("field"),
        oldValue = o.str("oldValue"),
        newValue = o.str("newValue"),
        createdAt = o.str("createdAt") ?: ""
    )

    private fun parseChat(o: JSONObject) = ChatMessageEntity(
        id = o.getInt("id"),
        userId = o.getInt("userId"),
        senderName = o.getString("senderName"),
        message = o.getString("message"),
        fromSupport = o.optBoolean("fromSupport", false),
        timestamp = o.str("timestamp") ?: ""
    )
}
