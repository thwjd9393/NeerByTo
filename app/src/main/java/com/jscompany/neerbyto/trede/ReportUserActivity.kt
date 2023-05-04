package com.jscompany.neerbyto.trede

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityReportUserBinding
import com.jscompany.neerbyto.profile.MyLikeService
import com.jscompany.neerbyto.profile.ProfileActivity
import com.jscompany.neerbyto.profile.ReportCtyItem
import okhttp3.internal.indexOf
import okhttp3.internal.toImmutableList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        //카테고리 셋팅
        getCategory()

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

    //카테고리 아이템
    private var items: MutableList<String> = mutableListOf()
    //private var itemsIdx: MutableList<String> = mutableListOf()
    //private var items: Array<String> = arrayOf("카테고리","연애 목적으로 활용","약속시간 안지킴","약속 장소에 나오지않음")
    //선택한 아이템 인덱스 번호
    var itemIdx = 0
    
    private fun clickSelect() {
        //카테고리 클릭 다이아로그
        builder.setItems(items.toTypedArray(), DialogInterface.OnClickListener { dialog, which ->
            //Toast.makeText(this@ReportUserActivity, items[which], Toast.LENGTH_SHORT).show()
            binding.tvReportCategory.text = items[which]

            itemIdx = items.indexOf(items[which])

            Log.i("TAG","itemIdx ${itemIdx}")
        }).create().show()

    }

    //카테고리 불러오고 셋팅
    private fun getCategory() {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .loadReportCategory().enqueue(object : Callback<MutableList<ReportCtyItem>>{
                override fun onResponse(
                    call: Call<MutableList<ReportCtyItem>>,
                    response: Response<MutableList<ReportCtyItem>>
                ) {
                    val result : MutableList<ReportCtyItem> = response.body() ?: mutableListOf()

                    for (i in 0 until result.size) {
                        items.add(result[i].reportCtyContent)
                        //itemsIdx.add(result[i].reportCtyNo.toString())
                    }

                }

                override fun onFailure(call: Call<MutableList<ReportCtyItem>>, t: Throwable) {
                    Common.makeToast(this@ReportUserActivity,getString(R.string.response_server_error))
                }
            })

    }
    
    //신고하기 등록
    private fun clickReport() {
        //데이터 준비
        val content = binding.tvContent.text.toString()
        val userNo = binding.tvUserNo.text.toString()
        val reportUserNo = Common.getUserNo(this)
        val selectCgy =binding.tvReportCategory.text

        if (selectCgy == items[0]) {
            Common.makeToast(this,"카테고리를 선택해 주세요")
            return
        }
        if (content.length<5) {
            Common.makeToast(this,"5자 이상 입력하셔야합니다")
            return
        }

        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .insertReportCategory(content,userNo,reportUserNo, (itemIdx+1).toString())
            .enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val result : String = response.body() ?: ""

                    AlertDialog.Builder(this@ReportUserActivity).setMessage("신고하시겠습니까?\n신고자의 정보는 신고자에게 노출되지않습니다")
                        .setPositiveButton("신고하기"
                        ) { dialog, which ->
                            Common.makeToast(this@ReportUserActivity, "${result}")

                            finish()
                        }
                        .setNegativeButton("취소"
                        ) { dialog, which -> dialog.dismiss()}.show()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Common.makeToast(this@ReportUserActivity,getString(R.string.response_server_error))
                }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}