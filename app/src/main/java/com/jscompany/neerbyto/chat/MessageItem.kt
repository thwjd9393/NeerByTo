package com.jscompany.neerbyto.chat

import java.sql.Timestamp


data class MessageItem(
    var nic:String , //닉네임
    var message: String,
    var profileUrl : String,
    var time :Timestamp,
    //var pushToken :String,
    )

data class chatRoom(
    val tredeNo : String, //tredeNo를 키값으로...
    val users : MutableList<String>, //참여한 유저들
    val lastChat:Timestamp, //마지막 쳇 시간
    val userLastVisited : Map<String,Timestamp>, // userId를 key값으로 timeStemp를 저장
    //마지막 푸시 시간보다 작으면 안읽은 메세지 있는 것
)

class ChatModel {
    var users: Map<String, Boolean> = HashMap() //채팅방 유저
    var comments: Map<String, Comment> = HashMap() //채팅 메시지

    class Comment {
        var uid: String? = null
        var message: String = ""
        var timestamp: String = ""
    }
}
