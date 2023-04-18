package com.jscompany.neerbyto.profile

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyLikeService {

    @GET("NeerByTo/loadLikeData.php")
    fun loadLikeData(@Query("userNo") userNo : String) : Call<MutableList<MyLikeItem>>

    @GET("NeerByTo/loadMyTredeData.php")
    fun loadMyTredeData(@Query("userNo") userNo : String, @Query("state") state : String) : Call<MutableList<MyWriteItem>>

}