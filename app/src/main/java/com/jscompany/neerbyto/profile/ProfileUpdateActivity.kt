package com.jscompany.neerbyto.profile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityProfileBinding
import com.jscompany.neerbyto.databinding.ActivityProfileUpdateBinding
import com.jscompany.neerbyto.main.MainActivity

class ProfileUpdateActivity : AppCompatActivity() {

    private val binding:ActivityProfileUpdateBinding by lazy { ActivityProfileUpdateBinding.inflate(layoutInflater) }

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
        //이미지 선택
        binding.civImgUser.setOnClickListener { clickImgSelect() }

        //이미지 삭제 (기본 이미지로 변경)
        binding.btnImgDelete.setOnClickListener { clickImgDelete() }
        
        //회원정보 수정 버튼
        binding.btnUserInfoUpdate.setOnClickListener { clickUserInfoUpdate() }

    }
    
    

    private fun clickImgSelect() {
        //이미지 선택

    }

    var imgPickResultLauncher : ActivityResultLauncher<Intent>
        = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
    ) {
            if(it.resultCode != RESULT_CANCELED){
                var intent = it.data!!
                var uri : Uri = intent.data!!

                //스태틱변수로 저장
                Common.PROFILEURI = uri

                Glide.with(this).load(uri).into(binding.civImgUser)
            }
    }

    private fun clickUserInfoUpdate() {

    }

    private fun clickImgDelete() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}