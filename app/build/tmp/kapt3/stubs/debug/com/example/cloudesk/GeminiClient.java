package com.example.cloudesk;

/**
 * Google Gemini integration for Live Chat Support — mirrors the web system's
 * chat integration (same system prompt, same fallback behavior, same lessons
 * learned the hard way while wiring up the website's version):
 *  - Gemini 3.x Flash can't fully disable "thinking", and the older
 *    thinkingBudget/thinkingConfig parameters caused HTTP 400s on this model,
 *    so this deliberately leaves generationConfig minimal.
 *  - The conversation sent to Gemini must start on a "user" turn, so any
 *    leading support-bot messages (like the initial greeting) are dropped
 *    before building the request.
 *  - Network calls must never run on the main thread on Android — call
 *    callGeminiForReply() from a background thread (ChatActivity does this).
 *
 * Returns null on any failure (missing key, network error, bad response) so
 * the caller can fall back to the local canned-response bot — the chat
 * feature should never simply break if Gemini is unavailable.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0007\u001a\u0004\u0018\u00010\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/example/cloudesk/GeminiClient;", "", "()V", "SYSTEM_PROMPT", "", "httpClient", "Lokhttp3/OkHttpClient;", "callGeminiForReply", "history", "", "Lcom/example/cloudesk/ChatMessageEntity;", "newUserMessage", "app_debug"})
public final class GeminiClient {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String SYSTEM_PROMPT = "\nYou are \"CloudDesk Support\", a helpful assistant embedded in the CloudDesk IT\nhelpdesk ticketing app\'s live chat.\n\nGuidelines:\n- Answer clearly and briefly \u2014 2 to 4 short sentences, no long essays.\n- You can help with general troubleshooting guidance (login issues, slow\n  performance, common software/network/hardware problems) and questions\n  about how to use CloudDesk itself (submitting a ticket, checking ticket\n  status, attaching a file, categories, priority levels).\n- You do NOT have access to the user\'s actual account, ticket history, or\n  live system status \u2014 never invent specific ticket numbers, statuses, or\n  account details. If asked about a specific ticket, tell them to check\n  their dashboard or open that ticket\'s detail page.\n- If an issue sounds urgent, serious, or needs hands-on technician work,\n  recommend they submit a ticket from the dashboard so it gets tracked and\n  routed properly, rather than trying to fully resolve it yourself in chat.\n- Stay on topic: IT support and using CloudDesk. Politely redirect anything\n  unrelated back to those topics.\n\nKnowledge base (use these facts when relevant to the question):\n- Forgot password: on the sign-in screen tap \"Forgot password?\", enter your\n  email, answer your security question and choose a new password. If the\n  account has no security question, ask an administrator to reset it.\n- Submitting a ticket: tap the + button on the dashboard, enter a title,\n  description, category and priority (the app suggests a priority), and\n  optionally attach a file.\n- Response targets by priority: Critical 4 hours, High 24 hours, Medium\n  72 hours, Low 120 hours. Overdue tickets are flagged for the support team.\n- Wi-Fi/VPN dropping: restart the device, forget and re-join the network,\n  update the VPN app, then submit a Network ticket if it continues.\n- Slow computer: restart, close unused apps, free up storage, install\n  pending updates; submit a Hardware or Software ticket if it persists.\n- After a ticket is Resolved, the person who opened it can rate it from the\n  ticket page. Ticket status and history are shown on the ticket page.\n";
    @org.jetbrains.annotations.NotNull
    private static final okhttp3.OkHttpClient httpClient = null;
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.GeminiClient INSTANCE = null;
    
    private GeminiClient() {
        super();
    }
    
    /**
     * @param history Prior messages in this conversation, oldest first.
     * @param newUserMessage The message the user just sent.
     * @return Gemini's reply text, or null if the call couldn't be completed.
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.String callGeminiForReply(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.cloudesk.ChatMessageEntity> history, @org.jetbrains.annotations.NotNull
    java.lang.String newUserMessage) {
        return null;
    }
}