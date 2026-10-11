package com.example.cloudesk

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TicketAdapter(
    private val tickets: List<TicketEntity>,
    private val userId: Int,
    private val userRole: String,
    private val currentUserName: String = ""
) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    class TicketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.ticketTitle)
        val description: TextView = view.findViewById(R.id.ticketDescription)
        val status: TextView = view.findViewById(R.id.ticketStatus)
        val priority: TextView = view.findViewById(R.id.ticketPriority)
        val attachmentIndicator: TextView = view.findViewById(R.id.attachmentIndicator)
        val assignedIndicator: TextView = view.findViewById(R.id.assignedIndicator)
        val overdueIndicator: TextView = view.findViewById(R.id.overdueIndicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ticket, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = tickets[position]
        holder.title.text = ticket.title
        holder.description.text = ticket.description
        val context = holder.itemView.context
        holder.status.text = context.getString(R.string.label_status, TicketVocabulary.statusDisplay(context, ticket.status))
        holder.priority.text = context.getString(R.string.label_priority, TicketVocabulary.priorityDisplay(context, ticket.priority))
        holder.attachmentIndicator.visibility =
            if (!ticket.attachmentUri.isNullOrEmpty()) View.VISIBLE else View.GONE

        holder.assignedIndicator.visibility =
            if (userRole == "technician" && currentUserName.isNotBlank() && ticket.assignedTo == currentUserName)
                View.VISIBLE else View.GONE

        holder.overdueIndicator.visibility =
            if (SlaHelper.isOverdue(ticket)) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, TicketDetailActivity::class.java)
            intent.putExtra("TKT_ID", ticket.id)
            intent.putExtra("TKT_TITLE", ticket.title)
            intent.putExtra("TKT_DESC", ticket.description)
            intent.putExtra("TKT_STATUS", ticket.status)
            intent.putExtra("TKT_PRIORITY", ticket.priority)
            intent.putExtra("TKT_CATEGORY", ticket.category)
            intent.putExtra("TKT_DATE", ticket.created_at)
            intent.putExtra("TKT_ATTACHMENT_URI", ticket.attachmentUri)
            intent.putExtra("TKT_ATTACHMENT_NAME", ticket.attachmentName)
            intent.putExtra("USER_ID", userId)
            intent.putExtra("TICKET_USER_ID", ticket.userId)
            intent.putExtra("USER_ROLE", userRole)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = tickets.size
}
