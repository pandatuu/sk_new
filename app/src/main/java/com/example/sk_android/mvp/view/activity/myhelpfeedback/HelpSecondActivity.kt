package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.api.myhelpfeedback.HelpFeedbackApi
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.myhelpfeedback.HelpModel
import com.example.sk_android.mvp.view.activity.person.PersonSetActivity
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.LevelSecondHelpFrag
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

class HelpSecondActivity : AppCompatActivity() {

    var actionBarNormalFragment: ActionBarNormalFragment?=null
    private lateinit var recycle: RecyclerView
    var parentId = ""
    val fragId = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        relativeLayout {
            verticalLayout {
                val actionBarId=3
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("求職攻略");
                    supportFragmentManager.beginTransaction().replace(id,actionBarNormalFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                relativeLayout {
                    id = fragId
                    val second = LevelSecondHelpFrag.newInstance(this@HelpSecondActivity,null)
                    supportFragmentManager.beginTransaction().add(fragId,second).commit()
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
        StatusBarUtil.setTranslucentForImageView(this@HelpSecondActivity, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent(this@HelpSecondActivity, HelpFeedbackActivity::class.java)
            startActivity(intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }

    override fun onResume() {
        super.onResume()
        if (getIntent().getSerializableExtra("parentId") != null) {
            val id = getIntent().getSerializableExtra("parentId")
            parentId = id.toString()
            GlobalScope.launch {
                getInformation(parentId)
            }
        }
    }

    private suspend fun getInformation(id: String) {
        val list = mutableListOf<HelpModel>()
        //获取全部子帮助信息
        var retrofitUils = RetrofitUtils(this@HelpSecondActivity,this.getString(R.string.helpUrl))

        try {
            val body = retrofitUils.create(HelpFeedbackApi::class.java)
                .getChildrenInformation(id)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()

            if(body.code() in 200..299){
                // Json转对象
                val page = Gson().fromJson(body.body(), PagedList::class.java)
                val obj = page.data
                for (item in obj) {
                    val model = Gson().fromJson(item, HelpModel::class.java)
                    list.add(model)
                }
                secondFrag(list)
            }
        } catch (throwable: Throwable) {
            println("失败！！！！！！！！！")
        }
    }

    private fun secondFrag(list: MutableList<HelpModel>) {

        val second = LevelSecondHelpFrag.newInstance(this@HelpSecondActivity,list)
        supportFragmentManager.beginTransaction().replace(fragId,second).commit()
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this@HelpSecondActivity, HelpFeedbackActivity::class.java)
            startActivity(intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
            return true
        } else {
            return false
        }
    }
}