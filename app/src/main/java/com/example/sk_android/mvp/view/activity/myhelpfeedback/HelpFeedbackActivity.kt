package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import com.example.sk_android.R
import org.jetbrains.anko.*
import com.alibaba.fastjson.JSON
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.myhelpfeedback.HelpModel
import com.example.sk_android.mvp.view.activity.person.PersonSetActivity
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.myhelpfeedback.HelpFeedbackMain
import com.umeng.message.PushAgent
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.sdk25.coroutines.onClick
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.MediaType
import okhttp3.RequestBody


class HelpFeedbackActivity : AppCompatActivity() {


    var actionBarNormalFragment: ActionBarNormalFragment?=null
    private lateinit var myDialog: MyDialog

    val fragId = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        relativeLayout {
            verticalLayout {
                val actionBarId=3
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("よくある質問");
                    supportFragmentManager.beginTransaction().replace(id,actionBarNormalFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }
                relativeLayout {
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
                                onClick {
                                    toast("私のフィードバック")
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
                                onClick {
                                    toast("フィードバックとアドバイス")
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
            finish()//返回
            overridePendingTransition(R.anim.right_out,R.anim.right_out)
        }
    }
    override fun onResume() {
        super.onResume()
        showLoading()
        GlobalScope.launch {
            getInformation()
        }
    }
    //获取全部帮助信息
    private suspend fun getInformation() {
        val list = mutableListOf<HelpModel>()
        val retrofitUils = RetrofitUtils(this@HelpFeedbackActivity,"https://help.sk.cgland.top/")
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
                hideLoading()
                return
            }
            hideLoading()
            finish()
        } catch (throwable: Throwable) {
            println("失败！！！！！！！！！")
            hideLoading()
            finish()
        }
    }

    // 更新fragment
    fun updateFrag(list: MutableList<HelpModel>) {
        val main = HelpFeedbackMain.newInstance(this@HelpFeedbackActivity, list)
        supportFragmentManager.beginTransaction().replace(fragId, main).commit()
    }

    //弹出等待转圈窗口
    private fun showLoading() {
        if (isInit()) {
            myDialog.dismiss()
            val builder = MyDialog.Builder(this@HelpFeedbackActivity)
                .setCancelable(false)
                .setCancelOutside(false)
            myDialog = builder.create()

        } else {
            val builder = MyDialog.Builder(this@HelpFeedbackActivity)
                .setCancelable(false)
                .setCancelOutside(false)
            myDialog = builder.create()
        }
        myDialog.show()
    }

    //关闭等待转圈窗口
    private fun hideLoading() {
        if (isInit() && myDialog.isShowing()) {
            myDialog.dismiss()
        }
    }

    //判断mmloading是否初始化,因为lainit修饰的变量,不能直接判断为null,要先判断初始化
    private fun isInit(): Boolean {
        return ::myDialog.isInitialized
    }
}