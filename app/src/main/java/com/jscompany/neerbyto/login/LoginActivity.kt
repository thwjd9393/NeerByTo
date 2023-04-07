package com.jscompany.neerbyto.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
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

//        var keyHash = Utility.getKeyHash(this)
//        Log.i("TAG", keyHash )


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
        
        val callback:(OAuthToken?, Throwable?) -> Unit = {token, error -> 
            
            if(token != null){
                //카카오 로그인 성공
                Common.makeToast(this, "카카오 로그인 성공")

                //회원 번호, 이메일 번호
                UserApiClient.instance.me { user, error ->
                    if (user != null) {
                        var kaoId = user.id.toString() //카카오에서 오는 id 값 -> 닉네임으로 사용
                        var email = user.kakaoAccount?.email ?: ""

                        //저장 후 페이지 이동
                        //쉐어드에 저장
                        sharedPreferences(email,kaoId)

                        moveToPage() //화면 이동

                    }
                }

            } else {
                Common.makeToast(this, "카카오 로그인 실패")
            }
            
        }

        //카카오톡이 설치되어 있으면 카톡으로 로그인 없으면 카카오 계정으로 로그인
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
        
    }

    private fun loginByGoogle() {

    }

    private fun loginByNaver() {

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

                    var nic = responseBack ?: "-"

                    //쉐어드에 저장
                    sharedPreferences(id,nic)

                    moveToPage() //화면 이동

                } else Common.makeToast(this@LoginActivity, "아이디와 비밀번호가 맞지 않습니다")

                Log.i("TAG", "${responseBack }")

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Common.makeToast(this@LoginActivity, t.message)
            }

        })



    }

    //환경설정에 값 저장
    private fun sharedPreferences (id : String, nic : String){
        val pref = getSharedPreferences("Data", MODE_PRIVATE)
        val editor = pref.edit()

        editor.putString("userId",id);
        editor.putString("userNic",nic);

        editor.commit()
    }
    
    //화면 이동
    private fun moveToPage(){
        startActivity(Intent(this,LocationActivity::class.java))
        finish()
    }
}