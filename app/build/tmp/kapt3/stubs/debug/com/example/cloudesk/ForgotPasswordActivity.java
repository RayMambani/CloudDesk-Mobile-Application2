package com.example.cloudesk;

/**
 * Password reset without email: the user answers the security question they chose at
 * registration, then sets a new password. Accounts with no question (older accounts) are
 * told to ask an administrator, who can reset the password from User Management.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/example/cloudesk/ForgotPasswordActivity;", "Lcom/example/cloudesk/BaseActivity;", "()V", "foundUser", "Lcom/example/cloudesk/UserEntity;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
public final class ForgotPasswordActivity extends com.example.cloudesk.BaseActivity {
    @org.jetbrains.annotations.Nullable
    private com.example.cloudesk.UserEntity foundUser;
    
    public ForgotPasswordActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
}