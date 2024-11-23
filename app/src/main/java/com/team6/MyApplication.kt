package com.team6

import android.app.Application
import com.kakao.vectormap.KakaoMapSdk
import com.team6.haedal6.R
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoMapSdk.init(this, getString(R.string.KAKAO_API_KEY))
    }
}
