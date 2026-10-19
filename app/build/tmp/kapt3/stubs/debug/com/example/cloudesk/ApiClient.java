package com.example.cloudesk;

/**
 * Talks to the online backend (api.php). Every call is a JSON POST:
 *  {"action": "...", ...}   with the header  X-Auth-Token: <token>  once signed in.
 *
 * call() blocks, so it must run off the main thread; callAsync() does that for you and
 * delivers the result on the main thread. Successful calls that return the user's data
 * refresh the local copy (ServerSync) automatically.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u001bB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\bJ>\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000f0\u0013J\u0016\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u0004J\u001d\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u0019H\u0000\u00a2\u0006\u0002\b\u001a\u00a8\u0006\u001c"}, d2 = {"Lcom/example/cloudesk/ApiClient;", "", "()V", "call", "Lcom/example/cloudesk/ApiClient$Result;", "context", "Landroid/content/Context;", "action", "", "body", "Lorg/json/JSONObject;", "authenticated", "", "baseUrl", "callAsync", "", "activity", "Landroid/app/Activity;", "onResult", "Lkotlin/Function1;", "errorText", "r", "parse", "text", "httpCode", "", "parse$app_debug", "Result", "app_debug"})
public final class ApiClient {
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.ApiClient INSTANCE = null;
    
    private ApiClient() {
        super();
    }
    
    /**
     * Blocking request. [baseUrl] is only overridden by tests.
     */
    @org.jetbrains.annotations.NotNull
    public final com.example.cloudesk.ApiClient.Result call(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String action, @org.jetbrains.annotations.NotNull
    org.json.JSONObject body, boolean authenticated, @org.jetbrains.annotations.NotNull
    java.lang.String baseUrl) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.cloudesk.ApiClient.Result parse$app_debug(@org.jetbrains.annotations.NotNull
    java.lang.String text, int httpCode) {
        return null;
    }
    
    /**
     * Runs [call] on a background thread and delivers the result on the main thread.
     * If the server says the session is no longer valid (token expired, account deactivated,
     * password reset by an admin) the user is sent back to the login screen.
     */
    public final void callAsync(@org.jetbrains.annotations.NotNull
    android.app.Activity activity, @org.jetbrains.annotations.NotNull
    java.lang.String action, @org.jetbrains.annotations.NotNull
    org.json.JSONObject body, boolean authenticated, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.example.cloudesk.ApiClient.Result, kotlin.Unit> onResult) {
    }
    
    /**
     * A message the user can read for a failed call.
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String errorText(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.example.cloudesk.ApiClient.Result r) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0015\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\nH\u00c6\u0003J?\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\u00032\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\nH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u0007H\u00d6\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013\u00a8\u0006\u001f"}, d2 = {"Lcom/example/cloudesk/ApiClient$Result;", "", "ok", "", "json", "Lorg/json/JSONObject;", "error", "", "networkError", "httpCode", "", "(ZLorg/json/JSONObject;Ljava/lang/String;ZI)V", "getError", "()Ljava/lang/String;", "getHttpCode", "()I", "getJson", "()Lorg/json/JSONObject;", "getNetworkError", "()Z", "getOk", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
    public static final class Result {
        private final boolean ok = false;
        @org.jetbrains.annotations.Nullable
        private final org.json.JSONObject json = null;
        @org.jetbrains.annotations.Nullable
        private final java.lang.String error = null;
        private final boolean networkError = false;
        private final int httpCode = 0;
        
        public Result(boolean ok, @org.jetbrains.annotations.Nullable
        org.json.JSONObject json, @org.jetbrains.annotations.Nullable
        java.lang.String error, boolean networkError, int httpCode) {
            super();
        }
        
        public final boolean getOk() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final org.json.JSONObject getJson() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String getError() {
            return null;
        }
        
        public final boolean getNetworkError() {
            return false;
        }
        
        public final int getHttpCode() {
            return 0;
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable
        public final org.json.JSONObject component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.String component3() {
            return null;
        }
        
        public final boolean component4() {
            return false;
        }
        
        public final int component5() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.cloudesk.ApiClient.Result copy(boolean ok, @org.jetbrains.annotations.Nullable
        org.json.JSONObject json, @org.jetbrains.annotations.Nullable
        java.lang.String error, boolean networkError, int httpCode) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}