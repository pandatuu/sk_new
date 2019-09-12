package com.example.sk_android.mvp.view.activity.privacyset

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.sk_android.R
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.custom.layout.smartRefreshLayout
import com.example.sk_android.mvp.api.privacyset.PrivacyApi
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.privacySet.BlackCompanyInformation
import com.example.sk_android.mvp.model.privacySet.BlackCompanyModel
import com.example.sk_android.mvp.model.privacySet.BlackListModel
import com.example.sk_android.mvp.view.adapter.privacyset.RecyclerAdapter
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.privacyset.BlackListBottomButton
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import com.jeremyliao.liveeventbus.LiveEventBus
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.*
import retrofit2.HttpException

class BlackListActivity : AppCompatActivity(), RecyclerAdapter.ApdaterClick {

    private lateinit var blackListBottomButton: BlackListBottomButton
    lateinit var recyclerView: RecyclerView
    var actionBarNormalFragment: ActionBarNormalFragment? = null
    lateinit var readapter: RecyclerAdapter
    lateinit var textV: TextView
    private lateinit var recycle: RecyclerView
    private lateinit var refresh: SmartRefreshLayout

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        val outside = 1
        frameLayout {
            id = outside
            verticalLayout {
                val actionBarId = 3
                frameLayout {
                    id = actionBarId
                    actionBarNormalFragment = ActionBarNormalFragment.newInstance("ブラックリスト")
                    supportFragmentManager.beginTransaction().replace(id, actionBarNormalFragment!!).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                verticalLayout {
                    textView {
                        text = "履歴書を見せたくない会社"
                        textSize = 16f
                        textColor = Color.parseColor("#FF202020")
                        typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                        gravity = Gravity.CENTER
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        topMargin = dip(15)
                    }
                    textV = textView {
                        text = "(合計0社)"
                        textSize = 13f
                        textColor = Color.parseColor("#FF5C5C5C")
                        gravity = Gravity.CENTER
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        topMargin = dip(5)
                        bottomMargin = dip(15)
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }

                view { backgroundColor = Color.parseColor("#FFF6F6F6") }.lparams {
                    width = matchParent
                    height = dip(10)
                }
                val a = 2
                frameLayout {
                    //黑名单公司,可左滑删除
                    relativeLayout {
                        refresh = smartRefreshLayout {
                            setEnableAutoLoadMore(false)
                            setRefreshHeader(MaterialHeader(this@BlackListActivity))
                            //刷新效果
                            setOnRefreshListener {
                                //                                toast("刷新....")
                                GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                                    getBlackList()
                                    it.finishRefresh(3000)
                                }
                            }
                            recycle = recyclerView {
                                layoutManager = LinearLayoutManager(this@BlackListActivity)
                                readapter = RecyclerAdapter(this@BlackListActivity)
                                adapter = readapter

                            }.lparams {
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams(matchParent, matchParent)
                    }.lparams {
                        width = matchParent
                        height = matchParent
                        bottomMargin = dip(80)
                    }
                    //最下面的按钮
                    frameLayout {
                        id = a
                        blackListBottomButton = BlackListBottomButton.newInstance(this@BlackListActivity)
                        supportFragmentManager.beginTransaction().add(id, blackListBottomButton).commit()
                    }
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
        loading()
    }

    @SuppressLint("SetTextI18n")
    private fun loading() {
        refresh.autoRefresh()

        LiveEventBus.get("blackCompanyAdd", MutableList::class.java)
            .observe(this, Observer<MutableList<*>> { t ->
                if (t.isNullOrEmpty()) {
                    // TODO: 提示空
                } else {
                    println(t)
                    for (item in t) {
                        readapter.addItem(item as BlackCompanyInformation)
                    }
                    textV.text = "(合計${readapter.itemCount}社)"
                    recycle.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
                        @SuppressLint("SetTextI18n")
                        override fun onChildViewDetachedFromWindow(p0: View) {
                            println("add----------做了一些操作-------------")
                            textV.text = "(合計${readapter.itemCount}社)"
                        }

                        override fun onChildViewAttachedToWindow(p0: View) {
                            println("add----------第一次添加--------------")
                        }

                    })
                }
            })
    }

    override fun onStart() {
        super.onStart()
        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@BlackListActivity, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent(this@BlackListActivity, PrivacySetActivity::class.java)
            startActivity(intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
        }
    }

    // 获取黑名单列表信息
    private suspend fun getBlackList() {
        try {
            val retrofitUils = RetrofitUtils(this@BlackListActivity, this.getString(R.string.userUrl))
            val it = retrofitUils.create(PrivacyApi::class.java)
                .getBlackList()
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()
            // Json转对象
            if (it.code() in 200..299) {
                println("获取成功")
                val page = Gson().fromJson(it.body(), PagedList::class.java)
                if (page.data.size > 0) {
                    val items = mutableListOf<BlackCompanyInformation>()
                    for (item in page.data) {
                        val json = Gson().fromJson(item, BlackListModel::class.java)
                        val model = getCompany(json.blackedOrganizationId.toString())
                        val address = getCompanyAddress(json.blackedOrganizationId.toString())
                        if (model != null) {
                            val black = BlackCompanyInformation(json.id, address ?: "", model)
                            items.add(black)
                        }
                    }

                    changeList(items)
                } else {
                }
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("code--------------" + throwable.code())
            }
        }
    }


    // 根据公司ID获取公司信息
    private suspend fun getCompany(id: String): BlackCompanyModel? {
        try {
            val retrofitUils = RetrofitUtils(this@BlackListActivity, this.getString(R.string.orgUrl))
            val it = retrofitUils.create(PrivacyApi::class.java)
                .getCompany(id)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()
            // Json转对象
            if (it.code() in 200..299) {
                println("获取成功")
                val json = Gson().fromJson(it.body(), BlackCompanyModel::class.java)
                val logo = if (json.logo.indexOf(";") != -1) json.logo.split(";")[0] else json.logo
                return BlackCompanyModel(json.id, json.name, json.acronym, logo)
            }
            return null
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("code--------------" + throwable.code())
            }
            return null
        }
    }

    // 根据公司ID获取公司地址
    private suspend fun getCompanyAddress(id: String): String? {
        try {
            val retrofitUils = RetrofitUtils(this@BlackListActivity, this.getString(R.string.orgUrl))
            val it = retrofitUils.create(PrivacyApi::class.java)
                .getCompanyAddress(id)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()
            // Json转对象
            if (it.code() in 200..299) {
                println("获取成功")
                val data = it.body()!!.asJsonObject.get("data").asJsonArray
                if (data.size() > 0) {
                    val addr = data[0].asJsonObject.get("address").asString
                    if (addr != "") {
//                        if (addr.length > 60) {
//                            val sub = addr.substring(0, 60) + "......"
//                            return sub
//                        }
                        return addr
                    }
                }
                return "なし"
            }
            return ""
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("code--------------" + throwable.code())
            }
            return ""
        }
    }

    //更改list
    @SuppressLint("SetTextI18n")
    private fun changeList(items: List<BlackCompanyInformation>) {
        if (items.isEmpty()) {
            // TODO: 提示空
        } else {
            readapter.setItems(items)
            textV.text = "(合計${readapter.itemCount}社)"
            recycle.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
                @SuppressLint("SetTextI18n")
                override fun onChildViewDetachedFromWindow(p0: View) {
                    println("add----------做了一些操作-------------")
                    textV.text = "(合計${readapter.itemCount}社)"
                }

                override fun onChildViewAttachedToWindow(p0: View) {
                    println("add----------第一次添加--------------")
                }

            })
        }
    }

    override fun delete(text: String) {
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this@BlackListActivity, PrivacySetActivity::class.java)
            startActivity(intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
            true
        } else {
            false
        }
    }
}