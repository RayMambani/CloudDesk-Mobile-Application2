package com.example.cloudesk

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import android.view.ViewGroup
import org.json.JSONArray
import org.json.JSONObject
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : BaseActivity() {

    // Active filters (canonical English keys; null = "All")
    private var statusFilter: String? = null
    private var priorityFilter: String? = null
    private var categoryFilter: String? = null
    private var shownTickets: List<TicketEntity> = emptyList()
    private var searchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // An admin-created account must pick its own password before using the app.
        if (SessionManager.mustChangePassword(this)) {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
            finish()
            return
        }

        val welcomeTextView = findViewById<TextView>(R.id.welcomeText)
        val subtitleTextView = findViewById<TextView>(R.id.subtitleText)
        val userName = intent.getStringExtra("USER_NAME") ?: "User"
        val userRole = intent.getStringExtra("USER_ROLE") ?: "user"

        val roleTitle = when (userRole) {
            "admin" -> getString(R.string.dashboard_admin)
            "technician" -> getString(R.string.dashboard_technician)
            else -> getString(R.string.dashboard_user)
        }

        welcomeTextView.text = roleTitle
        subtitleTextView.text = getString(R.string.welcome_message, userName)

        findViewById<TextView>(R.id.profileInitial).text = userName.trim().firstOrNull()?.uppercase() ?: "U"

        val profileButton = findViewById<FrameLayout>(R.id.profileButton)
        profileButton.setOnClickListener { showProfileMenu(profileButton) }

        val recyclerView = findViewById<RecyclerView>(R.id.ticketsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val fabNewTicket = findViewById<FloatingActionButton>(R.id.fabNewTicket)

        if (userRole == "user") {
            fabNewTicket.visibility = View.VISIBLE
            fabNewTicket.setOnClickListener {
                val currentUserId = this@MainActivity.intent.getIntExtra("USER_ID", -1)
                val submitIntent = Intent(this, SubmitTicketActivity::class.java)
                submitIntent.putExtra("USER_ID", currentUserId)
                startActivity(submitIntent)
            }
        } else {
            fabNewTicket.visibility = View.GONE
        }

        setupFilters(userRole)

        // Pull down on the list to refresh it
        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            // Pull the latest data from the online database.
            ServerSync.refreshAsync(this) { ok ->
                loadTickets(userRole)
                updateOfflineBanner()
                swipeRefresh.isRefreshing = false
                if (!ok) Toast.makeText(this, getString(R.string.toast_server_unreachable), Toast.LENGTH_SHORT).show()
            }
        }

        requestNotificationPermissionIfNeeded()
        loadTickets(userRole)
    }

    private fun setupFilters(userRole: String) {
        val searchInput = findViewById<EditText>(R.id.searchInput)
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchQuery = s?.toString()?.trim() ?: ""
                loadTickets(userRole)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        val statusGroup = findViewById<ChipGroup>(R.id.statusChipGroup)
        addChip(statusGroup, getString(R.string.filter_all_status), null, true) { statusFilter = it; loadTickets(userRole) }
        TicketVocabulary.STATUS_KEYS.forEach { key ->
            addChip(statusGroup, TicketVocabulary.statusDisplay(this, key), key, false) { statusFilter = it; loadTickets(userRole) }
        }

        val priorityGroup = findViewById<ChipGroup>(R.id.priorityChipGroup)
        addChip(priorityGroup, getString(R.string.filter_all_priority), null, true) { priorityFilter = it; loadTickets(userRole) }
        TicketVocabulary.PRIORITY_KEYS.forEach { key ->
            addChip(priorityGroup, TicketVocabulary.priorityDisplay(this, key), key, false) { priorityFilter = it; loadTickets(userRole) }
        }

        // Staff: filter by category (network, hardware, ...) so they can resolve a whole group at once.
        if (userRole == "admin" || userRole == "technician") {
            val priorityRow = priorityGroup.parent as View
            val host = priorityRow.parent as ViewGroup
            val categoryGroup = ChipGroup(this).apply {
                isSingleLine = true; isSingleSelection = true; isSelectionRequired = true
            }
            host.addView(HorizontalScrollView(this).apply {
                isHorizontalScrollBarEnabled = false
                addView(categoryGroup)
            }, host.indexOfChild(priorityRow) + 1)
            addChip(categoryGroup, getString(R.string.filter_all_categories), null, true) { categoryFilter = it; loadTickets(userRole) }
            TicketVocabulary.CATEGORY_KEYS.forEach { key ->
                addChip(categoryGroup, TicketVocabulary.categoryDisplay(this, key), key, false) { categoryFilter = it; loadTickets(userRole) }
            }
        }
    }

    private fun addChip(group: ChipGroup, label: String, key: String?, checked: Boolean, onSelected: (String?) -> Unit) {
        val chip = Chip(this).apply {
            text = label
            isCheckable = true
            isChecked = checked
            id = View.generateViewId()
            setOnCheckedChangeListener { _, isChecked -> if (isChecked) onSelected(key) }
        }
        group.addView(chip)
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (android.os.Build.VERSION.SDK_INT >= 33 &&
            checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1001)
        }
    }

    override fun onResume() {
        super.onResume()
        val userRole = intent.getStringExtra("USER_ROLE") ?: "user"
        updateOfflineBanner()
        loadTickets(userRole) // show what we have right away...
        ServerSync.refreshAsync(this) { ok -> // ...then bring it up to date from the online database
            if (ok) loadTickets(userRole)
        }
    }

    private fun updateOfflineBanner() {
        findViewById<TextView>(R.id.offlineBanner).visibility =
            if (ConnectivityHelper.isOnline(this)) View.GONE else View.VISIBLE
    }

    private fun showProfileMenu(anchor: View) {
        val userId = intent.getIntExtra("USER_ID", -1)
        val userName = intent.getStringExtra("USER_NAME") ?: "User"
        val userRole = intent.getStringExtra("USER_ROLE") ?: "user"

        val popup = PopupMenu(this, anchor, Gravity.END)
        popup.menuInflater.inflate(R.menu.menu_profile, popup.menu)
        popup.menu.findItem(R.id.menu_performance).isVisible = (userRole == "admin")
        popup.menu.findItem(R.id.menu_users).isVisible = (userRole == "admin")
        popup.menu.findItem(R.id.menu_bulk).isVisible = (userRole == "admin" || userRole == "technician")
        popup.menu.findItem(R.id.menu_autoassign).isVisible = (userRole == "admin")
        popup.menu.findItem(R.id.menu_deleted).isVisible = (userRole == "admin")
        popup.menu.findItem(R.id.menu_audit).isVisible = (userRole == "admin")
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_analytics -> {
                    val i = Intent(this, AnalyticsActivity::class.java)
                    i.putExtra("USER_ID", userId)
                    i.putExtra("USER_ROLE", userRole)
                    startActivity(i)
                    true
                }
                R.id.menu_chat -> {
                    val i = Intent(this, ChatActivity::class.java)
                    i.putExtra("USER_ID", userId)
                    i.putExtra("USER_NAME", userName)
                    i.putExtra("USER_ROLE", userRole)
                    startActivity(i)
                    true
                }
                R.id.menu_faq -> {
                    startActivity(Intent(this, FaqActivity::class.java))
                    true
                }
                R.id.menu_performance -> {
                    val i = Intent(this, PerformanceReportActivity::class.java)
                    i.putExtra("USER_ID", userId)
                    i.putExtra("USER_ROLE", userRole)
                    startActivity(i)
                    true
                }
                R.id.menu_users -> {
                    val i = Intent(this, UserManagementActivity::class.java)
                    i.putExtra("USER_ID", userId)
                    i.putExtra("USER_ROLE", userRole)
                    startActivity(i)
                    true
                }
                R.id.menu_bulk -> { showBulkDialog(); true }
                R.id.menu_autoassign -> { autoAssign(); true }
                R.id.menu_deleted -> {
                    startActivity(Intent(this, AdminLogActivity::class.java).putExtra("MODE", "deleted")); true
                }
                R.id.menu_audit -> {
                    startActivity(Intent(this, AdminLogActivity::class.java).putExtra("MODE", "audit")); true
                }
                R.id.menu_settings -> {
                    val i = Intent(this, SettingsActivity::class.java)
                    i.putExtra("USER_ID", userId)
                    startActivity(i)
                    true
                }
                R.id.menu_logout -> {
                    confirmLogout()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun confirmLogout() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.logout_dialog_title))
            .setMessage(getString(R.string.logout_dialog_message))
            .setPositiveButton(getString(R.string.action_logout)) { _, _ ->
                // Tell the server to forget this login token (best effort), then wipe the local copy.
                val token = SessionManager.getToken(this)
                val appContext = applicationContext
                if (token != null) {
                    Thread { ApiClient.call(appContext, "logout") }.start()
                }
                ServerSync.clearLocal(this)
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton(getString(R.string.action_cancel), null)
            .show()
    }

    private fun loadTickets(userRole: String) {
        val db = AppDatabase.getDatabase(this)
        val ticketDao = db.ticketDao()

        val ticketsFromDb = ticketDao.getAllTickets()
        val userId = intent.getIntExtra("USER_ID", -1)
        val myName = intent.getStringExtra("USER_NAME") ?: ""

        // What this role is allowed to see (before search/filters)
        var visibleTickets = when (userRole) {
            "user" -> ticketsFromDb.filter { it.userId == userId }
            else -> ticketsFromDb // Admin and Technician see all
        }

        if (userRole == "admin" || userRole == "technician") {
            // AI-based triage: most urgent open work first.
            visibleTickets = visibleTickets.sortedByDescending { ticket ->
                val aiScore = TicketPriorityAI.analyze(ticket.title, ticket.description, ticket.category).rawScore
                val isOpenWork = if (ticket.status == "open" || ticket.status == "progress") 100 else 0
                isOpenWork + aiScore
            }
        }

        // ---- Technician: tickets assigned to me ----
        val banner = findViewById<TextView>(R.id.assignedBanner)
        if (userRole == "technician") {
            val mine = ticketsFromDb.filter { it.assignedTo == myName && it.status != "resolved" && it.status != "closed" }
            visibleTickets = visibleTickets.sortedByDescending { if (it.assignedTo == myName) 1 else 0 }
            if (mine.isNotEmpty()) {
                banner.text = getString(R.string.assigned_banner_format, mine.size)
                banner.visibility = View.VISIBLE
            } else {
                banner.visibility = View.GONE
            }
            NotificationHelper.notifyNewAssignments(this, userId, mine.map { it.id })
        } else {
            banner.visibility = View.GONE
        }

        // ---- Ticket owner: tell them about status changes / new replies ----
        if (userRole == "user") {
            val commentDao = db.commentDao()
            val commentsByOthers = visibleTickets.associate { t ->
                t.id to commentDao.getCommentsForTicket(t.id).count { it.userId != userId }
            }
            NotificationHelper.notifyTicketUpdates(this, userId, visibleTickets, commentsByOthers)
        }

        // ---- SLA: overdue count for staff ----
        val overdueBanner = findViewById<TextView>(R.id.overdueBanner)
        val overdueCount = visibleTickets.count { SlaHelper.isOverdue(it) }
        if ((userRole == "admin" || userRole == "technician") && overdueCount > 0) {
            overdueBanner.text = getString(R.string.overdue_banner_format, overdueCount)
            overdueBanner.visibility = View.VISIBLE
        } else {
            overdueBanner.visibility = View.GONE
        }

        // ---- Stats (always for everything this role can see) ----
        val total = visibleTickets.size
        val open = visibleTickets.count { it.status == "open" || it.status == "progress" }
        val resolved = visibleTickets.count { it.status == "resolved" }
        findViewById<TextView>(R.id.totalTicketsText).text = total.toString()
        findViewById<TextView>(R.id.openTicketsText).text = open.toString()
        findViewById<TextView>(R.id.resolvedTicketsText).text = resolved.toString()

        // ---- Search & filters ----
        val query = searchQuery.lowercase()
        val displayTickets = visibleTickets.filter { t ->
            (statusFilter == null || t.status == statusFilter) &&
                (priorityFilter == null || t.priority == priorityFilter) &&
                (categoryFilter == null || t.category == categoryFilter) &&
                (query.isEmpty() ||
                    t.title.lowercase().contains(query) ||
                    t.description.lowercase().contains(query) ||
                    t.id.lowercase().contains(query) ||
                    (t.assignedTo ?: "").lowercase().contains(query) ||
                    TicketVocabulary.categoryDisplay(this, t.category).lowercase().contains(query))
        }

        shownTickets = displayTickets
        val recyclerView = findViewById<RecyclerView>(R.id.ticketsRecyclerView)
        recyclerView.adapter = TicketAdapter(displayTickets, userId, userRole, myName)
    }

    /** Admin: hand every unassigned open ticket to the technician with the fewest open tickets. */
    private fun autoAssign() {
        ApiClient.callAsync(this, "ticket_auto_assign") { r ->
            if (r.ok) {
                Toast.makeText(this, getString(R.string.toast_autoassign_format, r.json?.optInt("assigned", 0) ?: 0), Toast.LENGTH_LONG).show()
                loadTickets(intent.getStringExtra("USER_ROLE") ?: "user")
            } else Toast.makeText(this, ApiClient.errorText(this, r), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Staff: update every ticket currently shown (respecting the category / status / search filters)
     * in one go, with one message that each ticket owner receives.
     */
    private fun showBulkDialog() {
        val statusKeys = listOf("resolved", "closed", "progress")
        val targets = shownTickets
        val pad = (16 * resources.displayMetrics.density).toInt()
        val form = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(pad, pad / 2, pad, 0) }
        val spinner = Spinner(this).apply {
            adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_dropdown_item,
                statusKeys.map { TicketVocabulary.statusDisplay(this@MainActivity, it) })
        }
        val note = EditText(this).apply { hint = getString(R.string.bulk_note_hint); maxLines = 4 }
        form.addView(spinner); form.addView(note)

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.bulk_title_format, targets.size))
            .setView(form)
            .setPositiveButton(getString(R.string.bulk_apply)) { _, _ ->
                val status = statusKeys[spinner.selectedItemPosition]
                val text = note.text.toString().trim()
                val ids = targets.filter { (it.status == "open" || it.status == "progress") && it.status != status }.map { it.id }
                when {
                    text.isEmpty() -> Toast.makeText(this, getString(R.string.toast_fill_all_fields), Toast.LENGTH_SHORT).show()
                    ids.isEmpty() -> Toast.makeText(this, getString(R.string.toast_bulk_none), Toast.LENGTH_LONG).show()
                    else -> {
                        val body = JSONObject().put("ids", JSONArray(ids)).put("status", status).put("note", text)
                        ApiClient.callAsync(this, "ticket_bulk_update", body) { r ->
                            if (r.ok) {
                                Toast.makeText(this, getString(R.string.toast_bulk_done_format, r.json?.optInt("updated", 0) ?: 0), Toast.LENGTH_LONG).show()
                                loadTickets(intent.getStringExtra("USER_ROLE") ?: "user")
                            } else Toast.makeText(this, ApiClient.errorText(this, r), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            .setNegativeButton(getString(R.string.action_cancel), null)
            .show()
    }
}
