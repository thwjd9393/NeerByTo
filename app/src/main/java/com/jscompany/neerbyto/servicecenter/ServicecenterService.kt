package com.jscompany.neerbyto.servicecenter

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServicecenterService {

    //자주묻는 질문 로드
    @GET("NeerByTo/loadFnaData.php")
    fun  loadFnaData() : Call<MutableList<FnaItem>>

    @GET("NeerByTo/loadFnaDetail.php")
    fun  loadFnaDetail(@Query("faNo") faNo : Int) : Call<MutableList<FnaItem>>

    //공지사항 로든
    @GET("NeerByTo/loadNoticeData.php")
    fun loadNoticeData() : Call<MutableList<noticeItem>>

    @GET("NeerByTo/loadNoticeDetail.php")
    fun loadNoticeDetail(@Query("noticeNo") noticeNo : Int) : Call<MutableList<noticeItem>>

}