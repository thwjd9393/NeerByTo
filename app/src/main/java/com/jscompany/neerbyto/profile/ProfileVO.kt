package com.jscompany.neerbyto.profile

data class ProfileVO(
    val aaa : String
)

data class MyLikeItem(
    var likeNo : Int,
    var userNo : Int,
    var tredeNo : Int,
    var tredCtyNo : Int,
    var title : String,
    var content : String,
    var oriPrice : Int,
    var price : Int,
    var joinCount : Int,
    var joinDate : String,
    var joinTime : String,
    var joinPlace : String,
    var resion : String,
    var state : Int,
    var viewCnt : Int,
    var img1 : String,
    var tredCtyName : String, //카테고리 네임
    var likeCnt : Int, //좋아요 수
    var date : String,
)

//내가 쓴 글 체크
data class MyWriteItem(
    var tredeNo : Int,
    var title : String,
    var oriPrice : Int,
    var price : Int,
    var joinCount : Int,
    var joinDate : String,
    var joinTime : String,
    var joinPlace : String,
    var state : Int,
    var viewCnt : Int,
    var img1 : String,
    var tredCtyName : String, //카테고리 네임
    var tredCtyNo : Int, //카테고리 네임
    var likeCnt : Int,
    var userNo : Int,
    var date : String,
)