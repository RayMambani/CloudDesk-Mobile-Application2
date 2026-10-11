package com.example.cloudesk

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

// Models
data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val success: Boolean?, val user: User?, val error: String?)
data class User(val id: Int, val name: String, val email: String, val role: String)

data class Ticket(
    val id: String,
    val title: String,
    val description: String,
    val status: String,
    val priority: String,
    val category: String,
    val created_at: String
)

data class CreateTicketRequest(
    val userId: Int,
    val title: String,
    val category: String,
    val priority: String,
    val description: String
)

data class CreateTicketResponse(val success: Boolean?, val id: String?, val error: String?)

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val department: String,
    val password: String,
    val role: String
)

data class RegisterResponse(val success: Boolean?, val error: String?)

data class Comment(
    val id: Int,
    val ticket_id: String,
    val user_id: Int,
    val user_name: String,
    val content: String,
    val created_at: String
)

data class AddCommentRequest(
    val ticket_id: String,
    val user_id: Int,
    val content: String
)

data class GenericResponse(val success: Boolean?, val error: String?)

data class UpdateTicketRequest(
    val id: String,
    val status: String,
    val priority: String,
    val assignee: String?
)



// API Interface
interface CloudDeskApi {
    @POST("api/login.php")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("api/tickets.php")
    fun getTickets(@Query("user_id") userId: Int?): Call<List<Ticket>>

    @retrofit2.http.Multipart
    @POST("api/tickets.php")
    fun createTicket(
        @retrofit2.http.Part("userId") userId: RequestBody,
        @retrofit2.http.Part("title") title: RequestBody,
        @retrofit2.http.Part("category") category: RequestBody,
        @retrofit2.http.Part("priority") priority: RequestBody,
        @retrofit2.http.Part("description") description: RequestBody,
        @retrofit2.http.Part file: MultipartBody.Part?
    ): Call<CreateTicketResponse>

    @POST("api/register.php")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @GET("api/comments.php")
    fun getComments(@Query("ticket_id") ticketId: String): Call<List<Comment>>

    @POST("api/comments.php")
    fun addComment(@Body request: AddCommentRequest): Call<GenericResponse>

    @PUT("api/tickets.php")
    fun updateTicket(@Body request: UpdateTicketRequest): Call<GenericResponse>


}
