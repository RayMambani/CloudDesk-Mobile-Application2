package com.example.cloudesk;

/**
 * Security questions used by "Forgot password". The key is stored, the text is localized.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00052\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005J\u0016\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0005J\u0019\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u00112\u0006\u0010\r\u001a\u00020\u000e\u00a2\u0006\u0002\u0010\u0012J\u000e\u0010\u0013\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0005J\u0018\u0010\u0014\u001a\u0004\u0018\u00010\u00052\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0005J\u000e\u0010\u0016\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0005R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0017"}, d2 = {"Lcom/example/cloudesk/SecurityQuestions;", "", "()V", "KEYS", "", "", "getKEYS", "()Ljava/util/List;", "checkAnswer", "", "answer", "stored", "display", "context", "Landroid/content/Context;", "key", "displayList", "", "(Landroid/content/Context;)[Ljava/lang/String;", "hashAnswer", "keyFromDisplay", "text", "normalizeAnswer", "app_debug"})
public final class SecurityQuestions {
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.String> KEYS = null;
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.SecurityQuestions INSTANCE = null;
    
    private SecurityQuestions() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getKEYS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String display(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String key) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String[] displayList(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String keyFromDisplay(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String text) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String normalizeAnswer(@org.jetbrains.annotations.NotNull
    java.lang.String answer) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String hashAnswer(@org.jetbrains.annotations.NotNull
    java.lang.String answer) {
        return null;
    }
    
    public final boolean checkAnswer(@org.jetbrains.annotations.NotNull
    java.lang.String answer, @org.jetbrains.annotations.Nullable
    java.lang.String stored) {
        return false;
    }
}