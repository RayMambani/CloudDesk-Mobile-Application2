package com.example.cloudesk

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject

/**
 * Administrator screens that mirror the website: "Deleted tickets" (with Restore) and the
 * "Audit log". One class, two modes, chosen with the MODE extra ("deleted" or "audit").
 */
class AdminLogActivity : BaseActivity() {

    private lateinit var list: LinearLayout
    private var mode = "audit"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mode = intent.getStringExtra("MODE") ?: "audit"
        val pad = (16 * resources.displayMetrics.density).toInt()

        list = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(pad, pad, pad, pad) }
        list.addView(TextView(this).apply {
            text = getString(if (mode == "deleted") R.string.deleted_title else R.string.audit_title)
            textSize = 22f
            setTextColor(getColor(R.color.text_main))
            setTypeface(typeface, android.graphics.Typeface.BOLD)
        })
        setContentView(ScrollView(this).apply { setBackgroundColor(getColor(R.color.bg_dark)); addView(list) })
        load()
    }

    private fun load() {
        val action = if (mode == "deleted") "deleted_list" else "audit_list"
        ApiClient.callAsync(this, action) { r ->
            while (list.childCount > 1) list.removeViewAt(1)
            if (!r.ok) { Toast.makeText(this, ApiClient.errorText(this, r), Toast.LENGTH_SHORT).show(); return@callAsync }
            val rows = r.json?.optJSONArray(if (mode == "deleted") "deleted" else "audit")
            if (rows == null || rows.length() == 0) {
                list.addView(note(getString(if (mode == "deleted") R.string.deleted_empty else R.string.audit_empty)))
                return@callAsync
            }
            for (i in 0 until rows.length()) {
                val o = rows.getJSONObject(i)
                if (mode == "deleted") addDeleted(o) else addAudit(o)
            }
        }
    }

    private fun note(text: String) = TextView(this).apply {
        this.text = text
        setTextColor(getColor(R.color.text_muted))
        setPadding(0, 24, 0, 8)
    }

    private fun addAudit(o: JSONObject) {
        list.addView(TextView(this).apply {
            text = "${o.optString("when")} · ${o.optString("actor")}\n${o.optString("action")} ${o.optString("target")}\n${o.optString("detail")}"
            setTextColor(getColor(R.color.text_main))
            textSize = 13f
            setPadding(0, 18, 0, 6)
        })
    }

    private fun addDeleted(o: JSONObject) {
        val id = o.getString("id")
        list.addView(TextView(this).apply {
            text = "$id · ${o.optString("title")}\n${o.optString("deletedAt")} · ${o.optString("deletedBy")}\n${o.optString("reason")}"
            setTextColor(getColor(R.color.text_main))
            textSize = 14f
            setPadding(0, 20, 0, 4)
        })
        list.addView(Button(this).apply {
            text = getString(R.string.restore)
            setOnClickListener {
                isEnabled = false
                ApiClient.callAsync(this@AdminLogActivity, "ticket_restore", JSONObject().put("id", id)) { r ->
                    if (r.ok) {
                        Toast.makeText(this@AdminLogActivity, getString(R.string.toast_restored), Toast.LENGTH_SHORT).show()
                        load()
                    } else {
                        isEnabled = true
                        Toast.makeText(this@AdminLogActivity, ApiClient.errorText(this@AdminLogActivity, r), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
