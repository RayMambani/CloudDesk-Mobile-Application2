package com.example.cloudesk

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText

/**
 * Lets a logged-in user manage their own account: name, phone (used for SMS
 * notifications), password, email/SMS notification preferences, and app language.
 */
class SettingsActivity : BaseActivity() {

    companion object {
        const val PREFS_NOTIF = "cloudesk_notifications"
        const val KEY_EMAIL_NOTIF = "email_notifications_enabled"
        const val KEY_SMS_NOTIF = "sms_notifications_enabled"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val userId = intent.getIntExtra("USER_ID", -1)
        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()
        val user = userDao.getUserById(userId)

        findViewById<ImageButton>(R.id.backButton).setOnClickListener { finish() }

        if (user == null) {
            Toast.makeText(this, getString(R.string.toast_couldnt_load_profile), Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val firstNameInput = findViewById<TextInputEditText>(R.id.firstNameInput)
        val lastNameInput = findViewById<TextInputEditText>(R.id.lastNameInput)
        val phoneInput = findViewById<TextInputEditText>(R.id.phoneInput)
        val currentPasswordInput = findViewById<TextInputEditText>(R.id.currentPasswordInput)
        val newPasswordInput = findViewById<TextInputEditText>(R.id.newPasswordInput)
        val confirmPasswordInput = findViewById<TextInputEditText>(R.id.confirmPasswordInput)

        // Theme picker (Dark = the original blue look, is the default)
        val themeGroup = findViewById<RadioGroup>(R.id.themeRadioGroup)
        val currentTheme = ThemeHelper.getThemeMode(this)
        themeGroup.check(if (currentTheme == ThemeHelper.MODE_LIGHT) R.id.themeLightRadio else R.id.themeDarkRadio)

        findViewById<TextView>(R.id.emailReadOnlyText).text = user.email
        firstNameInput.setText(user.firstName)
        lastNameInput.setText(user.lastName)
        phoneInput.setText(user.phone ?: "")

        // Notification preferences (device-local; used to decide whether the app
        // nudges the user toward email/SMS updates elsewhere in the app).
        val prefs = getSharedPreferences(PREFS_NOTIF, MODE_PRIVATE)
        val emailSwitch = findViewById<SwitchMaterial>(R.id.emailNotifSwitch)
        val smsSwitch = findViewById<SwitchMaterial>(R.id.smsNotifSwitch)
        emailSwitch.isChecked = prefs.getBoolean(KEY_EMAIL_NOTIF, true)
        smsSwitch.isChecked = prefs.getBoolean(KEY_SMS_NOTIF, false)

        // Fingerprint / screen-lock sign-in
        val biometricSwitch = findViewById<SwitchMaterial>(R.id.biometricSwitch)
        biometricSwitch.isChecked = BiometricHelper.isEnabledFor(this, user.id)
        biometricSwitch.setOnCheckedChangeListener { button, checked ->
            if (!button.isPressed) return@setOnCheckedChangeListener // ignore programmatic changes
            if (checked) {
                if (!BiometricHelper.isAvailable(this)) {
                    Toast.makeText(this, getString(R.string.toast_biometric_unavailable), Toast.LENGTH_LONG).show()
                    biometricSwitch.isChecked = false
                } else {
                    BiometricHelper.authenticate(
                        this,
                        getString(R.string.biometric_prompt_title),
                        getString(R.string.biometric_enable_subtitle),
                        onSuccess = {
                            BiometricHelper.enable(this, user.id)
                            Toast.makeText(this, getString(R.string.toast_biometric_enabled), Toast.LENGTH_SHORT).show()
                        },
                        onFailure = { message ->
                            biometricSwitch.isChecked = false
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            } else {
                if (BiometricHelper.isEnabledFor(this, user.id)) BiometricHelper.disable(this)
            }
        }

        // Language picker
        val languageGroup = findViewById<RadioGroup>(R.id.languageRadioGroup)
        val currentLanguage = LocaleHelper.getLanguage(this)
        val buttonIdsByCode = mutableMapOf<String, Int>()
        LocaleHelper.SUPPORTED_LANGUAGES.forEach { (code, label) ->
            val radioButton = RadioButton(this).apply {
                text = label
                id = android.view.View.generateViewId()
                setTextColor(getColorStateList(R.color.text_main))
            }
            buttonIdsByCode[code] = radioButton.id
            languageGroup.addView(radioButton)
            if (code == currentLanguage) radioButton.isChecked = true
        }

        findViewById<Button>(R.id.saveSettingsButton).setOnClickListener {
            val firstName = firstNameInput.text.toString().trim()
            val lastName = lastNameInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()
            val currentPassword = currentPasswordInput.text.toString()
            val newPassword = newPasswordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_name_cant_be_empty), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Password change: current -> new -> confirm
            var passwordToSave = user.password
            if (currentPassword.isNotEmpty() || newPassword.isNotEmpty() || confirmPassword.isNotEmpty()) {
                if (!PasswordHasher.verify(currentPassword, user.password)) {
                    Toast.makeText(this, getString(R.string.toast_current_password_incorrect), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (newPassword.isEmpty()) {
                    Toast.makeText(this, getString(R.string.toast_enter_new_password), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (newPassword != confirmPassword) {
                    Toast.makeText(this, getString(R.string.toast_passwords_dont_match), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                passwordToSave = PasswordHasher.hash(newPassword)
            }

            val updatedUser = user.copy(
                firstName = firstName,
                lastName = lastName,
                phone = phone.ifEmpty { null },
                password = passwordToSave
            )
            userDao.updateUser(updatedUser)

            // Keep the session's display name in sync
            SessionManager.saveSession(this, updatedUser.id, "$firstName $lastName", updatedUser.role, updatedUser.email)

            prefs.edit()
                .putBoolean(KEY_EMAIL_NOTIF, emailSwitch.isChecked)
                .putBoolean(KEY_SMS_NOTIF, smsSwitch.isChecked)
                .apply()

            val selectedId = languageGroup.checkedRadioButtonId
            val selectedLanguage = buttonIdsByCode.entries.find { it.value == selectedId }?.key ?: currentLanguage
            val languageChanged = selectedLanguage != currentLanguage
            if (languageChanged) {
                LocaleHelper.setLocale(this, selectedLanguage)
            }

            Toast.makeText(this, getString(R.string.toast_settings_saved), Toast.LENGTH_SHORT).show()

            val selectedTheme = if (themeGroup.checkedRadioButtonId == R.id.themeLightRadio) ThemeHelper.MODE_LIGHT else ThemeHelper.MODE_DARK
            if (selectedTheme != currentTheme) {
                // Applying the night mode recreates the activities automatically.
                ThemeHelper.setThemeMode(this, selectedTheme)
            }

            if (languageChanged) {
                // Restart so the new language is applied everywhere immediately.
                recreate()
            } else {
                finish()
            }
        }
    }
}
