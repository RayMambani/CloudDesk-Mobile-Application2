package com.example.cloudesk

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject

/**
 * Shown right after sign-in when an administrator created the account (or reset the password)
 * and the person is still using the temporary password. They cannot continue until they choose
 * their own password. The server enforces the same password rules as the website.
 */
class ChangePasswordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pad = (20 * resources.displayMetrics.density).toInt()

        val form = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(pad, pad * 2, pad, pad)
        }
        form.addView(TextView(this).apply {
            text = getString(R.string.cp_title)
            textSize = 24f
            setTextColor(getColor(R.color.text_main))
            setTypeface(typeface, android.graphics.Typeface.BOLD)
        })
        form.addView(TextView(this).apply {
            text = getString(R.string.cp_message)
            setTextColor(getColor(R.color.text_muted))
            setPadding(0, pad / 2, 0, pad)
        })

        fun field(hintRes: Int): EditText = EditText(this).apply {
            hint = getString(hintRes)
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            setTextColor(getColor(R.color.text_main))
            setHintTextColor(getColor(R.color.text_muted))
            form.addView(this)
        }
        val current = field(R.string.cp_current_hint)
        val newPw = field(R.string.cp_new_hint)
        val confirm = field(R.string.cp_confirm_hint)

        val save = Button(this).apply {
            text = getString(R.string.cp_save)
            form.addView(this, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply { topMargin = pad })
        }
        val logout = TextView(this).apply {
            text = getString(R.string.action_logout)
            gravity = Gravity.CENTER
            setTextColor(getColor(R.color.primary))
            setPadding(0, pad, 0, 0)
            form.addView(this)
        }
        logout.setOnClickListener {
            val appContext = applicationContext
            Thread { ApiClient.call(appContext, "logout") }.start()
            ServerSync.clearLocal(this)
            startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
        }

        save.setOnClickListener {
            val a = current.text.toString()
            val b = newPw.text.toString()
            if (a.isEmpty() || b.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_fill_all_fields), Toast.LENGTH_SHORT).show(); return@setOnClickListener
            }
            if (b != confirm.text.toString()) {
                Toast.makeText(this, getString(R.string.toast_passwords_dont_match), Toast.LENGTH_SHORT).show(); return@setOnClickListener
            }
            val me = AppDatabase.getDatabase(this).userDao().getUserById(SessionManager.getUserId(this))
            if (me == null) { Toast.makeText(this, getString(R.string.toast_something_went_wrong), Toast.LENGTH_SHORT).show(); return@setOnClickListener }
            save.isEnabled = false
            val body = JSONObject()
                .put("firstName", me.firstName).put("lastName", me.lastName).put("phone", me.phone ?: "")
                .put("currentPassword", a).put("newPassword", b)
            ApiClient.callAsync(this, "profile_update", body) { r ->
                save.isEnabled = true
                if (r.ok) {
                    SessionManager.setMustChangePassword(this, false)
                    Toast.makeText(this, getString(R.string.toast_password_changed), Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java)
                        .putExtra("USER_ID", me.id)
                        .putExtra("USER_NAME", "${me.firstName} ${me.lastName}")
                        .putExtra("USER_ROLE", me.role))
                    finish()
                } else {
                    Toast.makeText(this, ApiClient.errorText(this, r), Toast.LENGTH_LONG).show()
                }
            }
        }

        setContentView(ScrollView(this).apply {
            setBackgroundColor(getColor(R.color.bg_dark))
            addView(form)
        })
    }

    // The person must choose a password before using the app, so Back does nothing here.
    @Deprecated("Back is disabled until a password is chosen")
    override fun onBackPressed() {}
}
