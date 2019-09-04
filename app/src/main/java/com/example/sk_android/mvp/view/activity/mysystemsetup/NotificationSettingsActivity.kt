package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.Switch
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.api.mysystemsetup.SystemSetupApi
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.model.mysystemsetup.UserSystemSetup
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.jaeger.library.StatusBarUtil
import com.umeng.message.IUmengCallback
import com.umeng.message.PushAgent
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.HttpException


class NotificationSettingsActivity : AppCompatActivity() {

    private var user: UserSystemSetup? = null
    var actionBarNormalFragment:ActionBarNormalFragment?=null
    private lateinit var switchh: Switch
    lateinit var ms: SharedPreferences
    private val push = App.getInstance()?.getPushAgent()


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        ms = PreferenceManager.getDefaultSharedPreferences(this@NotificationSettingsActivity)


        verticalLayout {
            verticalLayout {
                val actionBarId=3
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("通知設定")
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
                                    text = "SMS通知"
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
                                        //调用全局消息类
                                        if (isChecked) {
                                            push?.enable(object: IUmengCallback {
                                                override fun onSuccess() {
                                                    println("推送打开")
                                                }

                                                override fun onFailure(p0: String?, p1: String?) {

                                                }

                                            })
                                            putUserInformation(isChecked)
                                        } else {
                                            push?.disable(object: IUmengCallback {
                                                override fun onSuccess() {
                                                    println("推送关闭")
                                                }

                                                override fun onFailure(p0: String?, p1: String?) {

                                                }

                                            })
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
           overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }

    override fun onResume() {
        super.onResume()

        val bool = ms.getBoolean("isNofication", true)
        if(bool){
            switchh.isChecked = true
        }
//        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
//            getUserInformation()
//        }
    }
    //　更改用户设置信息
    private suspend fun putUserInformation(bool: Boolean) {
        try {
            val mEditor: SharedPreferences.Editor = ms.edit()
            mEditor.putBoolean("isNofication",bool)
            mEditor.commit()
            val params = mapOf(
                "Greeting" to user?.greeting,
                "GreetingID" to user?.greetingId,
                "OpenType" to user?.openType,
                "Remind" to bool,
                "Attributes" to user?.attributes
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@NotificationSettingsActivity, this.getString(R.string.userUrl))
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .updateUserInformation(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() in 200..299) {
                val toast = Toast.makeText(applicationContext, "更新成功", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }
    //　获取用户设置信息
//    private suspend fun getUserInformation() {
//        try {
//            val retrofitUils = RetrofitUtils(this@NotificationSettingsActivity, "https://user.sk.cgland.top/")
//            val it = retrofitUils.create(SystemSetupApi::class.java)
//                .getUserInformation()
//                .subscribeOn(Schedulers.io())
//                .awaitSingle()
//            if (it.code() in 200..299) {
//                val json = it.body()!!.asJsonObject
//                user = Gson().fromJson<UserSystemSetup>(json, UserSystemSetup::class.java)
//
//                switchh.isChecked = user?.remind!!
//            }
//        } catch (throwable: Throwable) {
//            if (throwable is HttpException) {
//                println("throwable ------------ ${throwable.code()}")
//            }
//        }
//    }

}