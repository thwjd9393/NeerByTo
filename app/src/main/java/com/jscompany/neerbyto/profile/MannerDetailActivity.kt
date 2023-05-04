package com.jscompany.neerbyto.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityMannerDetailBinding

class MannerDetailActivity : AppCompatActivity() {

    private val binding : ActivityMannerDetailBinding by lazy { ActivityMannerDetailBinding.inflate(layoutInflater) }

    private val tabManner : TabLayout by lazy { binding.tabManner }
    private val pagerManner : ViewPager2 by lazy { binding.pagerManner }

    var tabTitle = arrayOf("칭찬","비매너") //탭 제목

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

    }

    private fun init(){

        //사용자 번호
        val userNo = intent.getStringExtra("userNo")

        //플래그먼트한테 값 보내줄 번들
        var bundle : Bundle = Bundle()
        bundle.putString("userNo", userNo)

        //툴바 등록
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.manner_detail)

        //아답터
        val  mannerAdapter = MannerDetailAdapter(this)

        //플래그 먼트 준비
        val mannerGoodFragment = MannerGoodFragment()
        val mannerBadFragment = MannerBadFragment()

        //갑 전달
        mannerGoodFragment.arguments = bundle
        mannerBadFragment.arguments = bundle

        //프래그 먼트 추가
        mannerAdapter.addFragment(mannerGoodFragment)
        mannerAdapter.addFragment(mannerBadFragment)

        //뷰페이저에 연동
        pagerManner.adapter = mannerAdapter

        pagerManner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position).toString()
            }
        })

        //탭 레이아웃에 이름 등록
        TabLayoutMediator(tabManner, pagerManner) { tab, position ->
            tab.text = tabTitle.get(position)
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}