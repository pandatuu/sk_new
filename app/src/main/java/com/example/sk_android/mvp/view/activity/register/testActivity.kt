package com.example.sk_android.mvp.view.activity.register

import android.app.Person
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.mvp.view.activity.jobselect.JobWantedManageActivity
import com.example.sk_android.mvp.view.activity.person.*
import com.example.sk_android.mvp.view.activity.resume.ResumeListActivity
import okhttp3.MediaType
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class testActivity: AppCompatActivity() {
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            backgroundColor = Color.BLUE
            gravity = Gravity.CENTER
            button {
                backgroundColor = Color.WHITE
                gravity = Gravity.CENTER
                text = "点击"
                 this.withTrigger().click {
                    startActivity<MainActivity>()
                }
            }.lparams(width = dip(200),height = dip(150)){}
        }
    }


}