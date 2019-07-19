package com.example.sk_android.mvp.view.activity.collection

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.api.collection.CollectionApi
import com.example.sk_android.mvp.api.privacyset.PrivacyApi
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.collection.CollectionModel
import com.example.sk_android.mvp.model.privacySet.BlackCompanyInformation
import com.example.sk_android.mvp.model.privacySet.BlackCompanyModel
import com.example.sk_android.mvp.view.activity.person.PersonSetActivity
import com.example.sk_android.mvp.view.adapter.collection.CollectionAdapter
import com.example.sk_android.mvp.view.adapter.privacyset.RecyclerAdapter
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
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
import retrofit2.HttpException

class CollectionCompany: AppCompatActivity(), CollectionAdapter.ApdaterClick {

    lateinit var recyclerView: RecyclerView
    private var collectionListItemList = mutableListOf<BlackCompanyInformation>()
    var actionBarNormalFragment: ActionBarNormalFragment?=null
    private lateinit var dialogLoading: FrameLayout
    var listsize = 0
    lateinit var readapter: CollectionAdapter
    lateinit var recycle: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        listsize = collectionListItemList.size

        var outside = 1
        frameLayout {
            id = outside
            verticalLayout {
                val actionBarId=3
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("フォローしてる会社");
                    supportFragmentManager.beginTransaction().replace(id,actionBarNormalFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
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
                            layoutManager = LinearLayoutManager(this@CollectionCompany)
                            readapter = CollectionAdapter(this@CollectionCompany, collectionListItemList)
                            collectionListItemList = readapter.getData()
                            adapter = readapter

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
        StatusBarUtil.setTranslucentForImageView(this@CollectionCompany, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent(this@CollectionCompany, PersonSetActivity::class.java)
            startActivity(intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
        }
    }

    override fun onResume() {
        super.onResume()
        //显示转圈等待
        dialogLoading.visibility = LinearLayout.VISIBLE
        //显示列表
        recycle.visibility = LinearLayout.GONE
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            getCompany()
        }
    }

    override fun delete(text: String) {
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()
    }

    private suspend fun getCompany(){
        try {
            val retrofitUils = RetrofitUtils(this@CollectionCompany,"https://job.sk.cgland.top/")
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
                    collectionListItemList.clear()
                    for (item in page.data) {
                        val json = Gson().fromJson(item, CollectionModel::class.java)
                        val model = getCompany(json.targetEntityId.toString())
                        val address = getCompanyAddress(json.targetEntityId.toString())
                        if (model != null) {
                            val black = BlackCompanyInformation(json.id, address ?: "", model)
                            collectionListItemList.add(black)
                        }
                    }
                    if (collectionListItemList.size > 0) {
                        changeList()
                    }
                }else{



                    //关闭转圈等待
                    dialogLoading.visibility = LinearLayout.GONE
                    //关闭列表
                    recycle.visibility = LinearLayout.VISIBLE
                }
            }
            //关闭转圈等待
            dialogLoading.visibility = LinearLayout.GONE
            //关闭列表
            recycle.visibility = LinearLayout.VISIBLE
        } catch (throwable: Throwable) {
            println("失败！！！！！！！！！")
            //关闭转圈等待
            dialogLoading.visibility = LinearLayout.GONE
            //关闭列表
            recycle.visibility = LinearLayout.VISIBLE
        }
    }
    // 根据公司ID获取公司信息
    private suspend fun getCompany(id: String): BlackCompanyModel? {
        try {
            val retrofitUils = RetrofitUtils(this@CollectionCompany, "https://org.sk.cgland.top/")
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
                val model = BlackCompanyModel(json.id, json.name, json.acronym,logo)
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
            val retrofitUils = RetrofitUtils(this@CollectionCompany, "https://org.sk.cgland.top/")
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

        readapter = CollectionAdapter(this@CollectionCompany, collectionListItemList)
        recycle.adapter!!.notifyDataSetChanged()
    }

}