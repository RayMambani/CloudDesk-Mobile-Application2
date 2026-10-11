package com.example.cloudesk

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
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
    private var searchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            loadTickets(userRole)
            updateOfflineBanner()
            swipeRefresh.isRefreshing = false
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
        loadTickets(userRole)
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
                SessionManager.clearSession(this)
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

        // Seed data if database is empty (to show it works independently)
        if (ticketDao.getAllTickets().isEmpty()) {
            val seedTickets = listOf(
                TicketEntity("TKT-1001", "Login Issue", "Cannot log in with Google account", "open", "high", "Auth", "2026-05-11", 1),
                TicketEntity("TKT-1002", "Server Error on Save", "Getting 500 error when saving profile", "progress", "medium", "Backend", "2026-05-10", 1),
                TicketEntity("TKT-1003", "UI Alignment", "Buttons are cut off on small screens", "resolved", "low", "UI", "2026-05-09", 1)
            )
            ticketDao.insertTickets(seedTickets)
        }

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
                    (query.isEmpty() ||
                            t.title.lowercase().contains(query) ||
                            t.description.lowercase().contains(query) ||
                            t.id.lowercase().contains(query) ||
                            (t.assignedTo ?: "").lowercase().contains(query) ||
                            TicketVocabulary.categoryDisplay(this, t.category).lowercase().contains(query))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.ticketsRecyclerView)
        recyclerView.adapter = TicketAdapter(displayTickets, userId, userRole, myName)
    }
}