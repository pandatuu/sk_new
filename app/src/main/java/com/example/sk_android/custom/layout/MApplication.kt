package com.example.sk_android.custom.layout

import android.app.Application

class MApplication : Application() {
    private var mApplication: MApplication? = null

    override fun onCreate() {
        super.onCreate()
        mApplication = this
    }

    fun getContext(): MApplication? {
        return mApplication
    }
}