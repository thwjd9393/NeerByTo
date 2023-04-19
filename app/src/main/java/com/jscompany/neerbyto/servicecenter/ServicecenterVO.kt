package com.jscompany.neerbyto.servicecenter

data class noticeItem(
    var noticeNo : Int,
    var title : String,
    var content : String,
    var date : String,
)

data class FnaItem(
    var faNo : Int,
    var title : String,
    var content : String,
    var date : String,
)