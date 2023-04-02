package com.jscompany.neerbyto.trede

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivityTredeWriteBinding
import java.text.DateFormat
import java.util.Calendar

class TredeWriteActivity : AppCompatActivity() {

    private val binding : ActivityTredeWriteBinding by lazy { ActivityTredeWriteBinding.inflate(layoutInflater) }

    private val cal : Calendar by lazy { Calendar.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

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

    private fun clickSelectImg() {
        TODO("Not yet implemented")
    }

    private fun clickSpot() {
        TODO("Not yet implemented")
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

    private fun clickCategory() {
        TODO("Not yet implemented")
    }

    //옵션 메뉴 만드는 콜백
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_trede_write, menu)

        return super.onCreateOptionsMenu(menu)
    }
    
}