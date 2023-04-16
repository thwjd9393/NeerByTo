package com.jscompany.neerbyto

// 주소 검색
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

//좌표로 행정구역정보
data class KakaoSearchMyRegion(var meta: CoordinateMeta, var documents : MutableList<CoordinateDocuments>)

data class CoordinateMeta(var total_count: Int)

data class CoordinateDocuments(
    var region_type : String,
    var address_name : String,
    var region_1depth_name : String,
    var region_2depth_name : String,
    var region_3depth_name : String, // 동 단위
    var region_4depth_name : String, //법정동이며, 리 영역인 경우만 존재
    var code : String,
    var x : String, //경도(longitude)
    var y : String, // 위도(latitude)
)

//키워드로 찾기
data class KakaoSerchPlaceResponce(var meta : PlaceMeta, var documents : MutableList<PlaceDocumentsItem>)

data class PlaceMeta(var total_count : Int, var pageable_count : Int, var is_end : Boolean)

data class PlaceDocumentsItem(
    var id : String,
    var place_name : String,
    var phone : String,
    var address_name : String,
    var road_address_name : String,
    var x : String, //longitude (경도)
    var y : String, // latitude(위도)
    var place_url : String,
    var distance : String, //중심 좌표까지의 거리 단, 요청파라미터로 x,y 줬을 때만 나옴 단위는 m
)