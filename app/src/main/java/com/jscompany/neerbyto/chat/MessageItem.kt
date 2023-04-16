package com.jscompany.neerbyto.chat

import java.sql.Timestamp


data class MessageItem(
    var nic:String , //닉네임
    var message: String,
    var profileUrl : String,
    var time :String,
    //var pushToken :String,
    ) {
    constructor() : this("","","","")
}

data class ChatRoom(
    var tredeNo : String, //tredeNo를 키값으로...
    var users : MutableList<String>, //참여한 유저들
    var otherUserNic : String, //방 만든 사람
    var title : String, //
    var joinCount : String, //
    var joinTime : String, //
    var joinSpot : String, //
    var lastChat:String, //마지막 쳇 시간
    var userLastVisited : MutableMap<String,Timestamp>, // userId를 key값으로 timeStemp를 저장
    //마지막 푸시 시간보다 작으면 안읽은 메세지 있는 것
) {
    constructor(
        tredeNo : String, //tredeNo를 키값으로...
        users : MutableList<String>, //참여한 유저들
        otherUserNic : String, //방 만든 사람
        title : String, //
        joinCount : String, //
        joinTime : String, //
        joinSpot : String, //
    ) : this(tredeNo, users, otherUserNic,title,joinCount,joinTime,joinSpot,"", mutableMapOf())
}

class ChatModel {
    var users: Map<String, Boolean> = HashMap() //채팅방 유저
    var comments: Map<String, Comment> = HashMap() //채팅 메시지

    class Comment(
        var nic:String,
        var message: String,
        var profileUrl : String,
        var time :Timestamp)
}
