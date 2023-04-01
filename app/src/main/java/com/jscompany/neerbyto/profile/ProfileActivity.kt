package com.jscompany.neerbyto.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private val binding:ActivityProfileBinding by lazy { ActivityProfileBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //툴바 등록
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.profile)

        init()
    }

    private fun init(){

        //이미지 셋팅
        //binding.circleImgUser
        
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

    private fun clickGoManerpage() {
        TODO("Not yet implemented")
    }

    private fun clickOtherWrite() {
        TODO("Not yet implemented")
    }

    private fun clickReport() {
        TODO("Not yet implemented")
    }

    private fun clickMannerEstimate() {
        TODO("Not yet implemented")
    }

    private fun clickProfileUpdate() {
        startActivity(Intent(this, ProfileUpdateActivity::class.java))
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

}