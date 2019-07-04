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
import android.widget.Switch
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.model.mysystemsetup.UserSystemSetup
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.HttpException


class NotificationSettingsActivity : AppCompatActivity() {

    private var user: UserSystemSetup? = null
    var actionBarNormalFragment:ActionBarNormalFragment?=null
    private lateinit var switchh: Switch


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        verticalLayout {
            verticalLayout {
                val actionBarId=3
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("通知設定");
                    supportFragmentManager.beginTransaction().replace(id,actionBarNormalFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }
                frameLayout {
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
                                switchh = switch {
                                    setThumbResource(R.drawable.thumb)
                                    setTrackResource(R.drawable.track)
                                    onClick {
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
                }.lparams{
                    width = matchParent
                    height = 0
                    weight=1f
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
        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@NotificationSettingsActivity, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.right_out,R.anim.right_out)
        }
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            getUserInformation()
        }
    }
    //　更改用户设置信息
    private suspend fun putUserInformation(bool: Boolean) {
        val text = if (bool) "PUBLIC" else "PRIVATE"
        println("text------------------------------$text")
        try {
            val params = mapOf(
                "Greeting" to user?.greeting,
                "GreetingID" to user?.greetingId,
                "OpenType" to text,
                "Remind" to user?.remind,
                "Attributes" to user?.attributes
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@NotificationSettingsActivity, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .updateUserInformation(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() in 200..299) {
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
            if (it.code() in 200..299) {
                val json = it.body()!!.asJsonObject
                user = Gson().fromJson<UserSystemSetup>(json, UserSystemSetup::class.java)

                switchh.isChecked = "PUBLIC" == user?.openType
            }
        } catch (throwable: Throwable) {
            println("获取失败啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦")
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

}