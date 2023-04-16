package com.jscompany.neerbyto.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.FragmentMainChatBinding

class MainChatFragment : Fragment() {

    private val binding: FragmentMainChatBinding by lazy { FragmentMainChatBinding.inflate(layoutInflater) }

    //채팅방 이름으로 쓸 애
    lateinit var tredeNo : String

    //리사이클러뷰용 변수 - 리스트용
    //lateinit var chatRooms : MutableList<>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = binding.root

        var toolbar = binding.findViewById(R.id.toolbar) as Toolbar
        //var activity2 : AppCompatActivity = getActivity() as AppCompatActivity
        //activity2.setSupportActionBar(toolbar)
        toolbar.title = "채팅"

        //채팅방 번호 얻어오기 - 파이어베어스 키로 쓰기


        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    //방번호 얻어오기 sql

}