package com.jscompany.neerbyto.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityMyFriendBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFriendActivity : AppCompatActivity() {

    private val binding : ActivityMyFriendBinding by lazy { ActivityMyFriendBinding.inflate(layoutInflater) }

    //아답터
    var friendItem : MutableList<MyFriendItem> = mutableListOf()
    lateinit var friendAdapter : MyFriendAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

    }

    private fun init(){

        //툴바 등록
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.my_friend)

        //데이터 불러오기
        loadFriendData()

        //아답터 연동
        friendAdapter = MyFriendAdapter(this@MyFriendActivity,friendItem)
        binding.recyclerMyFriend.adapter = friendAdapter

    }

    //데이터 불러오기
    private fun loadFriendData() {

        Log.i("TAG", "넘어가는값 ${Common.getUserNo(this)} , ${Common.USER_STATUS_ABLE}")
        
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .loadMyFriendData(Common.getUserNo(this),Common.USER_STATUS_ABLE).enqueue(object : Callback<MutableList<MyFriendItem>>{
                override fun onResponse(
                    call: Call<MutableList<MyFriendItem>>,
                    response: Response<MutableList<MyFriendItem>>
                ) {

                    val items : MutableList<MyFriendItem> = response.body() ?: mutableListOf()

                    Log.i("TAG", "3 ${items}")

                    for (i in 0 until items.size) {
                        friendItem.add(items[i])
                    }
                    friendAdapter.notifyItemInserted(friendItem.size-1) //마지막 번호가 추가 됐다 알려주기

                    setView(friendItem.size)
                }

                override fun onFailure(call: Call<MutableList<MyFriendItem>>, t: Throwable) {
                    Common.makeToast(this@MyFriendActivity,getString(R.string.response_server_error))
                }
            })
        
    }

    //화면 셋
    private fun setView(size: Int) {
        if(size <= 0 ) {
            binding.emptyWarp.visibility = View.VISIBLE
        } else {
            binding.emptyWarp.visibility = View.GONE
        }
    }


    //친구 추가 삭제
    fun delFriend(friendNo: Int, position:Int) {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(MyLikeService::class.java)
            .delMyFriend(friendNo).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val result = response.body().toString()

                    Common.makeToast(this@MyFriendActivity, result)

                    friendItem.removeAt(position)// 기차 개수도 바꿔줘야함
                    //friendAdapter.notifyDataSetChanged() //클릭한 번호 삭제 됐다고 알려주기
                    friendAdapter.notifyItemRemoved(position)

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Common.makeToast(this@MyFriendActivity,getString(R.string.response_server_error))
                }
            })
    }

    //프로필 화면으로 이동
    fun moveToProfile(userNo: Int) {
        startActivity(Intent(this,ProfileActivity::class.java).putExtra("userNo",userNo.toString()))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

}