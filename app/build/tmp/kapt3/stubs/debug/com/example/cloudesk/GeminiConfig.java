package com.example.cloudesk;

/**
 * Gemini API configuration.
 *
 * IMPORTANT — different security situation than the website version: this is a
 * compiled Android app, not a server. Anything in here (including this API key)
 * gets bundled into the APK and CAN be extracted by anyone who decompiles it —
 * there's no way to truly hide a secret inside a client-side app. That's fine
 * for a student/demo project, but if this app were ever published publicly,
 * the right approach is to route Gemini calls through your own small backend
 * (so the key lives on a server you control, never on the device) rather than
 * calling Gemini directly from the app as done here.
 *
 * Get a free key: https://aistudio.google.com/apikey
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/example/cloudesk/GeminiConfig;", "", "()V", "API_KEY", "", "MODEL", "app_debug"})
public final class GeminiConfig {
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String API_KEY = "AQ.Ab8RN6Is_PJtVw7I8MYLXRHb8_zCGB2RRUvoSM2ZpxL8dDYKBg";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String MODEL = "gemini-3.8-flash";
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.GeminiConfig INSTANCE = null;
    
    private GeminiConfig() {
        super();
    }
}