package com.example.cloudesk

import org.json.JSONObject

import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Admin-only: add users, change roles, activate/deactivate accounts and reset passwords.
 */
class UserManagementActivity : BaseActivity() {

    private var currentUserId = -1
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)

        val role = intent.getStringExtra("USER_ROLE") ?: "user"
        currentUserId = intent.getIntExtra("USER_ID", -1)
        findViewById<ImageButton>(R.id.backButton).setOnClickListener { finish() }

        if (role != "admin") {
            Toast.makeText(this, getString(R.string.toast_admin_only), Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        recyclerView = findViewById(R.id.usersRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        findViewById<Button>(R.id.addUserButton).setOnClickListener { showAddUserDialog() }
        refresh()
        ServerSync.refreshAsync(this) { ok -> if (ok) refresh() } // latest accounts from the online database
    }

    private fun refresh() {
        val users = AppDatabase.getDatabase(this).userDao().getAllUsers()
        recyclerView.adapter = UserAdapter(users) { user -> showUserActions(user) }
    }

    private fun showUserActions(user: UserEntity) {
        val isSelf = user.id == currentUserId
        val toggleLabel = if (user.active) getString(R.string.user_action_deactivate) else getString(R.string.user_action_activate)
        val actions = arrayOf(getString(R.string.user_action_change_role), toggleLabel, getString(R.string.user_action_reset_password))
        AlertDialog.Builder(this)
            .setTitle("${user.firstName} ${user.lastName}")
            .setItems(actions) { _, which ->
                when (which) {
                    0 -> if (isSelf) selfWarning() else showRoleDialog(user)
                    1 -> if (isSelf) selfWarning() else toggleActive(user)
                    2 -> resetPassword(user)
                }
            }
            .show()
    }

    private fun selfWarning() {
        Toast.makeText(this, getString(R.string.toast_cant_change_self), Toast.LENGTH_SHORT).show()
    }

    private fun showRoleDialog(user: UserEntity) {
        val labels = UserAdapter.ROLE_KEYS.map { UserAdapter.roleDisplay(this, it) }.toTypedArray()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.user_action_change_role))
            .setItems(labels) { _, which ->
                val body = JSONObject().put("id", user.id).put("role", UserAdapter.ROLE_KEYS[which])
                ApiClient.callAsync(this, "admin_user_role", body) { r ->
                    if (r.ok) {
                        Toast.makeText(this, getString(R.string.toast_user_updated), Toast.LENGTH_SHORT).show()
                        refresh()
                    } else {
                        Toast.makeText(this, ApiClient.errorText(this, r), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .show()
    }

    private fun toggleActive(user: UserEntity) {
        val body = JSONObject().put("id", user.id).put("active", !user.active)
        ApiClient.callAsync(this, "admin_user_active", body) { r ->
            if (!r.ok) {
                Toast.makeText(this, ApiClient.errorText(this, r), Toast.LENGTH_SHORT).show()
                return@callAsync
            }
            Toast.makeText(this, getString(R.string.toast_user_updated), Toast.LENGTH_SHORT).show()
            refresh()
            if (user.active) { // it was active, so it has just been deactivated (and signed out everywhere)
                val name = user.firstName
                showNotifyDialog(
                    getString(R.string.user_deactivated_title),
                    getString(R.string.user_deactivated_message_format, "${user.firstName} ${user.lastName}"),
                    user,
                    getString(R.string.notify_deactivated_subject),
                    getString(R.string.notify_deactivated_body_format, name),
                    getString(R.string.notify_deactivated_sms_format, name)
                )
            }
        }
    }

    /** Shows a dialog with Email / SMS buttons that prefill the message to the affected user. */
    private fun showNotifyDialog(
        title: String, message: String, user: UserEntity,
        subject: String, emailBody: String, smsBody: String
    ) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.user_notify_email)) { _, _ ->
                NotificationHelper.sendEmail(this, user.email, subject, emailBody)
            }
            .setNeutralButton(getString(R.string.user_notify_sms)) { _, _ ->
                NotificationHelper.sendSms(this, user.phone, smsBody)
            }
            .setNegativeButton(getString(R.string.action_ok), null)
            .show()
    }

    private fun generateTempPassword(): String {
        val alphabet = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789"
        val random = java.security.SecureRandom()
        return (1..8).map { alphabet[random.nextInt(alphabet.length)] }.joinToString("")
    }

    private fun resetPassword(user: UserEntity) {
        // The server makes the temporary password, stores only its hash, and signs the user out everywhere.
        ApiClient.callAsync(this, "admin_user_reset", JSONObject().put("id", user.id)) { r ->
            val temp = r.json?.optString("tempPassword", "") ?: ""
            if (!r.ok || temp.isEmpty()) {
                Toast.makeText(this, ApiClient.errorText(this, r), Toast.LENGTH_SHORT).show()
                return@callAsync
            }
            showNotifyDialog(
                getString(R.string.user_temp_password_title),
                getString(R.string.user_password_changed_message_format, "${user.firstName} ${user.lastName}", temp),
                user,
                getString(R.string.notify_password_subject),
                getString(R.string.notify_password_body_format, user.firstName, temp),
                getString(R.string.notify_password_sms_format, user.firstName, temp)
            )
        }
    }

    private fun showAddUserDialog() {
        val pad = (16 * resources.displayMetrics.density).toInt()
        val form = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(pad, pad / 2, pad, 0)
        }
        fun field(hintRes: Int, type: Int): EditText = EditText(this).apply {
            hint = getString(hintRes)
            inputType = type
            form.addView(this)
        }
        val first = field(R.string.register_first_name_hint, InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS)
        val last = field(R.string.register_last_name_hint, InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS)
        val email = field(R.string.register_email_hint, InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        val spinner = Spinner(this).apply {
            adapter = ArrayAdapter(
                this@UserManagementActivity,
                android.R.layout.simple_spinner_dropdown_item,
                UserAdapter.ROLE_KEYS.map { UserAdapter.roleDisplay(this@UserManagementActivity, it) }
            )
            form.addView(this)
        }

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.users_add))
            .setView(form)
            .setPositiveButton(getString(R.string.users_create)) { _, _ ->
                val firstName = first.text.toString().trim()
                val lastName = last.text.toString().trim()
                val emailText = email.text.toString().trim()
                if (firstName.isEmpty() || lastName.isEmpty() || emailText.isEmpty()) {
                    Toast.makeText(this, getString(R.string.toast_fill_all_fields), Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                val body = JSONObject()
                    .put("firstName", firstName)
                    .put("lastName", lastName)
                    .put("email", emailText)
                    .put("role", UserAdapter.ROLE_KEYS[spinner.selectedItemPosition])
                // The server creates the account with a temporary password (only its hash is stored).
                ApiClient.callAsync(this, "admin_user_add", body) { r ->
                    val temp = r.json?.optString("tempPassword", "") ?: ""
                    if (!r.ok || temp.isEmpty()) {
                        Toast.makeText(this, ApiClient.errorText(this, r), Toast.LENGTH_SHORT).show()
                        return@callAsync
                    }
                    refresh()
                    AlertDialog.Builder(this)
                        .setTitle(getString(R.string.user_temp_password_title))
                        .setMessage(getString(R.string.user_temp_password_message_format, "$firstName $lastName", temp))
                        .setPositiveButton(getString(R.string.action_ok), null)
                        .show()
                }
            }
            .setNegativeButton(getString(R.string.action_cancel), null)
            .show()
    }
}
