package com.example.cloudesk;

/**
 * Fingerprint / face / screen-lock sign-in. A user opts in from Settings; the app then
 * remembers *which* account is enabled on this device (no password is stored) and the
 * Login screen offers a one-tap biometric button for it.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J@\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00062\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\u00102\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n0\u0012J\u000e\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0015J\"\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00042\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0006J\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/example/cloudesk/BiometricHelper;", "", "()V", "AUTHENTICATORS", "", "KEY_DEVICE_TOKEN", "", "KEY_USER_ID", "PREFS", "authenticate", "", "activity", "Landroidx/fragment/app/FragmentActivity;", "title", "subtitle", "onSuccess", "Lkotlin/Function0;", "onFailure", "Lkotlin/Function1;", "disable", "context", "Landroid/content/Context;", "enable", "userId", "deviceToken", "getDeviceToken", "getEnabledUserId", "isAvailable", "", "isEnabledFor", "app_debug"})
public final class BiometricHelper {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFS = "cloudesk_biometric";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_USER_ID = "enabled_user_id";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_DEVICE_TOKEN = "device_token";
    private static final int AUTHENTICATORS = 33023;
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.BiometricHelper INSTANCE = null;
    
    private BiometricHelper() {
        super();
    }
    
    public final boolean isAvailable(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return false;
    }
    
    public final int getEnabledUserId(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return 0;
    }
    
    public final boolean isEnabledFor(@org.jetbrains.annotations.NotNull
    android.content.Context context, int userId) {
        return false;
    }
    
    /**
     * The server-issued token that lets this phone sign in with a fingerprint (no password is stored).
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getDeviceToken(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    public final void enable(@org.jetbrains.annotations.NotNull
    android.content.Context context, int userId, @org.jetbrains.annotations.Nullable
    java.lang.String deviceToken) {
    }
    
    public final void disable(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
    
    public final void authenticate(@org.jetbrains.annotations.NotNull
    androidx.fragment.app.FragmentActivity activity, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onFailure) {
    }
}