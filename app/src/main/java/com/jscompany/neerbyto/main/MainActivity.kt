package com.jscompany.neerbyto.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.chat.MainChatFragment
import com.jscompany.neerbyto.databinding.ActivityMainBinding
import com.jscompany.neerbyto.profile.MainMyZoneFragment
import com.jscompany.neerbyto.trede.MainTredeFragment

class MainActivity : AppCompatActivity() {

    private val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    //바텀네비
    private val bottomnavi:BottomNavigationView by lazy { binding.bnav }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //맨 처음 보여줄 플래그먼트 데려오기
        supportFragmentManager.beginTransaction().add(R.id.fragment_warp, MainTredeFragment()).commit()
        //supportFragmentManager.beginTransaction().add(binding.fragmentWarp, MainTredeFragment())

        //바텀 네비게이션에 연동
        bottomnavi.setOnItemSelectedListener {
            if(it.itemId == R.id.bnv_home) supportFragmentManager.beginTransaction().replace(R.id.fragment_warp, MainTredeFragment()).commit()
            else if(it.itemId == R.id.bnv_chat) supportFragmentManager.beginTransaction().replace(R.id.fragment_warp, MainChatFragment()).commit()
            else if(it.itemId == R.id.bnv_myzone) supportFragmentManager.beginTransaction().replace(R.id.fragment_warp, MainMyZoneFragment()).commit()

            return@setOnItemSelectedListener true
        }
        
    }
}