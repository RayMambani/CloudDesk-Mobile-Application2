package com.example.cloudesk;

/**
 * Email & SMS integration.
 *
 * This project has no backend/mail-server or SMS-gateway API keys configured, so instead
 * of faking a server call, this hands off to the device's own email and messaging apps —
 * a real, working integration that needs no credentials. It's the same approach a lot of
 * lightweight apps use for "contact support" / "notify" actions.
 *
 * If a real backend gets added later (e.g. SendGrid for email, Twilio for SMS), this is
 * the single place to swap in an actual API call without touching any calling code.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\f\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u000eJ8\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000e2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\f0\u0014J(\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J(\u0010\u0019\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u001a\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u0004J \u0010\u001d\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u001e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001f\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/example/cloudesk/NotificationHelper;", "", "()V", "CHANNEL_ID", "", "PREFS_SEEN", "PREFS_SNAPSHOT", "notifyNewAssignments", "", "context", "Landroid/content/Context;", "technicianId", "", "assignedTicketIds", "", "notifyTicketUpdates", "userId", "tickets", "Lcom/example/cloudesk/TicketEntity;", "commentsByOthers", "", "post", "id", "title", "text", "sendEmail", "to", "subject", "body", "sendSms", "phone", "message", "app_debug"})
public final class NotificationHelper {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String CHANNEL_ID = "ticket_assignments";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFS_SEEN = "cloudesk_seen_assignments";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFS_SNAPSHOT = "cloudesk_ticket_snapshot";
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.NotificationHelper INSTANCE = null;
    
    private NotificationHelper() {
        super();
    }
    
    /**
     * Opens the user's email app with a prefilled message.
     */
    public final void sendEmail(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.Nullable
    java.lang.String to, @org.jetbrains.annotations.NotNull
    java.lang.String subject, @org.jetbrains.annotations.NotNull
    java.lang.String body) {
    }
    
    /**
     * Opens the user's SMS app with a prefilled message.
     */
    public final void sendSms(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.Nullable
    java.lang.String phone, @org.jetbrains.annotations.NotNull
    java.lang.String message) {
    }
    
    /**
     * Posts a system notification when a technician has tickets assigned that they haven't
     * been told about yet. Already-seen ticket ids are remembered per technician.
     */
    public final void notifyNewAssignments(@org.jetbrains.annotations.NotNull
    android.content.Context context, int technicianId, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> assignedTicketIds) {
    }
    
    /**
     * Tells a ticket owner when one of their tickets changed status or got a reply from
     * someone else since the last time the app looked. The first run just records a baseline.
     *
     * @param commentsByOthers ticketId -> number of comments written by someone other than the owner
     */
    public final void notifyTicketUpdates(@org.jetbrains.annotations.NotNull
    android.content.Context context, int userId, @org.jetbrains.annotations.NotNull
    java.util.List<com.example.cloudesk.TicketEntity> tickets, @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, java.lang.Integer> commentsByOthers) {
    }
    
    private final void post(android.content.Context context, int id, java.lang.String title, java.lang.String text) {
    }
}