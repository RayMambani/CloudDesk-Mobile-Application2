# Takes the CloudDesk app screenshots from a connected phone, one screen at a time.
# Needs: phone connected by USB with USB debugging on, and adb (part of Android Studio platform-tools).
$ErrorActionPreference = "Stop"
$adb = (Get-Command adb -ErrorAction SilentlyContinue).Source
if (-not $adb) { $adb = Join-Path $env:LOCALAPPDATA "Android\Sdk\platform-tools\adb.exe" }
if (-not (Test-Path $adb)) { Write-Host "adb not found. Install platform-tools in Android Studio (SDK Manager)."; exit 1 }
$devices = & $adb devices | Select-String "device$"
if (-not $devices) { Write-Host "No phone found. Connect it by USB, turn on USB debugging and accept the prompt on the phone."; exit 1 }
$out = Join-Path (Split-Path -Parent $PSScriptRoot) "docs\screenshots"
New-Item -ItemType Directory -Force $out | Out-Null
Write-Host "Saving to $out"; Write-Host "For each screen: open it on the phone, then press Enter here. Type s to skip, q to quit."
$shots = @(
  @("app-login", "Sign in", "Log out, so the sign-in screen is showing."),
  @("app-register", "Create an account", "Tap Register on the sign-in screen."),
  @("app-verify-dialog", "Verify your email", "Fill in the register form and tap Register, so the code dialog appears."),
  @("app-forgot-password", "Forgot password - email", "Tap Forgot password on the sign-in screen."),
  @("app-forgot-code", "Forgot password - code and new password", "Enter an email on the previous screen and tap the button, so the code fields show."),
  @("app-change-password", "Choose your own password", "Sign in with an account an admin just created (temporary password)."),
  @("app-user-dashboard", "User dashboard", "Sign in as a normal user, for example grace@company.com."),
  @("app-submit-ticket", "Create a ticket", "Tap the + button on the user dashboard."),
  @("app-ticket-detail", "Ticket detail", "Open any ticket."),
  @("app-confirm", "Is it really fixed?", "Open a RESOLVED ticket as its owner and scroll to the Is it really fixed? box."),
  @("app-reopen-dialog", "Reopen with a reason", "On the same screen tap No, reopen it."),
  @("app-rating", "Rate the service", "On a resolved ticket, scroll to the rating card."),
  @("app-chat", "Live chat support", "Profile menu, then Live chat."),
  @("app-analytics", "Analytics", "Profile menu, then Analytics."),
  @("app-faq", "Help and FAQ", "Profile menu, then FAQ."),
  @("app-profile-menu", "Profile menu", "Tap the round profile button at the top right."),
  @("app-settings", "Settings", "Profile menu, then Settings."),
  @("app-tech-dashboard", "Technician dashboard", "Sign in as a technician, for example john.tech@clouddesk.com."),
  @("app-tech-ticket", "Update a ticket", "Open a ticket as a technician."),
  @("app-bulk-dialog", "Update all shown tickets", "Tap a category chip such as Network, then Profile menu, then Update all shown tickets."),
  @("app-admin-dashboard", "Admin dashboard", "Sign in as admin@clouddesk.com."),
  @("app-admin-menu", "Admin menu", "Tap the profile button as admin."),
  @("app-users", "User management", "Profile menu, then Users."),
  @("app-add-user-dialog", "Add a user", "On the Users screen tap the add button."),
  @("app-performance", "Performance report", "Profile menu, then Performance."),
  @("app-delete-dialog", "Delete a ticket with a reason", "Open a ticket as admin and tap Delete ticket."),
  @("app-deleted", "Deleted tickets", "Profile menu, then Deleted tickets."),
  @("app-audit", "Audit log", "Profile menu, then Audit log."),
  @("app-server-info", "Online data", "Settings, then the Online data button."),
  @("app-language", "Another language", "Settings, choose a language and save, then go back to the dashboard."),
  @("app-light-theme", "Light theme", "Settings, choose Light and save.")
)
$i = 0
foreach ($s in $shots) {
  $i++
  Write-Host ""
  Write-Host ("[{0}/{1}] {2}" -f $i, $shots.Count, $s[1]) -ForegroundColor Cyan
  Write-Host ("    How: " + $s[2])
  $a = Read-Host "    Press Enter when the screen is showing (s = skip, q = quit)"
  if ($a -eq "q") { break }
  if ($a -eq "s") { continue }
  $file = Join-Path $out ($s[0] + ".png")
  cmd /c "`"$adb`" exec-out screencap -p > `"$file`""
  if ((Get-Item $file).Length -lt 5000) { Write-Host "    That looks empty, try again." -ForegroundColor Yellow } else { Write-Host "    Saved $($s[0]).png" -ForegroundColor Green }
}
Write-Host ""; Write-Host "Done. Next: git add -A, commit and push."
