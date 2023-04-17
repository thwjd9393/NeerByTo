package com.jscompany.neerbyto.chat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ItemChatBinding

class ChatRoomAdapter(val context:Context, var items : MutableList<ChatRoom>)
    : RecyclerView.Adapter<ChatRoomAdapter.VH>() {

    inner class VH(var binding : ItemChatBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemChatBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]

        var enterCnt = item.users as MutableList<String>

        holder.binding.tvTitle.text = item.title
        holder.binding.joinCnt.text = "${enterCnt.size}/${item.joinCount}"
        holder.binding.tvTime.text = item.lastChatTime
        holder.binding.tvLastChat.text = item.lastChat

        var address = ""
        if (item.writeImg != null) {
            address = Common.dotHomeImgUrl+item.writeImg
        }

        Glide.with(context).load(address).error(R.drawable.user_line).into(holder.binding.civUserImg)

        //클릭하면 메세지 디테일로 이동
        holder.binding.root.setOnClickListener { ClickChatRoom(item.tredeNo) }
    }

    private fun ClickChatRoom(tredeNo : String){
        context.startActivity(Intent(context,ChatDetailActivity::class.java).putExtra("tredeNo",tredeNo))
    }

}