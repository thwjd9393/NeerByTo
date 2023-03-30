package com.jscompany.neerbyto.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivitySingupBinding

class SingupActivity : AppCompatActivity() {

    val binding: ActivitySingupBinding by lazy { ActivitySingupBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //액션바 셋팅
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowTitleEnabled(false) //타이틀
        supportActionBar!!.setTitle(R.string.sign_up)

//        binding.tvConfId.setText(R.string.id_ok)
//        binding.tvConfId.setTextColor(R.color.purple_700)

        //회원가입 클릭
        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, LocationActivity::class.java))
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}