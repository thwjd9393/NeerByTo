package com.jscompany.neerbyto.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityLocationBinding
import com.jscompany.neerbyto.main.MainActivity

class LocationActivity : AppCompatActivity() {

    val binding:ActivityLocationBinding by lazy { ActivityLocationBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //공공데이터 API 연동


        //버튼 클릭 시 GPS 읽아오기
        binding.btnLocation.setOnClickListener { findByGps() }

    }

    private fun findByGps() {
        startActivity(Intent(this,MainActivity::class.java))
        finish() //여태 쌓인 화면 스택 전부 삭제해야됨
    }
}