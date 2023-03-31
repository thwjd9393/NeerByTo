package com.jscompany.neerbyto.trede

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityTredeDetailBinding

class TredeDetailActivity : AppCompatActivity() {

    val binding:ActivityTredeDetailBinding by lazy { ActivityTredeDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()



    }

    private fun init() {
        //커스텀 액션바 등록
        setSupportActionBar(findViewById(R.id.toolbar)) //include 한 레이아웃은 바인딩 안됨
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //왼쪽 뒤로가기 버튼
        supportActionBar!!.setDisplayShowTitleEnabled(false) //타이틀

        //닉네임 layout누르면 프로필 화면으로 이동


    }

    //옵션 메뉴 만드는 콜백
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_trede_detail, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) finish()
        else if(item.itemId == R.id.option_report) {
            val intent : Intent = Intent(this,ReportUserActivity::class.java)
                .putExtra("userNic","유저닉네임").putExtra("userNo","5")
            
            startActivity(intent)
        }


        return super.onOptionsItemSelected(item)
    }
}