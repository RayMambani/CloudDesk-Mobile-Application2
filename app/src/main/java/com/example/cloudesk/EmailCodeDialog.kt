package com.example.cloudesk

import android.app.Activity
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import org.json.JSONObject

/**
 * Asks for the 6-digit code that the server emailed to verify an email address.
 * The code is checked ONLINE (api.php "verify_email"). "Send a new code" cancels the old one.
 */
object EmailCodeDialog {
    fun show(activity: Activity, email: String, demoCode: String? = null, onVerified: () -> Unit = {}) {
        val input = EditText(activity).apply {
            inputType = InputType.TYPE_CLASS_NUMBER
            hint = activity.getString(R.string.verify_code_hint)
            filters = arrayOf(android.text.InputFilter.LengthFilter(6))
            contentDescription = activity.getString(R.string.verify_code_hint)
        }
        var msg = activity.getString(R.string.verify_message_format, email)
        if (!demoCode.isNullOrEmpty()) msg += "\n\n" + activity.getString(R.string.verify_demo_format, demoCode)
        val dialog = AlertDialog.Builder(activity)
            .setTitle(R.string.verify_title)
            .setMessage(msg)
            .setView(input)
            .setPositiveButton(R.string.verify_button, null)
            .setNeutralButton(R.string.verify_resend, null)
            .setNegativeButton(android.R.string.cancel, null)
            .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val body = JSONObject().put("email", email).put("code", input.text.toString().trim())
                ApiClient.callAsync(activity, "verify_email", body, authenticated = false) { r ->
                    if (r.ok) {
                        Toast.makeText(activity, activity.getString(R.string.toast_email_verified), Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                        onVerified()
                    } else {
                        Toast.makeText(activity, ApiClient.errorText(activity, r), Toast.LENGTH_LONG).show()
                    }
                }
            }
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener {
                val body = JSONObject().put("email", email).put("purpose", "verify")
                ApiClient.callAsync(activity, "resend_code", body, authenticated = false) { r ->
                    Toast.makeText(
                        activity,
                        if (r.ok) activity.getString(R.string.verify_resent) else ApiClient.errorText(activity, r),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        dialog.show()
    }
}
