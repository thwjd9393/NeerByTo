package com.jscompany.neerbyto.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import de.hdodenhof.circleimageview.CircleImageView


class ChatAdapter(var context: Context, var messageItems : MutableList<MessageItem>) :
    Adapter<ChatAdapter.VH>() {

    val TYPE_MY = 0
    val TYPE_OTHER = 1

    override fun getItemViewType(position: Int): Int {
        return if (messageItems.get(position).nic.equals(Common.getNic(context))) {
            TYPE_MY
        } else {
            TYPE_OTHER
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var itemView : View? = null
        if (viewType == TYPE_MY) itemView = LayoutInflater.from(context).inflate(R.layout.item_chat_my_message,parent,false)
        else itemView = LayoutInflater.from(context).inflate(R.layout.item_chat_other_message,parent,false)

        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        var item : MessageItem = messageItems.get(position)

        holder.tvName.text = item.nic
        holder.tvMsg.text = item.message
        holder.tvTime.text = item.time
        Glide.with(context).load(item.profileUrl).error(R.drawable.user_full).into(holder.civ)
    }


    override fun getItemCount(): Int = messageItems.size


    class VH(itemView: View) : ViewHolder(
        itemView
    ) {
        var civ: CircleImageView
        var tvName: TextView
        var tvMsg: TextView
        var tvTime: TextView

        init {
            //xml 의 id가 같아야 함
            civ = itemView.findViewById(R.id.civ)
            tvName = itemView.findViewById(R.id.tv_name)
            tvMsg = itemView.findViewById(R.id.tv_msg)
            tvTime = itemView.findViewById(R.id.tv_time)
        }
    }


}