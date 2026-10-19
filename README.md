# CloudDesk — IT Helpdesk Ticketing App (Android)

CloudDesk is an IT helpdesk app. Employees log tickets when they have a technical problem
(broken laptop, network fault, locked account). Technicians and admins pick the tickets up,
set the priority, reply, and track each one to resolution against a deadline (SLA).

A matching **website version** (PHP + MySQL/PostgreSQL) has the same features.

[![Android CI](../../actions/workflows/main.yml/badge.svg)](../../actions)

## Features

**Everyone**
- Sign in with email and password, Google account, or fingerprint
- Create an account (always a normal *user*; admins can promote people)
- Forgot password with a security question
- Dark / light theme and four languages: English, Sepedi, Tshivenda, Xitsonga
- FAQ, in-app notifications, settings (profile, notifications, language, theme, password, security question)

**Users**
- Submit tickets: title, category, priority, description, optional attachment
- Live AI priority suggestion while typing
- Follow ticket status, history and deadline; comment on tickets
- Rate the fix (1–5 stars + comment) once a ticket is resolved
- Chat with support (default replies)

**Technicians**
- See tickets assigned to them, change status and priority
- Reply to users and notify them by email or SMS
- Chat with Gemini AI assistance

**Admins** (everything technicians can do, plus)
- Assign tickets to technicians
- User management: add users, change roles, activate/deactivate accounts, reset passwords
  (the admin can notify the user by email and SMS)
- Analytics charts, performance reports (CSV / print / email) and CSV export

**Tickets**
- Status: open, in progress, resolved, closed
- Priority: low, medium, high, critical
- SLA targets: critical 4 h, high 24 h, medium 72 h, low 120 h; overdue tickets are flagged
- Every change is written to a ticket history

## How it works

| Part | What it uses |
|------|--------------|
| Language / UI | Kotlin, Android Views, Material Components |
| Local data | Room (SQLite) database on the phone: users, tickets, comments, history, chat |
| Passwords | Salted PBKDF2 hashes (never stored in plain text) |
| Sign-in | Google Sign-In (play-services-auth), BiometricPrompt for fingerprint |
| AI priority | On-device keyword scoring (`TicketPriorityAI`) |
| Chat AI | Google Gemini API, **admin and technician only**; users get default replies |
| Charts | MPAndroidChart |
| Email / SMS | Opens the phone's own email and messaging apps with the message filled in |

Data in the Android app is stored **on the device**. The only online services are Google
Sign-In and the Gemini API.

## Demo accounts

The app creates demo data on first launch. All demo accounts use the password `password123`
and the security answer `blue`.

| Role | Email |
|------|-------|
| Admin | admin@clouddesk.com |
| Technician | john.tech@clouddesk.com |
| User | grace@company.com |

Change these before using the app for anything real.

## Getting started

Requirements: Android Studio (Hedgehog or newer), JDK 17, an emulator or Android phone (Android 7.0 / API 24+).

1. Clone the repository and open it in Android Studio. Wait for Gradle sync.
2. Create `app/src/main/java/com/example/cloudesk/GeminiConfig.kt` (it is git-ignored so your key stays private):
   ```kotlin
   package com.example.cloudesk

   object GeminiConfig {
       const val API_KEY = "YOUR_GEMINI_API_KEY"   // free key: https://aistudio.google.com/apikey
       const val MODEL = "gemini-3.6-flash"
   }
   ```
   If the repository contains `GeminiConfig.kt.example`, copy it to `GeminiConfig.kt` and edit it.
   Without a real key the app still works; the chat falls back to default replies.
3. Press **Run**.

### Google Sign-In (optional)
1. In Google Cloud Console create an **Android** OAuth client with package name `com.example.cloudesk`
   and your debug SHA-1 (`.\gradlew signingReport`).
2. Add your Google account as a test user on the OAuth consent screen.

## Automated tests

Unit tests live in `app/src/test` and use JUnit and Robolectric. They cover password hashing,
forgot-password answers, AI priority, SLA deadlines, status/priority/category translations,
the signed-in session, and the database (accounts, roles, deactivation, tickets, ratings,
comments, history, chat, demo data).

Run them locally:
```powershell
.\gradlew testDebugUnitTest
```

GitHub Actions (`.github/workflows/main.yml`) runs the tests, builds the debug APK and runs lint
on every push. You can also start it by hand from the **Actions** tab with **Run workflow**.
Test reports and the APK are attached to each run.

## Project layout

```
app/src/main/java/com/example/cloudesk/   activities, DAOs, entities, helpers
app/src/main/res/                         layouts, strings (values, values-b+nso, values-ve, values-ts)
app/src/test/                             automated unit tests
.github/workflows/                        GitHub Actions
```

## Security notes
- Never commit your Gemini API key. A key inside an app can be extracted from the APK; for a
  public release, route Gemini calls through your own server.
- Admin password resets send the new password by email/SMS; users are told to change it in Settings.

## YouTube Video Link
https://youtu.be/Isd19GgFsuU?si=Ym-hSLwuuD2-HpBq


