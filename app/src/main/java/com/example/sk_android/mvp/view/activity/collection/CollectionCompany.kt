package com.example.sk_android.mvp.view.activity.collection

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.example.sk_android.R
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.custom.layout.smartRefreshLayout
import com.example.sk_android.mvp.api.collection.CollectionApi
import com.example.sk_android.mvp.api.privacyset.PrivacyApi
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.collection.CollectionModel
import com.example.sk_android.mvp.model.privacySet.BlackCompanyInformation
import com.example.sk_android.mvp.model.privacySet.BlackCompanyModel
import com.example.sk_android.mvp.view.activity.person.PersonSetActivity
import com.example.sk_android.mvp.view.adapter.collection.CollectionAdapter
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
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

class CollectionCompany: AppCompatActivity(), CollectionAdapter.ApdaterClick {

    lateinit var recyclerView: RecyclerView
    var actionBarNormalFragment: ActionBarNormalFragment?=null
    private lateinit var readapter: CollectionAdapter
    private lateinit var recycle: RecyclerView
    private lateinit var refresh: SmartRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        val outside = 1
        frameLayout {
            id = outside
            verticalLayout {
                val actionBarId=3
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("フォローしてる会社")
                    supportFragmentManager.beginTransaction().replace(id,actionBarNormalFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }
                frameLayout {
                    //黑名单公司,可左滑删除
                    relativeLayout {
                        refresh = smartRefreshLayout {
                            setEnableAutoLoadMore(false)
                            setRefreshHeader(MaterialHeader(this@CollectionCompany))
                            //刷新效果
                            setOnRefreshListener {
                                //                                toast("刷新....")
                                GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                                    getCompany()
                                    it.finishRefresh(3000)
                                }
                            }
                        //一开始隐藏列表
                        recycle = recyclerView {
                            layoutManager = LinearLayoutManager(this@CollectionCompany)
                            readapter = CollectionAdapter(this@CollectionCompany)
                            adapter = readapter

                        }.lparams {
                            width = matchParent
                            height = matchParent
                        }
                        }.lparams(matchParent, matchParent)
                    }.lparams {
                        width = matchParent
                        height = matchParent
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

        refresh.autoRefresh()
    }
    override fun onStart() {
        super.onStart()
        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@CollectionCompany, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent(this@CollectionCompany, PersonSetActivity::class.java)
            startActivity(intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
        }
    }

    override fun delete(text: String) {
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()
    }

    private suspend fun getCompany(){
        try {
            val retrofitUils = RetrofitUtils(this@CollectionCompany,this.getString(R.string.jobUrl))
            val it = retrofitUils.create(CollectionApi::class.java)
                .getFavoritesCompany("ORGANIZATION")
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
                        val json = Gson().fromJson(item, CollectionModel::class.java)
                        val model = getCompany(json.targetEntityId.toString())
                        val address = getCompanyAddress(json.targetEntityId.toString())
                        if (model != null) {
                            val black = BlackCompanyInformation(json.id, address ?: "", model)
                            items.add(black)
                        }
                    }
                    if (items.isEmpty()) {
                        // TODO: 提示空
                    } else {
                        readapter.setItems(items)
                    }
                }
            }
        } catch (throwable: Throwable) {
            println("失败！！！！！！！！！")
        }
    }
    // 根据公司ID获取公司信息
    private suspend fun getCompany(id: String): BlackCompanyModel? {
        try {
            val retrofitUils = RetrofitUtils(this@CollectionCompany, this.getString(R.string.orgUrl))
            val it = retrofitUils.create(PrivacyApi::class.java)
                .getCompany(id)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()
            // Json转对象
            if (it.code() in 200..299) {
                println("获取成功")
                val json = Gson().fromJson(it.body(), BlackCompanyModel::class.java)
                val logo = if(json.logo.indexOf(";")!=-1) json.logo.split(";")[0] else json.logo
                return BlackCompanyModel(json.id, json.name, json.acronym,logo)
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
            val retrofitUils = RetrofitUtils(this@CollectionCompany, this.getString(R.string.orgUrl))
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
//                        if(addr.length>60){
//                            val sub = addr.substring(0,60)+"......"
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

}