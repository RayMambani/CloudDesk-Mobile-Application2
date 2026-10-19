package com.example.cloudesk;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0007\u001a\u00020\u0004H\u0002J\"\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014J\u0012\u0010\u0011\u001a\u00020\u000b2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J*\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\t2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0004H\u0002J\b\u0010\u0019\u001a\u00020\u000bH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/example/cloudesk/SubmitTicketActivity;", "Lcom/example/cloudesk/BaseActivity;", "()V", "selectedFileUri", "Landroid/net/Uri;", "getFileFromUri", "Ljava/io/File;", "uri", "getFileName", "", "onActivityResult", "", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onTicketSubmitted", "id", "title", "priority", "fileUri", "openFilePicker", "Companion", "app_debug"})
public final class SubmitTicketActivity extends com.example.cloudesk.BaseActivity {
    public static final int MAX_ATTACHMENT_BYTES = 5242880;
    @org.jetbrains.annotations.Nullable
    private android.net.Uri selectedFileUri;
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.SubmitTicketActivity.Companion Companion = null;
    
    public SubmitTicketActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    /**
     * The ticket now exists in the online database; show the confirmation and the email/SMS options.
     */
    private final void onTicketSubmitted(java.lang.String id, java.lang.String title, java.lang.String priority, android.net.Uri fileUri) {
    }
    
    private final void openFilePicker() {
    }
    
    @java.lang.Override
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable
    android.content.Intent data) {
    }
    
    private final java.lang.String getFileName(android.net.Uri uri) {
        return null;
    }
    
    private final java.io.File getFileFromUri(android.net.Uri uri) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/cloudesk/SubmitTicketActivity$Companion;", "", "()V", "MAX_ATTACHMENT_BYTES", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}