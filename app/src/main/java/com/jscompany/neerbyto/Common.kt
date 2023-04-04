package com.jscompany.neerbyto

import android.content.Context
import android.widget.Toast
import java.util.Calendar
import java.util.Date
import java.util.regex.Pattern

class Common {

    companion object {

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

        //정규식
//        fun isValidEmail(email: String?): Boolean {
//            var err = false
//            val regex =
//                "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"
//            val p = Pattern.compile(regex)
//            val m = p.matcher(email)
//            if (m.matches()) {
//                err = true
//            }
//            return err
//        }

        fun verifyEmail(email: String) : Boolean {
            val regexEmail = """^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})${'$'}""".toRegex()
            return regexEmail.matches(email)
        }

        fun isValidPasswd(passwd: String?): Boolean {
            var err = false
            val regex = "^[A-Za-z0-9]{8,15}$"
            val p = Pattern.compile(regex)
            val m = p.matcher(passwd)
            if (m.matches()) {
                err = true
            }
            return err
        }

        //토스트 만들기
        fun makeToast(context: Context?, s: String?) {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
        }

    }

}