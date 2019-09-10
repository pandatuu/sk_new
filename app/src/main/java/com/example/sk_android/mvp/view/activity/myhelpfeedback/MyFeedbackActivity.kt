package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.myhelpfeedback.HelpFeedbackApi
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.myhelpfeedback.FeedbackModel
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.FeedbackInformationList
import com.example.sk_android.utils.DialogUtils
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

class MyFeedbackActivity : AppCompatActivity() {

    var actionBarNormalFragment: ActionBarNormalFragment?=null
    val mainId = 1
    private val fId = 2
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

        thisDialog=DialogUtils.showLoading(this@MyFeedbackActivity)
        mHandler.postDelayed(r, 12000)

        frameLayout {
            id = mainId
            verticalLayout {
                val actionBarId=3
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("私のフィードバック")
                    supportFragmentManager.beginTransaction().replace(id,actionBarNormalFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }
                frameLayout {
                    id = fId
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
        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@MyFeedbackActivity, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent(this@MyFeedbackActivity, HelpFeedbackActivity::class.java)
            startActivity(intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch {
            getUserFeedback()
        }
    }

    // 获取用户的反馈信息
    private suspend fun getUserFeedback() {
        val list = mutableListOf<FeedbackModel>()
        val retrofitUils = RetrofitUtils(this@MyFeedbackActivity,this.getString(R.string.helpUrl))
        try {
            val body = retrofitUils.create(HelpFeedbackApi::class.java)
                .userFeedback()
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()
            if(body.code() in 200..299){
                val page = Gson().fromJson(body.body()!!, PagedList::class.java)
                val feedList = page.data
                for (item in feedList) {
                    list.add(Gson().fromJson(item, FeedbackModel::class.java))
                }
                add(list)
            }
        } catch (throwable: Throwable) {
            println("失败！！！！！！！！")
            if (throwable is retrofit2.HttpException) {
                println(throwable.code())
            }
        }
        DialogUtils.hideLoading(thisDialog)
    }

    private fun add(list: MutableList<FeedbackModel>) {
        val feedList = FeedbackInformationList.newInstance(list, this@MyFeedbackActivity)
        supportFragmentManager.beginTransaction().add(fId, feedList).commit()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this@MyFeedbackActivity, HelpFeedbackActivity::class.java)
            startActivity(intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
            true
        } else {
            false
        }
    }
}