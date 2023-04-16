package com.jscompany.neerbyto.trede

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jscompany.neerbyto.PlaceDocumentsItem
import com.jscompany.neerbyto.databinding.ItemTredeSearchPlaceItemBinding


class TredeSearchPlaceAdapter(var context : Context, var documents : MutableList<PlaceDocumentsItem>)
    : Adapter<TredeSearchPlaceAdapter.VH>() {

    inner class VH(var binding : ItemTredeSearchPlaceItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemTredeSearchPlaceItemBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int = documents.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val place : PlaceDocumentsItem = documents[position]

        holder.binding.tvPlaceName.text = place.place_name
        holder.binding.tvAddress.text = if (place.road_address_name == "") place.address_name else place.road_address_name

        //웹뷰 보여주기
        holder.binding.btnLocation.setOnClickListener {
            val intent : Intent = Intent(context, PlaceUrlActivity::class.java)
            intent.putExtra("place_url", place.place_url) //값 같이 넘겨줌
            context.startActivity(intent)
        }
        
        //선택한 값 가지고 이동
        holder.binding.btnSelect.setOnClickListener {

            (context as TredeSearchPlaceActivity).setResultData(place.place_name,place.x,place.y)

        }

    }


}