package com.example.sk_android.mvp.view.fragment.message

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.example.sk_android.R
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.listener.message.ChatRecord
import com.example.sk_android.mvp.model.jobselect.Job
import com.example.sk_android.mvp.model.message.ChatRecordModel
import com.example.sk_android.mvp.view.adapter.message.MessageChatRecordListAdapter
import imui.jiguang.cn.imuisample.messages.MessageListActivity
import io.github.sac.Ack
import org.jetbrains.anko.support.v4.toast
import io.github.sac.Emitter
import io.github.sac.Socket
import kotlinx.android.synthetic.main.activity_recycler.*


class MessageChatRecordListFragment : Fragment(){


    private val Listhandler = object : Handler() {
        override fun handleMessage(msg: Message) {

            println("+++++++++++++++++++++++")
            println(json)
            println("+++++++++++++++++++++++")
            var type=json.getString("type")
            if(type!=null && type.equals("contactList")){
                var array:JSONArray=json.getJSONObject("content").getJSONObject("data").getJSONArray("groups")

                var members:JSONArray=JSONArray()
                for(i  in  array){
                    var item =(i as JSONObject)
                    var id =item.getString("id")
                    if(id=="0"){
                        members=item.getJSONArray("members")
                    }
                }

                chatRecordList = mutableListOf()
                for(i  in  members){
                    var item =(i as JSONObject)
                    println(item)
                    //未读条数
                    var unreads=(item.getJSONArray("unreads")).size.toString()
                    //对方名
                    var name=item["name"].toString()
                    //最后一条消息
                    var lastMsg=(item.getJSONObject("lastMsg"))
                    var msg=""
                    //对方ID
                    var uid=item["uid"].toString()
                    //对方职位
                    var position=item["position"].toString()
                    //对方头像
                    var avatar=item["avatar"].toString()

                    if(lastMsg==null){
                    }else{
                        var content=lastMsg.getJSONObject("content")
                        var contentType=content.getString("type")
                        if(contentType.equals("text")){
                            msg=content.getString("msg")
                        }else if(contentType.equals("image")){
                            msg="[图片]"
                        }
                    }
                    var  ChatRecordModel=ChatRecordModel(
                            uid,
                            name,
                            position,
                            avatar,
                            msg,
                            unreads)
                    chatRecordList.add(ChatRecordModel)
                }
                adapter.setChatRecords(chatRecordList)
            }
        }
    }

    var application:App? = null
    lateinit var socket: Socket
    var chatRecordList: MutableList<ChatRecordModel> = mutableListOf()
    lateinit var json:JSONObject
    private var mContext: Context? = null
    lateinit var recycler : RecyclerView
    lateinit var adapter: MessageChatRecordListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): MessageChatRecordListFragment {
            val fragment = MessageChatRecordListFragment()
            return fragment
        }
    }

    override fun onStart(){
        super.onStart()

        Handler().postDelayed({
            socket.emit("queryContactList", application!!.getToken())
        }, 200)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        //接受
        application=App.getInstance()
        socket= application!!.getSocket()

        //消息回调
        application!!.setChatRecord(object :ChatRecord{
            override fun getContactList(str: String) {
                json=JSON.parseObject(str)
                val message = Message()
                Listhandler.sendMessage(message)
            }
        })
        return fragmentView
    }

    fun createView(): View {
        val view = UI {
            linearLayout {
                backgroundResource= R.color.white
                linearLayout {

                    recycler= recyclerView{
                        overScrollMode = View.OVER_SCROLL_NEVER
                        var manager=LinearLayoutManager(this.getContext())
                        setLayoutManager(manager)
                        //manager.setStackFromEnd(true);

                    }.lparams {
                        leftMargin=dip(14)
                        rightMargin=dip(14)
                    }
                }.lparams {
                    width= matchParent
                    height=matchParent
                }
            }
        }.view

        adapter = MessageChatRecordListAdapter(recycler, mutableListOf()) { item ->
            var intent =Intent(mContext, MessageListActivity::class.java)
            intent.putExtra("hisId",item.uid)
            startActivity(intent)
        }
        recycler.adapter = adapter

        return view
    }


}

