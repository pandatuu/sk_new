package com.example.sk_android.mvp.tool

import android.app.Application
import com.yatoooon.screenadaptation.ScreenAdapterTools

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ScreenAdapterTools.init(this)
    }
}