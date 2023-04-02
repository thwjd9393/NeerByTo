package com.jscompany.neerbyto.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivitySettingBinding
import com.jscompany.neerbyto.profile.MyWriteEndFragment

class SettingActivity : AppCompatActivity() {

    private val binding:ActivitySettingBinding by lazy { ActivitySettingBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //맨 처음 보여줄 플래그먼트 데려오기
        supportFragmentManager.beginTransaction().add(R.id.fragment_setting, SettingMainFragment()).commit()

    }

}