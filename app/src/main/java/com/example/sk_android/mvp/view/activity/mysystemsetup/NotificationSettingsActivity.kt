package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.CompoundButton
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.model.mysystemsetup.UserSystemSetup
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.umeng.message.PushAgent
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.HttpException


class NotificationSettingsActivity : AppCompatActivity() {

    var openType: Boolean = false
    lateinit var user: UserSystemSetup

    override fun onStart() {
        super.onStart()

        checkOpen()
        GlobalScope.launch {
            getUserInformation()
        }
    }

    private fun checkOpen() {
        if (intent.getStringExtra("openType") != null) {
            val string = intent.getStringExtra("openType")
            openType = if ("PUBLIC".equals(string)) true else false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        checkOpen()

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
                        text = "通知設定"
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
                    verticalLayout {
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "大切なメッセージが受信できなかった場合、SMS で通知する"
                                textSize = 13f
                                textColor = Color.parseColor("#5C5C5C")
                            }.lparams {
                                width = dip(299)
                                centerVertically()
                                alignParentLeft()
                            }
                            switch {
                                setThumbResource(R.drawable.thumb)
                                setTrackResource(R.drawable.track)
                                isChecked = openType
                                onCheckedChange { buttonView, isChecked ->
                                    if (isChecked) {
                                        putUserInformation(isChecked)
                                    } else {
                                        putUserInformation(isChecked)
                                    }
                                }
                            }.lparams {
                                alignParentRight()
                                centerVertically()
                                rightMargin = dip(15)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(74)
                            setMargins(dip(15), 0, 0, 0)
                        }
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }
    //　更改用户设置信息
    private suspend fun putUserInformation(bool: Boolean) {
        val text = if (bool) "PUBLIC" else "PRIVATE"
        println("text------------------------------${text}")
        try {
            val params = mapOf(
                "Greeting" to user.greeting,
                "GreetingID" to user.greetingId,
                "OpenType" to text,
                "Remind" to user.remind,
                "Attributes" to user.attributes
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@NotificationSettingsActivity, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .updateUserInformation(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() == 200) {
                toast("更换成功")
            }
        } catch (throwable: Throwable) {
            println("获取失败啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦")
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }
    //　获取用户设置信息
    private suspend fun getUserInformation() {
        try {
            val retrofitUils = RetrofitUtils(this@NotificationSettingsActivity, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .getUserInformation()
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() == 200) {
                val json = it.body()!!.asJsonObject
                user = Gson().fromJson<UserSystemSetup>(json, UserSystemSetup::class.java)
            }
        } catch (throwable: Throwable) {
            println("获取失败啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦")
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

}