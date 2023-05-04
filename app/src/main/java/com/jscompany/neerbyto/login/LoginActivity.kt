package com.jscompany.neerbyto.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
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
            moveToPage()
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
            //startActivity(Intent(this,FindUserActivity::class.java))
            Common.makeToast(this,"기능 구현중")
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
                        //전송할 데이터 준비
                        val dataUser = mutableMapOf<String, String>()
                        dataUser["id"] = email
                        dataUser["passwd"] = ""
                        dataUser["nicname"] = kaoId
                        dataUser["join_path"] = Common.joinKakao
                        dataUser["apiId"] = kaoId //유일키를 위한

                        //1. db저장
                        emailChek(email, dataUser)

                        //2. 쉐어드에 저장
                        sharedPreferences(email,kaoId)

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
        val signInOptions : GoogleSignInOptions = GoogleSignInOptions.Builder( GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()

        val intent:Intent = GoogleSignIn.getClient(this,signInOptions).signInIntent
        resultLuancher.launch(intent)

    }

    val resultLuancher:ActivityResultLauncher<Intent>
            = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),object : ActivityResultCallback<ActivityResult>{
        override fun onActivityResult(result: ActivityResult?) {
            //로그인 결과를 가져온 인텐트 소환
            val intent:Intent? = result?.data
            //돌아온 인텐트로 부터 구글계정 정보를 가져오는 작업 수행
            val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)

            val account:GoogleSignInAccount = task.result
            var gid:String = account.id.toString()
            var email:String = account.email ?: ""

            //저장 후 페이지 이동
            //전송할 데이터 준비
            val dataUser = mutableMapOf<String, String>()
            dataUser["id"] = email
            dataUser["passwd"] = ""
            dataUser["nicname"] = gid
            dataUser["join_path"] = Common.joinGoogel
            dataUser["apiId"] = gid //유일키를 위한

            //1. db저장
            emailChek(email, dataUser)

            //2. 쉐어드에 저장
            sharedPreferences(email,gid)
        }
    })

    private fun loginByNaver() {
        //네이버로 초기화
        NaverIdLoginSDK.initialize(this, getString(R.string.naverClientId), getString(R.string.naverClientSecret),"가치가치")

        NaverIdLoginSDK.authenticate(this, object : OAuthLoginCallback{
            override fun onError(errorCode: Int, message: String) {
                Common.makeToast(this@LoginActivity, "error : $message")
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Common.makeToast(this@LoginActivity, "로그인 실패 : $message")
            }

            override fun onSuccess() {
                Common.makeToast(this@LoginActivity, "네이버 로그인 성공")

                val accessToken : String? = NaverIdLoginSDK.getAccessToken()

                val retrofit = RetrofitBaseUrl.getRetrofitInstance(Common.naverBaseUrl)

                retrofit.create(UserService::class.java).getNidUserInfo("Bearer $accessToken")
                    .enqueue(object : Callback<NIdUserInfo>{
                        override fun onResponse(
                            call: Call<NIdUserInfo>,
                            response: Response<NIdUserInfo>
                        ) {
                            val userInfo : NIdUserInfo? = response.body()
                            val id:String = userInfo?.response?.id ?: "" //앞이 널이면 뒤
                            val email:String = userInfo?.response?.email ?: ""

                            //저장 후 페이지 이동
                            //전송할 데이터 준비
                            val dataUser = mutableMapOf<String, String>()
                            dataUser["id"] = email
                            dataUser["passwd"] = ""
                            dataUser["nicname"] = id
                            dataUser["join_path"] = Common.joinNaver
                            dataUser["apiId"] = id //유일키를 위한

                            //1. db저장
                            emailChek(email, dataUser)

                            //2. 쉐어드에 저장
                            sharedPreferences(email,id)
                        }

                        override fun onFailure(call: Call<NIdUserInfo>, t: Throwable) {
                            Common.makeToast(this@LoginActivity,"회원정보 불러오기 실패 ${t.message}")
                        }
                    })
            }
        })
    }

    fun gotoLocation(){

        //아이디 비밀번호
        var id : String = etId.text.toString()
        var passwd : String = etpasswd.text.toString()

        if (id == "" ) {
            Common.makeToast(this, "아이디를 입력하지 않았습니다")
            etId.requestFocus() //포커스 올리기
            return
        } else if (passwd == ""){
            Common.makeToast(this, "비밀번호를 입력하지 않았습니다")
            etpasswd.requestFocus() //포커스 올리기
            return
        }

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

    private fun insertUser(dataUser : Map<String, String>) {
        //버튼 클릭 값 저장
        //mysql에 저장 되면 파이어베이스에도 저장!

        //1.
        val retrofit : Retrofit = RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl)

        //2. 서비스 객체 만들기
        val userService = retrofit.create(UserService::class.java)
        userService.insertUserPhp(dataUser).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val s = response.body()

                if(s == "회원가입 되셨습니다"){
                    //파이어베이스에 등록 & 전역변수에 등록
                    Log.i("TAG", "성공 베어스 등록")

                    //파이어스토어 디비에 파이어스토어 얻어오기
                    val db = FirebaseFirestore.getInstance()

                    db.collection("mUser").add(dataUser).addOnSuccessListener {

                        // 3. 화면이동
                        moveToPage()

                    }


                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Common.makeToast(this@LoginActivity, t.message)
                Log.i("TAG", "오류 ${t.message.toString()}")
            }
        })


    }

    private fun emailChek(id: String, dataUser : Map<String, String>) {

        //1.
        val retrofit : Retrofit = RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl)

        //2.
        val userService = retrofit.create(UserService::class.java)
        userService.userIdCheck(id).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {

                if(response.body() != "0") { //중복
                    Common.makeToast(this@LoginActivity, "moveToPage")
                    moveToPage()

                } else {
                    Common.makeToast(this@LoginActivity, "insertUser")
                    insertUser(dataUser)
                }

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