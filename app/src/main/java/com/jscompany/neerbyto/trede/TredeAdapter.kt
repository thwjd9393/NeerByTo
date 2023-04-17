package com.jscompany.neerbyto.trede

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.rpc.context.AttributeContext.Resource
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ItemTredeBinding

class TredeAdapter(var context:Context, var items:MutableList<TredeVO>) :
    Adapter<TredeAdapter.VH>() {

    inner class VH(var binding:ItemTredeBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemTredeBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item : TredeVO = items[position]

        holder.binding.tvTitle.text = item.title
        holder.binding.tvPrice.text = "${item.price} 원"
        holder.binding.tvJoinCount.text = item.joinCount.toString()
        holder.binding.tvDate.text = item.date.substring(2)
        holder.binding.tvCategory.text = item.tredCtyName
        holder.binding.tvPlace.text = item.joinPlace
        holder.binding.tvHangOutTime.text = item.joinTime
        holder.binding.tvViewCnt.text = item.likeCnt.toString()

        //게시글 상태
        if(item.state.toString() == Common.STATUS_ING){
            holder.binding.tvStatus.text = Common.STATUS_ING_TEXT
            holder.binding.tvStatus.background =  ResourcesCompat.getDrawable(context.resources,
                R.drawable.bg_status_ing, null)
        } else {
            holder.binding.tvStatus.text = Common.STATUS_END_TEXT
            holder.binding.tvStatus.background =  ResourcesCompat.getDrawable(context.resources,
                R.drawable.bg_status_end, null)
        }

        //그림 가져오기
        var address = ""
        if (item.img1 != null) address = "http://mrhisj23.dothome.co.kr/NeerByTo/" + item.img1
        Glide.with(context).load(address).error(R.drawable.bg_empty_img).fallback(R.drawable.bg_empty_img).into(holder.binding.ivTrede)

        holder.binding.root.setOnClickListener {
            context.startActivity(Intent(context, TredeDetailActivity::class.java).putExtra("tredeNo", item.tredeNo.toString()))
        }
    }
    
}