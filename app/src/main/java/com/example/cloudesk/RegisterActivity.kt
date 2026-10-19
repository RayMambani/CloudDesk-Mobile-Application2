package com.example.cloudesk

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

/** Creates an account in the ONLINE database. Self-registration always creates a normal user. */
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
        departmentDropdown.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, departments))

        // Shown for familiarity, but the server ignores it: new accounts are always standard
        // users and only an administrator can promote someone.
        val roles = arrayOf("Standard User", "Support Technician", "Administrator")
        roleDropdown.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, roles))

        securityQuestionDropdown.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, SecurityQuestions.displayList(this))
        )

        registerButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString().trim()
            val lastName = lastNameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()
            val department = departmentDropdown.text.toString()
            val securityKey = SecurityQuestions.keyFromDisplay(this, securityQuestionDropdown.text.toString())
            val securityAnswer = securityAnswerEditText.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@RegisterActivity, getString(R.string.toast_fill_all_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (securityKey == null || securityAnswer.isBlank()) {
                Toast.makeText(this@RegisterActivity, getString(R.string.toast_security_required), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerButton.isEnabled = false
            val body = JSONObject()
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("email", email)
                .put("password", password)
                .put("department", department.ifBlank { "General" })
                .put("securityQuestion", securityKey)
                .put("securityAnswer", securityAnswer)
            ApiClient.callAsync(this, "register", body, authenticated = false) { r ->
                registerButton.isEnabled = true
                if (r.ok) {
                    Toast.makeText(this@RegisterActivity, getString(R.string.toast_registration_successful), Toast.LENGTH_SHORT).show()
                    finish() // Go back to login
                } else {
                    Toast.makeText(this@RegisterActivity, ApiClient.errorText(this@RegisterActivity, r), Toast.LENGTH_SHORT).show()
                }
            }
        }

        backToLogin.setOnClickListener { finish() }
    }
}
