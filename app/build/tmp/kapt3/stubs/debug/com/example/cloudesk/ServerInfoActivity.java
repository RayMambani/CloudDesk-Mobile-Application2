package com.example.cloudesk;

/**
 * "Online data" screen: shows which API and database the app is using, tests the connection,
 * and lists every table in the online database with its row count and latest rows.
 * Everything here is fetched live from the server (api.php, action "server_info").
 * Personal columns are hidden unless the signed-in user is an admin.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\b\u0010\b\u001a\u00020\tH\u0002J\u0012\u0010\n\u001a\u00020\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0014J8\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u00142\b\b\u0002\u0010\u0016\u001a\u00020\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/example/cloudesk/ServerInfoActivity;", "Lcom/example/cloudesk/BaseActivity;", "()V", "content", "Landroid/widget/LinearLayout;", "dp", "", "v", "load", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "text", "Landroid/widget/TextView;", "msg", "", "sizeSp", "", "bold", "", "mono", "topDp", "app_debug"})
public final class ServerInfoActivity extends com.example.cloudesk.BaseActivity {
    private android.widget.LinearLayout content;
    
    public ServerInfoActivity() {
        super();
    }
    
    private final int dp(int v) {
        return 0;
    }
    
    private final android.widget.TextView text(java.lang.String msg, float sizeSp, boolean bold, boolean mono, int topDp) {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void load() {
    }
}