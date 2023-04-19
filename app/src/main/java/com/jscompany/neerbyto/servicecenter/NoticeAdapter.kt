package com.jscompany.neerbyto.servicecenter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jscompany.neerbyto.databinding.ItemServiceCenterBinding

class NoticeAdapter(var context:Context, var items:MutableList<noticeItem>) :Adapter<NoticeAdapter.VH>() {

    inner class VH(var binding:ItemServiceCenterBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH
        = VH(ItemServiceCenterBinding.inflate(LayoutInflater.from(context),parent,false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        var item : noticeItem = items.get(position)

        holder.binding.tvTitle.text = item.title
        holder.binding.tvDate.text = item.date

        holder.binding.root.setOnClickListener {
            (context as NoticeActivity).moveToDetail(item.noticeNo)
        }
    }
}