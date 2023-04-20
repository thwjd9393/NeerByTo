package com.jscompany.neerbyto.setting

import com.jscompany.neerbyto.servicecenter.FnaItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SettingService {

    @GET("NeerByTo/loadTermsData.php")
    fun  loadFnaDetail(@Query("termsNo") termsNo : Int) : Call<MutableList<TermsVO>>

    @GET("NeerByTo/loadVersionInfo.php")
    fun  loadVersionData() : Call<String>

}
