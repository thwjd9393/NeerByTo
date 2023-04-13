package com.jscompany.neerbyto.trede

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap


interface TredeService {

    //카테고리 가져오기
    @GET("NeerByTo/tredeCategoty.php")
    fun loadCategoty() : Call<MutableList<TredeCategotyVO>>

    //데이터 저장
    @Multipart
    @POST("NeerByTo/insertTredeData.php")
    //fun insertTredeData(@PartMap dataPart: MutableMap<String, String>) : Call<String>
    fun insertTredeData(@PartMap dataPart: MutableMap<String, String>,@Part imageList : MutableList<MultipartBody.Part?>) : Call<String>

    //데이터 불러오기
    @FormUrlEncoded
    @POST("NeerByTo/loadTredeData.php")
    fun loadTredeData(@Field("tredCtyNo") tredCtyNo : String) : Call<MutableList<TredeVO>>

}