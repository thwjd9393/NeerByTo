package com.jscompany.neerbyto.trede

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityTredeUpdateBinding
import java.util.Calendar

class TredeUpdateActivity : AppCompatActivity() {

    private val binding : ActivityTredeUpdateBinding by lazy { ActivityTredeUpdateBinding.inflate(layoutInflater) }

    private val builder  : AlertDialog.Builder by lazy { AlertDialog.Builder(this) }

    private val cal : Calendar by lazy { Calendar.getInstance() }

    //선택한 장소 위도 경도
    var selectLongitude : String = ""
    var selectLatitude : String = ""

    //이미지 담을 리스트
    var uriArrayList : MutableList<Uri> = mutableListOf()
    var imgPathList : MutableList<String> = mutableListOf() //이미지 path담을 애
    lateinit var imgAdapter : TredeImgAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

        //이미지 아답터 연결
        imgAdapter = TredeImgAdapter(this,uriArrayList)
        binding.recyclerTredeImg.adapter = imgAdapter

        //카메라 버튼 클릭 -> 갤러리 이동
        binding.btnSelectImg.setOnClickListener {
            clickSelectImg()
        }
    }

    //이미지 셀렉
    private fun clickSelectImg() {
        TODO("Not yet implemented")
    }

    //약속 장소 선택
    private fun clickSpot() {
        TODO("Not yet implemented")
    }

    //시간 피커
    private fun clickHangoutTime() {
        TODO("Not yet implemented")
    }

    //달력 띄우기
    private fun clickHangoutDate() {
        TODO("Not yet implemented")
    }

    //카테고리 클릭
    private fun clickCategory() {
        TODO("Not yet implemented")
    }

    //카테고리 불러오기
    private fun getCategoty() {
        TODO("Not yet implemented")
    }


    //데이터 업데이트
    private fun updateTredeContent() {
        TODO("Not yet implemented")
    }


    //데이터 로드
    private fun loadData() {

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