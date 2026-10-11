package com.example.cloudesk;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\'J\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\bH\'J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H\'\u00a8\u0006\f"}, d2 = {"Lcom/example/cloudesk/TicketHistoryDao;", "", "count", "", "getForTicket", "", "Lcom/example/cloudesk/TicketHistoryEntity;", "ticketId", "", "insert", "", "entry", "app_debug"})
@androidx.room.Dao
public abstract interface TicketHistoryDao {
    
    @androidx.room.Query(value = "SELECT * FROM ticket_history WHERE ticketId = :ticketId ORDER BY id DESC")
    @org.jetbrains.annotations.NotNull
    public abstract java.util.List<com.example.cloudesk.TicketHistoryEntity> getForTicket(@org.jetbrains.annotations.NotNull
    java.lang.String ticketId);
    
    @androidx.room.Insert
    public abstract void insert(@org.jetbrains.annotations.NotNull
    com.example.cloudesk.TicketHistoryEntity entry);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM ticket_history")
    public abstract int count();
}