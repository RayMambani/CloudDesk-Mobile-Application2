package com.example.cloudesk

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import org.json.JSONObject

/**
 * "Online data" screen: shows which API and database the app is using, tests the connection,
 * and lists every table in the online database with its row count and latest rows.
 * Everything here is fetched live from the server (api.php, action "server_info").
 * Personal columns are hidden unless the signed-in user is an admin.
 */
class ServerInfoActivity : BaseActivity() {

    private lateinit var content: LinearLayout

    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()

    private fun text(msg: String, sizeSp: Float = 14f, bold: Boolean = false, mono: Boolean = false, topDp: Int = 0): TextView =
        TextView(this).apply {
            text = msg
            setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp)
            if (bold) setTypeface(typeface, Typeface.BOLD)
            if (mono) typeface = Typeface.MONOSPACE
            setPadding(0, dp(topDp), 0, 0)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.online_data_title)
        val scroll = ScrollView(this)
        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(16), dp(16), dp(24))
        }
        scroll.addView(content)
        setContentView(scroll)
        load()
    }

    private fun load() {
        content.removeAllViews()
        content.addView(text(getString(R.string.online_data_title), 22f, bold = true))
        content.addView(text(getString(R.string.online_data_loading), topDp = 8))

        val started = System.currentTimeMillis()
        ApiClient.callAsync(this, "server_info") { r ->
            val ms = System.currentTimeMillis() - started
            content.removeAllViews()
            content.addView(text(getString(R.string.online_data_title), 22f, bold = true))

            if (!r.ok || r.json == null) {
                content.addView(text(ApiClient.errorText(this, r), topDp = 12))
                return@callAsync
            }
            val data: JSONObject = r.json

            // ---- API ----
            content.addView(text(getString(R.string.online_data_api), 18f, bold = true, topDp = 16))
            content.addView(text(ApiConfig.API_URL, mono = true, topDp = 4))
            content.addView(text(getString(R.string.online_data_connected_format, ms), topDp = 4))
            content.addView(text(getString(R.string.online_data_auth_note), topDp = 4))

            // ---- Authentication service ----
            val auth = data.optJSONObject("auth")
            if (auth != null) {
                content.addView(text(getString(R.string.online_data_auth_title), 18f, bold = true, topDp = 16))
                content.addView(text(auth.optString("service"), topDp = 4))
                content.addView(text(getString(R.string.online_data_auth_signed_in_format, auth.optString("signedInAs"), auth.optString("role")), topDp = 4))
                content.addView(text(getString(R.string.online_data_auth_sessions_format, auth.optInt("activeSessions"), auth.optInt("fingerprintDevices")), topDp = 4))
                content.addView(text(getString(R.string.online_data_auth_storage_format, auth.optString("passwordStorage"), auth.optString("tokenStorage")), topDp = 4))
                content.addView(text(getString(R.string.online_data_auth_lockout_format, auth.optString("lockout")), topDp = 4))
                content.addView(text(getString(if (auth.optBoolean("googleSignIn")) R.string.online_data_auth_google_on else R.string.online_data_auth_google_off), topDp = 4))
            }

            // ---- Database ----
            content.addView(text(getString(R.string.online_data_database), 18f, bold = true, topDp = 16))
            content.addView(text(getString(R.string.online_data_engine_format, data.optString("engine"), data.optString("host")), topDp = 4))
            if (!data.optBoolean("canSeeAll", false)) {
                content.addView(text(getString(R.string.online_data_hidden_note), topDp = 4))
            }

            // ---- Tables ----
            val tables = data.optJSONArray("tables")
            if (tables != null) {
                for (i in 0 until tables.length()) {
                    val t = tables.getJSONObject(i)
                    content.addView(text(getString(R.string.online_data_table_format, t.optString("name"), t.optInt("count")), 16f, bold = true, topDp = 16))
                    val rows = t.optJSONArray("rows")
                    if (rows == null || rows.length() == 0) {
                        content.addView(text(getString(R.string.online_data_no_rows), topDp = 2))
                        continue
                    }
                    for (j in 0 until rows.length()) {
                        val row = rows.getJSONObject(j)
                        val sb = StringBuilder()
                        val keys = row.keys()
                        while (keys.hasNext()) {
                            val k = keys.next()
                            sb.append(k).append(": ").append(row.optString(k)).append('\n')
                        }
                        content.addView(text(sb.toString().trimEnd(), 12f, mono = true, topDp = 6))
                    }
                }
            }
            val refresh = android.widget.Button(this).apply {
                text = getString(R.string.online_data_refresh)
                isAllCaps = false
                setOnClickListener { load() }
            }
            content.addView(refresh, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { topMargin = dp(20) })
            content.gravity = Gravity.START
        }
    }
}
