package com.example.cloudesk

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

/**
 * Analytics dashboard. Reads tickets straight out of Room (no separate analytics backend
 * needed) and renders status / priority / category breakdowns as charts via MPAndroidChart.
 * A regular user only sees stats for their own tickets; admins/technicians see the whole
 * queue, consistent with what they can already see on the main dashboard.
 */
class AnalyticsActivity : BaseActivity() {
    private val colorTextMain by lazy { androidx.core.content.ContextCompat.getColor(this, R.color.text_main) }
    private val colorTextMuted by lazy { androidx.core.content.ContextCompat.getColor(this, R.color.text_muted) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)

        findViewById<ImageButton>(R.id.backButton).setOnClickListener { finish() }

        val userId = intent.getIntExtra("USER_ID", -1)
        val userRole = intent.getStringExtra("USER_ROLE") ?: "user"

        val db = AppDatabase.getDatabase(this)
        val allTickets = db.ticketDao().getAllTickets()
        val tickets = if (userRole == "user") allTickets.filter { it.userId == userId } else allTickets

        findViewById<TextView>(R.id.scopeText).text =
            if (userRole == "user") getString(R.string.analytics_your_tickets)
            else getString(R.string.analytics_all_tickets_count_format, tickets.size)

        renderSummary(tickets)
        renderStatusPieChart(tickets)
        renderPriorityBarChart(tickets)
        renderCategoryBarChart(tickets)
    }

    private fun renderSummary(tickets: List<TicketEntity>) {
        val total = tickets.size
        val resolved = tickets.count { it.status == "resolved" || it.status == "closed" }
        val resolvedPct = if (total > 0) (resolved * 100 / total) else 0
        val urgent = tickets.count {
            TicketPriorityAI.analyze(it.title, it.description, it.category).priority in
                listOf(TicketPriorityAI.Priority.CRITICAL, TicketPriorityAI.Priority.HIGH)
        }

        findViewById<TextView>(R.id.statTotal).text = total.toString()
        findViewById<TextView>(R.id.statResolvedPct).text = "$resolvedPct%"
        findViewById<TextView>(R.id.statUrgent).text = urgent.toString()
    }

    private fun renderStatusPieChart(tickets: List<TicketEntity>) {
        val chart = findViewById<PieChart>(R.id.statusPieChart)
        val statusOrder = listOf("open", "progress", "resolved", "closed")
        val statusLabels = statusOrder.associateWith { TicketVocabulary.statusDisplay(this, it) }
        val statusColors = mapOf(
            "open" to Color.parseColor("#3b82f6"),
            "progress" to Color.parseColor("#f59e0b"),
            "resolved" to Color.parseColor("#10b981"),
            "closed" to Color.parseColor("#94a3b8")
        )

        val grouped = tickets.groupingBy { it.status }.eachCount()
        val entries = mutableListOf<PieEntry>()
        val colors = mutableListOf<Int>()
        statusOrder.forEach { status ->
            val count = grouped[status] ?: 0
            if (count > 0) {
                entries.add(PieEntry(count.toFloat(), statusLabels[status] ?: status))
                colors.add(statusColors[status] ?: Color.GRAY)
            }
        }

        if (entries.isEmpty()) {
            chart.clear()
            chart.setNoDataText(getString(R.string.analytics_no_tickets_yet))
            chart.setNoDataTextColor(colorTextMuted)
            return
        }

        val dataSet = PieDataSet(entries, "").apply {
            this.colors = colors
            valueTextColor = Color.WHITE
            valueTextSize = 12f
            sliceSpace = 2f
        }

        chart.apply {
            data = PieData(dataSet)
            description.isEnabled = false
            setHoleColor(Color.TRANSPARENT)
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(11f)
            legend.textColor = colorTextMain
            legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            animateY(600)
            invalidate()
        }
    }

    private fun renderPriorityBarChart(tickets: List<TicketEntity>) {
        val chart = findViewById<BarChart>(R.id.priorityBarChart)
        val priorityOrder = listOf(
            TicketPriorityAI.Priority.LOW,
            TicketPriorityAI.Priority.MEDIUM,
            TicketPriorityAI.Priority.HIGH,
            TicketPriorityAI.Priority.CRITICAL
        )
        val priorityColors = mapOf(
            TicketPriorityAI.Priority.LOW to Color.parseColor("#10b981"),
            TicketPriorityAI.Priority.MEDIUM to Color.parseColor("#3b82f6"),
            TicketPriorityAI.Priority.HIGH to Color.parseColor("#f59e0b"),
            TicketPriorityAI.Priority.CRITICAL to Color.parseColor("#ef4444")
        )

        val aiPriorities = tickets.map { TicketPriorityAI.analyze(it.title, it.description, it.category).priority }
        val grouped = aiPriorities.groupingBy { it }.eachCount()

        val entries = mutableListOf<BarEntry>()
        val colors = mutableListOf<Int>()
        priorityOrder.forEachIndexed { index, priority ->
            entries.add(BarEntry(index.toFloat(), (grouped[priority] ?: 0).toFloat()))
            colors.add(priorityColors[priority] ?: Color.GRAY)
        }

        val dataSet = BarDataSet(entries, "Tickets").apply {
            this.colors = colors
            valueTextColor = colorTextMain
            valueTextSize = 11f
        }

        chart.apply {
            data = BarData(dataSet).apply { barWidth = 0.6f }
            description.isEnabled = false
            legend.isEnabled = false
            axisRight.isEnabled = false
            axisLeft.textColor = colorTextMuted
            axisLeft.axisMinimum = 0f
            xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(priorityOrder.map { TicketVocabulary.priorityDisplay(this@AnalyticsActivity, it.label) })
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                setDrawGridLines(false)
                textColor = colorTextMuted
            }
            setFitBars(true)
            animateY(600)
            invalidate()
        }
    }

    private fun renderCategoryBarChart(tickets: List<TicketEntity>) {
        val chart = findViewById<BarChart>(R.id.categoryBarChart)
        val grouped = tickets.groupingBy { it.category.ifEmpty { "other" } }.eachCount()
            .toList().sortedByDescending { it.second }

        if (grouped.isEmpty()) {
            chart.clear()
            chart.setNoDataText(getString(R.string.analytics_no_tickets_yet))
            chart.setNoDataTextColor(colorTextMuted)
            return
        }

        val labels = grouped.map { TicketVocabulary.categoryDisplay(this, it.first) }
        val entries = grouped.mapIndexed { index, pair -> BarEntry(index.toFloat(), pair.second.toFloat()) }

        val dataSet = BarDataSet(entries, "Tickets").apply {
            color = Color.parseColor("#6366f1")
            valueTextColor = colorTextMain
            valueTextSize = 11f
        }

        chart.apply {
            data = BarData(dataSet).apply { barWidth = 0.6f }
            description.isEnabled = false
            legend.isEnabled = false
            axisRight.isEnabled = false
            axisLeft.textColor = colorTextMuted
            axisLeft.axisMinimum = 0f
            xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(labels)
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                setDrawGridLines(false)
                textColor = colorTextMuted
                labelRotationAngle = -20f
            }
            setFitBars(true)
            animateY(600)
            invalidate()
        }
    }
}
