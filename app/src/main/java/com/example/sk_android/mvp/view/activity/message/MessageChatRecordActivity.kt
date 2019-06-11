package com.example.sk_android.mvp.view.activity.message

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import android.widget.*
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.example.sk_android.R
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.listener.message.ChatRecord
import com.example.sk_android.mvp.model.message.ChatRecordModel
import com.example.sk_android.mvp.view.activity.common.BaseActivity
import com.example.sk_android.mvp.view.fragment.jobselect.RecruitInfoBottomMenuFragment
import com.example.sk_android.mvp.view.fragment.message.*
import com.jaeger.library.StatusBarUtil
import com.umeng.commonsdk.UMConfigure
import org.jetbrains.anko.*
import com.umeng.message.PushAgent
import com.umeng.message.IUmengRegisterCallback
import io.github.sac.Socket
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.toast


class MessageChatRecordActivity : BaseActivity(), MessageChatRecordActionBarFragment.ActionBarSearch,
        RecruitInfoBottomMenuFragment.RecruitInfoBottomMenu,
        MessageChatRecordSelectMenuFragment.MenuSelect, MessageChatRecordSearchActionBarFragment.SendSearcherText,
        MessageChatRecordFilterMenuFragment.FilterMenu {


    //筛选菜单
    override fun getFilterMenuselect(index: Int) {
        toast(index.toString())
        groupId = index
        val message = Message()
        Listhandler.sendMessage(message)
    }

    //取消 搜索框
    override fun cancle() {
        var mTransaction = supportFragmentManager.beginTransaction()
        messageChatRecordActionBarFragment = MessageChatRecordActionBarFragment.newInstance();
        mTransaction.replace(actionBar.id, messageChatRecordActionBarFragment!!)
        mTransaction.commit()
        afterSearchList()
    }

    //搜索框输入的文字
    override fun sendMessage(msg: String) {
        toast(msg)
        var mTransaction = supportFragmentManager.beginTransaction()
        messageChatRecordListFragment = MessageChatRecordListFragment.newInstance()
        mTransaction.replace(recordList.id, messageChatRecordListFragment)
        mTransaction.commit()
    }

    override fun getSelectedMenu() {

    }

    //顶部菜单(チャット履歴/連絡先)
    override fun getMenuSelect(index: Int) {
        toast(index.toString())
        var mTransaction = supportFragmentManager.beginTransaction()
        if (index == 1) {
            if (messageChatRecordFilterMenuFragment == null) {
                messageChatRecordFilterMenuFragment = MessageChatRecordFilterMenuFragment.newInstance(map);
                mTransaction.add(middleMenu.id, messageChatRecordFilterMenuFragment!!)
            }

        } else {
            if (messageChatRecordFilterMenuFragment != null) {
                mTransaction.remove(messageChatRecordFilterMenuFragment!!)
                messageChatRecordFilterMenuFragment = null
            }
            if(0!=groupId){
                groupId = 0
                val message = Message()
                Listhandler.sendMessage(message)
            }

        }
        mTransaction.commit()

    }

    //打开搜索框
    override fun searchGotClick() {
        var mTransaction = supportFragmentManager.beginTransaction()
        messageChatRecordSearchActionBarFragment = MessageChatRecordSearchActionBarFragment.newInstance();
        mTransaction.replace(actionBar.id, messageChatRecordSearchActionBarFragment!!)
        mTransaction.commit()
        whenSearchList()
    }


    lateinit var mainContainer: FrameLayout
    lateinit var middleMenu: FrameLayout
    lateinit var actionBar: FrameLayout
    lateinit var recordList: FrameLayout
    lateinit var selectMenu: FrameLayout
    lateinit var bottomMenu: FrameLayout

    var messageChatRecordActionBarFragment: MessageChatRecordActionBarFragment? = null
    var messageChatRecordSelectMenuFragment: MessageChatRecordSelectMenuFragment? = null
    lateinit var messageChatRecordListFragment: MessageChatRecordListFragment
    var messageChatRecordFilterMenuFragment: MessageChatRecordFilterMenuFragment? = null

    var messageChatRecordSearchActionBarFragment: MessageChatRecordSearchActionBarFragment? = null


    lateinit var json: JSONObject
    var chatRecordList: MutableList<ChatRecordModel> = mutableListOf()
    var map: MutableMap<String, Int> = mutableMapOf()
    var groupId = 0;
    var isFirst: Boolean = true

    private val Listhandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            println("+++++++++++++++++++++++")
            println(json)
            println("+++++++++++++++++++++++")
            var type = json.getString("type")
            if (type != null && type.equals("contactList")) {
                var array: JSONArray = json.getJSONObject("content").getJSONObject("data").getJSONArray("groups")

                var members: JSONArray = JSONArray()
                for (i in array) {
                    var item = (i as JSONObject)
                    var id = item.getString("id")
                    var name = item.getString("name")
                    map.put(name, id.toInt())
                    if (id == groupId.toString()) {
                        members = item.getJSONArray("members")
                    }
                }

                chatRecordList = mutableListOf()
                for (i in members) {
                    var item = (i as JSONObject)
                    println(item)
                    //未读条数
                    var unreads = (item.getJSONArray("unreads")).size.toString()
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

                    if (lastMsg == null) {
                    } else {
                        var content = lastMsg.getJSONObject("content")
                        var contentType = content.getString("type")
                        if (contentType.equals("text")) {
                            msg = content.getString("msg")
                        } else if (contentType.equals("image")) {
                            msg = "[图片]"
                        }
                    }
                    var ChatRecordModel = ChatRecordModel(
                            uid,
                            name,
                            position,
                            avatar,
                            msg,
                            unreads)
                    chatRecordList.add(ChatRecordModel)
                }

            }
            messageChatRecordListFragment.setRecyclerAdapter(chatRecordList)

        }
    }

    var application: App? = null
    lateinit var socket: Socket


    override fun onDestroy() {
        super.onDestroy()
        toast("")
    }

    override fun onStart() {
        super.onStart()
        setActionBar(messageChatRecordActionBarFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@MessageChatRecordActivity, 0, messageChatRecordActionBarFragment!!.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        initRequest()


        Handler().postDelayed({
            toast("xxxxxxxxxxxxxxxxxxx");
            socket.emit("queryContactList", application!!.getToken())
        }, 200)


    }

//    override fun  onResume(){
//        super.onResume()
//        initRequest()
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mainContainerId = 1

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

        mainContainer = frameLayout {
            id = mainContainerId
            backgroundColorResource = R.color.white
            verticalLayout {
                //ActionBar
                var actionBarId = 2
                actionBar = frameLayout {
                    id = actionBarId
                    messageChatRecordActionBarFragment = MessageChatRecordActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, messageChatRecordActionBarFragment!!).commit()


                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                //select menu
                var selectMenurId = 3
                selectMenu = frameLayout {
                    id = selectMenurId
                    messageChatRecordSelectMenuFragment = MessageChatRecordSelectMenuFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, messageChatRecordSelectMenuFragment!!).commit()


                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                //middle
                var middleMenuId = 4
                middleMenu = frameLayout {
                    id = middleMenuId
                    backgroundResource = R.color.originColor
                    textView {

                    }.lparams {
                        height = dip(8)
                        width = matchParent
                    }


                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                //list
                var listId = 5
                recordList = frameLayout {
                    id = listId
                    messageChatRecordListFragment = MessageChatRecordListFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, messageChatRecordListFragment).commit()
                }.lparams {
                    height = 0
                    weight = 1f
                    width = matchParent
                }
                // bottom menu
                var bottomMenuId = 6
                bottomMenu = frameLayout {
                    id = bottomMenuId
                    var recruitInfoBottomMenuFragment = RecruitInfoBottomMenuFragment.newInstance(2);
                    supportFragmentManager.beginTransaction().replace(id, recruitInfoBottomMenuFragment!!).commit()
                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }
    }


    fun whenSearchList() {
        selectMenu.visibility = View.GONE
        middleMenu.visibility = View.GONE
        bottomMenu.visibility = View.GONE
    }

    fun afterSearchList() {
        selectMenu.visibility = View.VISIBLE
        middleMenu.visibility = View.VISIBLE
        bottomMenu.visibility = View.VISIBLE
    }


    fun initRequest() {
        //发送消息请求,获取联系人列表
//        app!!.setChatRecord(messageChatRecordListFragment)
//        app!!.sendRequest("queryContactList")

//        app!!setChatRecord(messageChatRecordListFragment)
    }

}
