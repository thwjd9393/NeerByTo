package com.jscompany.neerbyto.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ItemFriendBinding

class MyFriendAdapter(var context:Context, var items:MutableList<MyFriendItem>) : Adapter<MyFriendAdapter.VH>(){

    inner class VH(var binding:ItemFriendBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemFriendBinding.inflate(LayoutInflater.from(context),parent,false))
    }


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        var item : MyFriendItem = items.get(position)

        holder.binding.tvUserNic.text = item.nicname

        var address = ""
        if (item.profileImg != "") address = "http://mrhisj23.dothome.co.kr/NeerByTo/"+item.profileImg

        Glide.with(context).load(address).error(R.drawable.user_line).into(holder.binding.ivUserImg)

        //친구 삭제
        holder.binding.btnClear.setOnClickListener {
            (context as MyFriendActivity).delFriend(item.friendNo, position)
        }

        //프로필로 이동
        holder.binding.root.setOnClickListener {
            (context as MyFriendActivity).moveToProfile(item.userNo)
        }
    }
}