package com.jscompany.neerbyto.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityProfileBinding
import com.jscompany.neerbyto.trede.ReportUserActivity

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
        startActivity(Intent(this, MannerDetailActivity::class.java))
    }

    private fun clickOtherWrite() {
        startActivity(Intent(this, MyWriteActivity::class.java))
    }

    private fun clickReport() {
        startActivity(Intent(this, ReportUserActivity::class.java))
    }

    //받은 매너 평가
    private fun clickMannerEstimate() {
        startActivity(Intent(this, MannerDetailActivity::class.java))
    }

    private fun clickProfileUpdate() {
        startActivity(Intent(this, ProfileUpdateActivity::class.java))
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

}