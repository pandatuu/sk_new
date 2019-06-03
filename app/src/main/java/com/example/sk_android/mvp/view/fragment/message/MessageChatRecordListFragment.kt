package com.example.sk_android.mvp.view.fragment.message

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
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
import io.github.sac.Ack
import org.jetbrains.anko.support.v4.toast
import io.github.sac.Emitter


class MessageChatRecordListFragment : Fragment(), ChatRecord {


    private val Listhandler = object : Handler() {
        override fun handleMessage(msg: Message) {

            println("+++++++++++++++++++++++")

            var array:JSONArray=json.getJSONObject("content").getJSONObject("data").getJSONArray("status")

            println(array)
            println("-------------------------------")
            chatRecordList = mutableListOf()
            for(i  in  array){
                var item =(i as JSONObject)
                println(item)
                var unreads=(item.getJSONArray("unreads")).size.toString()
                var name=item["name"].toString()
                var lastMsg=(item.getJSONObject("lastMsg"))

                if(lastMsg==null){
                    var  ChatRecordModel=ChatRecordModel(
                            name,
                            "",
                            unreads)
                    chatRecordList.add(ChatRecordModel)
                    continue
                }
                var content=lastMsg.getJSONObject("content")
                var msg=content.getString("msg")
                var  ChatRecordModel=ChatRecordModel(
                        name,
                        msg,
                        unreads)
                chatRecordList.add(ChatRecordModel)
                println(ChatRecordModel)

            }
            setAdapter(recycler,chatRecordList)

        }
    }



    var chatRecordList: MutableList<ChatRecordModel> = mutableListOf()
    lateinit var json:JSONObject
    private var mContext: Context? = null
    lateinit var recycler : RecyclerView
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
        println("------------------------------->>>>")
        //接受
        var application:App?=App.getInstance();
        var socket = application!!.getSocket()
        var channelRecieve = socket.createChannel("p_589daa8b-79bd-4cae-bf67-765e6e786a72")

        channelRecieve.subscribe { channelName, error, data ->
            if (error == null) {
                println("Subscribed to channel $channelName successfully")
            }
        }


        channelRecieve.onMessage(object : Emitter.Listener {
            override fun call(channelName: String, obj: Any) {
                println("------------------------------->>>>Got message for channel")
                println(obj.toString())



                json=JSON.parseObject(obj.toString())
                val message = Message()
                Listhandler.sendMessage(message)




            }
        })


        Handler().postDelayed({
            socket.emit("queryContactList", application!!.getToken())
        }, 1000)

    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        return fragmentView
    }

    fun createView(): View {


        return UI {
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
                    setAdapter(recycler,chatRecordList)
                }.lparams {
                    width= matchParent
                    height=matchParent
                }
            }
        }.view
    }


    fun setAdapter(recyclerView:RecyclerView,list :MutableList<ChatRecordModel>){

        recyclerView.setAdapter(MessageChatRecordListAdapter(recyclerView,  list) { item ->

        })
    }

    override  fun queryContactList(s:String){

    }

}

