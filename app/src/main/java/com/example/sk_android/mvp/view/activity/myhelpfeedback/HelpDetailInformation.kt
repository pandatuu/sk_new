package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import click
import com.example.sk_android.R
import com.example.sk_android.mvp.api.myhelpfeedback.HelpFeedbackApi
import com.example.sk_android.mvp.model.myhelpfeedback.HelpModel
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.HelpAnswerBody
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.*
import withTrigger

class HelpDetailInformation : AppCompatActivity() {

    var id = ""
    var actionBarNormalFragment: ActionBarNormalFragment? = null
    val list = mutableListOf<HelpModel>()
    val mainId = 1
    val headId = 2
    lateinit var titleV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()
        linearLayout {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                id = mainId
                val actionBarId = 3
                relativeLayout {
                    backgroundResource = R.drawable.actionbar_bottom_border
                    imageView {
                        imageResource = R.mipmap.icon_back
                        this.withTrigger().click {
                            finish()//返回
                            overridePendingTransition(R.anim.left_in, R.anim.right_out)
                        }
                    }.lparams(dip(9), dip(15)) {
                        leftMargin = dip(15)
                        alignParentBottom()
                        bottomMargin = dip(10)
                    }
                    titleV = textView {
                        textSize = 16f
                        textColor = Color.parseColor("#FF333333")
                        typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                    }.lparams(wrapContent, wrapContent) {
                        centerHorizontally()
                        alignParentBottom()
                        bottomMargin = dip(10)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(65)
                }
                relativeLayout {
                    frameLayout {
                        id = headId
                        val head = HelpAnswerBody.newInstance(list, this@HelpDetailInformation)
                        supportFragmentManager.beginTransaction().add(headId, head).commit()
                    }.lparams(matchParent, matchParent) {
                        bottomMargin = dip(110)
                    }
                    relativeLayout {
                        verticalLayout {
                            textView {
                                text = "未解决"
                                backgroundResource = R.drawable.button_shape_grey
                                textColor = Color.parseColor("#FFFFFFFF")
                                gravity = Gravity.CENTER


                                this.withTrigger().click {
                                    val intent =
                                        Intent(this@HelpDetailInformation, FeedbackSuggestionsActivity::class.java)
                                    startActivity(intent)
                                    overridePendingTransition(R.anim.right_in, R.anim.left_out)

                                }

                            }.lparams {
                                width = matchParent
                                height = dip(47)
                                bottomMargin = dip(16)
                            }
                            textView {
                                text = "解決済み"
                                backgroundResource = R.drawable.button_shape_orange
                                textColor = Color.parseColor("#FFFFFFFF")
                                gravity = Gravity.CENTER
                                this.withTrigger().click {

                                    val intent = Intent(this@HelpDetailInformation, HelpFeedbackActivity::class.java)
                                    startActivity(intent)
                                    overridePendingTransition(R.anim.right_in, R.anim.left_out)

                                }
                            }.lparams {
                                width = matchParent
                                height = dip(47)
                            }
                        }.lparams {
                            width = matchParent
                            height = matchParent
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                            centerHorizontally()
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(110)
                        alignParentBottom()
                        bottomPadding = dip(20)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }

    override fun onResume() {
        super.onResume()
        if (intent.getStringExtra("id") != null) {
            id = intent.getStringExtra("id")
            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
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
                titleV.text = list[0].title
//                actionBarNormalFragment = ActionBarNormalFragment.newInstance(list[0].title);
//                supportFragmentManager.beginTransaction().replace(titlebar.id, actionBarNormalFragment!!).commit()
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