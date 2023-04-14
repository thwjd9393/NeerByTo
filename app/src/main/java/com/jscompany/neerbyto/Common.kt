package com.jscompany.neerbyto

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.location.Location
import android.net.Uri
import android.widget.Toast
import io.grpc.internal.JsonUtil
import java.util.Calendar
import java.util.Date


class Common {

    companion object {

        // 닷홈 베이스 Url
        val dotHomeUrl : String = "http://mrhisj23.dothome.co.kr/"

        //네이버 베이스주소
        val naverBaseUrl = "https://openapi.naver.com"
        
        //카카오 베이스주소
        val kakaoBaseUrl = "https://dapi.kakao.com"


        // 가입경로
        val joinApp : String = "1"
        val joinKakao : String = "2"
        val joinNaver : String = "3"
        val joinGoogel : String = "4"


        //사용자 정보
        //lateinit var userNic : String;
        fun getId(context : Context) : String{
            val pref : SharedPreferences = context.getSharedPreferences("Data", MODE_PRIVATE)
            return pref.getString("userId", " - ") ?: ""
        }

        fun getNic(context : Context) : String{
            val pref : SharedPreferences = context.getSharedPreferences("Data", MODE_PRIVATE)
            return pref.getString("userNic", " - ") ?: ""
        }

        fun getUserNo(context : Context) : String{
            val pref : SharedPreferences = context.getSharedPreferences("Data", MODE_PRIVATE)
            return pref.getString("userNo", " - ") ?: ""
        }

        fun getUserlatitude(context : Context) : String{
            val pref : SharedPreferences = context.getSharedPreferences("Data", MODE_PRIVATE)
            return pref.getString("latitude", " - ") ?: ""
        }

        fun getUserlongitude(context : Context) : String{
            val pref : SharedPreferences = context.getSharedPreferences("Data", MODE_PRIVATE)
            return pref.getString("longitude", " - ") ?: ""
        }

        // 현재 내 위치 정보 객체
        var latitude : String? = null //경도
        var longitude : String? = null //위도
        var dong : String? = null //동
        var PROFILEURL : String = "https://firebasestorage.googleapis.com/v0/b/ex90firechattingbsj23.appspot.com/o/profileImg%2FIMG_20230317042549?alt=media&token=27b79bd3-fc44-4bdb-b5e6-c620dcedee45" // 파이어베어스 이미지 유알엘
        var PROFILEURI : Uri? = null // 파이어베어스 이미지 유알엘



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