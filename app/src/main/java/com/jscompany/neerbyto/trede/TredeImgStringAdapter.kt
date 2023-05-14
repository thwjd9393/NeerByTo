package com.jscompany.neerbyto.trede

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.databinding.ItemTredeSelectImgBinding

class TredeImgStringAdapter(var context:Context, var items : MutableList<String>) : Adapter<TredeImgStringAdapter.VH>() {

    inner class VH(var binding : ItemTredeSelectImgBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(ItemTredeSelectImgBinding.inflate(LayoutInflater.from(context),parent,false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        //iv_select_img
        //var iv : ImageView
        Glide.with(context).load(items.get(position)).into(holder.binding.ivSelectImg)
    }

}