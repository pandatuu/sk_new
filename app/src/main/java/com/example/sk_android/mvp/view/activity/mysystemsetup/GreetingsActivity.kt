package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.mysystemsetup.SystemSetupApi
import com.example.sk_android.mvp.model.mysystemsetup.Greeting
import com.example.sk_android.mvp.model.mysystemsetup.UserSystemSetup
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.mysystemsetup.GreetingListFrag
import com.example.sk_android.mvp.view.fragment.mysystemsetup.GreetingSwitchFrag
import com.example.sk_android.utils.DialogUtils
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
import retrofit2.HttpException
import java.util.*

class GreetingsActivity : AppCompatActivity(), GreetingListFrag.GreetingRadio, GreetingSwitchFrag.GreetingSwitch {

    var actionBarNormalFragment:ActionBarNormalFragment?=null
    var user: UserSystemSetup? = null
    private var greetingList = LinkedHashMap<Int, Greeting>()
    private val fragId = 3
    private var greeting: GreetingListFrag? = null
    var switch: GreetingSwitchFrag? = null
    var thisDialog: MyDialog?=null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!){
            val toast = Toast.makeText(applicationContext, "ネットワークエラー", Toast.LENGTH_SHORT)//网路出现问题
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        DialogUtils.hideLoading(thisDialog)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        val outside = 4
        frameLayout {
            id = outside
            verticalLayout {
                val actionBarId=1
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("ご挨拶")
                    supportFragmentManager.beginTransaction().replace(id,actionBarNormalFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                val rId = 2
                relativeLayout {
                    id = rId
                    gravity = Gravity.CENTER_VERTICAL
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
                scrollView {
                    isVerticalScrollBarEnabled = false
                    overScrollMode = View.OVER_SCROLL_NEVER
                    verticalLayout {
                        frameLayout {
                            id = fragId
                            greeting = GreetingListFrag.newInstance(this@GreetingsActivity, null, null)
                            supportFragmentManager.beginTransaction().add(fragId, greeting!!).commit()
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
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
        StatusBarUtil.setTranslucentForImageView(this@GreetingsActivity, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
           overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }

    override fun onResume() {
        super.onResume()
        thisDialog=DialogUtils.showLoading(this@GreetingsActivity)
        mHandler.postDelayed(r, 12000)
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            getUserInformation()
        }
    }

    private fun showNormalDialog(id: UUID) {
        thisDialog=DialogUtils.showLoading(this@GreetingsActivity)
        mHandler.postDelayed(r, 12000)
        //延迟3秒关闭
        GlobalScope.launch {
            val model = user!!
            model.greetingId = id
            putUserInformation(model)
        }
    }


    private var first = 1
    // 获取用户设置信息
    private suspend fun getUserInformation() {
        try {
            val retrofitUils = RetrofitUtils(this@GreetingsActivity, this.getString(R.string.userUrl))
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .getUserInformation()
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() in 200..299) {
                val json = it.body()!!.asJsonObject
                user = Gson().fromJson<UserSystemSetup>(json, UserSystemSetup::class.java)
                println("user-----------------------" + user.toString())
                if(first==1) {
                    val rId = 2
                    switch = GreetingSwitchFrag.newInstance(this@GreetingsActivity, user!!.greeting)
                    supportFragmentManager.beginTransaction().add(rId, switch!!).commit()
                    first++
                }else{
                    val rId = 2
                    switch = GreetingSwitchFrag.newInstance(this@GreetingsActivity, user!!.greeting)
                    supportFragmentManager.beginTransaction().replace(rId, switch!!).commit()
                }
                if (user!!.greeting){
                    getGreetings()
                    getGreetingById(user!!.greetingId)
                }
            }
            //新用户第一次查找时为404
            if(it.code() == 404){
                val params = mapOf<Any,Any>()
                val userJson = JSON.toJSONString(params)
                val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)
                val retrofitUrls = RetrofitUtils(this@GreetingsActivity, this.getString(R.string.userUrl))
                retrofitUrls.create(SystemSetupApi::class.java)
                    .updateUserInformation(body)
                    .subscribeOn(Schedulers.io())
                    .awaitSingle()
                getUserInformation()
            }
            DialogUtils.hideLoading(thisDialog)
        } catch (throwable: Throwable) {
            println("获取失败啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦")
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
            DialogUtils.hideLoading(thisDialog)
        }
    }

    // 更改用户设置信息
    private suspend fun putUserInformation(newUser: UserSystemSetup) {
        println("user-------------------------------$newUser")
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

            val retrofitUils = RetrofitUtils(this@GreetingsActivity, this.getString(R.string.userUrl))
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .updateUserInformation(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() in 200..299) {
                DialogUtils.hideLoading(thisDialog)
            }
        } catch (throwable: Throwable) {
            println("更换失败啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦")
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

    // 获取所有打招呼语
    private suspend fun getGreetings() {
        try {
            val retrofitUils = RetrofitUtils(this@GreetingsActivity, this.getString(R.string.userUrl))
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .getGreetings()
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() in 200..299) {
                val json = it.body()!!.asJsonArray
                for ((index, item) in json.withIndex()) {
                    val model = Gson().fromJson<Greeting>(item, Greeting::class.java)
                    greetingList[index] = model
                }

                greeting = GreetingListFrag.newInstance(this@GreetingsActivity, greetingList, null)
                supportFragmentManager.beginTransaction().replace(fragId, greeting!!).commit()
//                getGreetingById(greetingId)
            }
        } catch (throwable: Throwable) {
            println("获取失败啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦")
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

    // 获取打招呼语
    private suspend fun getGreetingById(greetingId1: UUID) {
        try {
            val retrofitUils = RetrofitUtils(this@GreetingsActivity, this.getString(R.string.userUrl))
            val it = retrofitUils.create(SystemSetupApi::class.java)
                .getGreetingById(greetingId1.toString())
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() in 200..299) {
                val json = it.body()!!.asJsonObject
                val model = Gson().fromJson<Greeting>(json, Greeting::class.java)
                for (entry in greetingList) {
                    if(entry.value.content == model.content){
                        greeting?.setCheck(entry.key)
                    }
                }
            }
        } catch (throwable: Throwable) {
            println("获取失败啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦")
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

    override suspend fun clickSwitch(bool: Boolean) {
        val model = user!!
        model.greeting = bool
        if (!bool) {
            thisDialog=DialogUtils.showLoading(this@GreetingsActivity)
            mHandler.postDelayed(r, 12000)
            putUserInformation(model)
            closeSwitch()
        } else {
            putUserInformation(model)
            thisDialog=DialogUtils.showLoading(this@GreetingsActivity)
            mHandler.postDelayed(r, 12000)
            getUserInformation()
        }
    }

    private fun closeSwitch() {
        if (greeting != null) {
            supportFragmentManager.beginTransaction().remove(greeting!!).commit()
        }
    }

    // 点击单选框,调用AlertDialog
    override fun clickRadio(id: UUID) {
        showNormalDialog(id)
    }

}