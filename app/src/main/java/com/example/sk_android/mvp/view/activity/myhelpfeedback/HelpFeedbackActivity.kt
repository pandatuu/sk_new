package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import click
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.myhelpfeedback.HelpFeedbackApi
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.myhelpfeedback.HelpModel
import com.example.sk_android.mvp.view.activity.person.PersonSetActivity
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.HelpFeedbackMain
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
import withTrigger


class HelpFeedbackActivity : AppCompatActivity() {


    var actionBarNormalFragment: ActionBarNormalFragment?=null
    private lateinit var rela: RelativeLayout

    val mainId = 1
    private val fragId = 2
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

        frameLayout {
            id = mainId
            verticalLayout {
                val actionBarId=3
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("よくある質問")
                    supportFragmentManager.beginTransaction().replace(id,actionBarNormalFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }
                rela = relativeLayout {
                    frameLayout {
                        id = fragId
                        val main = HelpFeedbackMain.newInstance(this@HelpFeedbackActivity, null)
                        supportFragmentManager.beginTransaction().add(fragId, main).commit()
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                    }
                    relativeLayout {
                        verticalLayout {
                            textView {
                                text = "私のフィードバック"
                                backgroundResource = R.drawable.button_shape
                                textColor = Color.parseColor("#FF202020")
                                gravity = Gravity.CENTER
                                this.withTrigger().click {
                                    val intent = Intent(this@HelpFeedbackActivity, MyFeedbackActivity::class.java)
                                    startActivity(intent)
                                                    overridePendingTransition(R.anim.right_in, R.anim.left_out)

                                }
                            }.lparams {
                                width = matchParent
                                height = dip(47)
                                bottomMargin = dip(10)
                                leftMargin = dip(15)
                                rightMargin = dip(15)
                            }
                            textView {
                                backgroundResource = R.drawable.button_shape_orange
                                text = "フィードバックとアドバイス"
                                textColor = Color.WHITE
                                gravity = Gravity.CENTER
                                this.withTrigger().click {
                                    val intent = Intent(this@HelpFeedbackActivity, FeedbackSuggestionsActivity::class.java)
                                    startActivity(intent)
                                                    overridePendingTransition(R.anim.right_in, R.anim.left_out)

                                }
                            }.lparams {
                                width = matchParent
                                height = dip(47)
                                leftMargin = dip(15)
                                rightMargin = dip(15)
                            }
                        }.lparams {
                            width = matchParent
                            height = matchParent
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(114)
                        alignParentBottom()
                    }
                }.lparams(matchParent, matchParent)
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.parseColor("#FFFFFF")
            }
        }

    }
    override fun onStart() {
        super.onStart()
        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@HelpFeedbackActivity, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent(this@HelpFeedbackActivity, PersonSetActivity::class.java)
            startActivity(intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }
    override fun onResume() {
        super.onResume()
        thisDialog=DialogUtils.showLoading(this@HelpFeedbackActivity)
        mHandler.postDelayed(r, 12000)
        GlobalScope.launch {
            getInformation()
        }
    }
    //获取全部帮助信息
    private suspend fun getInformation() {
        val list = mutableListOf<HelpModel>()
        val retrofitUils = RetrofitUtils(this@HelpFeedbackActivity,this.getString(R.string.helpUrl))
        try {
            val body = retrofitUils.create(HelpFeedbackApi::class.java)
                .getAllHelpInformation()
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()

            if(body.code() in 200..299){
                // Json转对象
                val page = Gson().fromJson(body.body(), PagedList::class.java)
                val obj = page.data.toMutableList()
                for (item in obj) {
                    val model = Gson().fromJson(item, HelpModel::class.java)
                    list.add(model)
                }
                updateFrag(list)
            }else{
//                noNetwork()
            }
            DialogUtils.hideLoading(thisDialog)
        } catch (throwable: Throwable) {
            println("失败！！！！！！！！！")
            DialogUtils.hideLoading(thisDialog)
        }
    }

    // 更新fragment
    private fun updateFrag(list: MutableList<HelpModel>) {
        val main = HelpFeedbackMain.newInstance(this@HelpFeedbackActivity, list)
        supportFragmentManager.beginTransaction().replace(fragId, main).commit()
    }


    private fun noNetwork(){
        rela.removeAllViews()
        val view = UI {
            linearLayout {
                linearLayout{
                    imageView {
                        imageResource = R.mipmap.no_network
                    }.lparams(dip(100),dip(100)){
                        gravity = Gravity.CENTER
                    }
                }.lparams(matchParent, matchParent)
            }
        }.view
        rela.addView(view)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this@HelpFeedbackActivity, PersonSetActivity::class.java)
            startActivity(intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
            true
        } else {
            false
        }
    }
}