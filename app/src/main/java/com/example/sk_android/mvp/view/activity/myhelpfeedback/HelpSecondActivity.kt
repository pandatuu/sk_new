package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import com.example.sk_android.R
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.myhelpfeedback.HelpModel
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.LevelSecondHelpFrag
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

class HelpSecondActivity : AppCompatActivity() {

    private lateinit var recycle: RecyclerView
    var parentId = ""
    val fragId = 2

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
                    }.lparams {
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
                    id = fragId
                    val second = LevelSecondHelpFrag.newInstance(this@HelpSecondActivity,null)
                    supportFragmentManager.beginTransaction().add(fragId,second).commit()
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
        if (getIntent().getSerializableExtra("parentId") != null) {
            val id = getIntent().getSerializableExtra("parentId")
            parentId = id.toString()
            GlobalScope.launch {
                getInformation(parentId)
            }
        }
    }

    private suspend fun getInformation(id: String) {
        val list = mutableListOf<HelpModel>()
        //获取全部子帮助信息
        var retrofitUils = RetrofitUtils(this@HelpSecondActivity,"https://help.sk.cgland.top/")

        try {
            val body = retrofitUils.create(HelpFeedbackApi::class.java)
                .getChildrenInformation(id)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()

            if(body.code() in 200..299){
                // Json转对象
                val page = Gson().fromJson(body.body(), PagedList::class.java)
                val obj = page.data
                for (item in obj) {
                    val model = Gson().fromJson(item, HelpModel::class.java)
                    list.add(model)
                }
                secondFrag(list)
            }
        } catch (throwable: Throwable) {
            println("失败！！！！！！！！！")
        }
    }

    fun secondFrag(list: MutableList<HelpModel>) {
        val second = LevelSecondHelpFrag.newInstance(this@HelpSecondActivity,list)
        supportFragmentManager.beginTransaction().replace(fragId,second).commit()
    }
}