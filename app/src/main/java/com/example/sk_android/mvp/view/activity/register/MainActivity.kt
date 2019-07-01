package com.example.sk_android.mvp.view.activity.register

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.jobselect.RecruitInfoShowActivity
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast
import retrofit2.adapter.rxjava2.HttpException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    @SuppressLint("CheckResult")
    private fun init() {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val token = sp.getString("token", "")
        println(sp.toString())
        println(token)
        if (token == "") {
            val i = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        } else {
            var retrofitUils = RetrofitUtils(this,this.getString(R.string.userUrl))

            retrofitUils.create(PersonApi::class.java)
                .information
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    val m = Intent(this@MainActivity, RecruitInfoShowActivity::class.java)
                    startActivity(m)
                    finish()
                },{
                    if (it is HttpException) {
                        if (it.code() in 400..499) {
                            val i = Intent(this@MainActivity, LoginActivity::class.java)
                            startActivity(i)
                            finish()
                        }else{
                            toast("系统正在维护,请稍后重试！！")
                        }
                    }
                })

        }
    }
}
