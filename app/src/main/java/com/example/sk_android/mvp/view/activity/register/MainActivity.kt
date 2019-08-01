package com.example.sk_android.mvp.view.activity.register

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.example.sk_android.R
import com.example.sk_android.mvp.api.person.User
import com.example.sk_android.mvp.view.activity.jobselect.RecruitInfoShowActivity
import com.example.sk_android.utils.RetrofitUtils
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.adapter.rxjava2.HttpException

class MainActivity : AppCompatActivity() {
    lateinit var stateSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()
        stateSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    @SuppressLint("CheckResult")
    private fun init() {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val token = sp.getString("token", "")
        var mEditor: SharedPreferences.Editor = stateSharedPreferences.edit()
        mEditor.putInt("condition", 0)
        mEditor.commit()
        if (token == "") {
            val i = Intent(this@MainActivity, LoginActivity::class.java)
            i.putExtra("condition",0)
            startActivity(i)
            finish()
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        } else {

            var requestUserInfo = RetrofitUtils(this, this.getString(R.string.userUrl))

            requestUserInfo.create(User::class.java)
                .getSelfInfo()
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    var intent  = Intent(this@MainActivity,RecruitInfoShowActivity::class.java)
                    startActivity(intent)
                    finish()
                    overridePendingTransition(R.anim.right_in, R.anim.left_out)
                },{
                    if (it is HttpException) {
                        if (it.code() == 404) {
                            val i = Intent(this@MainActivity, ImproveInformationActivity::class.java)
                            startActivity(i)
                            finish()
                            overridePendingTransition(R.anim.right_in, R.anim.left_out)
                        } else {
                            toast("ネットワーク異常")
                        }
                    }
                })















        }
    }
}
