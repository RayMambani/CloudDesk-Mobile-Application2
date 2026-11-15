# CloudDesk – Release notes

## v1.0 (14 November 2026) – first full release

This is the first complete release of CloudDesk. In the **prototype** the Android app and the website
each had their own separate database and ran only locally (on the phone, and on XAMPP), so a ticket
created in one never appeared in the other, and sign-in had little protection. Version 1.0 turns it into
one working system: the app and the website share one online database, with real sign-in security,
email verification, role-based dashboards, automatic work distribution and automated tests on GitHub.

### Innovative features added in this version

| Innovation | What it does | Why it matters |
|---|---|---|
| **Fair automatic ticket assignment** | Every new ticket goes to the active technician with the fewest open tickets (ties: fewest tickets overall, then lowest id). Admins can distribute a whole unassigned backlog with one **Auto-assign** button, most critical first. Each assignment is recorded in the history and the audit log with the reason "fewest open tickets". | Nobody is overloaded and no ticket waits for a manual decision. The rule is visible and explainable. |
| **"Is it really fixed?" owner confirmation + reopen** | When a ticket is resolved, only its owner can confirm the fix or reopen it with a reason. Staff see "awaiting confirmation" until then. The Performance Report shows, per technician, how many resolved tickets were **confirmed** and the **reopen rate**. | A technician can mark a ticket "resolved" while the user still has the problem. This lets an admin tell which resolved tickets are truly solved. |
| **Bulk resolve by category** | Filter by category (for example *Network*), tick the tickets, write one message and update them all. Every ticket gets the new status, a history entry, a comment, a notification and an email to its owner. | One outage affecting 30 people is closed in one action instead of 30. |
| **Deleted-ticket recovery and tamper-resistant audit log** | Admins delete tickets with a mandatory reason. Nothing is destroyed: the ticket moves to **Deleted tickets** with who, when and why, and can be restored. Every sensitive action (create user, reset password, delete, restore, bulk update, reopen, auto-assign) is written to the **Audit log**. | Accountability, and mistakes can be undone. |
| **Email-verified accounts with one-time codes** | Registration, sign-in of unverified accounts and password reset use a 6-digit code (valid 15 minutes, single use, locked after 5 wrong tries, only a hash stored). | Proves the person owns the email address; stops fake sign-ups. |
| **Brute-force protection stored in the database** | 5 wrong passwords lock an account for 60 seconds. The counter is in the database, not in a cookie, so it works for the website and the app and cannot be bypassed by clearing cookies. | Protects against password guessing. |
| **Admin-created accounts with forced password change** | An admin creates a technician or admin account without choosing a password. The person receives an email with a temporary password and must choose their own before anything else, on the website and in the app. | The admin never knows the final password. |
| **Resilient email delivery with a built-in test page** | Email is sent through the Gmail API, falling back to Brevo, then PHP `mail()`. An admin-only **Email test** page shows which provider is configured and exactly what each one answered. | Free hosting blocks normal email (SMTP); this works over HTTPS and makes failures easy to diagnose. |
| **Role-based dashboards and access control checked on the server** | Users see only their tickets; technicians see all tickets and work on them; only admins manage users, reports, deleted tickets and the audit log. Self-registration always creates a normal user; people cannot give themselves a role or department. | Least privilege. Hiding a button is not security, so the server refuses with 403. |
| **Common-password blocking** | Passwords such as `1234` or `password`, or ones containing your own email name, are refused. At least 8 characters with upper-case, lower-case and a number. | Weak passwords are the easiest way in. |

### Carried over from the prototype and improved

- AI-assisted priority scoring and SLA (due date / overdue) tracking.
- Live chat support with Google Gemini (admin/technician) and default replies for users.
- Fingerprint / screen-lock sign-in (Android) and passkeys (website), Google sign-in.
- Four languages: English, Sepedi, Tshivenda, Xitsonga; light and dark themes.
- Analytics, performance report and CSV export.

### All changes since the prototype

**Platform**
- Changed: the PHP website now runs online in Docker on Render instead of only on XAMPP.
- New: one JSON API (`api.php`) used by the Android app. The app signs in with a token and no longer stores passwords.
- Changed: the app and the website now read and write the same online PostgreSQL database (the website also still runs on MySQL and SQLite). The app keeps a Room cache so tickets can still be read offline, with an offline banner.
- New: database tables for email codes, API tokens, login throttling and the audit log; old databases upgrade automatically.

**Security and accounts**
- New: email verification, password reset by emailed code, approved email domains, password rules and common-password blocking.
- New: login throttling, session invalidation after a password reset (all other web sessions and app tokens stop working).
- New: forced password change for admin-created accounts; welcome email with temporary password.
- Fixed: users could choose their own role and department when registering. Roles are now set only by admins; the department comes from a fixed list.

**Tickets and workflow**
- New: automatic fair assignment; admin **Auto-assign** button.
- New: owner confirmation, reopen with reason, confirmed/reopen-rate in the performance report.
- New: category filter and bulk update with owner notifications.
- New: soft delete with reason, **Deleted tickets** page with restore, audit log.
- New: history entries for restored, reopened, confirmed and bulk-resolved events.

**Android app**
- New screens: choose-new-password, deleted tickets, audit log.
- New in the ticket screen: confirm / reopen for owners; delete for admins.
- New in the dashboard menu: update all shown tickets, auto-assign, category chips.
- New: verification-code dialog at sign-up and sign-in; forgot-password by email code.
- New: tooltips and content descriptions on every icon button.

**Quality and delivery**
- Extended: GitHub Actions for the website (PHP syntax check, **over 300 automated checks** on SQLite and MySQL, Docker build) and the Android app (unit tests, build, lint, downloadable APK).
- New: GitHub Release v1.0 with the installable `CloudDesk.apk`.
- Fixed: database errors on PostgreSQL when a long note was written to the history or audit log (columns are strict there).

### Known limitations

- The Sepedi, Tshivenda and Xitsonga translations of the newest screens in the app fall back to English.
- Delete, restore and the audit log are admin features; the app shows them, but the website remains the fuller management console.
- The free Render plan sleeps when idle, so the first request after a pause can take about a minute.
- The APK is a debug build, not published on Google Play.

### Upgrading from the prototype

Install the new APK over the old one (or uninstall first). Accounts and tickets that only existed in a prototype's local database do not carry over to the online system: register again, or ask an admin to create your account.
