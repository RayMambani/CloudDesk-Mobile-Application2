package com.example.cloudesk;

/**
 * Creates CSV / PDF exports in the app cache and hands them to the system share sheet.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002J\u0016\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u0004J\u001c\u0010\f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eJ&\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0004J\b\u0010\u0015\u001a\u00020\u0004H\u0002\u00a8\u0006\u0016"}, d2 = {"Lcom/example/cloudesk/ExportHelper;", "", "()V", "csvField", "", "value", "exportDir", "Ljava/io/File;", "context", "Landroid/content/Context;", "exportReportPdf", "reportText", "exportTicketsCsv", "tickets", "", "Lcom/example/cloudesk/TicketEntity;", "share", "", "file", "mimeType", "subject", "stamp", "app_debug"})
public final class ExportHelper {
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.ExportHelper INSTANCE = null;
    
    private ExportHelper() {
        super();
    }
    
    private final java.io.File exportDir(android.content.Context context) {
        return null;
    }
    
    private final java.lang.String stamp() {
        return null;
    }
    
    private final java.lang.String csvField(java.lang.String value) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.io.File exportTicketsCsv(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.util.List<com.example.cloudesk.TicketEntity> tickets) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.io.File exportReportPdf(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String reportText) {
        return null;
    }
    
    public final void share(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.io.File file, @org.jetbrains.annotations.NotNull
    java.lang.String mimeType, @org.jetbrains.annotations.NotNull
    java.lang.String subject) {
    }
}