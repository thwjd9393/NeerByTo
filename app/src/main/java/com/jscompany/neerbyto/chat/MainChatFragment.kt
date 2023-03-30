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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_chat, container,false)

        var toolbar = view.findViewById(R.id.toolbar) as Toolbar

        var activity2 : AppCompatActivity = getActivity() as AppCompatActivity

        activity2.setSupportActionBar(toolbar)
        toolbar.title = "채팅"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}