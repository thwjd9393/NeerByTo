package com.jscompany.neerbyto.trede

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityTredeUpdateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class TredeUpdateActivity : AppCompatActivity() {

    private val binding : ActivityTredeUpdateBinding by lazy { ActivityTredeUpdateBinding.inflate(layoutInflater) }

    private val builder  : AlertDialog.Builder by lazy { AlertDialog.Builder(this) }

    private val cal : Calendar by lazy { Calendar.getInstance() }

    //글번호
    var tredeNo : String = ""

    //선택한 장소 위도 경도
    var selectLongitude : String = ""
    var selectLatitude : String = ""

    //이미지 담을 리스트
    var uriArrayList : MutableList<Uri> = mutableListOf()
    var imgPathList : MutableList<String> = mutableListOf() //이미지 path담을 애
    lateinit var imgAdapter : TredeImgAdapter
    lateinit var imgStringAdapter : TredeImgStringAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //인텐트로 온 번호
        tredeNo = intent.getStringExtra("tredeNo") ?: ""

        loadData()

        init()

        getCategoty()

    }

    private fun init(){
        //커스텀 액션바 등록
        setSupportActionBar(findViewById(R.id.toolbar)) //include 한 레이아웃은 바인딩 안됨
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //왼쪽 뒤로가기 버튼
        supportActionBar!!.setTitle(R.string.to_update)

        //약속시간 클릭 -> 데이터 피커
        binding.tvHangoutDate.setOnClickListener { clickHangoutDate() }
        binding.tvHangoutTime.setOnClickListener { clickHangoutTime() }

        //카테고리 클릭 -> 다이로그
        binding.tvTredeCategory.setOnClickListener { clickCategory() }

        //약속장소 버튼 -> 카카오 지도 api 연동
        binding.btnSpot.setOnClickListener { clickSpot() }

        val placeName : String = intent.getStringExtra("placeName") ?: getString(R.string.select_stop)

        binding.tvSelectSpot.text = placeName  //tv_select_spot

        //카메라 버튼 클릭 -> 갤러리 이동
        binding.btnSelectImg.setOnClickListener {
            clickSelectImg()
        }
    }

    //이미지 셀렉
    private fun clickSelectImg() {

    }

    //약속 장소 선택
    private fun clickSpot() {

    }

    //시간 피커
    private fun clickHangoutTime() {

    }

    //달력 띄우기
    private fun clickHangoutDate() {

    }

    //카테고리 클릭
    private fun clickCategory() {

    }

    //카테고리 불러오기
    private fun getCategoty() {

    }


    //데이터 업데이트
    private fun updateTredeContent() {

    }


    //데이터 로드
    private fun loadData() {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(TredeService::class.java)
            .loadTredeDetail(tredeNo).enqueue(object : Callback<MutableList<TredeDetail>>{
                override fun onResponse(
                    call: Call<MutableList<TredeDetail>>,
                    response: Response<MutableList<TredeDetail>>
                ) {
                    val items : MutableList<TredeDetail> = response.body()!!

                    setData(items)


                }

                override fun onFailure(call: Call<MutableList<TredeDetail>>, t: Throwable) {
                    Common.makeToast(this@TredeUpdateActivity,getString(R.string.response_server_error))
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun setData(items : MutableList<TredeDetail>) {

        //이미지 셋
        if(items[0].img1 != "") imgPathList.add(items[0].img1)
        if(items[0].img2 != "") imgPathList.add(items[0].img2)
        if(items[0].img3 != "") imgPathList.add(items[0].img3)

        if(imgPathList.size > 0) {
            //이미지 불러오기
            //이미지 아답터 연결
            imgStringAdapter = TredeImgStringAdapter(this,imgPathList)
            binding.recyclerTredeImg.adapter = imgAdapter

            binding.tvImgCnt.text = "${imgPathList.size}/3"
        }

        binding.etTitle.setText(items[0].title)
        var price = 0
        if(items[0].price <=0) price.toString() else price = items[0].price
        binding.etPrice.setText(price.toString())
        binding.etJoinCount.setText(items[0].joinCount.toString())
        binding.etContent.setText(items[0].content)
        binding.tvSelectSpot.text = items[0].joinPlace
        binding.tvHangoutDate.text = items[0].joinDate
        binding.tvHangoutTime.text = items[0].joinTime

        
        //카테고리 번호
        items[0].tredCtyNo

    }


    //옵션 메뉴 만드는 콜백
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_trede_write, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        else if(item.itemId == R.id.option_write) {
            updateTredeContent()

        }
        return super.onOptionsItemSelected(item)
    }
}