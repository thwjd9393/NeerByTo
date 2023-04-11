package com.jscompany.neerbyto

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApiService {
    
    //카카오의 키워드 검색 API에 주소 찾기
    @GET("/v2/local/search/address")
    fun searchPlace(@Header("Authorization")header:String, @Query("query")query:String, @Query("page")page:String):Call<KakaoSearchPlaceVO>
    // @Query = 서버에서 오는 식별자

    //좌표로 행정구역정보
    @GET("/v2/local/geo/coord2regioncode")
    fun searchAdress(@Header("Authorization")header : String, @Query("x")longitude:String, @Query("y")latitude:String) : Call<KakaoSearchMyRegion>

    //키워드로 찾기 - X : 경도(longitude) Y : 위도(latitude)
    @GET("/v2/local/search/keyword")
    fun keywordPlace(@Header("Authorization")header:String,
                     @Query("query")query:String,
                     @Query("x")longitude:String,
                     @Query("y")latitude:String,
                     @Query("page")page:String):Call<KakaoSerchPlaceResponce>

}