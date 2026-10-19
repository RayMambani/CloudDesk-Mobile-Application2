package com.example.cloudesk;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\'J\u000e\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\'J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\tH\'J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0006H\'J\u0016\u0010\f\u001a\u00020\u00032\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\'\u00a8\u0006\u000e"}, d2 = {"Lcom/example/cloudesk/TicketDao;", "", "deleteAll", "", "getAllTickets", "", "Lcom/example/cloudesk/TicketEntity;", "getTicketById", "id", "", "insertTicket", "ticket", "insertTickets", "tickets", "app_debug"})
@androidx.room.Dao
public abstract interface TicketDao {
    
    @androidx.room.Query(value = "SELECT * FROM tickets ORDER BY created_at DESC")
    @org.jetbrains.annotations.NotNull
    public abstract java.util.List<com.example.cloudesk.TicketEntity> getAllTickets();
    
    @androidx.room.Query(value = "SELECT * FROM tickets WHERE id = :id LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract com.example.cloudesk.TicketEntity getTicketById(@org.jetbrains.annotations.NotNull
    java.lang.String id);
    
    @androidx.room.Query(value = "DELETE FROM tickets")
    public abstract void deleteAll();
    
    @androidx.room.Insert(onConflict = 1)
    public abstract void insertTickets(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.cloudesk.TicketEntity> tickets);
    
    @androidx.room.Insert(onConflict = 1)
    public abstract void insertTicket(@org.jetbrains.annotations.NotNull
    com.example.cloudesk.TicketEntity ticket);
}