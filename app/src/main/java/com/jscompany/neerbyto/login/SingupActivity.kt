package com.jscompany.neerbyto.login

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivitySingupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SingupActivity : AppCompatActivity() {

    private val binding: ActivitySingupBinding by lazy { ActivitySingupBinding.inflate(layoutInflater) }

    private val tvId by lazy{ binding.inputId }
    private val passwd by lazy{ binding.inputPasswd }
    private val passwdConf by lazy{ binding.inputPasswdCheck }
    private val nicName by lazy{ binding.inputNicname }

    //정규식 체크 값
    var boolEmail : Boolean = false
    var boolPassWd : Boolean = false

    //아이디 이메일 중복체크
    var boolEmailChek : Boolean = false
    var boolNicChek : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

        //회원가입 클릭
        binding.btnSignup.setOnClickListener {clickJoin() }

    }

    private fun clickJoin() {
        //회원가입 버튼 클릭
        when {
            tvId.text.toString() == "" -> Common.makeToast(this, "아이디를 입력하세요")
            passwd.text.toString() == "" -> Common.makeToast(this, "비밀번호를 입력하세요")
            passwdConf.text.toString() == "" -> Common.makeToast(this, "비밀번호 확인를 입력하세요")
            nicName.text.toString() == "" -> Common.makeToast(this, "닉에임을 입력하세요")
            boolEmail == false -> Common.makeToast(this, "아이디가 이메일 형식에 맞지 않습니다")
            boolPassWd == false -> Common.makeToast(this, "비밀번호가 형식에 맞지않습니다")
            passwd.text.toString() != passwdConf.text.toString() -> Common.makeToast(this, "비밀번호가 서로 다릅니다")
            boolEmailChek == false -> emailChek()

            else -> nicChek()
        }

    }

    private fun nicChek() {
        //닉네임 중복체크

        //전송할 데이터 준비
        var nicname = nicName.text.toString()

        //1.
        val retrofit : Retrofit = RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl)

        //2.
        val userService = retrofit.create(UserService::class.java)
        userService.userNicCheck(nicname).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                if(response.body() != "0") {
                    Common.makeToast(this@SingupActivity, "이미 존재하는 닉네임입니다")
                    nicName.requestFocus() //포커스 올리기
                    nicName.selectAll()
                } else {
                    Common.makeToast(this@SingupActivity, "사용 가능한 닉네임입니다")
                    boolNicChek == true

                    //전송할 데이터 준비
                    val dataUser = mutableMapOf<String, String>()
                    dataUser["id"] = tvId.text.toString()
                    dataUser["passwd"] = passwd.text.toString()
                    dataUser["nicname"] = nicName.text.toString()
                    dataUser["join_path"] = Common.joinApp

                    insertUser(dataUser)
                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Common.makeToast(this@SingupActivity, t.message)
            }

        })

    }

    private fun emailChek() {
        //아이디(이메일) 중복체크
        //전송할 데이터 준비
        var id : String = tvId.text.toString()

        //1.
        val retrofit : Retrofit = RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl)

        //2.
        val userService = retrofit.create(UserService::class.java)
        userService.userIdCheck(id).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {

                if(response.body() != "0") {
                    Common.makeToast(this@SingupActivity, "이미 등록된 이메일입니다")
                    tvId.requestFocus() //포커스 올리기
                    tvId.selectAll()

                } else {
                    Common.makeToast(this@SingupActivity, "사용 가능한 이메일입니다")
                    boolEmailChek = true

                    when {
                        boolNicChek == false -> nicChek()
                    }
                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Common.makeToast(this@SingupActivity, t.message)
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

                Log.i("TAG", "성공 ${s.toString()}")

                if(s == "회원가입 되셨습니다"){
                    //파이어베이스에 등록 & 전역변수에 등록
                    Log.i("TAG", "성공 베어스 등록")

                    //파이어스토어 디비에 파이어스토어 얻어오기
                    val db = FirebaseFirestore.getInstance()

                    //저장할 데이터
//                    val fireUser = mutableMapOf<String, String>()
//                    fireUser["id"] = tvId.text.toString()
//                    fireUser["nicname"] = nicName.text.toString()

                    db.collection("mUser").add(dataUser).addOnSuccessListener {
                        //저장 됨
                        AlertDialog.Builder(this@SingupActivity)
                            .setMessage("축하합니다 \n 회원가입이 완료되었습니다")
                            .setPositiveButton("확인"
                            ) { p0, p1 ->
                                //다이아로그의 OnClickListener
                                startActivity(
                                    Intent(
                                        this@SingupActivity,
                                        LoginActivity::class.java
                                    )
                                )

                                finish()
                                //회원가입 화면 종료
                            }.show()
                    }


                } 

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Common.makeToast(this@SingupActivity, t.message)
                Log.i("TAG", "오류 ${t.message.toString()}")
            }
        })


    }

    private fun init() {
        //액션바 셋팅
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.sign_up)

        //리스너 달기
        etListener(tvId)
        etListener(passwd)
        etListener(passwdConf)
        etListener(nicName)
    }


    //EditText 변경할때마다 체크하는 메소드
    private fun etListener(et : EditText) {
        et.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if(et == tvId) {
                    boolEmailChek = false
                    //이메일 체크
                    boolEmail = Common.verifyEmail(tvId.text.toString())
                    if(!boolEmail) {
                        binding.etId.error = getString(R.string.id_no)
                    } else {
                        binding.etId.error = ""
                        boolEmail = true
                        
                        //이메일 중복확인
                        //SELECT count(*) FROM `mUser` WHERE id = 'asd123@naver.com';
                        
                    }
                } else if(et==passwd){
                    boolPassWd = Common.isValidPasswd(passwd.text.toString())
                    if (!boolPassWd) {
                        binding.etPasswd.error = getString(R.string.passwd_no_patten)
                    } else {
                        binding.etPasswd.error = ""
                        boolPassWd = true

                    }
                } else if (et==nicName) {
                    boolNicChek == false
                }

            }

            override fun afterTextChanged(s: Editable?) {
                if(et == passwdConf || et==passwd) {
                    if (passwdConf.text.toString() == passwd.text.toString()) {
                        binding.etPasswdCheck.error = ""
                    } else if(passwdConf.text.toString() != passwd.text.toString()){
                        binding.etPasswdCheck.error = getString(R.string.passwd_no)
                    }
                }
            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}