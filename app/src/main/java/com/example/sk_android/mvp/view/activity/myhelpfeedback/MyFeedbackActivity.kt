package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.myhelpfeedback.FeedbackModel
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.FeedbackInformationList
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MyFeedbackActivity : AppCompatActivity() {

    val mainId = 1
    val fId = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        frameLayout {
            id = mainId
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                        onClick {
                            finish()
                        }
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "私のフィードバック"
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColor = Color.parseColor("#FF333333")
                        textSize = 16f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        centerInParent()
                    }
                }.lparams {
                    width = matchParent
                    height = dip(54)
                }
                frameLayout {
                    id = fId
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            getUserFeedback()
        }
    }

    // 获取用户的反馈信息
    private suspend fun getUserFeedback() {
        val list = mutableListOf<FeedbackModel>()
        val retrofitUils = RetrofitUtils(this@MyFeedbackActivity,"https://help.sk.cgland.top/")
        try {
            var body = retrofitUils.create(HelpFeedbackApi::class.java)
                .userFeedback()
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()
            val page = Gson().fromJson(body, PagedList::class.java)
            val feedList = page.data
            for (item in feedList) {
                val item = Gson().fromJson(item, FeedbackModel::class.java)
                list.add(item)
            }
            aaa(list)
        } catch (throwable: Throwable) {
            println("失败！！！！！！！！")
            if (throwable is retrofit2.HttpException) {
                println(throwable.code())
            }
            finish()
        }

    }

    fun aaa(list: MutableList<FeedbackModel>) {
        var feedList = FeedbackInformationList.newInstance(list, this@MyFeedbackActivity)
        supportFragmentManager.beginTransaction().add(fId, feedList).commit()
    }
}