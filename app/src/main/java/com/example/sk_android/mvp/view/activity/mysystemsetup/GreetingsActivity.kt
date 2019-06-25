package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.mysystemsetup.Greeting
import com.example.sk_android.mvp.model.mysystemsetup.UserSystemSetup
import com.example.sk_android.mvp.view.fragment.mysystemsetup.GreetingListFrag
import com.example.sk_android.mvp.view.fragment.mysystemsetup.GreetingSwitchFrag
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
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.HttpException
import java.util.*

class GreetingsActivity : AppCompatActivity(), GreetingListFrag.GreetingRadio, GreetingSwitchFrag.GreetingSwitch {
    override suspend fun clickSwitch(bool: Boolean) {
        var model = user!!
        model.greeting = bool
        if (!bool) {
            closeSwitch()
        }else{
            greeting = GreetingListFrag.newInstance(this@GreetingsActivity, greetingList, null)
            supportFragmentManager.beginTransaction().add(fragId, greeting!!).commit()
        }
        putUserInformation(model)
    }

    private fun closeSwitch() {
        if (greeting != null) {
            supportFragmentManager.beginTransaction().remove(greeting!!).commit()
        }
    }

    private lateinit var myDialog: MyDialog
    var user: UserSystemSetup? = null
    var greetingList = mutableListOf<Greeting>()
    val fragId = 3
    var greeting: GreetingListFrag? = null

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            getUserInformation()
        }
    }


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
                        text = "ご挨拶"
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
                val rId = 2
                relativeLayout {
                    id = rId
                    gravity = Gravity.CENTER_VERTICAL
                    val switch = GreetingSwitchFrag.newInstance(this@GreetingsActivity, true)
                    supportFragmentManager.beginTransaction().add(rId, switch).commit()
                }.lparams {
                    width = matchParent
                    height = dip(55)
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                }
                view {
                    backgroundColor = Color.parseColor("#F6F6F6")
                }.lparams {
                    width = matchParent
                    height = dip(8)
                }
                verticalLayout {
                    frameLayout {
                        id = fragId
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }

    private fun showNormalDialog(id: UUID) {
        showLoading()
        //延迟3秒关闭
        Handler().postDelayed({
            hideLoading(id)
        }, 3000)
    }

    private fun showLoading() {
        val builder = MyDialog.Builder(this@GreetingsActivity)
            .setMessage("提出中")
            .setCancelable(false)
            .setCancelOutside(false)
        myDialog = builder.create()
        myDialog.show()
    }

    private fun hideLoading(id: UUID) {
        if (isInit() && myDialog.isShowing()) {
            myDialog.dismiss()
        }
        GlobalScope.launch {
            val model = user!!
            model.greetingId = id
            putUserInformation(model)
        }
    }

    private fun isInit(): Boolean {

        return ::myDialog.isInitialized
    }

    // 获取用户设置信息
    private suspend fun getUserInformation() {
        try {
            val retrofitUils = RetrofitUtils(this@GreetingsActivity, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .getUserInformation()
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() == 200) {
                val json = it.body()!!.asJsonObject
                user = Gson().fromJson<UserSystemSetup>(json, UserSystemSetup::class.java)
                println("user-----------------------" + user.toString())

                val rId = 2
                val switch = GreetingSwitchFrag.newInstance(this@GreetingsActivity, user!!.greeting)
                supportFragmentManager.beginTransaction().replace(rId, switch).commit()

                getGreetings(user!!.greeting, user!!.greetingId)
            }
        } catch (throwable: Throwable) {
            println("获取失败啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦")
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

    // 更改用户设置信息
    private suspend fun putUserInformation(newUser: UserSystemSetup) {
        println("user-------------------------------"+newUser)
        try {
            val params = mapOf(
                "Greeting" to newUser.greeting,
                "GreetingID" to newUser.greetingId,
                "OpenType" to newUser.openType,
                "Remind" to newUser.remind,
                "Attributes" to newUser.attributes
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@GreetingsActivity, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .updateUserInformation(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() == 200) {
                toast("更换成功")
            }
        } catch (throwable: Throwable) {
            println("更换失败啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦")
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

    // 获取打招呼语
    private suspend fun getGreetings(isGreeting: Boolean, greetingId1: UUID) {
        try {
            val retrofitUils = RetrofitUtils(this@GreetingsActivity, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .getGreetings()
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() == 200) {
                val json = it.body()!!.asJsonObject
                val page = Gson().fromJson<PagedList>(json, PagedList::class.java)
                for (item in page.data) {
                    val model = Gson().fromJson<Greeting>(item, Greeting::class.java)
                    greetingList.add(model)
                }

                if (isGreeting) {
                    greeting = GreetingListFrag.newInstance(this@GreetingsActivity, greetingList, null)
                    supportFragmentManager.beginTransaction().add(fragId, greeting!!).commit()
                }else{
                    closeSwitch()
                }
            }
        } catch (throwable: Throwable) {
            println("获取失败啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦")
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

    // 点击单选框,调用AlertDialog
    override fun clickRadio(id: UUID) {
        showNormalDialog(id)
    }
}