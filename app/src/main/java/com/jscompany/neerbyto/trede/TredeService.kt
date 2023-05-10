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
import retrofit2.http.Query


interface TredeService {

    //카테고리 가져오기
    @GET("NeerByTo/tredeCategoty.php")
    fun loadCategoty() : Call<MutableList<TredeCategotyVO>>

    //데이터 저장
    @Multipart
    @POST("NeerByTo/insertTredeData.php")
    //fun insertTredeData(@PartMap dataPart: MutableMap<String, String>) : Call<String>
    fun insertTredeData(@PartMap dataPart: MutableMap<String, String>,@Part imageList : MutableList<MultipartBody.Part?>) : Call<String>

    //전체 데이터 불러오기
    @FormUrlEncoded
    @POST("NeerByTo/loadTredeData.php")
    fun loadTredeData(@Field("tredCtyNo") tredCtyNo : String, @Field("resion") resion : String) : Call<MutableList<TredeVO>>

    //디테일 화면 데이터 불러오기
    @FormUrlEncoded
    @POST("NeerByTo/loadTredeDetail.php")
    fun loadTredeDetail(@Field("tredeNo") tredeNo : String) : Call<MutableList<TredeDetail>>

    //글 삭제
    @GET("NeerByTo/deleteTrede.php")
    fun deleteTrede(@Query("tredeNo") tredeNo : String) : Call<String>

    //좋아요 - insert
    @GET("NeerByTo/insertLikeTrede.php")
    fun insertLikeTrede(@Query("userNo") userNo : String, @Query("tredeNo") tredeNo : String, @Query("tredCtyNo") tredCtyNo : String) : Call<String>

    //좋아요 - delete
    @GET("NeerByTo/deleteLikeTrede.php")
    fun deleteLikeTrede(@Query("userNo") userNo : String, @Query("tredeNo") tredeNo : String) : Call<String>

    //좋아요 - select
    @GET("NeerByTo/selectLikeTrede.php")
    fun selectLikeTrede(@Query("userNo") userNo : String, @Query("tredeNo") tredeNo : String) : Call<String>

}