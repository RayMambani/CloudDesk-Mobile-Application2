package com.example.cloudesk;

/**
 * Lightweight, on-device "AI" ticket triage engine.
 *
 * This app has no backend or API key wired up, so instead of faking a call to a
 * cloud model, this runs a transparent keyword + heuristic scoring pass over the
 * ticket text entirely on-device (fast, works offline, no data leaves the phone).
 *
 * If/when a real backend is added, this is the single place to swap in an actual
 * model call (e.g. POST the title/description to a classification endpoint and
 * map its response into a PriorityResult) without touching any UI code.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0002\u0013\u0014B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/example/cloudesk/TicketPriorityAI;", "", "()V", "criticalTerms", "", "", "highTerms", "lowTerms", "mediumTerms", "urgentCategories", "", "analyze", "Lcom/example/cloudesk/TicketPriorityAI$PriorityResult;", "title", "description", "category", "priorityRank", "", "priority", "Priority", "PriorityResult", "app_debug"})
public final class TicketPriorityAI {
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.String> criticalTerms = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.String> highTerms = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.String> mediumTerms = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.String> lowTerms = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.Set<java.lang.String> urgentCategories = null;
    @org.jetbrains.annotations.NotNull
    public static final com.example.cloudesk.TicketPriorityAI INSTANCE = null;
    
    private TicketPriorityAI() {
        super();
    }
    
    /**
     * Stable ordering for sorting a ticket queue, highest urgency first.
     */
    public final int priorityRank(@org.jetbrains.annotations.NotNull
    java.lang.String priority) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.cloudesk.TicketPriorityAI.PriorityResult analyze(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.lang.String category) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007j\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f\u00a8\u0006\r"}, d2 = {"Lcom/example/cloudesk/TicketPriorityAI$Priority;", "", "label", "", "displayName", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)V", "getDisplayName", "()Ljava/lang/String;", "getLabel", "LOW", "MEDIUM", "HIGH", "CRITICAL", "app_debug"})
    public static enum Priority {
        /*public static final*/ LOW /* = new LOW(null, null) */,
        /*public static final*/ MEDIUM /* = new MEDIUM(null, null) */,
        /*public static final*/ HIGH /* = new HIGH(null, null) */,
        /*public static final*/ CRITICAL /* = new CRITICAL(null, null) */;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String label = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String displayName = null;
        
        Priority(java.lang.String label, java.lang.String displayName) {
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getLabel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getDisplayName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.example.cloudesk.TicketPriorityAI.Priority> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J7\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u001b\u001a\u00020\bH\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\f\u00a8\u0006\u001c"}, d2 = {"Lcom/example/cloudesk/TicketPriorityAI$PriorityResult;", "", "priority", "Lcom/example/cloudesk/TicketPriorityAI$Priority;", "confidence", "", "matchedSignals", "", "", "rawScore", "(Lcom/example/cloudesk/TicketPriorityAI$Priority;ILjava/util/List;I)V", "getConfidence", "()I", "getMatchedSignals", "()Ljava/util/List;", "getPriority", "()Lcom/example/cloudesk/TicketPriorityAI$Priority;", "getRawScore", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
    public static final class PriorityResult {
        @org.jetbrains.annotations.NotNull
        private final com.example.cloudesk.TicketPriorityAI.Priority priority = null;
        private final int confidence = 0;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<java.lang.String> matchedSignals = null;
        private final int rawScore = 0;
        
        public PriorityResult(@org.jetbrains.annotations.NotNull
        com.example.cloudesk.TicketPriorityAI.Priority priority, int confidence, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> matchedSignals, int rawScore) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.cloudesk.TicketPriorityAI.Priority getPriority() {
            return null;
        }
        
        public final int getConfidence() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> getMatchedSignals() {
            return null;
        }
        
        public final int getRawScore() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.cloudesk.TicketPriorityAI.Priority component1() {
            return null;
        }
        
        public final int component2() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> component3() {
            return null;
        }
        
        public final int component4() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.cloudesk.TicketPriorityAI.PriorityResult copy(@org.jetbrains.annotations.NotNull
        com.example.cloudesk.TicketPriorityAI.Priority priority, int confidence, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> matchedSignals, int rawScore) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}