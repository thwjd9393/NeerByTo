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

    private val tabUserfind:TabLayout by lazy { binding.tabUserfind }

    private val pager:ViewPager2 by lazy { binding.pagerUserfind }

    var tabTitle = arrayOf("아이디 찾기","비밀번호 찾기") //탭 제목

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //앱바 설정
        setSupportActionBar(findViewById(R.id.toolbar)) //include 한 레이아웃은 바인딩 안됨
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //왼쪽 뒤로가기 버튼
        supportActionBar!!.setTitle(R.string.find_user) // 타이틀 재설정

        //뷰페이저 연동
        //1. 아답터 불러오기
        val findUserAdapter = FindUserAdapter(this) // 아답터 불러오기

        //2. 아답터에 addFragment() 메소드를 이용하여 보여줄 화면(플래그먼트)들 add
        findUserAdapter.addFragment(FindUserIdFragment())  
        findUserAdapter.addFragment(FindUserPasswdFragment()) //플래그먼트들 추가

        //3. 뷰 페이저에 아답터 연결
        pager.adapter = findUserAdapter

        //4. add 한 프래그먼트 페이지 붙이기
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        // 5. TabLayoutMediator 를 이용하여 탭 레이아웃 이름 등록
        TabLayoutMediator(tabUserfind, pager){ tab, position ->
            tab.text = tabTitle.get(position).toString()
        }.attach()

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

