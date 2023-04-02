package com.jscompany.neerbyto.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityMyEnterBinding

class MyEnterActivity : AppCompatActivity() {

    private val binding : ActivityMyEnterBinding by lazy { ActivityMyEnterBinding.inflate(layoutInflater) }

    private val tabMyEnter : TabLayout by lazy { binding.tabMyEnter }
    private val pagerMyEnter : ViewPager2 by lazy { binding.pagerMyEnter }

    var tabTitle = arrayOf("진행중","완료") //탭 제목

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

    }

    private fun init(){

        //툴바 등록
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.enter)

        //아답터
        val  myEnterAdapter = MyEnterAdapter(this)

        //프래그 먼트 추가
        myEnterAdapter.addFragment(MyEnterIngFragment())
        myEnterAdapter.addFragment(MyEnterEndFragment())

        //뷰페이저에 연동
        pagerMyEnter.adapter = myEnterAdapter

        pagerMyEnter.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position).toString()
            }
        })

        //탭 레이아웃에 이름 등록
        TabLayoutMediator(tabMyEnter, pagerMyEnter) { tab, position ->
            tab.text = tabTitle.get(position)
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}