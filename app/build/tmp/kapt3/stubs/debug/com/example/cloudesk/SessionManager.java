package com.example.cloudesk;

/**
 * Small wrapper around SharedPreferences that tracks who is currently logged in.
 * Used so Logout / Switch User can fully clear state and always land back on
 * the Login screen, regardless of which Activity the user is currently on.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\fH\u0002J.\u0010\u0016\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/example/cloudesk/SessionManager;", "", "()V", "KEY_USER_EMAIL", "", "KEY_USER_ID", "KEY_USER_NAME", "KEY_USER_ROLE", "PREFS_NAME", "clearSession", "", "context", "Landroid/content/Context;", "getUserEmail", "getUserId", "", "getUserName", "getUserRole", "isLoggedIn", "", "prefs", "Landroid/content/SharedPreferences;", "saveSession", "userId", "userName", "userRole", "email", "app_debug"})
public final class SessionManager {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFS_NAME = "cloudesk_session";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_USER_ID = "user_id";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_USER_NAME = "user_name";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_USER_ROLE = "user_role";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_USER_EMAIL = "user_email";
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.SessionManager INSTANCE = null;
    
    private SessionManager() {
        super();
    }
    
    private final android.content.SharedPreferences prefs(android.content.Context context) {
        return null;
    }
    
    public final void saveSession(@org.jetbrains.annotations.NotNull
    android.content.Context context, int userId, @org.jetbrains.annotations.NotNull
    java.lang.String userName, @org.jetbrains.annotations.NotNull
    java.lang.String userRole, @org.jetbrains.annotations.NotNull
    java.lang.String email) {
    }
    
    public final boolean isLoggedIn(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return false;
    }
    
    public final int getUserId(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getUserName(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getUserRole(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getUserEmail(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    /**
     * Clears the active session. Used by both Logout and Switch User.
     */
    public final void clearSession(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
}