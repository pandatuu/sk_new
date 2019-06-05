package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import com.example.sk_android.R
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.myhelpfeedback.HelpModel
import com.example.sk_android.mvp.view.adapter.myhelpfeedback.SecondHelpInformationAdapter
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class JobSearchStrategyActivity : AppCompatActivity() {

    private lateinit var recycle: RecyclerView
    var parentId  = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

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
                    }.lparams{
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "求職攻略"
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColor = Color.BLACK
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

                relativeLayout {
                    recycle = recyclerView {
                       layoutManager =
                           LinearLayoutManager(this@JobSearchStrategyActivity) as RecyclerView.LayoutManager?
                   }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }.lparams{
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(getIntent().getSerializableExtra("parentId")!=null){
            val id = getIntent().getSerializableExtra("parentId")
            parentId = id.toString()
        }
        getInformation()
    }

    private fun getInformation() {
        val list = mutableListOf<HelpModel>()
        //获取全部帮助信息
        var retrofitUils = RetrofitUtils("https://help.sk.cgland.top/")
        retrofitUils.create(HelpFeedbackApi::class.java)
            .getChildrenInformation(parentId)
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                // Json转对象
                val page = Gson().fromJson(it, PagedList::class.java)
                val obj = page.data
                for (item in obj) {
                    val model = Gson().fromJson(item,HelpModel::class.java)
                    list.add(model)
                }
                recycle.adapter = SecondHelpInformationAdapter(list, this@JobSearchStrategyActivity)
            }, {
                println("失败！！！！！！！！！")
            })
    }
}