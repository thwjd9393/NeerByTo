package com.jscompany.neerbyto.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityMyWriteBinding

class MyWriteActivity : AppCompatActivity() {

    private val binding : ActivityMyWriteBinding by lazy { ActivityMyWriteBinding.inflate(layoutInflater) }

    private val tabMyWrite : TabLayout by lazy { binding.tabMyWrite }
    private val pagerMyWrite : ViewPager2 by lazy { binding.pagerMyWrite }

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
        supportActionBar!!.setTitle(R.string.write)

        //넘겨받은 유저 값 플래그먼트로 보내주기
        val userNo : String = intent.getStringExtra("userNo") ?: ""

        val bundle = Bundle()
        bundle.putString("userNo",userNo) //데이터 담기

        //아답터
        val  myWriteAdapter = MyWriteAdapter(this)

        //프래그 먼트 추가
        val ingFragment  = MyWriteIngFragment()
        val endFragment  = MyWriteEndFragment()

        //데이터 넘기기
        ingFragment.arguments = bundle
        endFragment.arguments = bundle

        myWriteAdapter.addFragment(ingFragment)
        myWriteAdapter.addFragment(endFragment)

        //뷰페이저에 연동
        pagerMyWrite.adapter = myWriteAdapter

        pagerMyWrite.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position).toString()
            }
        })

        //탭 레이아웃에 이름 등록
        TabLayoutMediator(tabMyWrite, pagerMyWrite) { tab, position ->
            tab.text = tabTitle.get(position)
        }.attach()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}