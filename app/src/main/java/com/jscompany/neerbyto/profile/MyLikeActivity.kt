package com.jscompany.neerbyto.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityMyLikeBinding
import com.jscompany.neerbyto.trede.TredeDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyLikeActivity : AppCompatActivity() {

    private val binding : ActivityMyLikeBinding by lazy { ActivityMyLikeBinding.inflate(layoutInflater) }

    //게시물 수 있는지 체크
    var count : String = ""

    var likeItems : MutableList<MyLikeItem> = mutableListOf()
    lateinit var likeAdapter : MyLikeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //아답터 연결
        likeAdapter = MyLikeAdapter(this,likeItems)
        binding.recyclerMyLike.adapter = likeAdapter
        
        //데이터 로드
        roadData()
        
        init()
        
    }

    private fun init() {

        //툴바 등록
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.like)

    }

    override fun onResume() {
        super.onResume()

        if(count == "0" ){
            binding.emptyWarp.visibility = View.VISIBLE
            binding.recyclerMyLike.visibility = View.GONE
        } else {
            binding.emptyWarp.visibility = View.GONE
            binding.recyclerMyLike.visibility = View.VISIBLE
        }
    }

    //화면 로드
    private fun roadData() {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .loadLikeData(Common.getUserNo(this)).enqueue(object : Callback<MutableList<MyLikeItem>>{
                override fun onResponse(
                    call: Call<MutableList<MyLikeItem>>,
                    response: Response<MutableList<MyLikeItem>>
                ) {
                    val items : MutableList<MyLikeItem> = response.body() ?: mutableListOf()

                    for(i in 0 until items.size){
                        likeItems.add(items[i])
                    }
                    likeAdapter.notifyItemInserted(likeItems.size-1) //마지막 번호가 추가 됐다 알려주기

                    count = likeAdapter.itemCount.toString()
                }

                override fun onFailure(call: Call<MutableList<MyLikeItem>>, t: Throwable) {
                    Common.makeToast(this@MyLikeActivity,getString(R.string.response_server_error))
                }
            })
    }

    fun setResultData(tredeNo: String) {
        startActivity(Intent(this, TredeDetailActivity::class.java).putExtra("tredeNo", tredeNo))
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

}