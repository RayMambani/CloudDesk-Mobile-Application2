package com.example.cloudesk;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\'J\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\bH\'J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0006H\'\u00a8\u0006\u000b"}, d2 = {"Lcom/example/cloudesk/CommentDao;", "", "deleteAll", "", "getCommentsForTicket", "", "Lcom/example/cloudesk/CommentEntity;", "ticketId", "", "insertComment", "comment", "app_debug"})
@androidx.room.Dao
public abstract interface CommentDao {
    
    @androidx.room.Query(value = "SELECT * FROM comments WHERE ticketId = :ticketId ORDER BY created_at ASC")
    @org.jetbrains.annotations.NotNull
    public abstract java.util.List<com.example.cloudesk.CommentEntity> getCommentsForTicket(@org.jetbrains.annotations.NotNull
    java.lang.String ticketId);
    
    @androidx.room.Query(value = "DELETE FROM comments")
    public abstract void deleteAll();
    
    @androidx.room.Insert(onConflict = 1)
    public abstract void insertComment(@org.jetbrains.annotations.NotNull
    com.example.cloudesk.CommentEntity comment);
}