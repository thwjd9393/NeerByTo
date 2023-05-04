package com.jscompany.neerbyto.profile

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityMannerEstimateGoodBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MannerEstimateGoodActivity : AppCompatActivity() {

    private val binding : ActivityMannerEstimateGoodBinding by lazy { ActivityMannerEstimateGoodBinding.inflate(layoutInflater) }

    //카테고리 아답터
    var items : MutableList<GoodCtyItem> = mutableListOf()
    var selectId = ""

    var userNo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

    }

    private fun init() {

        //툴바 등록
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.manner_good)

        //넘어온 데이터
        userNo = intent.getStringExtra("userNo") ?: ""
        val userNic = intent.getStringExtra("userNic") ?: ""

        binding.tvNicname.text = userNic

        getdata()

        binding.btnOk.setOnClickListener { clickOk() }
        binding.btnCancle.setOnClickListener { finish() }

    }

    //칭찬 카테고리 가져오기
    private fun getdata() {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .loadGMannerCategory().enqueue(object : Callback<MutableList<GoodCtyItem>>{
                override fun onResponse(
                    call: Call<MutableList<GoodCtyItem>>,
                    response: Response<MutableList<GoodCtyItem>>
                ) {
                    var result : MutableList<GoodCtyItem> = response.body() ?: mutableListOf()

                    for(i in 0 until  result.size){
                        items.add(GoodCtyItem(result[i].gCtyNo, result[i].content))

                        val rdbtn = RadioButton(this@MannerEstimateGoodActivity)
                        rdbtn.id = items[i].gCtyNo
                        rdbtn.text = items[i].content
                        binding.radioGroup.addView(rdbtn)
                    }

                    //선택한 인덱스 번호
                    binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
                        selectId = i.toString()
                    }

                }

                override fun onFailure(call: Call<MutableList<GoodCtyItem>>, t: Throwable) {
                    Common.makeToast(this@MannerEstimateGoodActivity, getString(R.string.response_server_error))
                }
            })
    }

    //완료
    private fun clickOk() {

        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .insertGManner(userNo, selectId).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Common.makeToast(this@MannerEstimateGoodActivity, "${response.body()}")

                    finish()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Common.makeToast(this@MannerEstimateGoodActivity, getString(R.string.response_server_error))
                }
            })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}