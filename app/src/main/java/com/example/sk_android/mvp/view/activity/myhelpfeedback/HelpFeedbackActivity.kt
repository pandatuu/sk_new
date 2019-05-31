package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import com.example.sk_android.R
import org.jetbrains.anko.*
import android.support.v7.widget.LinearLayoutManager
import com.example.sk_android.custom.layout.recyclerView
import com.umeng.message.PushAgent
import com.example.sk_android.mvp.view.adapter.myhelpfeedback.HelpFeedbackAdapter
import com.example.sk_android.utils.RetrofitUtils
import org.jetbrains.anko.sdk25.coroutines.onClick
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class HelpFeedbackActivity : AppCompatActivity() {

    private lateinit var recycle: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        println("-----------------")

        relativeLayout {
            relativeLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
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
                relativeLayout {
                    scrollView {
                        relativeLayout {
                            recycle = recyclerView {
                                layoutManager =
                                    LinearLayoutManager(this@HelpFeedbackActivity) as RecyclerView.LayoutManager?
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
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

        getInformation()
    }

    private fun getInformation() {
                val list = mutableListOf<String>()
                //获取全部帮助信息
//        RetrofitUtils.create(HelpFeedbackApi::class.java)
//            .getHelpInformation()
//            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
//            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
//            .subscribe({
//                println("成功！！！！！！！！！")
//                val obj = it.get("data")
//                for (item in obj.asJsonArray) {
//                    val title = item.asJsonObject.get("title")
//                    list.add(title.toString())
//                }
//                recycle.setAdapter(HelpFeedbackAdapter(list,this@HelpFeedbackActivity))
//            }, {
//                println("失败！！！！！！！！！")
//            })

    }


}