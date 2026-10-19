package com.example.cloudesk;

/**
 * Keeps the local database (Room) in step with the online one.
 *
 * The ONLINE database is the source of truth. After sign-in and after every change the
 * server sends back everything this user may see (staff: all tickets and users; a normal
 * user: only their own tickets). [apply] replaces the local copy with that data, so every
 * screen keeps reading from Room exactly as before.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\bH\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\bH\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\bH\u0002J.\u0010\u0011\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\b2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00150\u00142\b\u0010\u0016\u001a\u0004\u0018\u00010\u0015H\u0002J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\f\u001a\u00020\bJ$\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u001b2\u0014\b\u0002\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u00040\u001dJ/\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H!0 \"\u0004\b\u0000\u0010!*\u0004\u0018\u00010\"2\u0012\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u0002H!0\u001dH\u0082\bJ\u0016\u0010$\u001a\u0004\u0018\u00010\u0015*\u00020\b2\u0006\u0010%\u001a\u00020\u0015H\u0002\u00a8\u0006&"}, d2 = {"Lcom/example/cloudesk/ServerSync;", "", "()V", "apply", "", "context", "Landroid/content/Context;", "json", "Lorg/json/JSONObject;", "clearLocal", "parseChat", "Lcom/example/cloudesk/ChatMessageEntity;", "o", "parseComment", "Lcom/example/cloudesk/CommentEntity;", "parseHistory", "Lcom/example/cloudesk/TicketHistoryEntity;", "parseTicket", "Lcom/example/cloudesk/TicketEntity;", "localAttachments", "", "", "token", "parseUser", "Lcom/example/cloudesk/UserEntity;", "refreshAsync", "activity", "Landroid/app/Activity;", "onDone", "Lkotlin/Function1;", "", "mapObjects", "", "T", "Lorg/json/JSONArray;", "transform", "str", "name", "app_debug"})
public final class ServerSync {
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.ServerSync INSTANCE = null;
    
    private ServerSync() {
        super();
    }
    
    /**
     * Pulls the latest data from the server; [onDone] gets true when it worked.
     */
    public final void refreshAsync(@org.jetbrains.annotations.NotNull
    android.app.Activity activity, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onDone) {
    }
    
    @kotlin.jvm.Synchronized
    public final synchronized void apply(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    org.json.JSONObject json) {
    }
    
    /**
     * Forget everything about the signed-in person (logout / expired session).
     */
    @kotlin.jvm.Synchronized
    public final synchronized void clearLocal(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
    
    private final java.lang.String str(org.json.JSONObject $this$str, java.lang.String name) {
        return null;
    }
    
    private final <T extends java.lang.Object>java.util.List<T> mapObjects(org.json.JSONArray $this$mapObjects, kotlin.jvm.functions.Function1<? super org.json.JSONObject, ? extends T> transform) {
        return null;
    }
    
    /**
     * The password never leaves the server, so the local row holds no password.
     */
    @org.jetbrains.annotations.NotNull
    public final com.example.cloudesk.UserEntity parseUser(@org.jetbrains.annotations.NotNull
    org.json.JSONObject o) {
        return null;
    }
    
    private final com.example.cloudesk.TicketEntity parseTicket(org.json.JSONObject o, java.util.Map<java.lang.String, java.lang.String> localAttachments, java.lang.String token) {
        return null;
    }
    
    private final com.example.cloudesk.CommentEntity parseComment(org.json.JSONObject o) {
        return null;
    }
    
    private final com.example.cloudesk.TicketHistoryEntity parseHistory(org.json.JSONObject o) {
        return null;
    }
    
    private final com.example.cloudesk.ChatMessageEntity parseChat(org.json.JSONObject o) {
        return null;
    }
}