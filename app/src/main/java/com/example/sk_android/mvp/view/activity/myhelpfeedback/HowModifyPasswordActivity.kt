package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import com.example.sk_android.mvp.view.adapter.myhelpfeedback.SecondHelpInformationAdapter
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.HelpAnswerBody
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.HelpAnswerButton
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.JsonObject
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_chat.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class HowModifyPasswordActivity : AppCompatActivity() {

    var parentId = ""
    val list = mutableListOf<JsonObject>()
    val mainId = 1
    val headId = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        frameLayout {
            id = mainId
            frameLayout {
                id = headId
                println("第三-------------------------" + list)
                var head = HelpAnswerBody.newInstance(list, this@HowModifyPasswordActivity)
                supportFragmentManager.beginTransaction().add(headId, head).commit()
            }.lparams(matchParent, matchParent) {
                bottomMargin = dip(110)
            }
            frameLayout {
                var head = HelpAnswerButton.newInstance(this@HowModifyPasswordActivity)
                supportFragmentManager.beginTransaction().add(mainId, head).commit()
            }.lparams(matchParent, matchParent)
        }
    }

    override fun onStart() {
        super.onStart()
        if (getIntent().getStringExtra("parentId") != null) {
            val id = getIntent().getStringExtra("parentId")
            parentId = id
        }
        getInformation()
    }

    private fun getInformation() {

        //获取全部帮助信息
        var retrofitUils = RetrofitUtils("https://help.sk.cgland.top/")
        retrofitUils.create(HelpFeedbackApi::class.java)
            .getChildrenInformation(parentId)
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                val obj = it.get("data").asJsonArray
                for (item in obj) {
                    val model = item.asJsonObject
                    list.add(model)
                }
                titleBody()
            }, {
                println("失败！！！！！！！！！")
            })
    }

    fun titleBody() {
        var head = HelpAnswerBody.newInstance(list, this@HowModifyPasswordActivity)
        supportFragmentManager.beginTransaction().replace(headId, head).commit()
    }
}