package com.jscompany.neerbyto.trede

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityTredeDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class TredeDetailActivity : AppCompatActivity() {

    val binding:ActivityTredeDetailBinding by lazy { ActivityTredeDetailBinding.inflate(layoutInflater) }

    var tredeNo : String = ""

    var imgList : MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        tredeNo = intent.getStringExtra("tredeNo") ?: ""

        Log.i("TAG","${tredeNo}")

        init()

    }

    private fun init() {
        //커스텀 액션바 등록
        setSupportActionBar(findViewById(R.id.toolbar)) //include 한 레이아웃은 바인딩 안됨
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //왼쪽 뒤로가기 버튼
        supportActionBar!!.setDisplayShowTitleEnabled(false) //타이틀

        loadData() //데이터 로드

        //닉네임 layout누르면 프로필 화면으로 이동
        binding.nicnameWarp.setOnClickListener {  }

        //좋아요 버튼
        binding.btnLike.setOnClickListener {  }

        //채팅 버튼
        binding.btnChat.setOnClickListener {  }

    }

    //데이터 로드
    private fun loadData() {

        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(TredeService::class.java)
            .loadTredeDetail(tredeNo).enqueue(object : Callback<MutableList<TredeDetail>>{
                override fun onResponse(
                    call: Call<MutableList<TredeDetail>>,
                    response: Response<MutableList<TredeDetail>>
                ) {
                    var items : MutableList<TredeDetail> = response.body()!!

                    //이미지 셋
                    var img1 = items.get(0).img1
                    var img2 = items.get(0).img2
                    var img3 = items.get(0).img3

                    if(img1 != null || img1 != "") imgList.add(img1)
                    else if(img2 != null || img2 != "") imgList.add(img2)
                    else if(img3 != null || img3 != "") imgList.add(img3)

                    if(imgList.size > 0) {
                        //페이저에 뷰 연결
                        binding.imgPager.adapter = TredeDetailImgAdapter(this@TredeDetailActivity,imgList)
                        binding.dotsIndcator.attachTo(binding.imgPager)
                    }

                    binding.tvUserNicname.text = items.get(0).userNic
                    
                    //사용자 프로필 사진
                    var address = ""
                    if (items.get(0).profileImg != null) address = "http://mrhisj23.dothome.co.kr/NeerByTo/"+items.get(0).profileImg

                    Glide.with(this@TredeDetailActivity).load(address)
                        .error(R.drawable.user_full).fallback(R.drawable.user_full).into(binding.ivCircleImgUser)

                    binding.tvTitle.text = items.get(0).title
                    binding.tvCategri.text = items.get(0).tredCtyName
                    binding.tvDate.text = items.get(0).date
                    binding.tvJoinCount.text = ": ${items.get(0).joinCount}명"
                    binding.tvLikeCnt.text = items.get(0).likeCnt.toString()
                    binding.tvContent.text = items.get(0).content

                    binding.tvHangOutTime.text = items.get(0).joinDate
                    binding.tvHangOutSpot.text = items.get(0).joinPlace
                    binding.tvPrice.text = items.get(0).price.toString()
                    binding.tvOriPrice.text = items.get(0).oriPrice.toString()

                }

                override fun onFailure(call: Call<MutableList<TredeDetail>>, t: Throwable) {
                    Common.makeToast(this@TredeDetailActivity,"서버에 문제가 있습니다")
                }
            })

    }

    //옵션 메뉴 만드는 콜백
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_trede_detail, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) finish()
        else if(item.itemId == R.id.option_report) { //유저 신고하기
            val intent : Intent = Intent(this,ReportUserActivity::class.java)
                .putExtra("userNic","유저닉네임").putExtra("userNo","5")
            
            startActivity(intent)
        } else if (item.itemId == R.id.option_share) { //페이지 공유하기

        }


        return super.onOptionsItemSelected(item)
    }
}