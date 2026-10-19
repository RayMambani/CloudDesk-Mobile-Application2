package com.example.cloudesk

import org.json.JSONObject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream

class SubmitTicketActivity : BaseActivity() {

    private var selectedFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_ticket)

        val userId = intent.getIntExtra("USER_ID", -1)
        if (userId == -1) {
            Toast.makeText(this, getString(R.string.toast_user_not_logged_in), Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val titleEditText = findViewById<TextInputEditText>(R.id.ticketTitle)
        val descriptionEditText = findViewById<TextInputEditText>(R.id.ticketDescription)
        val categoryDropdown = findViewById<AutoCompleteTextView>(R.id.categoryDropdown)
        val priorityDropdown = findViewById<AutoCompleteTextView>(R.id.priorityDropdown)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val attachmentBox = findViewById<LinearLayout>(R.id.attachmentBox)
        val attachmentText = findViewById<TextView>(R.id.attachmentText)

        attachmentBox.setOnClickListener {
            if (selectedFileUri != null) {
                // Already have a file selected — offer to change or remove it.
                androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle(attachmentText.text.toString())
                    .setItems(arrayOf(getString(R.string.action_change_file), getString(R.string.action_remove_attachment))) { _, which ->
                        if (which == 0) {
                            openFilePicker()
                        } else {
                            selectedFileUri = null
                            attachmentText.text = getString(R.string.submit_ticket_attachment_hint)
                        }
                    }
                    .show()
            } else {
                openFilePicker()
            }
        }

        // Populate Dropdowns — displayed in the current language, but the
        // canonical English key is what actually gets stored (see TicketVocabulary).
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, TicketVocabulary.categoryDisplayList(this))
        categoryDropdown.setAdapter(categoryAdapter)

        val priorityAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, TicketVocabulary.priorityDisplayList(this))
        priorityDropdown.setAdapter(priorityAdapter)

        val db = AppDatabase.getDatabase(this)
        val ticketDao = db.ticketDao()

        // --- AI Priority Suggestion ---
        val aiSuggestionCard = findViewById<MaterialCardView>(R.id.aiSuggestionCard)
        val aiSuggestionText = findViewById<TextView>(R.id.aiSuggestionText)
        val applyAiSuggestionButton = findViewById<Button>(R.id.applyAiSuggestionButton)
        var lastSuggestion: TicketPriorityAI.PriorityResult? = null

        fun runAiSuggestion() {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val category = TicketVocabulary.categoryKeyFromDisplay(this, categoryDropdown.text.toString())

            if (title.length < 3 && description.length < 5) {
                aiSuggestionCard.visibility = android.view.View.GONE
                return
            }

            val result = TicketPriorityAI.analyze(title, description, category)
            lastSuggestion = result
            aiSuggestionCard.visibility = android.view.View.VISIBLE
            val localizedPriority = TicketVocabulary.priorityDisplay(this, result.priority.label)
            aiSuggestionText.text = getString(R.string.ai_priority_suggested_format, localizedPriority, result.confidence) +
                if (result.matchedSignals.isNotEmpty()) {
                    getString(R.string.ai_priority_based_on_format, result.matchedSignals.take(3).joinToString(", "))
                } else ""
        }

        val aiWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { runAiSuggestion() }
            override fun afterTextChanged(s: Editable?) {}
        }
        titleEditText.addTextChangedListener(aiWatcher)
        descriptionEditText.addTextChangedListener(aiWatcher)
        categoryDropdown.setOnItemClickListener { _, _, _, _ -> runAiSuggestion() }

        applyAiSuggestionButton.setOnClickListener {
            lastSuggestion?.let {
                val localizedPriority = TicketVocabulary.priorityDisplay(this, it.priority.label)
                priorityDropdown.setText(localizedPriority, false)
                Toast.makeText(this, getString(R.string.toast_priority_set_to, localizedPriority), Toast.LENGTH_SHORT).show()
            }
        }

        submitButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val category = TicketVocabulary.categoryKeyFromDisplay(this, categoryDropdown.text.toString())
            val priority = TicketVocabulary.priorityKeyFromDisplay(this, priorityDropdown.text.toString())

            if (title.isNotEmpty() && description.isNotEmpty() && category.isNotEmpty() && priority.isNotEmpty()) {
                
                submitButton.isEnabled = false
                val fileUri = selectedFileUri
                // Reading the file and calling the server both happen off the main thread.
                Thread {
                    var attachmentName: String? = null
                    var attachmentBase64: String? = null
                    var tooBig = false
                    if (fileUri != null) {
                        try {
                            val bytes = contentResolver.openInputStream(fileUri)?.use { it.readBytes() }
                            if (bytes != null) {
                                if (bytes.size > MAX_ATTACHMENT_BYTES) {
                                    tooBig = true
                                } else {
                                    attachmentName = getFileName(fileUri)
                                    attachmentBase64 = android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP)
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    runOnUiThread {
                        if (tooBig) {
                            submitButton.isEnabled = true
                            Toast.makeText(this@SubmitTicketActivity, getString(R.string.toast_attachment_too_large), Toast.LENGTH_LONG).show()
                            return@runOnUiThread
                        }
                        val body = JSONObject()
                            .put("title", title)
                            .put("description", description)
                            .put("category", category)
                            .put("priority", priority)
                        if (attachmentName != null && attachmentBase64 != null) {
                            body.put("attachmentName", attachmentName).put("attachmentBase64", attachmentBase64)
                        }
                        ApiClient.callAsync(this@SubmitTicketActivity, "ticket_create", body) { r ->
                            submitButton.isEnabled = true
                            val newId = r.json?.optString("ticketId", "") ?: ""
                            if (r.ok && newId.isNotEmpty()) {
                                onTicketSubmitted(newId, title, priority, fileUri)
                            } else {
                                Toast.makeText(this@SubmitTicketActivity, ApiClient.errorText(this@SubmitTicketActivity, r), Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }.start()
            } else {
                Toast.makeText(this@SubmitTicketActivity, getString(R.string.toast_fill_all_fields), Toast.LENGTH_SHORT).show()
            }
        }
    }

    /** The ticket now exists in the online database; show the confirmation and the email/SMS options. */
    private fun onTicketSubmitted(id: String, title: String, priority: String, fileUri: Uri?) {
        val db = AppDatabase.getDatabase(this)
        val userId = intent.getIntExtra("USER_ID", -1)
        // Keep opening the attachment from this phone's copy.
        if (fileUri != null) {
            db.ticketDao().getTicketById(id)?.let { db.ticketDao().insertTicket(it.copy(attachmentUri = fileUri.toString())) }
        }
                val attachmentNote = if (fileUri != null) getString(R.string.toast_ticket_submitted_with_attachment) else ""
                Toast.makeText(this@SubmitTicketActivity, getString(R.string.toast_ticket_submitted, attachmentNote), Toast.LENGTH_SHORT).show()

                val submitter = db.userDao().getUserById(userId)
                val localizedPriorityForEmail = TicketVocabulary.priorityDisplay(this, priority)
                val confirmationBody = "Hi ${submitter?.firstName ?: ""},\n\nYour ticket $id ('$title') has been submitted " +
                    "with $localizedPriorityForEmail priority. We'll follow up as it's worked on.\n\n— CloudDesk Support"
                androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle(getString(R.string.ticket_submitted_dialog_title))
                    .setMessage(getString(R.string.ticket_submitted_dialog_message))
                    .setPositiveButton(getString(R.string.action_email_me)) { _, _ ->
                        NotificationHelper.sendEmail(this, submitter?.email, "Ticket $id submitted", confirmationBody)
                        finish()
                    }
                    .setNeutralButton(getString(R.string.action_text_me)) { _, _ ->
                        if (submitter?.phone.isNullOrBlank()) {
                            Toast.makeText(this, getString(R.string.toast_add_phone_first), Toast.LENGTH_SHORT).show()
                        } else {
                            NotificationHelper.sendSms(this, submitter?.phone, confirmationBody)
                        }
                        finish()
                    }
                    .setNegativeButton(getString(R.string.action_no_thanks)) { _, _ -> finish() }
                    .setOnCancelListener { finish() }
                    .show()
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        startActivityForResult(intent, 1001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                try {
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (e: SecurityException) {
                    // Some providers don't support persistable permissions — the file will
                    // still work for this session, it just may not survive an app restart.
                    e.printStackTrace()
                }
                selectedFileUri = uri
                val attachmentText = findViewById<TextView>(R.id.attachmentText)
                attachmentText.text = getFileName(uri)
            }
        }
    }

    private fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (index >= 0) {
                        result = cursor.getString(index)
                    }
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != null && cut != -1) {
                result = result?.substring(cut + 1)
            }
        }
        return result ?: "file"
    }

    private fun getFileFromUri(uri: Uri): File? {
        val fileName = getFileName(uri)
        val file = File(cacheDir, fileName)
        try {
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            outputStream.close()
            inputStream.close()
            return file
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
