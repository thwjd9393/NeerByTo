package com.jscompany.neerbyto.trede

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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

        //카메라 버튼 클릭 -> 갤러리 이동
        binding.btnSelectImg.setOnClickListener { clickSelectImg() }
    }

    //카메라 버튼 클릭 -> 갤러리 이동
    private fun clickSelectImg() {

    }

    //약속장소 버튼 -> 카카오 지도 api 연동
    private fun clickSpot() {

    }

    private fun clickHangoutDate() {

        val dataPick = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            binding.tvHangoutDate.text = "${year}/${month}/${dayOfMonth}"
        }

        DatePickerDialog(this, dataPick, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }


    private fun clickHangoutTime() {

        val timePick = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            if(hourOfDay < 12) binding.tvHangoutTime.text = "AM ${hourOfDay} : ${minute}"
            else binding.tvHangoutTime.text = "PM ${hourOfDay} : ${minute}"
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
            
            Toast.makeText(this, "글이 등록 되었습니다", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    //글 등록
    private fun insertTredeContent() {
        //전송할 데이터 묶기
        var title = binding.etTitle.text.toString()
        var content = binding.etContent.text.toString()
        var oriPrice = binding.etPrice.text.toString()
        var price = binding.etPrice.text.toString() // oriPrice/joinCount
        var joinCount = binding.etJoinCount.text.toString()
        var joinDate = binding.tvHangoutDate.text.toString()
        var joinTime = binding.tvHangoutTime.text.toString()
        var joinPlace = binding.tvSelectSpot.text.toString()
        var resion = Common.dong
        var tredCtyNo = binding.tvTredeCategory.text // 비교해서 인덱스 번호
        //var userNo =

    }

}