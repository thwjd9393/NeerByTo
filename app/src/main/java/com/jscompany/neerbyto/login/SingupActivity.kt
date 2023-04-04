package com.jscompany.neerbyto.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivitySingupBinding

class SingupActivity : AppCompatActivity() {

    private val binding: ActivitySingupBinding by lazy { ActivitySingupBinding.inflate(layoutInflater) }

    private val id = binding.inputId.text.toString()
    private val passwd = binding.inputPasswd.text.toString()
    private val passwdConf = binding.inputPasswdCheck.text.toString()
    private val nicName = binding.inputNicname.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

        edtextCheck()

        //회원가입 클릭
        binding.btnSignup.setOnClickListener {clickJoin() }

        binding.etId
    }

    private fun clickJoin() {
        //회원가입 버튼 클릭
        if (binding.inputId.text.toString() != "" && binding.inputPasswd.text.toString() != "" && binding.inputNicname.text.toString() != "") {
            Toast.makeText(this, "화면이동", Toast.LENGTH_SHORT).show()
            //db저장

            //startActivity(Intent(this, LocationActivity::class.java))
        } else {
            Toast.makeText(this, "입력 하지 않은 항목이 있습니다", Toast.LENGTH_SHORT).show()
            return
        }
    }

    private fun init() {
        //액션바 셋팅
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.sign_up)
    }


    private fun edtextCheck() {



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