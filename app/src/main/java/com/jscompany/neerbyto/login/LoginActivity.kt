package com.jscompany.neerbyto.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class LoginActivity : AppCompatActivity() {

    val binding:ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    val etId by lazy { binding.inputId }
    val etpasswd by lazy { binding.inputPasswd }

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

        //아이디 비밀번호
        var id : String = etId.text.toString()
        var passwd : String = etpasswd.text.toString()

        //1.
        val retrofit : Retrofit = RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl)

        //2.
        val userService = retrofit.create(UserService::class.java)
        userService.userLogin(id,passwd).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                var responseBack = response.body()

                if(responseBack != "no") {
                    Common.makeToast(this@LoginActivity, "로그인")

                    //쉐어드에 저장
                    val pref = getSharedPreferences("Data", MODE_PRIVATE)
                    val editor = pref.edit()

                    editor.putString("userId",id);
                    editor.putString("userNic",responseBack);

                    editor.commit()

                    startActivity(Intent(this@LoginActivity,LocationActivity::class.java))
                    finish()
                } else Common.makeToast(this@LoginActivity, "아이디와 비밀번호가 맞지 않습니다")

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Common.makeToast(this@LoginActivity, t.message)
            }

        })



    }
}