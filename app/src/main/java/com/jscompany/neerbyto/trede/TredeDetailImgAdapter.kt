package com.jscompany.neerbyto.trede

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ItemTredeDetailImgviewBinding

class TredeDetailImgAdapter(var context:Context,var items : MutableList<String>) : Adapter<TredeDetailImgAdapter.VH>(){

    inner class VH(var binding:ItemTredeDetailImgviewBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemTredeDetailImgviewBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {

        var address = ""
        if (itemCount > 0) address = "http://mrhisj23.dothome.co.kr/NeerByTo/"+items.get(position)

        Glide.with(context).load(address).fallback(R.drawable.baseline_emoji_nature_24).error(R.drawable.baseline_emoji_nature_24).into(holder.binding.ivPager)
    }

}