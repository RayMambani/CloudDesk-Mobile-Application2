package com.example.cloudesk;

/**
 * Bridges between the app's canonical, always-English stored values (used in
 * every comparison throughout the app: sorting, filtering, AI scoring,
 * report grouping) and what gets displayed to the user in their chosen
 * language.
 *
 * IMPORTANT: status, priority, and category are stored in the database as
 * fixed English keys ("open", "high", "network", etc.) because business logic
 * elsewhere compares against those exact strings (e.g. TicketPriorityAI,
 * PerformanceReportActivity's resolved/closed checks, AI queue sorting).
 * Never store a localized display string in the database — always store the
 * canonical key from *_KEYS below, and only translate for display via the
 * *Display() functions.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0005J\u0019\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u00112\u0006\u0010\r\u001a\u00020\u000e\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0005J\u0016\u0010\u0015\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0005J\u0019\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u00112\u0006\u0010\r\u001a\u00020\u000e\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0017\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0005J\u0016\u0010\u0018\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0005J\u0019\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u00112\u0006\u0010\r\u001a\u00020\u000e\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u001a\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0005R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007\u00a8\u0006\u001b"}, d2 = {"Lcom/example/cloudesk/TicketVocabulary;", "", "()V", "CATEGORY_KEYS", "", "", "getCATEGORY_KEYS", "()Ljava/util/List;", "PRIORITY_KEYS", "getPRIORITY_KEYS", "STATUS_KEYS", "getSTATUS_KEYS", "categoryDisplay", "context", "Landroid/content/Context;", "key", "categoryDisplayList", "", "(Landroid/content/Context;)[Ljava/lang/String;", "categoryKeyFromDisplay", "display", "priorityDisplay", "priorityDisplayList", "priorityKeyFromDisplay", "statusDisplay", "statusDisplayList", "statusKeyFromDisplay", "app_debug"})
public final class TicketVocabulary {
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.String> PRIORITY_KEYS = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.String> CATEGORY_KEYS = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.String> STATUS_KEYS = null;
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.TicketVocabulary INSTANCE = null;
    
    private TicketVocabulary() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getPRIORITY_KEYS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getCATEGORY_KEYS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getSTATUS_KEYS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String priorityDisplay(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String key) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String statusDisplay(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String key) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String categoryDisplay(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String key) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String[] priorityDisplayList(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String[] categoryDisplayList(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String[] statusDisplayList(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    /**
     * Reverse lookup: what the user sees in a dropdown -> the canonical English key to store.
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String priorityKeyFromDisplay(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String display) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String categoryKeyFromDisplay(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String display) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String statusKeyFromDisplay(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String display) {
        return null;
    }
}