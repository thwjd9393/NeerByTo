package com.jscompany.neerbyto.profile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityProfileBinding
import com.jscompany.neerbyto.databinding.ActivityProfileUpdateBinding
import com.jscompany.neerbyto.login.UserService
import com.jscompany.neerbyto.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ProfileUpdateActivity : AppCompatActivity() {

    private val binding:ActivityProfileUpdateBinding by lazy { ActivityProfileUpdateBinding.inflate(layoutInflater) }

    //정규식 체크 값
    var boolPassWd : Boolean = false

    //닉네임 중복체크
    var boolNicChek : Boolean = false

    //이미지 uri
    lateinit var uri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //툴바 등록
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.profile_update)

        init()

    }

    private fun init() {

        //리스너 달기
        etListener(binding.inputNicname)
        etListener(binding.inputPasswd)
        etListener(binding.inputPasswdCheck)

        //이미지 선택
        binding.civImgUser.setOnClickListener { clickImgSelect() }

        //이미지 삭제 (기본 이미지로 변경)
        binding.btnImgDelete.setOnClickListener { clickImgDelete() }
        
        //회원정보 수정 버튼
        binding.btnUserInfoUpdate.setOnClickListener { clickCheckEmpty() }

    }
    
    

    private fun clickImgSelect() {
        //이미지 선택
        var intent = Intent(Intent.ACTION_PICK).setType("image/*")

        imgPickResultLauncher.launch(intent)
    }

    var imgPickResultLauncher : ActivityResultLauncher<Intent>
        = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
    ) {
            if(it.resultCode != RESULT_CANCELED){
                var intent = it.data!!
                var uri : Uri = intent.data!!

                //스태틱변수로 저장
                Common.PROFILEURI = uri
                //Common.PROFILEIMG = uri

                //이미지 뷰에 셋팅
//                var profileImg = ""
//                if (Common.PROFILEIMG != "") profileImg = "http://mrhisj23.dothome.co.kr/NeerByTo/"+Common.PROFILEIMG
                Glide.with(this).load(uri).error(R.drawable.user_line).into(binding.civImgUser)

            }
    }

    //이미지 삭제
    private fun clickImgDelete() {


    }

    //빈값 체크
    private fun clickCheckEmpty() {
        //회원가입 버튼 클릭
        when {
            binding.inputNicname.text.toString() == "" -> Common.makeToast(this, "비밀번호를 입력하세요")
            binding.inputPasswd.text.toString() == "" -> Common.makeToast(this, "비밀번호를 입력하세요")
            binding.inputPasswdCheck.text.toString() == "" -> Common.makeToast(this, "비밀번호 확인를 입력하세요")
            boolPassWd == false -> Common.makeToast(this, "비밀번호가 형식에 맞지않습니다")
            binding.inputPasswd.text.toString() != binding.inputPasswdCheck.text.toString() -> Common.makeToast(this, "비밀번호가 서로 다릅니다")

            else -> nicChek()
        }

    }

    //에디트 텍스트 리스너
    private fun etListener(et : EditText) {
        et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if(et==binding.inputPasswd){
                    boolPassWd = Common.isValidPasswd(binding.inputPasswd.text.toString())
                    if (!boolPassWd) {
                        binding.etPasswd.error = getString(R.string.passwd_no_patten)
                    } else {
                        binding.etPasswd.error = ""
                        boolPassWd = true

                    }
                }

            }

            override fun afterTextChanged(s: Editable?) {
                if(et == binding.inputPasswd || et==binding.inputPasswdCheck) {
                    if (binding.inputPasswd.text.toString() == binding.inputPasswdCheck.text.toString()) {
                        binding.etPasswdCheck.error = ""
                    } else if(binding.inputPasswd.text.toString() != binding.inputPasswdCheck.text.toString()){
                        binding.etPasswdCheck.error = getString(R.string.passwd_no)
                    }
                }
            }
        })
    }

    private fun nicChek() {
        //닉네임 중복체크

        //전송할 데이터 준비
        var nicname = binding.inputNicname.text.toString()

        //1.
        val retrofit : Retrofit = RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl)

        //2.
        val userService = retrofit.create(UserService::class.java)
        userService.userNicCheck(nicname).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                if(response.body() != "0") {
                    Common.makeToast(this@ProfileUpdateActivity, "이미 존재하는 닉네임입니다")
                    binding.inputNicname.requestFocus() //포커스 올리기
                    binding.inputNicname.selectAll()
                } else {
                    Common.makeToast(this@ProfileUpdateActivity, "사용 가능한 닉네임입니다")
                    boolNicChek = true

                    //전송할 데이터 준비
                    val dataUser = mutableMapOf<String, String>()
                    dataUser["passwd"] = binding.inputPasswd.text.toString()
                    dataUser["nicname"] = binding.inputNicname.text.toString()

                    clickUserInfoUpdate(dataUser)
                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Common.makeToast(this@ProfileUpdateActivity, t.message)
            }

        })

    }

    //회원 정보 수정
    private fun clickUserInfoUpdate(dataUser: MutableMap<String, String>) {

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}