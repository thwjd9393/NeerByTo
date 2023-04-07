package com.jscompany.neerbyto

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //kokoo SDK 초기화
        KakaoSdk.init(this, getString(R.string.kakaoNativeAppKey2)) //개발자 사이트에 등록한 네이티브 앱키
    }
}