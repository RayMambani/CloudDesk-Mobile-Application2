package com.example.cloudesk

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** Creates CSV / PDF exports in the app cache and hands them to the system share sheet. */
object ExportHelper {

    private fun exportDir(context: Context): File =
        File(context.cacheDir, "exports").also { it.mkdirs() }

    private fun stamp(): String = SimpleDateFormat("yyyyMMdd_HHmm", Locale.getDefault()).format(Date())

    private fun csvField(value: String?): String {
        val v = value ?: ""
        return "\"" + v.replace("\"", "\"\"") + "\""
    }

    fun exportTicketsCsv(context: Context, tickets: List<TicketEntity>): File {
        val file = File(exportDir(context), "CloudDesk_Tickets_${stamp()}.csv")
        val sb = StringBuilder()
        sb.append("id,title,description,status,priority,category,created_at,assigned_to,resolved_at,sla_due,overdue,rating,rating_comment\n")
        tickets.forEach { t ->
            sb.append(
                listOf(
                    t.id, t.title, t.description, t.status, t.priority, t.category, t.created_at,
                    t.assignedTo, t.resolvedAt, SlaHelper.dueLabel(t),
                    if (SlaHelper.isOverdue(t)) "yes" else "no",
                    t.rating?.toString(), t.ratingComment
                ).joinToString(",") { csvField(it) }
            ).append("\n")
        }
        file.writeText(sb.toString(), Charsets.UTF_8)
        return file
    }

    fun exportReportPdf(context: Context, reportText: String): File {
        val file = File(exportDir(context), "CloudDesk_Report_${stamp()}.pdf")
        val pageWidth = 595
        val pageHeight = 842
        val margin = 40f
        val maxTextWidth = pageWidth - 2 * margin

        val bodyPaint = Paint().apply { textSize = 11f; isAntiAlias = true }
        val lineHeight = 16f

        val document = PdfDocument()
        var pageNumber = 1
        var page = document.startPage(PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create())
        var canvas = page.canvas
        var y = margin + 10f

        fun newPage() {
            document.finishPage(page)
            pageNumber++
            page = document.startPage(PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create())
            canvas = page.canvas
            y = margin + 10f
        }

        reportText.split("\n").forEach { rawLine ->
            // Simple word wrap
            var line = ""
            val words = rawLine.split(" ")
            val wrapped = mutableListOf<String>()
            for (word in words) {
                val candidate = if (line.isEmpty()) word else "$line $word"
                if (bodyPaint.measureText(candidate) <= maxTextWidth) {
                    line = candidate
                } else {
                    if (line.isNotEmpty()) wrapped.add(line)
                    line = word
                }
            }
            wrapped.add(line)
            wrapped.forEach { text ->
                if (y > pageHeight - margin) newPage()
                canvas.drawText(text, margin, y, bodyPaint)
                y += lineHeight
            }
        }
        document.finishPage(page)
        FileOutputStream(file).use { document.writeTo(it) }
        document.close()
        return file
    }

    fun share(context: Context, file: File, mimeType: String, subject: String) {
        val uri = FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = mimeType
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, subject))
    }
}
