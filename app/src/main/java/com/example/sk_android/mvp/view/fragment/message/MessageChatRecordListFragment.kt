package com.example.sk_android.mvp.view.fragment.message

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.os.Handler
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
import org.jetbrains.anko.support.v4.toast

class MessageChatRecordListFragment : Fragment(), ChatRecord {

    var UPDATE_LIST=1

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == UPDATE_LIST) {
                var array:JSONArray=(((json["content"]as JSONObject)["data"] as JSONObject)["status"] as JSONArray)
                chatRecordList = mutableListOf()
                for(i  in array){
                   var item =(i as JSONObject)
                   var  ChatRecordModel=ChatRecordModel(
                           item["name"].toString(),
                           ((item["lastMsg"] as JSONObject)["content"] as JSONObject)["msg"].toString(),
                           ((item["unreads"] as MutableList<String>).size.toString()))
                    chatRecordList.add(ChatRecordModel)
                }
                setAdapter(recycler,chatRecordList)
            }
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
        json=JSON.parseObject(s)
        val message = Message()
        message.what = UPDATE_LIST
        handler.sendMessage(message)
    }

}

