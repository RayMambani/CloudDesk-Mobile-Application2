package com.example.cloudesk;

/**
 * Persists the user's chosen app language and applies it to any Context.
 *
 * Usage: every Activity should extend BaseActivity (which calls wrapContext in
 * attachBaseContext) so the saved language is applied before that screen's views
 * inflate. Change the language via setLocale() + recreate()/restart the activity.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rJ\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0004J\u000e\u0010\u0011\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0012"}, d2 = {"Lcom/example/cloudesk/LocaleHelper;", "", "()V", "DEFAULT_LANGUAGE", "", "KEY_LANGUAGE", "PREFS_NAME", "SUPPORTED_LANGUAGES", "Ljava/util/LinkedHashMap;", "getSUPPORTED_LANGUAGES", "()Ljava/util/LinkedHashMap;", "getLanguage", "context", "Landroid/content/Context;", "setLocale", "", "languageCode", "wrapContext", "app_debug"})
public final class LocaleHelper {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFS_NAME = "cloudesk_locale";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_LANGUAGE = "language";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String DEFAULT_LANGUAGE = "en";
    @org.jetbrains.annotations.NotNull
    private static final java.util.LinkedHashMap<java.lang.String, java.lang.String> SUPPORTED_LANGUAGES = null;
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.LocaleHelper INSTANCE = null;
    
    private LocaleHelper() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.LinkedHashMap<java.lang.String, java.lang.String> getSUPPORTED_LANGUAGES() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLanguage(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    public final void setLocale(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String languageCode) {
    }
    
    /**
     * Wraps a Context so its resources resolve using the saved language.
     */
    @org.jetbrains.annotations.NotNull
    public final android.content.Context wrapContext(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
}