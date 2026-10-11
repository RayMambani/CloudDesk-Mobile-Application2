package com.example.cloudesk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private val users: List<UserEntity>,
    private val onClick: (UserEntity) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.userNameText)
        val email: TextView = view.findViewById(R.id.userEmailText)
        val meta: TextView = view.findViewById(R.id.userMetaText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        val context = holder.itemView.context
        holder.name.text = "${user.firstName} ${user.lastName}"
        holder.email.text = user.email
        val status = if (user.active) context.getString(R.string.user_status_active) else context.getString(R.string.user_status_inactive)
        holder.meta.text = "${roleDisplay(context, user.role)} · $status"
        holder.itemView.alpha = if (user.active) 1f else 0.55f
        holder.itemView.setOnClickListener { onClick(user) }
    }

    override fun getItemCount() = users.size

    companion object {
        val ROLE_KEYS = listOf("user", "technician", "admin")

        fun roleDisplay(context: android.content.Context, role: String): String = when (role) {
            "admin" -> context.getString(R.string.role_admin)
            "technician" -> context.getString(R.string.role_technician)
            else -> context.getString(R.string.role_user)
        }
    }
}
