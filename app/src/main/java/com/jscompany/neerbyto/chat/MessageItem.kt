package com.jscompany.neerbyto.chat

import java.sql.Timestamp


data class MessageItem(
    var nic:String , //닉네임
    var message: String,
    var profileUrl : String,
    var time :String,
    //var pushToken :String,
    ) {
    //constructor() : this("","","","")
}

data class ChatRoom(
    var tredeNo : String, //tredeNo를 키값으로...
    var users : MutableList<String>, //참여한 유저들
    var writeUserNic : String, //방 만든 사람
    var writeUserNo : String, //방 만든 사람
    var writeImg: String, //방 만든 사람
    var title : String, //
    var joinCount : String, //
    var joinTime : String, //
    var joinSpot : String, //
    var lastChat:String = "", //마지막 쳇
    var lastChatTime:String = "", //마지막 쳇 시간
    var userLastVisited : MutableMap<String,Timestamp> = mutableMapOf(), // userId를 key값으로 timeStemp를 저장
    //마지막 푸시 시간보다 작으면 안읽은 메세지 있는 것
)

