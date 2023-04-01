package com.jscompany.neerbyto.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityProfileBinding
import com.jscompany.neerbyto.databinding.ActivityProfileUpdateBinding

class ProfileUpdateActivity : AppCompatActivity() {

    private val binding:ActivityProfileUpdateBinding by lazy { ActivityProfileUpdateBinding.inflate(layoutInflater) }

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
        //이미지 삭제 (기본 이미지로 변경)
        binding.btnImgDelete.setOnClickListener { clickImgDelete() }

        //비밀번호 변경 버튼
        binding.btnPasswdChange.setOnClickListener { clickPasswdChange() }
        
        //회원정보 수정 버튼
        binding.btnUserInfoUpdate.setOnClickListener { clickUserInfoUpdate() }

    }

    private fun clickUserInfoUpdate() {
        TODO("Not yet implemented")
    }

    private fun clickPasswdChange() {
        TODO("Not yet implemented")
    }

    private fun clickImgDelete() {
        TODO("Not yet implemented")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}