package com.example.cloudesk;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J@\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\b\u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0014\u0010\u000f\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\b0\u0010H\u0002J\b\u0010\u0011\u001a\u00020\bH\u0002J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0004H\u0002J\u0012\u0010\u0014\u001a\u00020\b2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014J\b\u0010\u0017\u001a\u00020\bH\u0014J\b\u0010\u0018\u001a\u00020\bH\u0002J\u0010\u0010\u0019\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0004H\u0002J\u0010\u0010\u001a\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\bH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/example/cloudesk/MainActivity;", "Lcom/example/cloudesk/BaseActivity;", "()V", "priorityFilter", "", "searchQuery", "statusFilter", "addChip", "", "group", "Lcom/google/android/material/chip/ChipGroup;", "label", "key", "checked", "", "onSelected", "Lkotlin/Function1;", "confirmLogout", "loadTickets", "userRole", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "requestNotificationPermissionIfNeeded", "setupFilters", "showProfileMenu", "anchor", "Landroid/view/View;", "updateOfflineBanner", "app_debug"})
public final class MainActivity extends com.example.cloudesk.BaseActivity {
    @org.jetbrains.annotations.Nullable
    private java.lang.String statusFilter;
    @org.jetbrains.annotations.Nullable
    private java.lang.String priorityFilter;
    @org.jetbrains.annotations.NotNull
    private java.lang.String searchQuery = "";
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupFilters(java.lang.String userRole) {
    }
    
    private final void addChip(com.google.android.material.chip.ChipGroup group, java.lang.String label, java.lang.String key, boolean checked, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSelected) {
    }
    
    private final void requestNotificationPermissionIfNeeded() {
    }
    
    @java.lang.Override
    protected void onResume() {
    }
    
    private final void updateOfflineBanner() {
    }
    
    private final void showProfileMenu(android.view.View anchor) {
    }
    
    private final void confirmLogout() {
    }
    
    private final void loadTickets(java.lang.String userRole) {
    }
}