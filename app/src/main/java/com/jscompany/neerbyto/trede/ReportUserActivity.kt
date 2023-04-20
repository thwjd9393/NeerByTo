package com.jscompany.neerbyto.trede

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityReportUserBinding

class ReportUserActivity : AppCompatActivity() {

    private val binding:ActivityReportUserBinding by lazy { ActivityReportUserBinding.inflate(layoutInflater) }

    private val builder  : AlertDialog.Builder by lazy { AlertDialog.Builder(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

    }

    private fun init() {
        //툴바 등록
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.report_btn)

        //유저 번호, 닉네임 셋팅
        val getIntent : Intent = intent

        var userNic = getIntent.getStringExtra("userNic")
        var userNo = getIntent.getStringExtra("userNo")

        binding.tvHelper.text ="\'" + userNic + "\'"
        binding.tvUserNo.text = userNo

        //카테고리 선택 다이아로그
        binding.tvReportCategory.setOnClickListener { clickSelect() }
        
        //신고하기 버튼
        binding.btnReport.setOnClickListener { clickReport() }

    }


    private var items: Array<String> = arrayOf("카테고리","연애 목적으로 활용","약속시간 안지킴","약속 장소에 나오지않음")

    private fun clickSelect() {
        //카테고리 클릭 다이아로그
        builder.setItems(items, DialogInterface.OnClickListener { dialog, which ->
            Toast.makeText(this@ReportUserActivity, items[which], Toast.LENGTH_SHORT).show()
            binding.tvReportCategory.text = items[which]
        }).create().show()

    }

    //신고하기 등록
    private fun clickReport() {
        Toast.makeText(this, "기능 구현중", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}