package com.example.cloudesk;

/**
 * Populates the database with simulated demo data the first time the app runs
 * (i.e. only if the users table is still empty), so the app has realistic
 * content to demonstrate every feature — role-based dashboards, AI priority
 * sorting, assigned tickets, comment threads, and chat history — without
 * needing to manually register accounts and create tickets by hand first.
 *
 * All seeded accounts use the password "password123" (see the printed login
 * list this function logs on first run). This never overwrites or touches
 * existing data — it only runs when every table is empty.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u0004H\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/example/cloudesk/SeedData;", "", "()V", "PASSWORD", "", "SECURITY_ANSWER", "daysAgo", "days", "", "time", "seedIfEmpty", "", "db", "Lcom/example/cloudesk/AppDatabase;", "app_debug"})
public final class SeedData {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PASSWORD = "password123";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String SECURITY_ANSWER = "blue";
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.SeedData INSTANCE = null;
    
    private SeedData() {
        super();
    }
    
    private final java.lang.String daysAgo(int days, java.lang.String time) {
        return null;
    }
    
    public final void seedIfEmpty(@org.jetbrains.annotations.NotNull
    com.example.cloudesk.AppDatabase db) {
    }
}