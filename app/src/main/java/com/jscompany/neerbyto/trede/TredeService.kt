package com.jscompany.neerbyto.trede

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface TredeService {

    //카테고리 가져오기
    @GET("NeerByTo/tredeCategoty.php")
    fun loadCategoty() : Call<MutableList<TredeCategotyVO>>

    //데이터 저장
//    @POST("NeerByTo/insertTredeData.php")
//    fun insertTredeData() : Call<>

}