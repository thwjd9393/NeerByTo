package com.jscompany.neerbyto.login

import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap

interface UserService {

    //유저 등록 - php
    @Multipart
    @POST("NeerByTo/userInsert.php")
    fun insertUserPhp(@PartMap dataUser : Map<String, String>) : Call<String>

    //이메일 체크
    @Multipart
    @POST("NeerByTo/userIdCheck.php")
    fun userIdCheck(@PartMap dataUser : Map<String, String>) : Call<String>

    //닉네임 체크
    @Multipart
    @POST("NeerByTo/userNicCheck.php")
    fun userNicCheck(@PartMap dataUser : Map<String, String>) : Call<String>

    //유저 등록 - 파리어베이스

}