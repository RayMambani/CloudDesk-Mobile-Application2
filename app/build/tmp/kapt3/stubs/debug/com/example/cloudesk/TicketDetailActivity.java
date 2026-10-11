package com.example.cloudesk;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u000fH\u0002J\u0012\u0010\u0011\u001a\u00020\u000f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/example/cloudesk/TicketDetailActivity;", "Lcom/example/cloudesk/BaseActivity;", "()V", "addCommentButton", "Landroid/widget/Button;", "commentEditText", "Lcom/google/android/material/textfield/TextInputEditText;", "commentsRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "ticketId", "", "userId", "", "userRole", "loadComments", "", "loadHistory", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "refreshRatingCard", "ticket", "Lcom/example/cloudesk/TicketEntity;", "app_debug"})
public final class TicketDetailActivity extends com.example.cloudesk.BaseActivity {
    private androidx.recyclerview.widget.RecyclerView commentsRecyclerView;
    private com.google.android.material.textfield.TextInputEditText commentEditText;
    private android.widget.Button addCommentButton;
    @org.jetbrains.annotations.NotNull
    private java.lang.String ticketId = "";
    private int userId = -1;
    @org.jetbrains.annotations.NotNull
    private java.lang.String userRole = "user";
    
    public TicketDetailActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    /**
     * Shows the rating form to the ticket owner once resolved; shows the result to everyone after.
     */
    private final void refreshRatingCard(com.example.cloudesk.TicketEntity ticket) {
    }
    
    private final void loadHistory() {
    }
    
    private final void loadComments() {
    }
}