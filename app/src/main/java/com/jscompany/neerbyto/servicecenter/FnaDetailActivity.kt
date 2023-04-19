package com.jscompany.neerbyto.servicecenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityFnaDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FnaDetailActivity : AppCompatActivity() {

    private val binding : ActivityFnaDetailBinding by lazy { ActivityFnaDetailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

    }


    private fun init() {

        //툴바 등록
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.fna)

        var faNo = intent.getIntExtra("faNo",0)

        loadFnaData(faNo)
    }

    private fun loadFnaData(faNo : Int) {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(ServicecenterService::class.java)
            .loadFnaDetail(faNo).enqueue(object : Callback<MutableList<FnaItem>>{
                override fun onResponse(
                    call: Call<MutableList<FnaItem>>,
                    response: Response<MutableList<FnaItem>>
                ) {
                    val result : MutableList<FnaItem> = response.body() ?: mutableListOf()

                    setdata(result[0])
                }

                override fun onFailure(call: Call<MutableList<FnaItem>>, t: Throwable) {
                    Common.makeToast(this@FnaDetailActivity,getString(R.string.response_server_error))
                }
            })
    }

    private fun setdata(fnaItem: FnaItem) {
        binding.tvTitle.text = fnaItem.title
        binding.tvDate.text = fnaItem.date
        binding.tvContent.text = fnaItem.content
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}