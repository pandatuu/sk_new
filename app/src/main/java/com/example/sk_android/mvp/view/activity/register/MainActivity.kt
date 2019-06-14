package com.example.sk_android.mvp.view.activity.register

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.adapter.rxjava2.HttpException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    @SuppressLint("CheckResult")
    private fun init() {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val token = sp.getString("token", null)
        println(sp.toString())
        println(token)
        if (token == null) {
            val i = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        } else {
            var retrofitUils = RetrofitUtils(this,"https://auth.sk.cgland.top/")
            retrofitUils.create(RegisterApi::class.java)
                .verifyUser()
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    println("进入首页")
                    finish()
                },{
                    if(it is HttpException){
                        if(it.code() == 401){
                            val i = Intent(this@MainActivity, ImproveInformationActivity::class.java)
                            startActivity(i)
                            finish()
                        }
                    }
                })
        }

    }
}
