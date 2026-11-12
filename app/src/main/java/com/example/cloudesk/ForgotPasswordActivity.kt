package com.example.cloudesk

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

/**
 * Password reset by emailed code: the user enters their email, the ONLINE backend emails a
 * 6-digit code (15 minutes, single use, 5 tries), then the user enters the code and a new
 * password. The old password and every old login token stop working.
 */
class ForgotPasswordActivity : BaseActivity() {

    private var foundEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val emailInput = findViewById<TextInputEditText>(R.id.forgotEmailInput)
        val resetSection = findViewById<LinearLayout>(R.id.resetSection)
        val questionText = findViewById<TextView>(R.id.questionText)
        val answerInput = findViewById<TextInputEditText>(R.id.answerInput)
        val newPasswordInput = findViewById<TextInputEditText>(R.id.resetNewPasswordInput)
        val confirmPasswordInput = findViewById<TextInputEditText>(R.id.resetConfirmPasswordInput)
        // The "answer" box is reused for the 6-digit code.
        answerInput.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        answerInput.filters = arrayOf(android.text.InputFilter.LengthFilter(6))
        (answerInput.parent.parent as? com.google.android.material.textfield.TextInputLayout)?.hint = getString(R.string.verify_code_hint)
        findViewById<TextView>(R.id.forgotSubtitle)?.text = getString(R.string.forgot_subtitle_code)

        findViewById<ImageButton>(R.id.backButton).setOnClickListener { finish() }

        val findButton = findViewById<Button>(R.id.findAccountButton)
        findButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_account_not_found), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            findButton.isEnabled = false
            ApiClient.callAsync(this, "forgot_request", JSONObject().put("email", email), authenticated = false) { r ->
                findButton.isEnabled = true
                if (r.ok) {
                    foundEmail = email
                    var msg = getString(R.string.forgot_code_sent)
                    val demo = r.json?.optString("demoCode", "") ?: ""
                    if (demo.isNotEmpty()) msg += "  " + getString(R.string.verify_demo_format, demo)
                    questionText.text = msg
                    resetSection.visibility = View.VISIBLE
                } else {
                    foundEmail = null
                    resetSection.visibility = View.GONE
                    Toast.makeText(this, ApiClient.errorText(this, r), Toast.LENGTH_LONG).show()
                }
            }
        }

        val resetButton = findViewById<Button>(R.id.resetPasswordButton)
        resetButton.setOnClickListener {
            val email = foundEmail ?: return@setOnClickListener
            val answer = answerInput.text.toString()
            val newPassword = newPasswordInput.text.toString()
            val confirm = confirmPasswordInput.text.toString()

            if (newPassword.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_enter_new_password), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (newPassword != confirm) {
                Toast.makeText(this, getString(R.string.toast_passwords_dont_match), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            resetButton.isEnabled = false
            val body = JSONObject().put("email", email).put("code", answer.trim()).put("newPassword", newPassword)
            ApiClient.callAsync(this, "forgot_confirm", body, authenticated = false) { r ->
                resetButton.isEnabled = true
                if (r.ok) {
                    Toast.makeText(this, getString(R.string.toast_password_reset_done), Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, ApiClient.errorText(this, r), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
