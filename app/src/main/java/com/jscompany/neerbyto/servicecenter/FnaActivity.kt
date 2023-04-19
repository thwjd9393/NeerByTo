package com.jscompany.neerbyto.servicecenter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityFnaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FnaActivity : AppCompatActivity() {

    private val binding : ActivityFnaBinding by lazy { ActivityFnaBinding.inflate(layoutInflater) }

    lateinit var faAdapter : FnaAdapter
    var fnaList : MutableList<FnaItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadFaData()

        init()
    }

    private fun init() {

        //툴바 등록
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.fna)

    }

    //데이터 연동
    private fun loadFaData() {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(ServicecenterService::class.java)
            .loadFnaData().enqueue(object : Callback<MutableList<FnaItem>>{
                override fun onResponse(
                    call: Call<MutableList<FnaItem>>,
                    response: Response<MutableList<FnaItem>>
                ) {
                    val result : MutableList<FnaItem> = response.body() ?: mutableListOf()

                    for (i in 0 until result.size) {
                        fnaList.add(result[i])
                    }

                    faAdapter = FnaAdapter(this@FnaActivity,fnaList)
                    binding.recyclerNotice.adapter = faAdapter
                }

                override fun onFailure(call: Call<MutableList<FnaItem>>, t: Throwable) {
                    Common.makeToast(this@FnaActivity,getString(R.string.response_server_error))
                }
            })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    fun moveToDetail(faNo: Int) {
        startActivity(Intent(this, FnaDetailActivity::class.java).putExtra("faNo",faNo))
    }
}