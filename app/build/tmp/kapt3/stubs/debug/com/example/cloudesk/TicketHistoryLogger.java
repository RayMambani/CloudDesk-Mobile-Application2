package com.example.cloudesk;

/**
 * Helpers for writing and describing ticket history entries.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ>\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00042\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0004J\u0006\u0010\u0012\u001a\u00020\u0004\u00a8\u0006\u0013"}, d2 = {"Lcom/example/cloudesk/TicketHistoryLogger;", "", "()V", "describe", "", "context", "Landroid/content/Context;", "e", "Lcom/example/cloudesk/TicketHistoryEntity;", "log", "", "db", "Lcom/example/cloudesk/AppDatabase;", "ticketId", "actor", "field", "old", "new", "now", "app_debug"})
public final class TicketHistoryLogger {
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.TicketHistoryLogger INSTANCE = null;
    
    private TicketHistoryLogger() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String now() {
        return null;
    }
    
    public final void log(@org.jetbrains.annotations.NotNull
    com.example.cloudesk.AppDatabase db, @org.jetbrains.annotations.NotNull
    java.lang.String ticketId, @org.jetbrains.annotations.NotNull
    java.lang.String actor, @org.jetbrains.annotations.NotNull
    java.lang.String field, @org.jetbrains.annotations.Nullable
    java.lang.String old, @org.jetbrains.annotations.Nullable
    java.lang.String p5_54480) {
    }
    
    /**
     * Human-readable, localized description of one history entry.
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String describe(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.example.cloudesk.TicketHistoryEntity e) {
        return null;
    }
}