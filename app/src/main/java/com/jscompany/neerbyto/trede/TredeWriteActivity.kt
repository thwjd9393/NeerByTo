package com.jscompany.neerbyto.trede

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityTredeWriteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.Calendar


class TredeWriteActivity : AppCompatActivity() {

    private val binding : ActivityTredeWriteBinding by lazy { ActivityTredeWriteBinding.inflate(layoutInflater) }

    private val builder  : AlertDialog.Builder by lazy { AlertDialog.Builder(this) }

    private val cal : Calendar by lazy { Calendar.getInstance() }

    //이미지 담을 리스트
    var uriArrayList : MutableList<Uri> = mutableListOf()
    lateinit var imgAdapter : TredeImgAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

        getCategoty()

    }

    private fun init(){
        //커스텀 액션바 등록
        setSupportActionBar(findViewById(R.id.toolbar)) //include 한 레이아웃은 바인딩 안됨
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //왼쪽 뒤로가기 버튼
        supportActionBar!!.setTitle(R.string.to_write)

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
        binding.btnSelectImg.setOnClickListener { clickSelectImg() }

        //이미지 아답터 연결
        imgAdapter = TredeImgAdapter(this,uriArrayList)
        binding.recyclerTredeImg.adapter = imgAdapter

    }

    //카메라 버튼 클릭 -> 갤러리 이동
    private fun clickSelectImg() {
        //사진픽 띄우기
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).setType("image/*").putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        resultLauncher.launch(intent)
    }

    var resultLauncher : ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if (result.resultCode != RESULT_CANCELED) { //취소하고 돌아오지 않을 때
            val intent = result.data //uri
            val clipData = intent?.clipData
            //ClipData : 선택한걸 저장 복사한 조그마한 저장소
            if(clipData == null) return@registerForActivityResult

            val size = clipData!!.itemCount //아이템 개수

            //!!이미지 셋!!
            for (i in 0 until size) {
                uriArrayList.add(clipData.getItemAt(i).uri)
            }

            //아답터한테 알려주기 - 화면 갱신~!
            imgAdapter.notifyDataSetChanged()
        }
    }

    //약속장소 버튼 -> 키워드 검색 -> 클릭 => 지도 확인,,,,
    private fun clickSpot() {
        //내 위치 기준으로 가까운 곳 찾아줌
        startActivity(Intent(this,TredeSearchPlaceActivity::class.java))

    }

    private fun clickHangoutDate() {

        val dataPick = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            binding.tvHangoutDate.text = "${year}/${month}/${dayOfMonth}"
        }

        DatePickerDialog(this, dataPick, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }


    private fun clickHangoutTime() {

        val timePick = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            if(hourOfDay < 12) binding.tvHangoutTime.text = "AM ${hourOfDay}:${minute}"
            else binding.tvHangoutTime.text = "PM ${hourOfDay}:${minute}"
        }
        val dialog = TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,timePick,cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true)
        dialog.setTitle(R.string.hang_out_time)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private var items: MutableList<String> = mutableListOf()

    //카테고리 디비에서 불러오기
     private fun getCategoty(){
        val retrofit: Retrofit = RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl)
        retrofit.create(TredeService::class.java).loadCategoty().enqueue(object : Callback<MutableList<TredeCategotyVO>>{
            override fun onResponse(
                call: Call<MutableList<TredeCategotyVO>>,
                response: Response<MutableList<TredeCategotyVO>>
            ) {
                var resposeItem : MutableList<TredeCategotyVO>? = response.body()

                for(i in resposeItem!!.indices){
                    items.add(resposeItem.get(i).tredCtyName)
                }
            }

            override fun onFailure(call: Call<MutableList<TredeCategotyVO>>, t: Throwable) {
                Common.makeToast(this@TredeWriteActivity, "서버에 문제가 있습니다")
            }
        })

     }

    private fun clickCategory() {
        //카테고리 클릭 다이아로그
        builder.setItems(items.toTypedArray() , DialogInterface.OnClickListener { dialog, which ->
            binding.tvTredeCategory.text = items[which]
            binding.tvTredeCategoryIndenx.text = which.toString()
        }).create().show()
    }

    //옵션 메뉴 만드는 콜백
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_trede_write, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        else if(item.itemId == R.id.option_write) {
            insertTredeContent()
            
        }
        return super.onOptionsItemSelected(item)
    }

    //글 등록
    private fun insertTredeContent() {
        //전송할 데이터 묶기
        var title = binding.etTitle.text.toString()
        var content = binding.etContent.text.toString()
        var oriPrice = binding.etPrice.text.toString()
        var joinCount = binding.etJoinCount.text.toString()
        var joinDate = binding.tvHangoutDate.text.toString()
        var joinTime = binding.tvHangoutTime.text.toString()
        var joinPlace = intent.getStringExtra("placeName") ?: "대화하고 정해요" //아답터에서 선택한 이름
        var resion = Common.dong ?: ""
        var tredCtyNo = binding.tvTredeCategoryIndenx.text.toString()
        var userNo = Common.getUserNo(this)
        var price = (oriPrice.toInt() / joinCount.toInt()).toString()

        //맵에 넣기
        var dataPart : MutableMap<String,String> = mutableMapOf()
        dataPart.put("title", title)
        dataPart.put("content", content)
        dataPart.put("oriPrice", oriPrice)
        dataPart.put("price", price)
        dataPart.put("joinDate", joinDate)
        dataPart.put("joinTime", joinTime)
        dataPart.put("joinPlace", joinPlace)
        dataPart.put("resion", resion)
        dataPart.put("tredCtyNo", tredCtyNo)
        dataPart.put("userNo", userNo)

        //레트로핏 작업
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl)
            .create(TredeService::class.java).insertTredeData(dataPart).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    var result = response.body()

                    Common.makeToast(this@TredeWriteActivity, result)
                }

                override fun onFailure(call: Call<String>, t: Throwable) = Common.makeToast(this@TredeWriteActivity,"서버에 문제가 있습니다")
            })

    }

}