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
 * Password reset without email: the user answers the security question they chose at
 * registration, then sets a new password. The answer is checked by the ONLINE backend.
 * Accounts with no question are told to ask an administrator, who can reset the password
 * from User Management.
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

        findViewById<ImageButton>(R.id.backButton).setOnClickListener { finish() }

        val findButton = findViewById<Button>(R.id.findAccountButton)
        findButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_account_not_found), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            findButton.isEnabled = false
            ApiClient.callAsync(this, "forgot_question", JSONObject().put("email", email), authenticated = false) { r ->
                findButton.isEnabled = true
                val question = r.json?.optString("question", "") ?: ""
                if (r.ok && question.isNotEmpty()) {
                    foundEmail = email
                    questionText.text = SecurityQuestions.display(this, question)
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
            val body = JSONObject().put("email", email).put("answer", answer).put("newPassword", newPassword)
            ApiClient.callAsync(this, "forgot_reset", body, authenticated = false) { r ->
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
