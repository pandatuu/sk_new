package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import org.jetbrains.anko.*
import com.alibaba.fastjson.JSON
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.myhelpfeedback.HelpModel
import com.example.sk_android.mvp.view.activity.person.PersonSetActivity
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.HelpFeedbackMain
import com.umeng.message.PushAgent
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import org.jetbrains.anko.sdk25.coroutines.onClick
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.MediaType
import okhttp3.RequestBody


class HelpFeedbackActivity : AppCompatActivity() {


    val fragId = 2
    override fun onStart() {
        super.onStart()
//        getToken()
        GlobalScope.launch {
            getInformation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        relativeLayout {
            relativeLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                        onClick {
                            val intent = Intent(this@HelpFeedbackActivity,PersonSetActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "よくある質問"
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
                frameLayout {
                    id = fragId
                    val main = HelpFeedbackMain.newInstance(this@HelpFeedbackActivity, null)
                    supportFragmentManager.beginTransaction().add(fragId, main).commit()
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    bottomMargin = dip(120)
                    topMargin = dip(54)
                }
                relativeLayout {
                    verticalLayout {
                        textView {
                            text = "私のフィードバック"
                            backgroundResource = R.drawable.button_shape
                            textColor = Color.parseColor("#FF202020")
                            gravity = Gravity.CENTER
                            onClick {
                                toast("私のフィードバック")
                                val intent = Intent(this@HelpFeedbackActivity, MyFeedbackActivity::class.java)
                                startActivity(intent)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(47)
                            bottomMargin = dip(10)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        textView {
                            backgroundResource = R.drawable.button_shape_orange
                            text = "フィードバックとアドバイス"
                            textColor = Color.WHITE
                            gravity = Gravity.CENTER
                            onClick {
                                toast("フィードバックとアドバイス")
                                val intent = Intent(this@HelpFeedbackActivity, FeedbackSuggestionsActivity::class.java)
                                startActivity(intent)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(47)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }.lparams {
                    width = matchParent
                    height = dip(114)
                    alignParentBottom()
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.parseColor("#FFFFFF")
            }
        }

    }

    //获取全部帮助信息
    private suspend fun getInformation() {
        val list = mutableListOf<HelpModel>()
        val retrofitUils = RetrofitUtils(this@HelpFeedbackActivity,"https://help.sk.cgland.top/")
        try {
            val body = retrofitUils.create(HelpFeedbackApi::class.java)
                .getAllHelpInformation()
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()
            if(body.code() in 200..299){
                // Json转对象
                val page = Gson().fromJson(body.body(), PagedList::class.java)
                val obj = page.data.toMutableList()
                for (item in obj) {
                    val model = Gson().fromJson(item, HelpModel::class.java)
                    list.add(model)
                }
                updateFrag(list)
            }
        } catch (throwable: Throwable) {
            println("失败！！！！！！！！！")
        }
    }

    // 更新fragment
    fun updateFrag(list: MutableList<HelpModel>) {
        val main = HelpFeedbackMain.newInstance(this@HelpFeedbackActivity, list)
        supportFragmentManager.beginTransaction().replace(fragId, main).commit()
    }
}