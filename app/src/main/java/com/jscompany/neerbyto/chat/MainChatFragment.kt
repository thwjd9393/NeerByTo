package com.jscompany.neerbyto.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.FragmentMainChatBinding

class MainChatFragment : Fragment() {

    private val binding: FragmentMainChatBinding by lazy { FragmentMainChatBinding.inflate(layoutInflater) }

    //파이어베이스
    lateinit var firestore : FirebaseFirestore

    //리사이클러뷰용 변수
    var messageItems : MutableList<ChatRoom> = mutableListOf()
    lateinit var chatAdapter : ChatRoomAdapter

    //채팅방 이름으로 쓸 애
    lateinit var tredeNo : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = binding.root

        var toolbar = binding.findViewById(R.id.toolbar) as Toolbar
        toolbar.title = "채팅"

        firestore = FirebaseFirestore.getInstance()

        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        //아답터 연결
        chatAdapter = ChatRoomAdapter(requireActivity(), messageItems)
        binding.charRecycler.adapter = chatAdapter

        getInChat()
    }


    //내가 들어가 있는 채팅방 번호 가져오기
    private fun getInChat(){
        //채팅방 번호 얻어오기 - 파이어베어스 키로 쓰기
        firestore.collection("Chat").get().addOnSuccessListener {
            var data : MutableMap<String,Any> = mutableMapOf()
            var users : MutableList<String> = mutableListOf()
            for (snapshot in it) { //저장되어 있는 것들 차장오기
                data = snapshot.data
                users = data["users"] as MutableList<String>

                if( Common.getUserNo(requireActivity()) in users) {
                    //내가 들어가 있는 방 list에 넣기 - 대량의 데이터 준비
                    messageItems.add(ChatRoom(data["tredeNo"].toString(),users,data["writeUserNic"].toString(),
                        data["writeUserNo"].toString(), data["writeImg"].toString(),data["title"].toString(),
                        data["joinCount"].toString(), data["joinTime"].toString(),data["joinSpot"].toString(),
                        data["lastChat"].toString(), data["lastChatTime"].toString()))

                    chatAdapter.notifyItemInserted(messageItems.size-1) //마지막 번호가 추가 됐다 알려주기
                }
            }
        }
    }
    


}