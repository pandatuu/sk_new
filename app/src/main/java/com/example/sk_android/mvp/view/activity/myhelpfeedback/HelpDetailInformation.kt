package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.sk_android.mvp.model.myhelpfeedback.HelpModel
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.HelpAnswerBody
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.HelpAnswerButton
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.dip
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent

class HelpDetailInformation : AppCompatActivity() {

    var id = ""
    val list = mutableListOf<HelpModel>()
    val mainId = 1
    val headId = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        frameLayout {
            id = mainId
            frameLayout {
                id = headId
                val head = HelpAnswerBody.newInstance(list, this@HelpDetailInformation)
                supportFragmentManager.beginTransaction().add(headId, head).commit()
            }.lparams(matchParent, matchParent) {
                bottomMargin = dip(110)
            }
            frameLayout {
                val head = HelpAnswerButton.newInstance(this@HelpDetailInformation)
                supportFragmentManager.beginTransaction().add(mainId, head).commit()
            }.lparams(matchParent, matchParent)
        }
    }

    override fun onStart() {
        super.onStart()
        if (intent.getStringExtra("id") != null) {
            id = intent.getStringExtra("id")
            GlobalScope.launch {
                getInformation(id)
            }
        }
    }

    private suspend fun getInformation(id: String) {

        //获取全部帮助信息
        val retrofitUils = RetrofitUtils(this@HelpDetailInformation, "https://help.sk.cgland.top/")

        try {
            val body = retrofitUils.create(HelpFeedbackApi::class.java)
                .getHelpInformation(id)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()

            if (body.code() in 200..299) {
                // Json转对象

                val model = Gson().fromJson(body.body(), HelpModel::class.java)
                list.add(model)

                titleBody()
            }
        } catch (throwable: Throwable) {
            println("失败！！！！！！！！！")
        }
    }

    private fun titleBody() {
        val head = HelpAnswerBody.newInstance(list, this@HelpDetailInformation)
        supportFragmentManager.beginTransaction().replace(headId, head).commit()
    }
}