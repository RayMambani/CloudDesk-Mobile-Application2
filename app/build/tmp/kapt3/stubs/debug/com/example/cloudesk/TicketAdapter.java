package com.example.cloudesk;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0015B-\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u00a2\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\u0007H\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0007H\u0016J\u0018\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0007H\u0016R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/example/cloudesk/TicketAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/cloudesk/TicketAdapter$TicketViewHolder;", "tickets", "", "Lcom/example/cloudesk/TicketEntity;", "userId", "", "userRole", "", "currentUserName", "(Ljava/util/List;ILjava/lang/String;Ljava/lang/String;)V", "getItemCount", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "TicketViewHolder", "app_debug"})
public final class TicketAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.cloudesk.TicketAdapter.TicketViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.example.cloudesk.TicketEntity> tickets = null;
    private final int userId = 0;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String userRole = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String currentUserName = null;
    
    public TicketAdapter(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.cloudesk.TicketEntity> tickets, int userId, @org.jetbrains.annotations.NotNull
    java.lang.String userRole, @org.jetbrains.annotations.NotNull
    java.lang.String currentUserName) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.example.cloudesk.TicketAdapter.TicketViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.example.cloudesk.TicketAdapter.TicketViewHolder holder, int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u0011\u0010\r\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\bR\u0011\u0010\u000f\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\bR\u0011\u0010\u0011\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\bR\u0011\u0010\u0013\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\b\u00a8\u0006\u0015"}, d2 = {"Lcom/example/cloudesk/TicketAdapter$TicketViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Landroid/view/View;)V", "assignedIndicator", "Landroid/widget/TextView;", "getAssignedIndicator", "()Landroid/widget/TextView;", "attachmentIndicator", "getAttachmentIndicator", "description", "getDescription", "overdueIndicator", "getOverdueIndicator", "priority", "getPriority", "status", "getStatus", "title", "getTitle", "app_debug"})
    public static final class TicketViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView title = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView description = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView status = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView priority = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView attachmentIndicator = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView assignedIndicator = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView overdueIndicator = null;
        
        public TicketViewHolder(@org.jetbrains.annotations.NotNull
        android.view.View view) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getTitle() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getDescription() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getStatus() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getPriority() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getAttachmentIndicator() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getAssignedIndicator() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getOverdueIndicator() {
            return null;
        }
    }
}