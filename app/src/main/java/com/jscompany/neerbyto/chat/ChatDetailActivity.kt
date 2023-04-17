package com.jscompany.neerbyto.chat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityChatDetailBinding
import java.util.Calendar


class ChatDetailActivity : AppCompatActivity() {

    private val binding : ActivityChatDetailBinding by lazy { ActivityChatDetailBinding.inflate(layoutInflater) }

    //파이어베이스
    lateinit var firestore : FirebaseFirestore

    //채팅방 이름
    lateinit var tredeNo : String
    var title : String = ""

    //리사이클러뷰용 변수
    var messageItems : MutableList<MessageItem> = mutableListOf()
    lateinit var chatAdapter : ChatAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //채팅방 번호 얻어오기 - 파이어베어스 키로 쓰기
        tredeNo = intent.getStringExtra("tredeNo") ?: ""
        firestore = FirebaseFirestore.getInstance()
        //chatRef = firestore.collection("Chat").document(tredeNo)

        getChatRoom()

        init()

    }

    private fun init(){
        setSupportActionBar(findViewById(R.id.toolbar)) //include 한 레이아웃은 바인딩 안됨
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //왼쪽 뒤로가기 버튼
        //supportActionBar!!.title = title

        //아답터 연결
        chatAdapter = ChatAdapter(this, messageItems)
        binding.recyclerChat.adapter = chatAdapter

        //메세지 부르기
        getChatMessage()
    }

    //방 정보 얻어오기
    private fun getChatRoom(){
        firestore.collection("Chat")
            .whereEqualTo("tredeNo",tredeNo)
            .get().addOnSuccessListener{
                it.documents

                for (snapshot in it) {
                    //(요소 하나 임시 저장 변수: 값 가진 배열)
                    val data = snapshot.data

                    binding.tvHangOutSpot.text = data.get("joinSpot").toString().substring(3)
                    binding.tvHangOutTime.text = data.get("joinTime").toString()
                    supportActionBar!!.title =data.get("title").toString()

                }
        }
    }
    
    //해당 메세지 가져오기
    private fun getChatMessage(){
        firestore.collection("Chat").document(tredeNo).collection("ChatMessages").addSnapshotListener { value, error ->

            //변경된 거 찾아오기
            var documentChanges : MutableList<DocumentChange> = value?.documentChanges ?: mutableListOf()

            for (documentChange in documentChanges) {
                //2.변경된 문서내역의 데이터를 촬영한 DocumentSnapshot얻어오기
                val snapshot: DocumentSnapshot = documentChange.document

                //3.Document에 있는 필드값 가져오기
                val msg = snapshot.data
                val nic = msg!!["nic"].toString()
                val message = msg!!["message"].toString()
                val profileUrl = msg!!["profileUrl"].toString()
                val time = msg!!["time"].toString()

                //4.읽어온 메세지를 리스트에 추가
                messageItems.add(MessageItem(nic, message, profileUrl, time))

                //아답터에게 데이터가 추가 되었다고 공지 -> 해야 화면 갱신됨
                chatAdapter.notifyItemInserted(messageItems.size - 1)

                //리사이클러뷰의 스크롤위치 가장 아래로 이동
                binding.recyclerChat.scrollToPosition(messageItems.size - 1)
            }
        }
        binding.btnSend.setOnClickListener { sendMessage() }
    }

    var sendMsgTime: String = ""

    //메세지 보내기
    private fun sendMessage(){
        //메세지 데이터
        val nic: String = Common.getNic(this)
        val message: String = binding.etMessage.getText().toString()
        val profileUrl: String = Common.PROFILEURL
        //메세지를 작성 시간을 문자열 [시:분]
        val calendar: Calendar = Calendar.getInstance()
        sendMsgTime = "${calendar.get(Calendar.HOUR_OF_DAY)} : ${calendar.get(Calendar.MINUTE)}"

        var item : MessageItem = MessageItem(nic, message, profileUrl, sendMsgTime)

        firestore.collection("Chat").document(tredeNo).collection("ChatMessages")
            .document("MSG_"+ System.currentTimeMillis())
            .set(item).addOnSuccessListener {
            //Common.makeToast(this, "save")
            //메세지 보내기 성공
        }

        var chatSendInfo : MutableMap<String, Any> = mutableMapOf()
        chatSendInfo["lastChat"] = message
        chatSendInfo["lastChatTime"] = sendMsgTime

        //메세지 보내면 마지막 메세지와 시간 업데이트
        firestore.collection("Chat").document(tredeNo).update(chatSendInfo)
            .addOnSuccessListener { 
                Log.i("TAG", "마지막 시간 & 메세지 등록완료")
            }

        binding.etMessage.setText("");

        //키보드 내리기
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }

    
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