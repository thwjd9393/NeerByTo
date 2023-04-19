package com.jscompany.neerbyto.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.chat.MainChatFragment
import com.jscompany.neerbyto.databinding.ActivityMainBinding
import com.jscompany.neerbyto.login.UserService
import com.jscompany.neerbyto.profile.MainMyZoneFragment
import com.jscompany.neerbyto.trede.MainTredeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    //바텀네비
    private val bottomnavi:BottomNavigationView by lazy { binding.bnav }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //맨 처음 보여줄 플래그먼트 데려오기
        //supportFragmentManager.beginTransaction().add(R.id.fragment_warp, MainTredeFragment()).commit()
        changeFragment(R.id.bnv_home)

        //바텀 네비게이션에 연동
        bottomnavi.setOnItemSelectedListener {
            changeFragment(it.itemId)
            true
        }

        getUserNum()// 유저번호

        val deleteCaht = intent.getBooleanExtra("deleteCaht",false)

        if(deleteCaht == true){
            Log.i("TAG","deleteCaht : ${deleteCaht}")

            bottomnavi.selectedItemId = R.id.bnv_chat //네이게이션 이동
        }

    }

    //플래그먼트 변경 함수
    private fun changeFragment(menuItemId:Int){
        val targetFragment = getFragment(menuItemId)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_warp, targetFragment).commit()
    }

    // 플래그먼트 화면 반환 함수
    private fun getFragment(menuItemId: Int) : Fragment {
        val fragment = when (menuItemId) {
            R.id.bnv_home -> MainTredeFragment()
            R.id.bnv_chat -> MainChatFragment()
            R.id.bnv_myzone -> MainMyZoneFragment()
            else -> throw IllegalArgumentException("not found menu item id")
        }
        return fragment
    }

    //유저 번호 얻어오는 메소드
    private fun getUserNum(){
        val retrofit = RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl)
        retrofit.create(UserService::class.java).getUserNo(Common.getId(this))
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {

                    var userNo = response.body()

                    //쉐어드에 저장
                    val pref = getSharedPreferences("Data", MODE_PRIVATE)
                    val editor = pref.edit()

                    editor.putString("userNo",userNo);

                    editor.commit()

                    Log.i("TAG","userNo =  $userNo")

                }

                override fun onFailure(call: Call<String>, t: Throwable) = Common.makeToast(this@MainActivity, getString(R.string.response_server_error))
            })
    }
}