package com.example.cloudesk;

/**
 * Live Chat Support screen.
 *
 * Replies come from Google's Gemini API (see GeminiClient) once
 * GeminiConfig.API_KEY is filled in, giving real, context-aware answers.
 * If the key isn't configured yet, or a request fails for any reason, this
 * falls back to a small local canned-response bot so chat never just breaks.
 * Messages are persisted to Room so the conversation survives navigating
 * away and back.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0011H\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\u0012\u0010\u0016\u001a\u00020\u00152\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\b\u0010\u0019\u001a\u00020\u0015H\u0002J\b\u0010\u001a\u001a\u00020\u0015H\u0002J\u0010\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u0011H\u0002J\b\u0010\u001d\u001a\u00020\u0011H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/example/cloudesk/ChatActivity;", "Lcom/example/cloudesk/BaseActivity;", "()V", "adapter", "Lcom/example/cloudesk/ChatAdapter;", "handler", "Landroid/os/Handler;", "messages", "", "Lcom/example/cloudesk/ChatMessageEntity;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "typingIndicator", "Landroid/widget/TextView;", "userId", "", "userName", "", "generateSupportReply", "userText", "loadConversation", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "reloadMessages", "scrollToBottom", "sendUserMessage", "text", "timeNow", "app_debug"})
public final class ChatActivity extends com.example.cloudesk.BaseActivity {
    private com.example.cloudesk.ChatAdapter adapter;
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private android.widget.TextView typingIndicator;
    @org.jetbrains.annotations.NotNull
    private final android.os.Handler handler = null;
    private int userId = -1;
    @org.jetbrains.annotations.NotNull
    private java.lang.String userName = "User";
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.example.cloudesk.ChatMessageEntity> messages = null;
    
    public ChatActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadConversation() {
    }
    
    /**
     * Re-reads the conversation from the local copy of the online database.
     */
    private final void reloadMessages() {
    }
    
    private final void sendUserMessage(java.lang.String text) {
    }
    
    private final java.lang.String generateSupportReply(java.lang.String userText) {
        return null;
    }
    
    private final void scrollToBottom() {
    }
    
    private final java.lang.String timeNow() {
        return null;
    }
}