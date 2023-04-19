package com.jscompany.neerbyto.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityProfileBinding
import com.jscompany.neerbyto.login.UserVO
import com.jscompany.neerbyto.trede.ReportUserActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class ProfileActivity : AppCompatActivity() {

    private val binding:ActivityProfileBinding by lazy { ActivityProfileBinding.inflate(layoutInflater) }

    var isfriedCheck : Boolean = false

    var userNo : String = ""
    var friendNo : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //툴바 등록
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.profile)

        init()
        
        //화면 로드 되자마자 친구추가 했는지 체크
        checkFriend()

        Log.i("TAG", "init = ${isfriedCheck}")
    }

    private fun init(){
        
        //넘어온 유저 번호
        userNo = intent.getStringExtra("userNo") ?: ""
        Log.i("TAG", "넘어온 유저 번호 : ${userNo}")
        
        //사용자 정보 셋팅
        loadUserInfo(userNo)

        //프로필 수정 - 내 정보와 인텐트로 온 정보가 같으면 보여주기
        if (Common.getUserNo(this) == userNo) {
            binding.btnProfileUpdate.visibility = View.VISIBLE
        } else {
            //매너평가, 신고, 작성글 로그인 정보가 없을 떄 보여줌
            binding.warpOtherInfo.visibility = View.VISIBLE
            binding.btnFriend.visibility = View.VISIBLE
        }

        //친구즐겨찾기 추가
        binding.imgBtn.setOnClickListener { clickMyFriend() }

        //프로필 수정
        binding.btnProfileUpdate.setOnClickListener { clickProfileUpdate() }

        //매너평가 - 다른 유저 일때 보여줌
        binding.btnMannerEstimate.setOnClickListener { clickMannerEstimate() }
        
        //신고 - 다른 유저 일때 보여줌
        binding.btnReport.setOnClickListener { clickReport() }
        
        //작성글 - 다른 유저 일때 보여줌
        binding.btnOtherWrite.setOnClickListener { clickOtherWrite() }
        
        //받은 매너평가
        binding.btnGoManerpage.setOnClickListener { clickGoManerpage() }

    }


    //유저 정보 셋팅
    private fun loadUserInfo(userNo:String) {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .loadUserInfo(userNo).enqueue(object : Callback<MutableList<UserVO>>{
                override fun onResponse(
                    call: Call<MutableList<UserVO>>,
                    response: Response<MutableList<UserVO>>
                ) {
                    var resutList : MutableList<UserVO> = response.body() ?: mutableListOf()

                    setUserInfo(resutList.get(0))
                }

                override fun onFailure(call: Call<MutableList<UserVO>>, t: Throwable) {
                    Common.makeToast(this@ProfileActivity,getString(R.string.response_server_error))
                }

            })
    }

    //정보 셋팅
    private fun setUserInfo(userVo: UserVO) {
        binding.tvNicname.text = userVo.nicname

        var date : String = userVo.join_date

        val sdf1 = SimpleDateFormat("yyyyMMdd")
        val sdf2 = SimpleDateFormat("yyyy/MM/dd")
        val formatDate: Date = sdf1.parse(date)

        binding.tvJoinDate.text = "가입일 : ${sdf2.format(formatDate)}"

        var profileImg = ""
        if (Common.PROFILEIMG != "") profileImg = "http://mrhisj23.dothome.co.kr/NeerByTo/"+Common.PROFILEIMG

        Glide.with(this).load(profileImg).error(R.drawable.user_line).into(binding.civImgUser)
    }

    //친구 추가 체크
    private fun checkFriend() {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .loadMyFriendCheck(Common.getUserNo(this),userNo).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    var result = response.body().toString()

                    if(result != ""){
                        binding.imgBtn.background = getDrawable(R.drawable.baseline_star_24)
                        isfriedCheck = true
                        friendNo = result
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Common.makeToast(this@ProfileActivity,getString(R.string.response_server_error))
                }
            })
    }

    //친구 추가
    private fun clickMyFriend() {
        if (isfriedCheck == false) {
            insertFriend()
        } else {
            delFriend()
        }
    }
    //친추
    private fun insertFriend() {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .insertMyFriend(Common.getUserNo(this),userNo).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    var result = response.body().toString()
                    binding.imgBtn.background = getDrawable(R.drawable.baseline_star_24)
                    isfriedCheck = true
                    friendNo = result
                    
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Common.makeToast(this@ProfileActivity,getString(R.string.response_server_error))
                }
            })
    }
    //친삭
    private fun delFriend() {

        var friendNoInt = friendNo.toInt()

        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .delMyFriend(friendNoInt).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    binding.imgBtn.background = getDrawable(R.drawable.baseline_star_border_24)
                    isfriedCheck = false
                    friendNo = ""
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Common.makeToast(this@ProfileActivity,getString(R.string.response_server_error))
                }
            })
    }

    //받은 매너평가
    private fun clickGoManerpage() {
        startActivity(Intent(this, MannerDetailActivity::class.java))
    }

    //작성글 넘어가기
    private fun clickOtherWrite() {
        startActivity(Intent(this, MyWriteActivity::class.java).putExtra("userNo",userNo))
    }

    private fun clickReport() {
        startActivity(Intent(this, ReportUserActivity::class.java))
    }

    //매너평가 페이지
    private fun clickMannerEstimate() {

        //커스텀 다이아로그 띄우기
        val dialoView = layoutInflater.inflate(R.layout.layout_dialog, null)
        val alertDialog = AlertDialog.Builder(this).setView(dialoView).create()

        val btnMgood : TextView = dialoView.findViewById(R.id.btn_manner_good)
        val btnMbad : TextView = dialoView.findViewById(R.id.btn_manner_bad)

        btnMgood.setOnClickListener {
            alertDialog.dismiss()
            startActivity(Intent(this, MannerEstimateGoodActivity::class.java))
        }

        btnMbad.setOnClickListener {
            alertDialog.dismiss()
            startActivity(Intent(this, MannerEstimateBadActivity::class.java))
        }

        alertDialog.show()
    }

    //

    private fun clickProfileUpdate() {
        startActivity(Intent(this, ProfileUpdateActivity::class.java))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

}