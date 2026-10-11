package com.example.cloudesk;

/**
 * Admin-only: add users, change roles, activate/deactivate accounts and reset passwords.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0014J\b\u0010\r\u001a\u00020\nH\u0002J\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\nH\u0002J\b\u0010\u0012\u001a\u00020\nH\u0002J8\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\bH\u0002J\u0010\u0010\u0019\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u001a\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u001b\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/example/cloudesk/UserManagementActivity;", "Lcom/example/cloudesk/BaseActivity;", "()V", "currentUserId", "", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "generateTempPassword", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "refresh", "resetPassword", "user", "Lcom/example/cloudesk/UserEntity;", "selfWarning", "showAddUserDialog", "showNotifyDialog", "title", "message", "subject", "emailBody", "smsBody", "showRoleDialog", "showUserActions", "toggleActive", "app_debug"})
public final class UserManagementActivity extends com.example.cloudesk.BaseActivity {
    private int currentUserId = -1;
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    
    public UserManagementActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void refresh() {
    }
    
    private final void showUserActions(com.example.cloudesk.UserEntity user) {
    }
    
    private final void selfWarning() {
    }
    
    private final void showRoleDialog(com.example.cloudesk.UserEntity user) {
    }
    
    private final void toggleActive(com.example.cloudesk.UserEntity user) {
    }
    
    /**
     * Shows a dialog with Email / SMS buttons that prefill the message to the affected user.
     */
    private final void showNotifyDialog(java.lang.String title, java.lang.String message, com.example.cloudesk.UserEntity user, java.lang.String subject, java.lang.String emailBody, java.lang.String smsBody) {
    }
    
    private final java.lang.String generateTempPassword() {
        return null;
    }
    
    private final void resetPassword(com.example.cloudesk.UserEntity user) {
    }
    
    private final void showAddUserDialog() {
    }
}