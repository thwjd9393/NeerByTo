package com.jscompany.neerbyto.login

data class NIdUserInfo(var resultcode : String, var message : String, var response : NIdUser)

data class NIdUser(var id:String, var email:String)
