package com.example.cloudesk

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Admin-only screen. Computes real performance metrics per technician straight from the
 * local ticket data (no separate reporting backend needed) and lets the admin share a
 * text summary via email.
 */
class PerformanceReportActivity : BaseActivity() {
    private val colorTextMain by lazy { androidx.core.content.ContextCompat.getColor(this, R.color.text_main) }
    private val colorTextMuted by lazy { androidx.core.content.ContextCompat.getColor(this, R.color.text_muted) }

    private val createdFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val resolvedFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performance_report)

        val userRole = intent.getStringExtra("USER_ROLE") ?: "user"
        findViewById<ImageButton>(R.id.backButton).setOnClickListener { finish() }

        if (userRole != "admin") {
            Toast.makeText(this, getString(R.string.toast_reports_admin_only), Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val db = AppDatabase.getDatabase(this)
        val tickets = db.ticketDao().getAllTickets()
        val technicians = db.userDao().getUsersByRole("technician")

        val reportText = buildReport(tickets, technicians)
        findViewById<Button>(R.id.exportCsvButton).setOnClickListener {
            val file = ExportHelper.exportTicketsCsv(this, tickets)
            ExportHelper.share(this, file, "text/csv", getString(R.string.export_csv))
        }
        findViewById<Button>(R.id.exportPdfButton).setOnClickListener {
            val file = ExportHelper.exportReportPdf(this, reportText)
            ExportHelper.share(this, file, "application/pdf", getString(R.string.export_pdf))
        }
        findViewById<Button>(R.id.shareReportButton).setOnClickListener {
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            NotificationHelper.sendEmail(
                this,
                to = null,
                subject = getString(R.string.performance_email_subject_format, today),
                body = reportText
            )
        }
    }

    private fun buildReport(tickets: List<TicketEntity>, technicians: List<UserEntity>): String {
        val resolvedStatuses = setOf("resolved", "closed")
        val total = tickets.size
        val resolvedCount = tickets.count { it.status in resolvedStatuses }
        val resolvedPct = if (total > 0) resolvedCount * 100 / total else 0
        val overallAvgHours = averageResolutionHours(tickets.filter { it.status in resolvedStatuses })

        findViewById<TextView>(R.id.overallSummaryText).text =
            getString(R.string.performance_total_tickets_format, total) + "\n" +
                getString(R.string.performance_resolved_format, resolvedCount, resolvedPct) + "\n" +
                getString(R.string.performance_avg_resolution_format, formatHours(overallAvgHours)) + "\n" +
                getString(R.string.performance_avg_rating_format, formatRating(averageRating(tickets)))

        val rowsContainer = findViewById<LinearLayout>(R.id.technicianRowsContainer)
        rowsContainer.removeAllViews()

        val reportBuilder = StringBuilder()
        reportBuilder.append(getString(R.string.performance_email_title)).append("\n")
        reportBuilder.append(getString(R.string.performance_generated_format,
            SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date()))).append("\n\n")
        reportBuilder.append(getString(R.string.performance_overall_caps)).append("\n")
        reportBuilder.append(getString(R.string.performance_total_tickets_format, total)).append("\n")
        reportBuilder.append(getString(R.string.performance_resolved_format, resolvedCount, resolvedPct)).append("\n")
        reportBuilder.append(getString(R.string.performance_avg_resolution_format, formatHours(overallAvgHours))).append("\n")
        reportBuilder.append(getString(R.string.performance_avg_rating_format, formatRating(averageRating(tickets)))).append("\n\n")
        reportBuilder.append(getString(R.string.performance_by_technician_caps)).append("\n")

        // Build the list of buckets to show: every registered technician (even with 0
        // tickets, so admins can see who's idle) plus an "Unassigned" bucket if relevant.
        val technicianNames = technicians.map { "${it.firstName} ${it.lastName}" }
        val unassignedTickets = tickets.filter { it.assignedTo.isNullOrBlank() }

        technicianNames.forEach { name ->
            val assigned = tickets.filter { it.assignedTo == name }
            addTechnicianRow(rowsContainer, name, assigned, resolvedStatuses)
            reportBuilder.append(formatTechnicianLine(name, assigned, resolvedStatuses))
        }

        if (unassignedTickets.isNotEmpty()) {
            val unassignedLabel = getString(R.string.performance_unassigned)
            addTechnicianRow(rowsContainer, unassignedLabel, unassignedTickets, resolvedStatuses)
            reportBuilder.append(formatTechnicianLine(unassignedLabel, unassignedTickets, resolvedStatuses))
        }

        if (technicianNames.isEmpty() && unassignedTickets.isEmpty()) {
            val empty = TextView(this).apply {
                text = getString(R.string.performance_no_technicians)
                setTextColor(colorTextMuted)
                textSize = 13f
            }
            rowsContainer.addView(empty)
        }

        return reportBuilder.toString()
    }

    private fun formatTechnicianLine(name: String, assigned: List<TicketEntity>, resolvedStatuses: Set<String>): String {
        val resolved = assigned.count { it.status in resolvedStatuses }
        val rate = if (assigned.isNotEmpty()) resolved * 100 / assigned.size else 0
        val avgHours = averageResolutionHours(assigned.filter { it.status in resolvedStatuses })
        return getString(
            R.string.performance_technician_line_format,
            name, assigned.size, resolved, rate, formatHours(avgHours)
        ) + " | " + getString(R.string.performance_avg_rating_format, formatRating(averageRating(assigned))) + "\n"
    }

    private fun addTechnicianRow(
        container: LinearLayout,
        name: String,
        assigned: List<TicketEntity>,
        resolvedStatuses: Set<String>
    ) {
        val resolved = assigned.count { it.status in resolvedStatuses }
        val rate = if (assigned.isNotEmpty()) resolved * 100 / assigned.size else 0
        val avgHours = averageResolutionHours(assigned.filter { it.status in resolvedStatuses })

        val row = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 12, 0, 12)
        }
        val nameView = TextView(this).apply {
            text = name
            setTextColor(colorTextMain)
            textSize = 14f
            setTypeface(typeface, Typeface.BOLD)
        }
        val statsView = TextView(this).apply {
            text = getString(R.string.performance_row_stats_format, assigned.size, resolved, rate, formatHours(avgHours)) +
                "\n" + getString(R.string.performance_avg_rating_format, formatRating(averageRating(assigned)))
            setTextColor(colorTextMuted)
            textSize = 12f
        }
        row.addView(nameView)
        row.addView(statsView)
        container.addView(row)
    }

    /** Approximate: created_at is date-only, resolvedAt is date+time. */
    private fun averageResolutionHours(resolvedTickets: List<TicketEntity>): Double? {
        val durations = resolvedTickets.mapNotNull { ticket ->
            val resolvedAtStr = ticket.resolvedAt ?: return@mapNotNull null
            try {
                val start = createdFormat.parse(ticket.created_at) ?: return@mapNotNull null
                val end = resolvedFormat.parse(resolvedAtStr) ?: return@mapNotNull null
                val diffMs = end.time - start.time
                if (diffMs < 0) null else TimeUnit.MILLISECONDS.toMinutes(diffMs) / 60.0
            } catch (e: Exception) {
                null
            }
        }
        return if (durations.isEmpty()) null else durations.average()
    }

    private fun formatHours(hours: Double?): String {
        if (hours == null) return getString(R.string.time_not_available)
        return if (hours < 24) getString(R.string.time_hours_format, hours)
        else getString(R.string.time_days_format, hours / 24)
    }

    private fun averageRating(tickets: List<TicketEntity>): Double? {
        val ratings = tickets.mapNotNull { it.rating }
        return if (ratings.isEmpty()) null else ratings.average()
    }

    private fun formatRating(avg: Double?): String =
        if (avg == null) getString(R.string.time_not_available) else String.format(Locale.getDefault(), "%.1f / 5", avg)
}
