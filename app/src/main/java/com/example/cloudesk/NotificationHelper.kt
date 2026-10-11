package com.example.cloudesk

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

/**
 * Email & SMS integration.
 *
 * This project has no backend/mail-server or SMS-gateway API keys configured, so instead
 * of faking a server call, this hands off to the device's own email and messaging apps —
 * a real, working integration that needs no credentials. It's the same approach a lot of
 * lightweight apps use for "contact support" / "notify" actions.
 *
 * If a real backend gets added later (e.g. SendGrid for email, Twilio for SMS), this is
 * the single place to swap in an actual API call without touching any calling code.
 */
object NotificationHelper {

    /** Opens the user's email app with a prefilled message. */
    fun sendEmail(context: Context, to: String?, subject: String, body: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            if (!to.isNullOrBlank()) putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        try {
            context.startActivity(Intent.createChooser(intent, "Send email via"))
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.toast_no_email_app), Toast.LENGTH_SHORT).show()
        }
    }

    /** Opens the user's SMS app with a prefilled message. */
    fun sendSms(context: Context, phone: String?, message: String) {
        val uri = if (!phone.isNullOrBlank()) Uri.parse("smsto:$phone") else Uri.parse("smsto:")
        val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
            putExtra("sms_body", message)
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.toast_no_messaging_app), Toast.LENGTH_SHORT).show()
        }
    }

    private const val CHANNEL_ID = "ticket_assignments"
    private const val PREFS_SEEN = "cloudesk_seen_assignments"
    private const val PREFS_SNAPSHOT = "cloudesk_ticket_snapshot"

    /**
     * Posts a system notification when a technician has tickets assigned that they haven't
     * been told about yet. Already-seen ticket ids are remembered per technician.
     */
    fun notifyNewAssignments(context: Context, technicianId: Int, assignedTicketIds: List<String>) {
        val prefs = context.getSharedPreferences(PREFS_SEEN, Context.MODE_PRIVATE)
        val key = "seen_$technicianId"
        val seen = prefs.getStringSet(key, null)
        val current = assignedTicketIds.toSet()
        val fresh = if (seen == null) emptySet() else current - seen
        prefs.edit().putStringSet(key, current).apply()
        if (fresh.isEmpty()) return

        post(
            context, 2001,
            context.getString(R.string.notif_new_assignment_title),
            context.getString(R.string.notif_new_assignment_text_format, fresh.size)
        )
    }

    /**
     * Tells a ticket owner when one of their tickets changed status or got a reply from
     * someone else since the last time the app looked. The first run just records a baseline.
     *
     * @param commentsByOthers ticketId -> number of comments written by someone other than the owner
     */
    fun notifyTicketUpdates(
        context: Context,
        userId: Int,
        tickets: List<TicketEntity>,
        commentsByOthers: Map<String, Int>
    ) {
        val prefs = context.getSharedPreferences(PREFS_SNAPSHOT, Context.MODE_PRIVATE)
        val key = "snap_$userId"
        val previous = prefs.getStringSet(key, null)
        val current = tickets.map { "${it.id}|${it.status}|${commentsByOthers[it.id] ?: 0}" }.toSet()
        prefs.edit().putStringSet(key, current).apply()
        if (previous == null) return

        val previousById = previous.associate { entry ->
            val parts = entry.split("|")
            parts[0] to Pair(parts.getOrElse(1) { "" }, parts.getOrElse(2) { "0" }.toIntOrNull() ?: 0)
        }
        var updates = 0
        tickets.forEach { ticket ->
            val before = previousById[ticket.id] ?: return@forEach
            if (before.first != ticket.status) updates++
            if ((commentsByOthers[ticket.id] ?: 0) > before.second) updates++
        }
        if (updates == 0) return

        post(
            context, 2002,
            context.getString(R.string.notif_ticket_update_title),
            context.getString(R.string.notif_ticket_update_text_format, updates)
        )
    }

    private fun post(context: Context, id: Int, title: String, text: String) {
        if (android.os.Build.VERSION.SDK_INT >= 33 &&
            context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) return

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            manager.createNotificationChannel(
                android.app.NotificationChannel(
                    CHANNEL_ID,
                    context.getString(R.string.notif_channel_name),
                    android.app.NotificationManager.IMPORTANCE_DEFAULT
                ).apply { description = context.getString(R.string.notif_channel_description) }
            )
        }
        val openApp = android.app.PendingIntent.getActivity(
            context, 0, Intent(context, LoginActivity::class.java),
            android.app.PendingIntent.FLAG_IMMUTABLE or android.app.PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = androidx.core.app.NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(text)
            .setContentIntent(openApp)
            .setAutoCancel(true)
            .build()
        manager.notify(id, notification)
    }
}
