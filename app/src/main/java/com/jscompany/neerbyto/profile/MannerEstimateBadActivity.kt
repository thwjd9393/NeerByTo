package com.jscompany.neerbyto.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.Toast
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityMannerEstimateBadBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MannerEstimateBadActivity : AppCompatActivity() {

    private val binding : ActivityMannerEstimateBadBinding by lazy { ActivityMannerEstimateBadBinding.inflate(layoutInflater) }

    var items : MutableList<BadCtyItem> = mutableListOf()
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
        supportActionBar!!.setTitle(R.string.manner_bad)

        //넘어온 데이터
        userNo = intent.getStringExtra("userNo") ?: ""
        val userNic = intent.getStringExtra("userNic") ?: ""

        binding.tvNicname.text = userNic

        getdata()

        binding.btnOk.setOnClickListener { clickOk() }
        binding.btnCancle.setOnClickListener { finish() }

    }

    private fun getdata() {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .loadBMannerCategory().enqueue(object : Callback<MutableList<BadCtyItem>> {
                override fun onResponse(
                    call: Call<MutableList<BadCtyItem>>,
                    response: Response<MutableList<BadCtyItem>>
                ) {

                    var result : MutableList<BadCtyItem> = response.body() ?: mutableListOf()

                    for(i in 0 until  result.size){
                        items.add(BadCtyItem(result[i].bCtyNo, result[i].content))

                        val rdbtn = RadioButton(this@MannerEstimateBadActivity)
                        rdbtn.id = items[i].bCtyNo
                        rdbtn.text = items[i].content
                        binding.radioGroup.addView(rdbtn)
                    }

                    //선택한 인덱스 번호
                    binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
                        selectId = i.toString()
                    }

                }

                override fun onFailure(call: Call<MutableList<BadCtyItem>>, t: Throwable) {
                    Common.makeToast(this@MannerEstimateBadActivity, getString(R.string.response_server_error))
                }
            })
    }

    private fun clickOk() {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .insertBManner(userNo, selectId).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Common.makeToast(this@MannerEstimateBadActivity, "${response.body()}")

                    finish()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Common.makeToast(this@MannerEstimateBadActivity, getString(R.string.response_server_error))
                }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}