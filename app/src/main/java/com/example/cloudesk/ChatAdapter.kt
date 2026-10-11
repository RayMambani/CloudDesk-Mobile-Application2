package com.example.cloudesk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val messages: List<ChatMessageEntity>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_SENT = 1
        private const val TYPE_RECEIVED = 2
    }

    class SentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView = view.findViewById(R.id.messageText)
        val time: TextView = view.findViewById(R.id.timeText)
    }

    class ReceivedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView = view.findViewById(R.id.messageText)
        val time: TextView = view.findViewById(R.id.timeText)
        val senderName: TextView = view.findViewById(R.id.senderNameText)
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].fromSupport) TYPE_RECEIVED else TYPE_SENT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_SENT) {
            SentViewHolder(inflater.inflate(R.layout.item_chat_sent, parent, false))
        } else {
            ReceivedViewHolder(inflater.inflate(R.layout.item_chat_received, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = messages[position]
        when (holder) {
            is SentViewHolder -> {
                holder.message.text = msg.message
                holder.time.text = msg.timestamp
            }
            is ReceivedViewHolder -> {
                holder.message.text = msg.message
                holder.time.text = msg.timestamp
                holder.senderName.text = msg.senderName
            }
        }
    }

    override fun getItemCount() = messages.size
}
