package com.example.sk_android.mvp.view.activity.person

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.example.sk_android.R
import com.example.sk_android.mvp.api.jobselect.JobApi
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.listener.message.ChatRecord
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.jobselect.FavoriteType
import com.example.sk_android.mvp.model.message.ChatRecordModel
import com.example.sk_android.mvp.view.fragment.common.ActionBarThemeFragment
import com.example.sk_android.mvp.view.fragment.jobselect.MyRecruitInfoListFragment
import com.example.sk_android.mvp.view.fragment.message.MessageChatRecordListFragment
import com.example.sk_android.mvp.view.fragment.person.InterviewListSelectShowFragment
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import io.github.sac.Ack
import io.github.sac.Socket
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.*
import retrofit2.HttpException

class MyRecruitListActivity : AppCompatActivity() {


    val COMMUNICATED = 1
    val COLLECTED = 2
    val SENT = 3


    lateinit var actionBar: ActionBarThemeFragment

    lateinit var myRecruitInfoListFragment: MyRecruitInfoListFragment

    var application: App? = null
    lateinit var socket: Socket
    var titleShow = ""

    lateinit var json: JSONObject


    //已经沟通的职位  入口-1
    //通过联系人列表查询出positionId的list
    private val Listhandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            println("+++++++++++++++++++++++")
            println(json)
            println("+++++++++++++++++++++++")
            var type = json.getString("type")
            if (type != null && type.equals("contactList")) {
                var array: JSONArray = json.getJSONObject("content").getJSONArray("groups")

                var members: JSONArray = JSONArray()
                for (i in array) {
                    var item = (i as JSONObject)
                    var id = item.getIntValue("id")
                    var name = item.getString("name")
                    if (id == 0) {
                        members = item.getJSONArray("members")
                    }

                }

                println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")

                println(members)

                var positionList = mutableListOf<String>()

                for (i in members) {
                    var item = (i as JSONObject)
                    println(item)
                    // 显示的职位的id
                    var lastPositionId = item.getString("lastPositionId")
                    if(lastPositionId!=null && !"".equals(lastPositionId)){
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
            overridePendingTransition(R.anim.right_out, R.anim.right_out)
        }


    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        var intent = intent
        type = intent.getIntExtra("type", 0)





        if (type == COMMUNICATED) {

            titleShow = "疎通した職"
            //接受
            application = App.getInstance()
            socket = application!!.getSocket()
            //消息回调
            application!!.setChatRecord(object : ChatRecord {
                override fun getContactList(str: String) {
                    json = JSON.parseObject(str)
                    val message = Message()
                    Listhandler.sendMessage(message)
                }
            })

            Handler().postDelayed({
                socket.emit("queryContactList", application!!.getMyToken(),
                    object : Ack {
                        override fun call(name: String?, error: Any?, data: Any?) {
                            println("Got message for :" + name + " error is :" + error + " data is :" + data)
                        }

                    })
            }, 200)
        } else if (type == COLLECTED) {
            titleShow = "私のお気に入りのポジション"
        } else if (type == SENT) {
            titleShow = "履歴書を郵送した役職です"
        }





        frameLayout {
            backgroundColor = Color.WHITE
            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {
                    id = actionBarId



                    actionBar = ActionBarThemeFragment.newInstance(titleShow)
                    supportFragmentManager.beginTransaction().replace(id, actionBar).commit()


                }.lparams {
                    height = wrapContent
                    width = matchParent
                }


                var mainBodyId = 6
                frameLayout {
                    id = mainBodyId
                    myRecruitInfoListFragment = MyRecruitInfoListFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, myRecruitInfoListFragment).commit()
                }.lparams {
                    height = 0
                    weight = 1f
                    width = matchParent
                }


            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }


        if (type == COLLECTED) {
            getFavoritesJob()
        } else if (type == SENT) {

        }



    }


    //通过type获取交换信息
    private suspend fun getexchangesinfo() {
        try {

            val retrofitUils = RetrofitUtils(this@MyRecruitListActivity, "https://interview.sk.cgland.top/")
            val it = retrofitUils.create(PersonApi::class.java)
                .getexchangesinfo("EXCHANGED")
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() == 200) {

            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    //获取收藏职位记录 入口-2
    private fun getFavoritesJob() {
        //请求搜藏
        var requestAddress = RetrofitUtils(this, "https://job.sk.cgland.top/")
        requestAddress.create(JobApi::class.java)
            .getFavorites(
                1, 1000000, FavoriteType.Key.ORGANIZATION_POSITION.toString()
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("搜藏请求成功")
                println(it)
                var responseStr = org.json.JSONObject(it.toString())
                var soucangData = responseStr.getJSONArray("data")
                var collectionList: MutableList<String> = mutableListOf()
                for (i in 0..soucangData.length() - 1) {
                    var item = soucangData.getJSONObject(i)
                    var targetEntityId = item.getString("targetEntityId")
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
