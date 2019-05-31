package com.example.sk_android.mvp.view.activity.message

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.*
import cn.jiguang.imui.commons.ImageLoader
import cn.jiguang.imui.commons.models.IMessage
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.message.*
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*
import cn.jiguang.imui.messages.MessageList
import cn.jiguang.imui.messages.MsgListAdapter
import com.umeng.message.PushAgent


class MessageChatActivity : AppCompatActivity(), MessageChatRecordActionBarFragment.ActionBarSearch

{
    override fun searchGotClick() {

    }

    lateinit var mainContainer:FrameLayout
    lateinit var actionBar:FrameLayout
    var messageChatRecordActionBarFragment:MessageChatRecordActionBarFragment?=null
    override fun onStart() {
        super.onStart()
        setActionBar(messageChatRecordActionBarFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@MessageChatActivity, 0, messageChatRecordActionBarFragment!!.toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();
        var mainContainerId=1
        mainContainer=frameLayout {
            id=mainContainerId
            backgroundColorResource= R.color.white
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


                 frameLayout {

//                     var view = View.inflate(context, R.layout.chat_list, null)
//                     addView(view)
//                     val messageList = findViewById<View>(R.id.msg_list) as MessageList
//                     val adapter = MsgListAdapter<IMessage>("0",  imageLoader)
//                     messageList.setAdapter(adapter)

                }.lparams {
                    height = 0
                     weight=1f
                    width = matchParent
                }



            }.lparams {
                width= matchParent
                height= matchParent
            }

        }

    }

}
