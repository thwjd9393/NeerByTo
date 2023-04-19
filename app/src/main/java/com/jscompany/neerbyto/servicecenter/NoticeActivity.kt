package com.jscompany.neerbyto.servicecenter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityNoticeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeActivity : AppCompatActivity() {

    private val binding : ActivityNoticeBinding by lazy { ActivityNoticeBinding.inflate(layoutInflater) }

    lateinit var noticeAdapter : NoticeAdapter
    var noticeItem : MutableList<noticeItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadNoticeData()

        init()
    }

    private fun init() {

        //툴바 등록
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.notice)

    }

    //데이터 로드
    private fun loadNoticeData() {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(ServicecenterService::class.java)
            .loadNoticeData().enqueue(object : Callback<MutableList<noticeItem>>{
                override fun onResponse(
                    call: Call<MutableList<noticeItem>>,
                    response: Response<MutableList<noticeItem>>
                ) {
                    val result : MutableList<noticeItem> = response.body() ?: mutableListOf()

                    for (i in 0 until result.size) {
                        noticeItem.add(result[i])
                    }

                    noticeAdapter = NoticeAdapter(this@NoticeActivity,noticeItem)
                    binding.recyclerNotice.adapter = noticeAdapter
                }

                override fun onFailure(call: Call<MutableList<noticeItem>>, t: Throwable) {
                    Common.makeToast(this@NoticeActivity,getString(R.string.response_server_error))
                }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    fun moveToDetail(noticeNo: Int) {
        startActivity(Intent(this, NoticeDetailActivity::class.java).putExtra("noticeNo",noticeNo))
    }

}