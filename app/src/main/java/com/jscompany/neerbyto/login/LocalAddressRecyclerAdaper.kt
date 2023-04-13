package com.jscompany.neerbyto.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.Documents
import com.jscompany.neerbyto.RoadAddress
import com.jscompany.neerbyto.databinding.ItemLocationBinding
import com.jscompany.neerbyto.main.MainActivity

class LocalAddressRecyclerAdaper(var context:Context, var documents : MutableList<Documents>) :
    Adapter<LocalAddressRecyclerAdaper.VH>() {

    inner class VH(var binding : ItemLocationBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH
        = VH(ItemLocationBinding.inflate(LayoutInflater.from(context),parent,false))

    override fun getItemCount(): Int {
        return documents.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val documents : Documents  = documents[position]

        holder.binding.tvAddress.text = documents.address_name

        // 상세 페이지로 이동
        holder.binding.root.setOnClickListener {
            //누르면 좌표 가지고 넘어가기
            context.startActivity(Intent(context, MainActivity::class.java))

            var latitude = documents.y //위도
            var longitude = documents.x //경도(longitude)
            sharedPreferences(latitude,longitude) //쉐어드에 저장
            
            Common.dong = documents.address.region_3depth_name ?: ""

            //Toast.makeText(context, "${documents.address.region_3depth_name}", Toast.LENGTH_SHORT).show()

            (context as Activity).finish()

        }
    }

    private fun sharedPreferences (latitude : String, longitude : String){
        val pref = context.getSharedPreferences("Data", AppCompatActivity.MODE_PRIVATE)
        val editor = pref.edit()

        editor.putString("latitude",latitude); //경도
        editor.putString("longitude",longitude); //위도

        editor.commit()
    }

}