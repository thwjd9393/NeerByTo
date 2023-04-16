package com.jscompany.neerbyto.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityChatDetailBinding

class ChatDetailActivity : AppCompatActivity() {

    private val binding : ActivityChatDetailBinding by lazy { ActivityChatDetailBinding.inflate(layoutInflater) }

    //파이어베이스
    lateinit var firestore : FirebaseFirestore
    lateinit var chatRef : CollectionReference

    //채팅방 이름
    lateinit var tredeNo : String
    //lateinit var title : String

    //채팅방 제작자 정보 - 번호, 닉네임, 사진
    lateinit var otherUserNo : String
    lateinit var otherUserNic : String
    lateinit var otherImgUrl : String

    //채팅방 정보 -
    lateinit var joinCount : String
    lateinit var joinTime : String
    lateinit var joinSpot : String

    //리사이클러뷰용 변수
    lateinit var messageItems : MutableList<MessageItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //채팅방 번호 얻어오기 - 파이어베어스 키로 쓰기
        tredeNo = intent.getStringExtra("tredeNo") ?: ""

        //인텐트로 받은 값 보기
//        Log.i("TAG","글번호 ${tredeNo}")
//        Log.i("TAG","글쓴이번호 ${otherUserNo}")
//        Log.i("TAG","글쓴이 닉 ${otherUserNic}")
//        Log.i("TAG","글쓴이 사진 ${otherImgUrl}")

        //사용자 이미지 얻어오기
        Common.PROFILEURL

        firestore = FirebaseFirestore.getInstance();
        chatRef = firestore.collection(tredeNo); //채팅방 번호로 방 이름 만들기

        init()

    }

    private fun init(){
        setSupportActionBar(findViewById(R.id.toolbar)) //include 한 레이아웃은 바인딩 안됨
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //왼쪽 뒤로가기 버튼
        //supportActionBar!!.title = title


        
    }

    //

    //채팅방 방 만들기 -> 채팅 하기



    //프래그먼트로 값 보내기 - 리스트 생성을 위해
//    private fun createChatList(){
//        val transaction = supportFragmentManager.beginTransaction()
//
//        var bundle = Bundle()
//        bundle.putString("tredeNo",tredeNo)
//        val mainChatFragment = MainChatFragment()
//        mainChatFragment.arguments = bundle
//
//        transaction.commit()
//    }


    
    //옵션 메뉴
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_chat_detail, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        else if(item.itemId == R.id.option_chat_exit) {
            Toast.makeText(this, "채팅 나가기", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

}