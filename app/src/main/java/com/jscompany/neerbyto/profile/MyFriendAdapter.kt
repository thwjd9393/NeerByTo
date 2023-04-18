package com.jscompany.neerbyto.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jscompany.neerbyto.databinding.ItemFriendBinding

class MyFriendAdapter(var context:Context, var items:MutableList<ProfileVO>)
    : Adapter<MyFriendAdapter.VH>(){

    inner class VH(var binding:ItemFriendBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH
        = VH(ItemFriendBinding.inflate(LayoutInflater.from(context),parent,false))


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        var item = items.get(position)

        holder.binding.tvUserNic.text = item.aaa
    }
}