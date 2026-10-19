package com.example.cloudesk;

/**
 * Where the online backend lives. CloudDesk's accounts, tickets, comments, history and
 * chat are stored in the ONLINE database behind the website's api.php (hosted on Render).
 * The app keeps a local copy (Room) only so screens load instantly and work read-only offline.
 *
 * 1. Deploy the website to Render (see the website README) and paste its address below.
 * 2. Paste the Google *Web application* OAuth client ID (the same one the website uses)
 *   so "Sign in with Google" can be verified by the server.
 *
 * Testing on the Android emulator against XAMPP on your PC: http://10.0.2.2/cloudDeskWeb/
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u0004J\u0006\u0010\f\u001a\u00020\nR\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/example/cloudesk/ApiConfig;", "", "()V", "API_URL", "", "getAPI_URL", "()Ljava/lang/String;", "BASE_URL", "GOOGLE_WEB_CLIENT_ID", "isConfigured", "", "url", "isGoogleConfigured", "app_debug"})
public final class ApiConfig {
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String BASE_URL = "https://clouddesk-web-application.onrender.com/";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String GOOGLE_WEB_CLIENT_ID = "350970218855-j2m3i3t22u40nr1isrlfmapde2g3om7v.apps.googleusercontent.com.apps.googleusercontent.com";
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.ApiConfig INSTANCE = null;
    
    private ApiConfig() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getAPI_URL() {
        return null;
    }
    
    /**
     * [url] is only passed by tests; the app always checks BASE_URL.
     */
    public final boolean isConfigured(@org.jetbrains.annotations.NotNull
    java.lang.String url) {
        return false;
    }
    
    public final boolean isGoogleConfigured() {
        return false;
    }
}