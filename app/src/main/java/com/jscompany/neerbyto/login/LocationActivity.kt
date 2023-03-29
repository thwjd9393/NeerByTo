package com.jscompany.neerbyto.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityLoginBinding
import com.jscompany.neerbyto.main.MainActivity

class LocationActivity : AppCompatActivity() {

    val binding:ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        //공공데이터 API 연동


        //버튼 클릭 시 GPS 읽아오기
        binding.btnLogin.setOnClickListener { findByGps() }

    }

    private fun findByGps() {
        startActivity(Intent(this,MainActivity::class.java))
    }
}