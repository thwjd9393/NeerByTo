package com.jscompany.neerbyto.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityFindUserBinding

class FindUserActivity : AppCompatActivity() {

    val binding:ActivityFindUserBinding by lazy { ActivityFindUserBinding.inflate(layoutInflater) }

    lateinit var tabUserfind:TabLayout
    lateinit var medator:TabLayoutMediator
    lateinit var pager:ViewPager2

    var tabTitle = arrayOf(R.string.find_id, R.string.find_passwd) //탭 제목

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //앱바 설정
        setSupportActionBar(findViewById(R.id.toolbar)) //include 한 레이아웃은 바인딩 안됨
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //왼쪽 뒤로가기 버튼
        supportActionBar!!.setTitle(R.string.find_user) // 타이틀 재설정

        //Fragment 연동
        pager = binding.pagerUserfind //뷰 페이저 가져오기
        
        val findUserAdaper = FindUserAdaper(this) // 아답터 불러오기

        findUserAdaper.addFragment(FindUserIdFragment())
        findUserAdaper.addFragment(FindUserPasswdFragment()) //플래그먼트 추가

        pager.adapter = findUserAdaper


        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })


//        TabLayoutMediator(ta, viewPager){ tab, position ->
//            tab.text = "Tab ${position+1}"
//        }.attach()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> { //뒤로가기 버튼 활성화
                finish()
                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }
}

