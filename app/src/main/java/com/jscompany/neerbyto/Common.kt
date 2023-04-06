package com.jscompany.neerbyto

import android.content.Context
import android.widget.Toast
import java.util.Calendar
import java.util.Date
import java.util.regex.Pattern

class Common {

    companion object {

        // 닷홈 베이스 Url
        val dotHomeUrl : String = "http://mrhisj23.dothome.co.kr/"

        // 가입경로
        val joinApp : String = "1"
        val joinKakao : String = "2"
        val joinNaver : String = "3"


        //사용자 정보
        //lateinit var userNic : String;


        //달 계산
        fun addMonth(date: Date?, months: Int): Date? {
            val cal = Calendar.getInstance()
            cal.time = date
            cal.add(Calendar.MONTH, months)
            return cal.time
        }

        fun dateFormat(date: String): String? {
            val y = date.substring(0, 4)
            val m = date.substring(4, 6)
            val d = date.substring(6)
            return "$y-$m-$d"
        }


        fun verifyEmail(email: String) : Boolean {
            val regexEmail = """^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})${'$'}""".toRegex()
            return regexEmail.matches(email)
        }

        fun isValidPasswd(passwd: String): Boolean {
            val regex = "^[A-Za-z0-9]{8,15}$".toRegex()
            return regex.matches(passwd)
        }

        //토스트 만들기
        fun makeToast(context: Context?, s: String?) {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
        }

    }

}