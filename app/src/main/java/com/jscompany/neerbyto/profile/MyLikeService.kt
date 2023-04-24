package com.jscompany.neerbyto.profile

import com.jscompany.neerbyto.login.UserVO
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface MyLikeService {

    //내 정보 가져오기
    @GET("NeerByTo/loadUserInfo.php")
    fun loadUserInfo(@Query("userNo") userNo : String) : Call<MutableList<UserVO>>

    //내 정보 수정
    @Multipart
    @POST("NeerByTo/updateUserInfo.php")
    fun updateUserInfo(@PartMap dataUser: MutableMap<String, String>, @Part file : MultipartBody.Part?) : Call<MutableList<UserVO>>

    //좋아요한 데이터들 가져오기
    @GET("NeerByTo/loadLikeData.php")
    fun loadLikeData(@Query("userNo") userNo : String) : Call<MutableList<MyLikeItem>>

    @GET("NeerByTo/loadMyTredeData.php")
    fun loadMyTredeData(@Query("userNo") userNo : String, @Query("state") state : String) : Call<MutableList<MyWriteItem>>

    //친구 select
    @GET("NeerByTo/loadMyFriendData.php")
    fun loadMyFriendData(@Query("myUserNo") myUserNo : String,@Query("state") state : String) : Call<MutableList<MyFriendItem>>

    //친구 추가 insert
    @GET("NeerByTo/insertMyFriend.php")
    fun insertMyFriend(@Query("myUserNo") myUserNo : String, @Query("userNo") userNo : String) : Call<String>

    //친구 추가 delete
    @GET("NeerByTo/delMyFriend.php")
    fun delMyFriend(@Query("friendNo") friendNo : Int) : Call<String>

    //친구 추가 되어 있는지 확인
    @GET("NeerByTo/loadMyFriendCheck.php")
    fun loadMyFriendCheck(@Query("myUserNo") myUserNo : String, @Query("userNo") userNo : String) : Call<String>

    //신고 카테고리 가져오기
    @GET("NeerByTo/loadReportCategory.php")
    fun loadReportCategory() : Call<MutableList<ReportCtyItem>>

    //카테고리 저장
    @GET("NeerByTo/insertReportCategory.php")
    fun insertReportCategory(@Query("content") content : String,
                             @Query("userNo") userNo : String,
                             @Query("reportUserNo") reportUserNo : String,
                             @Query("reportCtyNo") reportCtyNo : String) : Call<String>

    //칭찬 카테고리 가져오기
    @GET("NeerByTo/loadGMannerCategory.php")
    fun loadGMannerCategory() : Call<MutableList<GoodCtyItem>>

    //칭찬 저장
    @GET("NeerByTo/insertGManner.php")
    fun insertGManner(@Query("userNo") userNo:String, @Query("gCtyNo") gCtyNo:String, ): Call<String>

    //비매너 카테고리 가져오기
    @GET("NeerByTo/loadBMannerCategory.php")
    fun loadBMannerCategory() : Call<MutableList<BadCtyItem>>

    //비매너 저장
    @GET("NeerByTo/insertBManner.php")
    fun insertBManner(@Query("userNo") userNo:String, @Query("bCtyNo") bCtyNo:String, ): Call<String>
    
    //매너 비매너 가장 많이 받은 순서대로 정렬
    @GET("NeerByTo/loadGManner.php")
    fun loadGManner(@Query("userNo") userNo:String) : Call<MutableList<GoodItem>> //content,cnt

    @GET("NeerByTo/loadBManner.php")
    fun loadBManner(@Query("userNo") userNo:String) : Call<MutableList<BadItem>> //content,cnt

}
