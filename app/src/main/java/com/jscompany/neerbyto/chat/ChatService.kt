package com.jscompany.neerbyto.chat

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatService {

    @GET("NeerByTo/updateTredeState.php")
    fun updateTredeState(@Query("tredeNo") tredeNo : String) : Call<String>

}
