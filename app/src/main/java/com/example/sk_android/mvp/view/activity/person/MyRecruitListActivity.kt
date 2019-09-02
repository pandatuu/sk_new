package com.example.sk_android.mvp.view.activity.person

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.annotation.RequiresApi
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.example.sk_android.R
import com.example.sk_android.mvp.api.jobselect.JobApi
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.listener.message.ChatRecord
import com.example.sk_android.mvp.model.jobselect.FavoriteType
import com.example.sk_android.mvp.view.activity.common.BaseActivity
import com.example.sk_android.mvp.view.fragment.common.ActionBarThemeFragment
import com.example.sk_android.mvp.view.fragment.jobselect.MyRecruitInfoListFragment
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.utils.RetrofitUtils
import com.jaeger.library.StatusBarUtil
import io.github.sac.Socket
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*

class MyRecruitListActivity : BaseActivity() {

    private val COMMUNICATED = 1
    private val COLLECTED = 2
    private val SENT = 3


    private lateinit var actionBar: ActionBarThemeFragment

    lateinit var myRecruitInfoListFragment: MyRecruitInfoListFragment

    var application: App? = null
    var socket: Socket? = null
    private var titleShow = ""

    lateinit var json: JSONObject


    //已经沟通的职位  入口-1
    //通过联系人列表查询出positionId的list
    private val Listhandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            println("+++++++++++++++++++++++")
            println(json)
            println("+++++++++++++++++++++++")
            val type = json.getString("type")
            if (type != null && type == "contactList") {
                val array: JSONArray = json.getJSONObject("content").getJSONArray("groups")

                var members = JSONArray()
                for (i in array) {
                    val item = (i as JSONObject)
                    val id = item.getIntValue("id")
                    var name = item.getString("name")
                    if (id == 0) {
                        members = item.getJSONArray("members")
                    }

                }

                println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")

                println(members)

                val positionList = mutableListOf<String>()

                for (i in members) {
                    val item = (i as JSONObject)
                    println(item)
                    // 显示的职位的id
                    val lastPositionId = item.getString("lastPositionId")
                    if(lastPositionId!=null && "" != lastPositionId){
                        positionList.add(lastPositionId)
                    }
                }
                println(positionList)

                myRecruitInfoListFragment.reuqestRecruitInfoData(positionList)
            }
        }
    }

    var type = 1

    override fun onStart() {
        super.onStart()
        setActionBar(actionBar.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@MyRecruitListActivity, 0, actionBar.toolbar1)
        actionBar.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }


    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        type = intent.getIntExtra("type", 0)


        //ActionBar
        when (type) {
            COMMUNICATED -> {

                titleShow = "連絡あった役職"
                //接受
                application = App.getInstance()
                socket = application?.socket
                //消息回调
                application?.setChatRecord(object : ChatRecord {
                    override fun requestContactList() {

                    }

                    override fun getContactList(str: String) {
                        json = JSON.parseObject(str)
                        val message = Message()
                        Listhandler.sendMessage(message)
                    }
                })

                Handler().postDelayed({
                    socket?.emit("queryContactList", application!!.getMyToken()
                    ) { name, error, data -> println("Got message for :$name error is :$error data is :$data") }
                }, 200)
            }
            COLLECTED -> titleShow = "お気に入りな役職"
            SENT -> titleShow = "履歴書提出した役職"
        }





        frameLayout {
            backgroundColor = Color.WHITE
            verticalLayout {
                //ActionBar
                val actionBarId = 2
                frameLayout {
                    id = actionBarId



                    actionBar = ActionBarThemeFragment.newInstance(titleShow)
                    supportFragmentManager.beginTransaction().replace(id, actionBar).commit()


                }.lparams {
                    height = wrapContent
                    width = matchParent
                }


                val mainBodyId = 6
                frameLayout {
                    id = mainBodyId
                    myRecruitInfoListFragment = MyRecruitInfoListFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, myRecruitInfoListFragment).commit()
                }.lparams {
                    height = 0
                    weight = 1f
                    width = matchParent
                }


            }.lparams {
                width = matchParent
                height = matchParent
            }

        }


        if (type == COLLECTED) {
            getFavoritesJob()
        } else if (type == SENT) {
            getexchangesinfo()
        }



    }


    //通过type获取交换信息  入口-3
    @SuppressLint("CheckResult")
    private fun getexchangesinfo() {

        //得到投递信息
        val requestAddress = RetrofitUtils(this, this.getString(R.string.interUrl))
        requestAddress.create(PersonApi::class.java)
            .getExchangesInfo("EXCHANGED")
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("得到投递信息成功")
                println(it)
                val responseStr = org.json.JSONObject(it.toString())
                val array = responseStr.getJSONArray("data")
                val list: MutableList<String> = mutableListOf()
                for (i in 0 until array.length()) {
                    val item = array.getJSONObject(i)
                    val organizationPositionId = item.getString("organizationPositionId")
                    list.add(organizationPositionId)

                }
                myRecruitInfoListFragment.reuqestRecruitInfoData(list)

            }, {
                //失败
                println("投递信请求失败")
                println(it)
            })

    }

    //获取收藏职位记录 入口-2
    @SuppressLint("CheckResult")
    private fun getFavoritesJob() {
        //请求搜藏
        val requestAddress = RetrofitUtils(this, this.getString(R.string.jobUrl))
        requestAddress.create(JobApi::class.java)
            .getFavorites(
                1, 1000000, FavoriteType.Key.ORGANIZATION_POSITION.toString()
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("搜藏请求成功")
                println(it)
                val responseStr = org.json.JSONObject(it.toString())
                val soucangData = responseStr.getJSONArray("data")
                val collectionList: MutableList<String> = mutableListOf()
                for (i in 0 until soucangData.length()) {
                    val item = soucangData.getJSONObject(i)
                    val targetEntityId = item.getString("targetEntityId")
                    collectionList.add(targetEntityId)

                }
                myRecruitInfoListFragment.reuqestRecruitInfoData(collectionList)

            }, {
                //失败
                println("搜藏请求失败")
                println(it)
            })

    }
}
