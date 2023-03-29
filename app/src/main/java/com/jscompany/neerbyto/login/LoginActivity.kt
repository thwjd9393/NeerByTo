package com.jscompany.neerbyto.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityLoginBinding
import com.jscompany.neerbyto.main.MainActivity

class LoginActivity : AppCompatActivity() {

    val binding:ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

    }

    fun init() {
        // 둘러보기 클릭 시 메인 화면으로 이동
        binding.tvLookAround.setOnClickListener {
            gotoLocation()
        }

        //로그인 버튼 클릭 화면 이동
        binding.btnLogin.setOnClickListener {
            gotoLocation()
        }

        //회원가입 화면이동
        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this,SingupActivity::class.java))
        }

        //아이디 비번 찾기 화면이동
        binding.tvFindUser.setOnClickListener {
            startActivity(Intent(this,FindUserActivity::class.java))
        }

        //간편 로그인
        binding.ivLoginKakao.setOnClickListener { loginByKakao() }
        binding.ivLoginGoogle.setOnClickListener { loginByGoogle() }
        binding.ivLoginNaver.setOnClickListener { loginByNaver() }
    }

    private fun loginByKakao() {
        TODO("Not yet implemented")
    }

    private fun loginByGoogle() {
        TODO("Not yet implemented")
    }

    private fun loginByNaver() {
        TODO("Not yet implemented")
    }

    fun gotoLocation(){
        startActivity(Intent(this,LocationActivity::class.java))
        finish()
    }
}