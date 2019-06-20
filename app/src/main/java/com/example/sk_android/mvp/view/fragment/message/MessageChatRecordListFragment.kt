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


    private var mContext: Context? = null
    lateinit var recycler : RecyclerView
    lateinit var adapter: MessageChatRecordListAdapter
    lateinit var thisGroupArray:JSONArray

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
            intent.putExtra("companyName",item.companyName)
            intent.putExtra("hisName",item.userName)


            for(i in 1..thisGroupArray.size){
                var array=thisGroupArray.get(i-1) as JSONArray
                for(j in array){
                    var json=j as JSONObject

                    //传递组别,初始化组别分类的显示
                    if(json.get("uid")==item.uid){
                        intent.putExtra("groupId",i-1)
                    }
                }
            }

            startActivity(intent)
        }
        recycler.adapter = adapter

        return view
    }



    fun setRecyclerAdapter(chatRecordList: MutableList<ChatRecordModel>,groupArray:JSONArray){
        adapter.setChatRecords(chatRecordList)
        thisGroupArray=groupArray
    }



}

