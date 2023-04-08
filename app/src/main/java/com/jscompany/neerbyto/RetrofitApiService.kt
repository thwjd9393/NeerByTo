package com.jscompany.neerbyto

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApiService {
    
    //카카오의 키워드 검색 API에 주소 찾기
    @Headers("Authorization: KakaoAK ")
    @GET("/v2/local/search/address")
    fun searchPlace(@Query("query")query:String, @Query("page")page:String):Call<KakaoSearchPlaceVO>
    // @Query = 서버에서 오는 식별자
}