package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.model.myhelpfeedback.HelpModel
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.HelpAnswerBody
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.HelpAnswerButton
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class HelpDetailInformation : AppCompatActivity() {

    var id = ""
    var actionBarNormalFragment: ActionBarNormalFragment? = null
    val list = mutableListOf<HelpModel>()
    val mainId = 1
    val headId = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()
        verticalLayout {
            verticalLayout {
                id = mainId
                val actionBarId = 3
                frameLayout {
                    id = actionBarId
                    actionBarNormalFragment = ActionBarNormalFragment.newInstance("");
                    supportFragmentManager.beginTransaction().replace(id, actionBarNormalFragment!!).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
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
                                onClick {
                                    val intent = Intent(this@HelpDetailInformation, FeedbackSuggestionsActivity::class.java)
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
                                onClick {
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
        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@HelpDetailInformation, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.right_out, R.anim.right_out)
        }
    }

    override fun onResume() {
        super.onResume()
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
                val id = 3
                actionBarNormalFragment = ActionBarNormalFragment.newInstance(list[0].title);
                supportFragmentManager.beginTransaction().replace(id, actionBarNormalFragment!!).commit()
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