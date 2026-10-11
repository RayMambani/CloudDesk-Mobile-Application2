package com.example.cloudesk

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

/**
 * Password reset without email: the user answers the security question they chose at
 * registration, then sets a new password. Accounts with no question (older accounts) are
 * told to ask an administrator, who can reset the password from User Management.
 */
class ForgotPasswordActivity : BaseActivity() {

    private var foundUser: UserEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val userDao = AppDatabase.getDatabase(this).userDao()
        val emailInput = findViewById<TextInputEditText>(R.id.forgotEmailInput)
        val resetSection = findViewById<LinearLayout>(R.id.resetSection)
        val questionText = findViewById<TextView>(R.id.questionText)
        val answerInput = findViewById<TextInputEditText>(R.id.answerInput)
        val newPasswordInput = findViewById<TextInputEditText>(R.id.resetNewPasswordInput)
        val confirmPasswordInput = findViewById<TextInputEditText>(R.id.resetConfirmPasswordInput)

        findViewById<ImageButton>(R.id.backButton).setOnClickListener { finish() }

        findViewById<Button>(R.id.findAccountButton).setOnClickListener {
            val email = emailInput.text.toString().trim()
            val user = if (email.isEmpty()) null else userDao.getUserByEmail(email)
            if (user == null) {
                Toast.makeText(this, getString(R.string.toast_account_not_found), Toast.LENGTH_SHORT).show()
                resetSection.visibility = View.GONE
                foundUser = null
                return@setOnClickListener
            }
            if (user.securityQuestion.isNullOrBlank() || user.securityAnswerHash.isNullOrBlank()) {
                Toast.makeText(this, getString(R.string.toast_no_security_question), Toast.LENGTH_LONG).show()
                resetSection.visibility = View.GONE
                foundUser = null
                return@setOnClickListener
            }
            foundUser = user
            questionText.text = SecurityQuestions.display(this, user.securityQuestion)
            resetSection.visibility = View.VISIBLE
        }

        findViewById<Button>(R.id.resetPasswordButton).setOnClickListener {
            val user = foundUser ?: return@setOnClickListener
            val answer = answerInput.text.toString()
            val newPassword = newPasswordInput.text.toString()
            val confirm = confirmPasswordInput.text.toString()

            if (!SecurityQuestions.checkAnswer(answer, user.securityAnswerHash)) {
                Toast.makeText(this, getString(R.string.toast_wrong_security_answer), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (newPassword.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_enter_new_password), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (newPassword != confirm) {
                Toast.makeText(this, getString(R.string.toast_passwords_dont_match), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            userDao.updateUser(user.copy(password = PasswordHasher.hash(newPassword)))
            Toast.makeText(this, getString(R.string.toast_password_reset_done), Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
