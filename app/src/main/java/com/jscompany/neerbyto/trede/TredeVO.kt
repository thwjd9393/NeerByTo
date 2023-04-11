package com.jscompany.neerbyto.trede

data class TredeVO(
    var tredeNo : Int,
    var title : String,
    var content : String,
    var oriPrice : Int,
    var price : Int,
    var joinCount : Int,
    var joinDate : String,
    var joinTime : String,
    var joinPlace : String,
    var state : Int,
    var viewCnt : Int,
    var img1 : String,
    var img2 : String,
    var img3 : String,
    var tredCtyNo : Int,
    var tredCtyName : String, //카테고리 네임
    var userNo : Int,
    var likeCnt : Int, //좋아요 수
    var date : String,
    )

