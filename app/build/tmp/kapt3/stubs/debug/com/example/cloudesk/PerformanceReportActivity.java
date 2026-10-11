package com.example.cloudesk;

/**
 * Admin-only screen. Computes real performance metrics per technician straight from the
 * local ticket data (no separate reporting backend needed) and lets the admin share a
 * text summary via email.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J4\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00140\u0019H\u0002J\u001d\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002\u00a2\u0006\u0002\u0010\u001dJ\u001d\u0010\u001e\u001a\u0004\u0018\u00010\u001b2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002\u00a2\u0006\u0002\u0010\u001dJ$\u0010 \u001a\u00020\u00142\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u0016H\u0002J\u0017\u0010#\u001a\u00020\u00142\b\u0010$\u001a\u0004\u0018\u00010\u001bH\u0002\u00a2\u0006\u0002\u0010%J\u0017\u0010&\u001a\u00020\u00142\b\u0010\'\u001a\u0004\u0018\u00010\u001bH\u0002\u00a2\u0006\u0002\u0010%J,\u0010(\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00140\u0019H\u0002J\u0012\u0010)\u001a\u00020\u00102\b\u0010*\u001a\u0004\u0018\u00010+H\u0014R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\n\u0010\u0006R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/example/cloudesk/PerformanceReportActivity;", "Lcom/example/cloudesk/BaseActivity;", "()V", "colorTextMain", "", "getColorTextMain", "()I", "colorTextMain$delegate", "Lkotlin/Lazy;", "colorTextMuted", "getColorTextMuted", "colorTextMuted$delegate", "createdFormat", "Ljava/text/SimpleDateFormat;", "resolvedFormat", "addTechnicianRow", "", "container", "Landroid/widget/LinearLayout;", "name", "", "assigned", "", "Lcom/example/cloudesk/TicketEntity;", "resolvedStatuses", "", "averageRating", "", "tickets", "(Ljava/util/List;)Ljava/lang/Double;", "averageResolutionHours", "resolvedTickets", "buildReport", "technicians", "Lcom/example/cloudesk/UserEntity;", "formatHours", "hours", "(Ljava/lang/Double;)Ljava/lang/String;", "formatRating", "avg", "formatTechnicianLine", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
public final class PerformanceReportActivity extends com.example.cloudesk.BaseActivity {
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy colorTextMain$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy colorTextMuted$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final java.text.SimpleDateFormat createdFormat = null;
    @org.jetbrains.annotations.NotNull
    private final java.text.SimpleDateFormat resolvedFormat = null;
    
    public PerformanceReportActivity() {
        super();
    }
    
    private final int getColorTextMain() {
        return 0;
    }
    
    private final int getColorTextMuted() {
        return 0;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final java.lang.String buildReport(java.util.List<com.example.cloudesk.TicketEntity> tickets, java.util.List<com.example.cloudesk.UserEntity> technicians) {
        return null;
    }
    
    private final java.lang.String formatTechnicianLine(java.lang.String name, java.util.List<com.example.cloudesk.TicketEntity> assigned, java.util.Set<java.lang.String> resolvedStatuses) {
        return null;
    }
    
    private final void addTechnicianRow(android.widget.LinearLayout container, java.lang.String name, java.util.List<com.example.cloudesk.TicketEntity> assigned, java.util.Set<java.lang.String> resolvedStatuses) {
    }
    
    /**
     * Approximate: created_at is date-only, resolvedAt is date+time.
     */
    private final java.lang.Double averageResolutionHours(java.util.List<com.example.cloudesk.TicketEntity> resolvedTickets) {
        return null;
    }
    
    private final java.lang.String formatHours(java.lang.Double hours) {
        return null;
    }
    
    private final java.lang.Double averageRating(java.util.List<com.example.cloudesk.TicketEntity> tickets) {
        return null;
    }
    
    private final java.lang.String formatRating(java.lang.Double avg) {
        return null;
    }
}