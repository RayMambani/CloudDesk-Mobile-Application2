package com.example.cloudesk;

/**
 * Lets a logged-in user manage their own account: name, phone (used for SMS
 * notifications), password, email/SMS notification preferences, and app language.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014\u00a8\u0006\b"}, d2 = {"Lcom/example/cloudesk/SettingsActivity;", "Lcom/example/cloudesk/BaseActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "app_debug"})
public final class SettingsActivity extends com.example.cloudesk.BaseActivity {
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String PREFS_NOTIF = "cloudesk_notifications";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String KEY_EMAIL_NOTIF = "email_notifications_enabled";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String KEY_SMS_NOTIF = "sms_notifications_enabled";
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.SettingsActivity.Companion Companion = null;
    
    public SettingsActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/example/cloudesk/SettingsActivity$Companion;", "", "()V", "KEY_EMAIL_NOTIF", "", "KEY_SMS_NOTIF", "PREFS_NOTIF", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}