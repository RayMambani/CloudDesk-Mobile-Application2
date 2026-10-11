package com.example.cloudesk;

/**
 * Salted PBKDF2 password hashing. Stored format:  pbkdf2$<iterations>$<salt>$<hash>
 * Old plain-text passwords (from before hashing was added) are still accepted by verify()
 * so nobody gets locked out; callers should re-save them hashed (see needsUpgrade()).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0006H\u0002J\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u0004J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0004J\u000e\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0004J\u0016\u0010\u0015\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/example/cloudesk/PasswordHasher;", "", "()V", "ALGORITHM", "", "ITERATIONS", "", "KEY_BITS", "PREFIX", "b64", "bytes", "", "derive", "plain", "salt", "iterations", "hash", "isHashed", "", "stored", "needsUpgrade", "verify", "app_debug"})
public final class PasswordHasher {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFIX = "pbkdf2";
    private static final int ITERATIONS = 10000;
    private static final int KEY_BITS = 256;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String ALGORITHM = "PBKDF2WithHmacSHA1";
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.PasswordHasher INSTANCE = null;
    
    private PasswordHasher() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String hash(@org.jetbrains.annotations.NotNull
    java.lang.String plain) {
        return null;
    }
    
    public final boolean isHashed(@org.jetbrains.annotations.NotNull
    java.lang.String stored) {
        return false;
    }
    
    public final boolean needsUpgrade(@org.jetbrains.annotations.NotNull
    java.lang.String stored) {
        return false;
    }
    
    public final boolean verify(@org.jetbrains.annotations.NotNull
    java.lang.String plain, @org.jetbrains.annotations.NotNull
    java.lang.String stored) {
        return false;
    }
    
    private final byte[] derive(java.lang.String plain, byte[] salt, int iterations) {
        return null;
    }
    
    private final java.lang.String b64(byte[] bytes) {
        return null;
    }
}