package com.example.cloudesk;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\'J\u000e\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\'J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\tH\'J\u0012\u0010\n\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000b\u001a\u00020\fH\'J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u000e\u001a\u00020\tH\'J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0006H\'J\u0010\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0006H\'\u00a8\u0006\u0013"}, d2 = {"Lcom/example/cloudesk/UserDao;", "", "deleteAll", "", "getAllUsers", "", "Lcom/example/cloudesk/UserEntity;", "getUserByEmail", "email", "", "getUserById", "id", "", "getUsersByRole", "role", "insertUser", "", "user", "updateUser", "app_debug"})
@androidx.room.Dao
public abstract interface UserDao {
    
    @androidx.room.Query(value = "SELECT * FROM users WHERE lower(email) = lower(:email) LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract com.example.cloudesk.UserEntity getUserByEmail(@org.jetbrains.annotations.NotNull
    java.lang.String email);
    
    @androidx.room.Query(value = "SELECT * FROM users WHERE id = :id LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract com.example.cloudesk.UserEntity getUserById(int id);
    
    @androidx.room.Query(value = "SELECT * FROM users WHERE role = :role")
    @org.jetbrains.annotations.NotNull
    public abstract java.util.List<com.example.cloudesk.UserEntity> getUsersByRole(@org.jetbrains.annotations.NotNull
    java.lang.String role);
    
    @androidx.room.Insert(onConflict = 1)
    public abstract long insertUser(@org.jetbrains.annotations.NotNull
    com.example.cloudesk.UserEntity user);
    
    @androidx.room.Update
    public abstract void updateUser(@org.jetbrains.annotations.NotNull
    com.example.cloudesk.UserEntity user);
    
    @androidx.room.Query(value = "DELETE FROM users")
    public abstract void deleteAll();
    
    @androidx.room.Query(value = "SELECT * FROM users ORDER BY role, firstName")
    @org.jetbrains.annotations.NotNull
    public abstract java.util.List<com.example.cloudesk.UserEntity> getAllUsers();
}