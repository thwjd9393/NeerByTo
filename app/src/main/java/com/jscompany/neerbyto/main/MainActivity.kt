package com.jscompany.neerbyto.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
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

        //바텀 네비게이션에 연동
        bottomnavi.setOnItemSelectedListener {
            changeFragment(it.itemId)
            true
        }

        // 첫 화면
        changeFragment(R.id.bnv_home)
        
    }

    //플래그먼트 변경 함수
    private fun changeFragment(menuItemId:Int){
        val targetFragment = getFragment(menuItemId)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_warp, targetFragment).commit()
    }

    // 플래그먼트 화면 반환 함수
    private fun getFragment(menuItemId: Int) : Fragment {
        val fragment = when (menuItemId) {
            R.id.bnv_home -> MainTredeFragment()
            R.id.bnv_chat -> MainChatFragment()
            R.id.bnv_myzone -> MainMyZoneFragment()
            else -> throw IllegalArgumentException("not found menu item id")
        }
        return fragment
    }

}