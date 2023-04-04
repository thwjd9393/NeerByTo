package com.jscompany.neerbyto.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.databinding.ActivitySingupBinding

class SingupActivity : AppCompatActivity() {

    private val binding: ActivitySingupBinding by lazy { ActivitySingupBinding.inflate(layoutInflater) }

    private val id by lazy{ binding.inputId }
    private val passwd by lazy{ binding.inputPasswd }
    private val passwdConf by lazy{ binding.inputPasswdCheck }
    private val nicName by lazy{ binding.inputNicname }

    //
    var boolEmail : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

        //회원가입 클릭
        binding.btnSignup.setOnClickListener {clickJoin() }

    }

    private fun clickJoin() {
        //회원가입 버튼 클릭
        if ((id.text.toString() != "" && passwd.text.toString() != ""
                && passwdConf.text.toString() != "" && nicName.text.toString() != "") && boolEmail
        ) {
            Toast.makeText(this, "화면이동", Toast.LENGTH_SHORT).show()
            //db저장

            //startActivity(Intent(this, LocationActivity::class.java))
        } else {
            Common.makeToast(this, "입력하지 않은 항목이 있습니다")
            return
        }
    }

    private fun init() {
        //액션바 셋팅
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.sign_up)

        //리스너 달기
        focuce(id)
        focuce(passwd)
        focuce(passwdConf)
        focuce(nicName)
    }


    private fun focuce(et : EditText) {
        et.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                
                if(et == id) {
                    //이메일 체크
                    boolEmail = Common.verifyEmail(id.text.toString())
                    if(!boolEmail) {
                        binding.tvConfIdOk.visibility = View.INVISIBLE
                        binding.tvConfId.visibility = View.VISIBLE
                    } else {
                        binding.tvConfIdOk.visibility = View.VISIBLE
                        binding.tvConfId.visibility = View.INVISIBLE
                        boolEmail = true
                    }
                } else if(et == passwdConf || et==passwd) {
                    //비밀번호 체크
                    if (passwdConf.text.toString() == passwd.text.toString()) {
                        binding.tvConfPasswdOk.visibility = View.VISIBLE
                        binding.tvConfPasswd.visibility = View.INVISIBLE
                    } else {
                        binding.tvConfPasswdOk.visibility = View.INVISIBLE
                        binding.tvConfPasswd.visibility = View.VISIBLE
                    }
                }
//                else if (et==nicName) {
//                    //닉네임 체크
//                }

                binding.btnSignup.isEnabled = true
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
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