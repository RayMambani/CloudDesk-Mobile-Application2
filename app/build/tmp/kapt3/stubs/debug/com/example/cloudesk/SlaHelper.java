package com.example.cloudesk;

/**
 * Service-level targets: how long each priority is allowed to stay unresolved.
 * Critical 4h, High 24h, Medium 72h, Low 120h.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u000b\u001a\u00020\u0004J\u0010\u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\r\u001a\u00020\bJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\b\u00a8\u0006\u0011"}, d2 = {"Lcom/example/cloudesk/SlaHelper;", "", "()V", "dueDate", "Ljava/util/Date;", "ticket", "Lcom/example/cloudesk/TicketEntity;", "dueLabel", "", "isOverdue", "", "now", "parseCreated", "created", "slaHours", "", "priority", "app_debug"})
public final class SlaHelper {
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.SlaHelper INSTANCE = null;
    
    private SlaHelper() {
        super();
    }
    
    public final int slaHours(@org.jetbrains.annotations.NotNull
    java.lang.String priority) {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Date parseCreated(@org.jetbrains.annotations.NotNull
    java.lang.String created) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.Date dueDate(@org.jetbrains.annotations.NotNull
    com.example.cloudesk.TicketEntity ticket) {
        return null;
    }
    
    public final boolean isOverdue(@org.jetbrains.annotations.NotNull
    com.example.cloudesk.TicketEntity ticket, @org.jetbrains.annotations.NotNull
    java.util.Date now) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String dueLabel(@org.jetbrains.annotations.NotNull
    com.example.cloudesk.TicketEntity ticket) {
        return null;
    }
}