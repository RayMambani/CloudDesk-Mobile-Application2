package com.example.cloudesk

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val firstNameEditText = findViewById<TextInputEditText>(R.id.firstName)
        val lastNameEditText = findViewById<TextInputEditText>(R.id.lastName)
        val emailEditText = findViewById<TextInputEditText>(R.id.email)
        val passwordEditText = findViewById<TextInputEditText>(R.id.password)
        val departmentDropdown = findViewById<AutoCompleteTextView>(R.id.departmentDropdown)
        val roleDropdown = findViewById<AutoCompleteTextView>(R.id.roleDropdown)
        val securityQuestionDropdown = findViewById<AutoCompleteTextView>(R.id.securityQuestionDropdown)
        val securityAnswerEditText = findViewById<TextInputEditText>(R.id.securityAnswerInput)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val backToLogin = findViewById<TextView>(R.id.backToLogin)

        // Populate Dropdowns
        val departments = arrayOf("Marketing", "Finance", "Operations", "HR", "Sales")
        val departmentAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, departments)
        departmentDropdown.setAdapter(departmentAdapter)

        val roles = arrayOf("Standard User", "Support Technician", "Administrator")
        val roleAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, roles)
        roleDropdown.setAdapter(roleAdapter)

        securityQuestionDropdown.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, SecurityQuestions.displayList(this))
        )

        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        registerButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val department = departmentDropdown.text.toString()
            val role = when (roleDropdown.text.toString()) {
                "Standard User" -> "user"
                "Support Technician" -> "technician"
                "Administrator" -> "admin"
                else -> "user"
            }

            val securityKey = SecurityQuestions.keyFromDisplay(this, securityQuestionDropdown.text.toString())
            val securityAnswer = securityAnswerEditText.text.toString()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                if (securityKey == null || securityAnswer.isBlank()) {
                    Toast.makeText(this@RegisterActivity, getString(R.string.toast_security_required), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                // Check if user already exists
                val existingUser = userDao.getUserByEmail(email)
                if (existingUser != null) {
                    Toast.makeText(this@RegisterActivity, getString(R.string.toast_user_exists), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val user = UserEntity(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    department = department,
                    password = PasswordHasher.hash(password),
                    role = role,
                    securityQuestion = securityKey,
                    securityAnswerHash = SecurityQuestions.hashAnswer(securityAnswer)
                )
                val id = userDao.insertUser(user)
                if (id > 0) {
                    Toast.makeText(this@RegisterActivity, getString(R.string.toast_registration_successful), Toast.LENGTH_SHORT).show()
                    finish() // Go back to login
                } else {
                    Toast.makeText(this@RegisterActivity, getString(R.string.toast_registration_failed), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@RegisterActivity, getString(R.string.toast_fill_all_fields), Toast.LENGTH_SHORT).show()
            }
        }

        backToLogin.setOnClickListener {
            finish()
        }
    }
}
