package com.example.cloudesk;

/**
 * Stores and applies the user's Light/Dark choice. Default is Dark (the original blue look).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/example/cloudesk/ThemeHelper;", "", "()V", "KEY", "", "MODE_DARK", "MODE_LIGHT", "PREFS", "applyTheme", "", "context", "Landroid/content/Context;", "getThemeMode", "setThemeMode", "mode", "app_debug"})
public final class ThemeHelper {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFS = "cloudesk_theme";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY = "theme_mode";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String MODE_LIGHT = "light";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String MODE_DARK = "dark";
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.ThemeHelper INSTANCE = null;
    
    private ThemeHelper() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getThemeMode(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    public final void setThemeMode(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String mode) {
    }
    
    public final void applyTheme(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
}