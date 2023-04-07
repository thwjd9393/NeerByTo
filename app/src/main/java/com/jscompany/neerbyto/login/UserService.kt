package com.jscompany.neerbyto.login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap
import retrofit2.http.Query

interface UserService {

    //유저 등록 - php
    @Multipart
    @POST("NeerByTo/userInsert.php")
    fun insertUserPhp(@PartMap dataUser : Map<String, String>) : Call<String>

    //아이디 체크
    @FormUrlEncoded
    @POST("NeerByTo/userIdCheck.php")
    fun userIdCheck(@Field("id") id : String) : Call<String>

    //닉네임 체크
    @FormUrlEncoded
    @POST("NeerByTo/userNicCheck.php")
    fun userNicCheck(@Field("nicname") nicname : String) : Call<String>

    //유저 등록 - 파리어베이스
    @FormUrlEncoded
    @POST("NeerByTo/userLogin.php")
    fun userLogin(@Field("id") id : String, @Field("passwd") passwd : String) : Call<String>

    //네이버 아이디 로그인 사용자 정보 API
    @GET("/v1/nid/me")
    fun getNidUserInfo(@Header("Authorization") authorization : String) : Call<NIdUserInfo>
    //Authorization : 토큰값 가져올 애

}