package com.example.cloudesk;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'JL\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\b\b\u0001\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u000b\u001a\u00020\n2\b\b\u0001\u0010\f\u001a\u00020\n2\b\b\u0001\u0010\r\u001a\u00020\n2\b\b\u0001\u0010\u000e\u001a\u00020\n2\n\b\u0001\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\'J\u001e\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u00032\b\b\u0001\u0010\u0014\u001a\u00020\u0015H\'J%\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00120\u00032\n\b\u0001\u0010\t\u001a\u0004\u0018\u00010\u0018H\'\u00a2\u0006\u0002\u0010\u0019J\u0018\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u001cH\'J\u0018\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u001fH\'J\u0018\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020!H\'\u00a8\u0006\""}, d2 = {"Lcom/example/cloudesk/CloudDeskApi;", "", "addComment", "Lretrofit2/Call;", "Lcom/example/cloudesk/GenericResponse;", "request", "Lcom/example/cloudesk/AddCommentRequest;", "createTicket", "Lcom/example/cloudesk/CreateTicketResponse;", "userId", "Lokhttp3/RequestBody;", "title", "category", "priority", "description", "file", "Lokhttp3/MultipartBody$Part;", "getComments", "", "Lcom/example/cloudesk/Comment;", "ticketId", "", "getTickets", "Lcom/example/cloudesk/Ticket;", "", "(Ljava/lang/Integer;)Lretrofit2/Call;", "login", "Lcom/example/cloudesk/LoginResponse;", "Lcom/example/cloudesk/LoginRequest;", "register", "Lcom/example/cloudesk/RegisterResponse;", "Lcom/example/cloudesk/RegisterRequest;", "updateTicket", "Lcom/example/cloudesk/UpdateTicketRequest;", "app_debug"})
public abstract interface CloudDeskApi {
    
    @retrofit2.http.POST(value = "api/login.php")
    @org.jetbrains.annotations.NotNull
    public abstract retrofit2.Call<com.example.cloudesk.LoginResponse> login(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.example.cloudesk.LoginRequest request);
    
    @retrofit2.http.GET(value = "api/tickets.php")
    @org.jetbrains.annotations.NotNull
    public abstract retrofit2.Call<java.util.List<com.example.cloudesk.Ticket>> getTickets(@retrofit2.http.Query(value = "user_id")
    @org.jetbrains.annotations.Nullable
    java.lang.Integer userId);
    
    @retrofit2.http.Multipart
    @retrofit2.http.POST(value = "api/tickets.php")
    @org.jetbrains.annotations.NotNull
    public abstract retrofit2.Call<com.example.cloudesk.CreateTicketResponse> createTicket(@retrofit2.http.Part(value = "userId")
    @org.jetbrains.annotations.NotNull
    okhttp3.RequestBody userId, @retrofit2.http.Part(value = "title")
    @org.jetbrains.annotations.NotNull
    okhttp3.RequestBody title, @retrofit2.http.Part(value = "category")
    @org.jetbrains.annotations.NotNull
    okhttp3.RequestBody category, @retrofit2.http.Part(value = "priority")
    @org.jetbrains.annotations.NotNull
    okhttp3.RequestBody priority, @retrofit2.http.Part(value = "description")
    @org.jetbrains.annotations.NotNull
    okhttp3.RequestBody description, @retrofit2.http.Part
    @org.jetbrains.annotations.Nullable
    okhttp3.MultipartBody.Part file);
    
    @retrofit2.http.POST(value = "api/register.php")
    @org.jetbrains.annotations.NotNull
    public abstract retrofit2.Call<com.example.cloudesk.RegisterResponse> register(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.example.cloudesk.RegisterRequest request);
    
    @retrofit2.http.GET(value = "api/comments.php")
    @org.jetbrains.annotations.NotNull
    public abstract retrofit2.Call<java.util.List<com.example.cloudesk.Comment>> getComments(@retrofit2.http.Query(value = "ticket_id")
    @org.jetbrains.annotations.NotNull
    java.lang.String ticketId);
    
    @retrofit2.http.POST(value = "api/comments.php")
    @org.jetbrains.annotations.NotNull
    public abstract retrofit2.Call<com.example.cloudesk.GenericResponse> addComment(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.example.cloudesk.AddCommentRequest request);
    
    @retrofit2.http.PUT(value = "api/tickets.php")
    @org.jetbrains.annotations.NotNull
    public abstract retrofit2.Call<com.example.cloudesk.GenericResponse> updateTicket(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.example.cloudesk.UpdateTicketRequest request);
}