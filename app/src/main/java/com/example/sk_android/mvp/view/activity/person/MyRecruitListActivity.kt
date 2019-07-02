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
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.listener.message.ChatRecord
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.message.ChatRecordModel
import com.example.sk_android.mvp.view.fragment.common.ActionBarThemeFragment
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
    lateinit var listShow: InterviewListSelectShowFragment
    var application: App? = null
    lateinit var socket: Socket
    var titleShow = ""

    lateinit var json: JSONObject
    var map: MutableMap<String, Int> = mutableMapOf()
    var groupId = 0
    var isFirstGotGroup: Boolean = true
    var groupArray: JSONArray = JSONArray()
    var chatRecordList: MutableList<ChatRecordModel> = mutableListOf()
    lateinit var messageChatRecordListFragment: MessageChatRecordListFragment
    lateinit var text1: TextView


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
                    var id = item.getString("id")
                    var name = item.getString("name")
                    map.put(name, id.toInt())
                    if (id == groupId.toString()) {
                        members = item.getJSONArray("members")
                    }
                    if (isFirstGotGroup) {

                        if (id == "4") {
                            var group1 = item.getJSONArray("members")
                            groupArray.add(group1)
                        }
                        if (id == "5") {
                            var group2 = item.getJSONArray("members")
                            groupArray.add(group2)
                        }
                        if (id == "6") {
                            var group3 = item.getJSONArray("members")
                            groupArray.add(group3)
                        }


                    }
                }
                isFirstGotGroup = false
                chatRecordList = mutableListOf()
                for (i in members) {
                    var item = (i as JSONObject)
                    println(item)
                    //未读条数
                    var unreads = item.getIntValue("unreads").toString()
                    //对方名
                    var name = item["name"].toString()
                    //最后一条消息
                    var lastMsg = (item.getJSONObject("lastMsg"))
                    var msg = ""
                    //对方ID
                    var uid = item["uid"].toString()
                    //对方职位
                    var position = item["position"].toString()
                    //对方头像
                    var avatar = item["avatar"].toString()
                    //公司
                    var companyName = item["companyName"].toString()
                    // 显示的职位的id
                    var lastPositionId = item.getString("lastPositionId")


                    if (lastMsg == null) {
                    } else {
                        var content = lastMsg.getJSONObject("content")
                        var contentType = content.getString("type")
                        if (contentType.equals("image")) {
                            msg = "[图片]"
                        } else if (contentType.equals("voice")) {
                            msg = "[语音]"
                        } else {
                            msg = content.getString("msg")
                        }
                    }
                    var ChatRecordModel = ChatRecordModel(
                        uid,
                        name,
                        position,
                        avatar,
                        msg,
                        unreads,
                        companyName,
                        lastPositionId
                    )
                    chatRecordList.add(ChatRecordModel)
                }

            }
            println(chatRecordList)
            messageChatRecordListFragment.setRecyclerAdapter(chatRecordList, groupArray)
            setPositionList(chatRecordList)
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

        if (type == COMMUNICATED) {
            isFirstGotGroup = true
            groupArray.clear()
            Handler().postDelayed({
                socket.emit("queryContactList",application!!.getMyToken(),
                    object: Ack {
                        override fun call(name: String?, error: Any?, data: Any?) {
                            println("Got message for :" + name + " error is :" + error + " data is :" + data)
                        }

                    })
            }, 200)
        } else if (type == COLLECTED) {
            titleShow = "私のお気に入りのポジション"
            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                getFavoritesJob()
            }
        } else if (type == SENT) {
            titleShow = "履歴書を郵送した役職です"
            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                getexchangesinfo()
            }
        }

    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        var intent = intent
        type = intent.getIntExtra("type", 0)

        frameLayout {
            backgroundColor = Color.WHITE
            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {
                    id = actionBarId

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

                    } else if (type == COLLECTED) {
                        titleShow = "私のお気に入りのポジション"
                    } else if (type == SENT) {
                        titleShow = "履歴書を郵送した役職です"
                    }

                    actionBar = ActionBarThemeFragment.newInstance(titleShow)
                    supportFragmentManager.beginTransaction().replace(id, actionBar).commit()


                }.lparams {
                    height = wrapContent
                    width = matchParent
                }


                var mainBodyId = 6
                frameLayout {
                    id = mainBodyId

                    if (type == COMMUNICATED) {
                        messageChatRecordListFragment = MessageChatRecordListFragment.newInstance();
                        supportFragmentManager.beginTransaction().add(id, messageChatRecordListFragment).commit()

                    } else if (type == COLLECTED) {
                        titleShow = "私のお気に入りのポジション"


                    } else if (type == SENT) {
                        titleShow = "履歴書を郵送した役職です"


                    }

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

    }


    private fun setPositionList(chatRecordList: MutableList<ChatRecordModel>) {
        val list = mutableListOf<JsonObject?>()
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            for (item in chatRecordList) {
                if (item.lastPositionId != "") list.add(getPositionById(item.lastPositionId))
            }
        }

        println("111111111")
    }

    // 通过职位ID获取职位
    private suspend fun getPositionById(id: String): JsonObject? {
        try {

            val retrofitUils = RetrofitUtils(this@MyRecruitListActivity, "https://organization-position.sk.cgland.top/")
            val it = retrofitUils.create(PersonApi::class.java)
                .getPositionById(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() == 200) {
                return it.body()!!.get("organization").asJsonObject
            }
            return null
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
            return null
        }
    }

    //通过type获取交换信息
    private suspend fun getexchangesinfo(){
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

    //获取收藏职位记录
    private suspend fun getFavoritesJob(){
        try {

            val retrofitUils = RetrofitUtils(this@MyRecruitListActivity, "https://job.sk.cgland.top/")
            val it = retrofitUils.create(PersonApi::class.java)
                .getFavoritesJob("ORGANIZATION_POSITION")
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() == 200) {
                var list = mutableListOf<JsonObject?>()
                val page = Gson().fromJson(it.body()!!,PagedList::class.java)
                for (item in page.data){
                    list.add(getPositionById(item.get("targetEntityId").asString))
                }
                println(list.size)
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }
}
