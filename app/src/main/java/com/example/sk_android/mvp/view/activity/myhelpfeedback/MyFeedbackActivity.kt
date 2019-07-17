package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.api.myhelpfeedback.HelpFeedbackApi
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.myhelpfeedback.FeedbackModel
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.FeedbackInformationList
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
    val fId = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        frameLayout {
            id = mainId
            verticalLayout {
                val actionBarId=3
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("私のフィードバック");
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
        val retrofitUils = RetrofitUtils(this@MyFeedbackActivity,"https://help.sk.cgland.top/")
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
                    val item = Gson().fromJson(item, FeedbackModel::class.java)
                    list.add(item)
                }
                add(list)
            }
        } catch (throwable: Throwable) {
            println("失败！！！！！！！！")
            if (throwable is retrofit2.HttpException) {
                println(throwable.code())
            }
        }

    }

    private fun add(list: MutableList<FeedbackModel>) {
        var feedList = FeedbackInformationList.newInstance(list, this@MyFeedbackActivity)
        supportFragmentManager.beginTransaction().add(fId, feedList).commit()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this@MyFeedbackActivity, HelpFeedbackActivity::class.java)
            startActivity(intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
            return true
        } else {
            return false
        }
    }
}