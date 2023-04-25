package com.jscompany.neerbyto.login

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jscompany.neerbyto.Common
import com.jscompany.neerbyto.R
import com.jscompany.neerbyto.RetrofitBaseUrl
import com.jscompany.neerbyto.databinding.ActivityFindUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FindUserActivity : AppCompatActivity() {

    val binding:ActivityFindUserBinding by lazy { ActivityFindUserBinding.inflate(layoutInflater) }

    var userId : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //앱바 설정
        setSupportActionBar(findViewById(R.id.toolbar)) //include 한 레이아웃은 바인딩 안됨
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //왼쪽 뒤로가기 버튼
        supportActionBar!!.setTitle(R.string.find_user) // 타이틀 재설정


        binding.btnFindPasswd.setOnClickListener { clickBtn() }
    }

    //아이디 체크
    fun checkId() {
        RetrofitBaseUrl.getRetrofitInstance(Common.dotHomeUrl).create(UserService::class.java)
            .userIdCheck(userId).enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val result : String = response.body() ?: ""

                    if(result == "0") {
                        binding.etId.error = "가입하지 않은 이메일 입니다"
                    } else {
                        binding.btnFindPasswd.isClickable = true
                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Common.makeToast(this@FindUserActivity, getString(R.string.response_server_error))
                }
            })
    }


    //이메일 보내기
    fun clickBtn(){

        AlertDialog.Builder(this).setMessage("임시 비밀번호 보내기")
            .setPositiveButton("확인") { p0, p1 ->
                userId = binding.inputId.text.toString()
                // 임시 비밀번호 담기
                val imsiPassWd :String = createEmailCode()



            }
            .setNegativeButton("취소") { p0, p1 ->
                p0.dismiss()
            }.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> { //뒤로가기 버튼 활성화
                finish()
                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun createEmailCode(): String { //이메일 인증코드 생성
        val str = arrayOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        var newCode = String()
        for (x in 0..7) {
            val random = (Math.random() * str.size).toInt()
            newCode += str[random]
        }
        return newCode
    }

//    class emailThread(var insiPassWd:String, var userId: String,
//        var title: String ) : Thread() {
//
//        override fun run() {
//            super.run()
//
//            // 보내는 메일 주소와 비밀번호
//            val username = "dev.sj@gmail.com"
//            val password = "thgml332!!"
//
//            val props = Properties();
//            props["mail.smtp.auth"] = "true";
//            props["mail.smtp.starttls.enable"] = "true";
//            props["mail.smtp.host"] = "smtp.gmail.com";
//            props["mail.smtp.port"] = "587";
//
//            // 비밀번호 인증으로 세션 생성
//            val session = Session.getInstance(props,
//                object: javax.mail.Authenticator() {
//                    override  fun getPasswordAuthentication(): PasswordAuthentication {
//                        return PasswordAuthentication(username, password);
//                    }
//                })
//
//            // 메시지 객체 만들기
//            val message = MimeMessage(session)
//            message.setFrom(InternetAddress(username))
//            // 수신자 설정, 여러명으로도 가능
//            message.setRecipients(Message.RecipientType.TO,
//                InternetAddress.parse(userId))
//            message.subject = title
//            message.setText(insiPassWd)
//
//
//            // 전송
//            Transport.send(message)
//
//        }
//
//    }

}



