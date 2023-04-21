package com.jscompany.neerbyto.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.FilePathFormUri
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityProfileUpdateBinding
import com.jscompany.neerbyto.login.UserService
import com.jscompany.neerbyto.login.UserVO
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File


class ProfileUpdateActivity : AppCompatActivity() {

    private val binding:ActivityProfileUpdateBinding by lazy { ActivityProfileUpdateBinding.inflate(layoutInflater) }

    //정규식 체크 값
    var boolPassWd : Boolean = false

    //닉네임 중복체크
    var boolNicChek : Boolean = false

    //이미지 파일 경로
    private var imgPath : String = ""
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //툴바 등록
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.profile_update)

        init()

        Log.i("TAG",Common.getUserNo(this))
        Log.i("TAG",Common.getNic(this))
        Log.i("TAG",Common.getId(this))

    }

    private fun init() {

        //리스너 달기
        etListener(binding.inputNicname)
        etListener(binding.inputPasswd)
        etListener(binding.inputPasswdCheck)

        //기존 닉네임과 저장되어 있는 이미지 가져오기
        loadProfileData()

        //이미지 선택
        binding.civImgUser.setOnClickListener { clickImgSelect() }

        //이미지 삭제 (기본 이미지로 변경)
        binding.btnImgDelete.setOnClickListener { clickImgDelete() }

        //회원정보 수정 버튼
        binding.btnUserInfoUpdate.setOnClickListener { clickCheckEmpty() }

    }


    private fun clickImgSelect() {

        //이미지 선택
        var intent = Intent(MediaStore.ACTION_PICK_IMAGES)
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

                Log.i("TAG", "이미지 경로 uri : ${Common.PROFILEURI}")

                //이미지 뷰에 셋팅
//                if (Common.PROFILEIMG != "") profileImg = "http://mrhisj23.dothome.co.kr/NeerByTo/"+Common.PROFILEIMG
                Glide.with(this).load(uri).error(R.drawable.user_line).into(binding.civImgUser)

                //v이미지 파일 주소 얻어오기
                imgPath = FilePathFormUri.getFilePathFromUri(uri, this)

            }
    }

    //이미지 삭제
    private fun clickImgDelete() {
        //StoragePermission.verifyStoragePermissions(this)

    }

    //빈값 체크
    private fun clickCheckEmpty() {
        //회원가입 버튼 클릭
        when {
            binding.inputNicname.text.toString() == "" -> Common.makeToast(this, "닉네임을 입력하세요")
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


    //닉네임 중복체크
    private fun nicChek() {

        //전송할 데이터 준비
        var nicname = binding.inputNicname.text.toString()

        val dataUser = mutableMapOf<String, String>()

        //닉네임은 변경 안하고 비밀번호만 변경하려 할 때 if 처리
        if (nicname == Common.getNic(this))  {

            dataUser["passwd"] = binding.inputPasswd.text.toString()
            dataUser["nicname"] = nicname
            dataUser["userNo"] = Common.getUserNo(this)
            clickUserInfoUpdate(dataUser)

        } else {
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
                        boolNicChek = true

                        //전송할 데이터 준비
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

    }

    //회원 정보 수정
    private fun clickUserInfoUpdate(dataUser: MutableMap<String, String>) {

        //이미지
        var filePart : MultipartBody.Part?

        val file = File(imgPath)

        Log.i("TAG", "유저 정보 ${dataUser}")
        Log.i("TAG", "사진 file ${file}")

        val body: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        filePart = MultipartBody.Part.createFormData("img", file.name, body)

        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .updateUserInfo(dataUser,filePart).enqueue(object : Callback<MutableList<UserVO>>{
                override fun onResponse(
                    call: Call<MutableList<UserVO>>,
                    response: Response<MutableList<UserVO>>
                ) {
                    val result = response.body() ?: mutableListOf()

                    Log.i("TAG", "정보 ${result}")

                    binding.inputNicname.setText(result[0].nicname)

                    var profileImg = ""
                    if (Common.PROFILEIMG != "") profileImg = "http://mrhisj23.dothome.co.kr/NeerByTo/"+ result[0].profileImg

                    Glide.with(this@ProfileUpdateActivity).load(profileImg).fallback(R.drawable.user_line)
                        .error(R.drawable.user_line).into(binding.civImgUser)

                    //쉐어드에 저장
                    sharedPreferences(result[0].nicname, result[0].profileImg, result[0].id, result[0].userNo)
                    //이미지 저장
                    Common.PROFILEIMG = result[0].profileImg

                    //화면이동
                    startActivity(Intent(this@ProfileUpdateActivity,ProfileActivity::class.java).putExtra("userNo",result[0].userNo))
                    finish()

                }

                override fun onFailure(call: Call<MutableList<UserVO>>, t: Throwable) {
                    Common.makeToast(this@ProfileUpdateActivity, getString(R.string.response_server_error))
                    Log.i("TAG", "${t.message}")
                }
            })
    }

    private fun sharedPreferences (nicnName : String, profileImg : String, userId : String ,userNo : String){
        val pref = this.getSharedPreferences("Data", AppCompatActivity.MODE_PRIVATE)
        val editor = pref.edit()

        editor.putString("userNic",nicnName);
        editor.putString("profileImg",profileImg);
        editor.putString("userId",userId);
        editor.putString("userNo",userNo);

        editor.commit()
    }
    
    
    //기존 회원 정보 불러오기
    private fun loadProfileData(){

        binding.inputNicname.setText(Common.getNic(this))

        var profileImg = ""
        if (Common.PROFILEIMG != "") profileImg = "http://mrhisj23.dothome.co.kr/NeerByTo/"+Common.PROFILEIMG

        Glide.with(this).load(profileImg).error(R.drawable.user_line).fallback(R.drawable.user_line).into(binding.civImgUser)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}