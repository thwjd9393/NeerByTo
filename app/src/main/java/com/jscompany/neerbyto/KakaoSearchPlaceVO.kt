package com.jscompany.neerbyto

data class KakaoSearchPlaceVO(var meta:PlaeMeta , var documents : MutableList<Documents>)

data class PlaeMeta(var total_count : Int, var pageable_count : Int, var is_end : Boolean)

data class Documents(
    var address_name : String,
    var y : String,
    var x : String,
    var address_type : String,
    var address : Address,
    var road_address : RoadAddress,
)

data class Address(
    var address_name : String,
    var region_1depth_name : String,
    var region_2depth_name : String,
    var region_3depth_name : String,
    var region_3depth_h_name : String,
    var h_code : String, //부송동
    var b_code : String, //부송동
    var mountain_yn : String,
    var main_address_no : String,
    var sub_address_no : String,
    var x : String, // 경도(longitude)
    var y : String,
)

data class RoadAddress(
    var address_name : String,
    var region_1depth_name : String,
    var region_2depth_name : String,
    var region_3depth_name : String, //부송동
    var road_name : String,
    var underground_yn : String,
    var main_building_no : String,
    var sub_building_no : String,
    var building_name : String,
    var zone_no : String,
    var x : String, // 경도(longitude)
    var y : String, // 위도(latitude)
)
