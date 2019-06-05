package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.myhelpfeedback.FeedbackModel
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.FeedbackInformation
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
import retrofit2.converter.gson.GsonConverterFactory

class MyFeedbackContentActivity : AppCompatActivity() {

    val fragId = 2

    override fun onStart() {
        super.onStart()
        if (getIntent().getSerializableExtra("id") != null) {
            val id = getIntent().getSerializableExtra("id")
            GlobalScope.launch {
                getFeedbackById(id.toString())
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        relativeLayout {
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
                    id = fragId
                    var model : FeedbackModel? = null
                    val information = FeedbackInformation.newInstance(model, this@MyFeedbackContentActivity)
                    supportFragmentManager.beginTransaction().add(fragId,information).commit()
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }


    private suspend fun getFeedbackById(id: String) {
        val retrofitUils = RetrofitUtils(this@MyFeedbackContentActivity,"https://help.sk.cgland.top/")
        try {
            val json = retrofitUils.create(HelpFeedbackApi::class.java)
                .getFeedbackById(id)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()
            val model = Gson().fromJson<FeedbackModel>(json, FeedbackModel::class.java)
            updateFrag(model)
        } catch (throwable: Throwable) {
            println(throwable)
            if (throwable is retrofit2.HttpException) {
                println(throwable.code())
            }
            finish()
            println("失败！！！！！！！！！")
        }

    }

    fun updateFrag(model : FeedbackModel){
        val information = FeedbackInformation.newInstance(model, this@MyFeedbackContentActivity)
        supportFragmentManager.beginTransaction().replace(fragId,information).commit()
    }
}