package com.jscompany.neerbyto.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityMyFriendBinding

class MyFriendActivity : AppCompatActivity() {

    private val binding : ActivityMyFriendBinding by lazy { ActivityMyFriendBinding.inflate(layoutInflater) }

    //아답터
    var friendItem : MutableList<ProfileVO> = mutableListOf()
    lateinit var friendAdapter : MyFriendAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //아답터 연동
        friendAdapter = MyFriendAdapter(this,friendItem)
        binding.recyclerMyFriend.adapter = friendAdapter

        init()

    }

    private fun init(){

        //툴바 등록
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.my_friend)

        //데이터 불러오기
        getFriendData()

    }

    private fun getFriendData() {

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}