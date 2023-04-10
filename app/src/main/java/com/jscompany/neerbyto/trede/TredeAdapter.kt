package com.jscompany.neerbyto.trede

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jscompany.neerbyto.databinding.ItemTredeBinding

class TredeAdapter(var contrxt:Context, var items:MutableList<TredeVO>) :
    Adapter<TredeAdapter.VH>() {

    inner class VH(var binding:ItemTredeBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemTredeBinding.inflate(LayoutInflater.from(contrxt),parent,false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item : TredeVO = items[position]

        holder.binding.tvTitle.text = item.title
        holder.binding.tvPrice.text = "${item.price} 원"
        holder.binding.tvJoinCount.text = item.joinCount.toString()
    }
    
}