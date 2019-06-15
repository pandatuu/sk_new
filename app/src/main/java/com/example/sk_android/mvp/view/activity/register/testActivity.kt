package com.example.sk_android.mvp.view.activity.register

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.adapter.rxjava2.HttpException

class testActivity: AppCompatActivity() {
    var myName = ""
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            backgroundColor = Color.RED
            button {
                backgroundColor = Color.WHITE
                gravity = Gravity.CENTER
                text = "点击"
                onClick {
                    startActivity<PersonInformationFourActivity>("resumeId" to "1fbb4b93-352f-4cf5-8815-85ff70d1a120")
                }
            }.lparams(width = dip(200),height = dip(150)){}
        }
    }


}