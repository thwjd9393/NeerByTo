package com.jscompany.neerbyto.servicecenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityNoticeDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeDetailActivity : AppCompatActivity() {

    private val binding:ActivityNoticeDetailBinding by lazy { ActivityNoticeDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

    }

    private fun init() {

        //툴바 등록
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.notice)

        val noticeNo = intent.getIntExtra("noticeNo",0)

        loadNoticeData(noticeNo)

    }

    private fun loadNoticeData(noticeNo: Int) {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(ServicecenterService::class.java)
            .loadNoticeDetail(noticeNo).enqueue(object : Callback<MutableList<noticeItem>>{
                override fun onResponse(
                    call: Call<MutableList<noticeItem>>,
                    response: Response<MutableList<noticeItem>>
                ) {
                    val result : MutableList<noticeItem> = response.body() ?: mutableListOf()

                    setData(result[0])
                }

                override fun onFailure(call: Call<MutableList<noticeItem>>, t: Throwable) {
                    Common.makeToast(this@NoticeDetailActivity,getString(R.string.response_server_error))
                }
            })
    }

    private fun setData(noticeItem: noticeItem) {
        binding.tvTitle.text = noticeItem.title
        binding.tvDate.text = noticeItem.date
        binding.tvContent.text = noticeItem.content
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}