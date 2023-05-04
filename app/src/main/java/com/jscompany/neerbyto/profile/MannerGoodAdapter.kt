package com.jscompany.neerbyto.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jscompany.neerbyto.databinding.ItemMannerDetailBinding

class MannerGoodAdapter(var context: Context, var items : MutableList<GoodItem>) : Adapter<MannerGoodAdapter.VH>() {

    inner class VH(var binding:ItemMannerDetailBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH
        = VH(ItemMannerDetailBinding.inflate(LayoutInflater.from(context),parent,false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items.get(position)

        holder.binding.tvRank.text = item.content
        holder.binding.tvRankCnt.text = item.cnt.toString()

    }


}