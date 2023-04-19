package com.jscompany.neerbyto.profile

import com.jscompany.neerbyto.login.UserVO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyLikeService {

    //내 정보 가져오기
    @GET("NeerByTo/loadUserInfo.php")
    fun loadUserInfo(@Query("userNo") userNo : String) : Call<MutableList<UserVO>>

    //내 정보

    @GET("NeerByTo/loadLikeData.php")
    fun loadLikeData(@Query("userNo") userNo : String) : Call<MutableList<MyLikeItem>>

    @GET("NeerByTo/loadMyTredeData.php")
    fun loadMyTredeData(@Query("userNo") userNo : String, @Query("state") state : String) : Call<MutableList<MyWriteItem>>

    //친구추가 select
    @GET("NeerByTo/loadMyFriendData.php")
    fun loadMyFriendData(@Query("myUserNo") myUserNo : String,@Query("state") state : String) : Call<MutableList<MyFriendItem>>

    //친구 추가 insert
    @GET("NeerByTo/insertMyFriend.php")
    fun insertMyFriend(@Query("myUserNo") myUserNo : String, @Query("userNo") userNo : String) : Call<String>

    //친구 추가 delete
    @GET("NeerByTo/delMyFriend.php")
    fun delMyFriend(@Query("friendNo") friendNo : Int) : Call<String>

}