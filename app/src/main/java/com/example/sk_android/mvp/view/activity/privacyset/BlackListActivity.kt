package com.example.sk_android.mvp.view.activity.privacyset

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.api.privacyset.PrivacyApi
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.privacySet.BlackCompanyInformation
import com.example.sk_android.mvp.model.privacySet.BlackCompanyModel
import com.example.sk_android.mvp.model.privacySet.BlackListModel
import com.example.sk_android.mvp.view.activity.person.PersonSetActivity
import com.example.sk_android.mvp.view.adapter.privacyset.RecyclerAdapter
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.common.DialogLoading
import com.example.sk_android.mvp.view.fragment.privacyset.BlackListBottomButton
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.HttpException
import java.io.Serializable

class BlackListActivity : AppCompatActivity(), BlackListBottomButton.BlackListJump,RecyclerAdapter.ApdaterClick{

    lateinit var blackListBottomButton: BlackListBottomButton
    lateinit var recyclerView: RecyclerView
    private var blackListItemList = mutableListOf<BlackCompanyInformation>()
    var actionBarNormalFragment: ActionBarNormalFragment?=null
    private lateinit var dialogLoading: FrameLayout
    var listsize = 0
    lateinit var readapter: RecyclerAdapter
    lateinit var textV: TextView
    lateinit var recycle: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        listsize = blackListItemList.size

        var outside = 1
        frameLayout {
            id = outside
            verticalLayout {
                val actionBarId=3
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("ブラックリスト");
                    supportFragmentManager.beginTransaction().replace(id,actionBarNormalFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                verticalLayout {
                    textView {
                        text = "履歴書を見せない会社"
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
                        text = "(合計${listsize}社)"
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
                        //一开始加载转圈动画
                        dialogLoading = frameLayout {
                            val image = imageView {}.lparams(dip(70), dip(80))
                            Glide.with(this@relativeLayout)
                                .load(R.mipmap.turn_around)
                                .into(image)
                        }.lparams{
                            gravity = Gravity.CENTER
                        }
                        //一开始隐藏列表
                        recycle = recyclerView {
                            visibility = LinearLayout.GONE
                            layoutManager = LinearLayoutManager(this@BlackListActivity)
                            readapter = RecyclerAdapter(this@BlackListActivity, blackListItemList)
                            blackListItemList = readapter.getData()
                            adapter = readapter

                        }.lparams {
                            width = matchParent
                            height = matchParent
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                        bottomMargin = dip(80)
                    }
                    //最下面的按钮
                    frameLayout {
                        id = a
                        blackListBottomButton = BlackListBottomButton.newInstance(this@BlackListActivity);
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
    }
    override fun onStart() {
        super.onStart()
        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@BlackListActivity, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent(this@BlackListActivity, PrivacySetActivity::class.java)
            startActivity(intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }
    override fun onResume() {
        super.onResume()
        //显示转圈等待
        dialogLoading.visibility = LinearLayout.VISIBLE
        //显示列表
        recycle.visibility = LinearLayout.GONE
//        DialogUtils.showLoading(this@BlackListActivity)
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            getBlackList()
//            DialogUtils.hideLoading()
        }
    }

    // 点击添加黑名单按钮
    override fun blackButtonClick() {
        val intent = Intent(this@BlackListActivity, BlackAddCompanyActivity::class.java)
        startActivity(intent)
                        overridePendingTransition(R.anim.right_in, R.anim.left_out)

    }

    // 获取黑名单列表信息
    private suspend fun getBlackList() {
        try {
            val retrofitUils = RetrofitUtils(this@BlackListActivity, "https://user.sk.cgland.top/")
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
                    blackListItemList.clear()
                    for (item in page.data) {
                        val json = Gson().fromJson(item, BlackListModel::class.java)
                        val model = getCompany(json.blackedOrganizationId.toString())
                        val address = getCompanyAddress(json.blackedOrganizationId.toString())
                        if (model != null) {
                            val black = BlackCompanyInformation(json.id, address ?: "", model)
                            blackListItemList.add(black)
                        }
                    }
                    if (blackListItemList.size > 0) {
                        changeList()
                    }
                }else{
                    //关闭转圈等待
                    dialogLoading.visibility = LinearLayout.INVISIBLE
                    //关闭列表
                    recycle.visibility = LinearLayout.VISIBLE
                }
            }
            //关闭转圈等待
            dialogLoading.visibility = LinearLayout.GONE
            //关闭列表
            recycle.visibility = LinearLayout.VISIBLE
        } catch (throwable: Throwable) {
            //关闭转圈等待
            dialogLoading.visibility = LinearLayout.INVISIBLE
            //关闭列表
            recycle.visibility = LinearLayout.VISIBLE
            if (throwable is HttpException) {
                println("code--------------" + throwable.code())
            }
        }
    }


    // 根据公司ID获取公司信息
    private suspend fun getCompany(id: String): BlackCompanyModel? {
        try {
            val retrofitUils = RetrofitUtils(this@BlackListActivity, "https://org.sk.cgland.top/")
            val it = retrofitUils.create(PrivacyApi::class.java)
                .getCompany(id)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()
            // Json转对象
            if (it.code() in 200..299) {
                println("获取成功")
                val json = Gson().fromJson(it.body(), BlackCompanyModel::class.java)
                val model = BlackCompanyModel(json.id, json.name, json.acronym,json.logo)
                return model
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
            val retrofitUils = RetrofitUtils(this@BlackListActivity, "https://org.sk.cgland.top/")
            val it = retrofitUils.create(PrivacyApi::class.java)
                .getCompanyAddress(id)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()
            // Json转对象
            if (it.code() in 200..299) {
                println("获取成功")
                val data = it.body()!!.asJsonObject.get("data").asJsonArray
                if(data.size()>0){
                    val addr = data[0].asJsonObject.get("address").asString
                    if(addr!=""){
                        if(addr.length>60){
                            val sub = addr.substring(0,60)+"......"
                            return sub
                        }
                        return addr
                    }
                }
                return "暂无公司地址"
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
    private fun changeList() {
        //关掉转圈等待
        dialogLoading.visibility = LinearLayout.GONE
        //显示列表
        recycle.visibility = LinearLayout.VISIBLE

        readapter = RecyclerAdapter(this@BlackListActivity, blackListItemList)
        recycle.adapter!!.notifyDataSetChanged()
        listsize = readapter.itemCount
        textV.text = "(合計${listsize}社)"
        recycle.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(p0: View) {
                println("add----------做了一些操作-------------")
                listsize = readapter.itemCount
                textV.text = "(合計${listsize}社)"
            }

            override fun onChildViewAttachedToWindow(p0: View) {
                println("add----------第一次添加--------------")
            }

        })
    }

    override fun delete(text: String) {
        toast(text)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this@BlackListActivity, PrivacySetActivity::class.java)
            startActivity(intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
            return true
        } else {
            return false
        }
    }
}