package com.example.sk_android.mvp.view.activity.message

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.message.*
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import org.jetbrains.anko.*
import android.content.Intent
import org.jetbrains.anko.sdk25.coroutines.onClick





class MessageChatWithoutLoginActivity : AppCompatActivity()
{



    lateinit var mainContainer:FrameLayout
    lateinit var centerScreen:FrameLayout
    lateinit var actionBar:FrameLayout


    var messageChatWithoutLoginActionBarFragment: MessageChatWithoutLoginActionBarFragment?=null



    override fun onStart() {
        super.onStart()
        setActionBar(messageChatWithoutLoginActionBarFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@MessageChatWithoutLoginActivity, 0, messageChatWithoutLoginActionBarFragment!!.toolbar1)
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
                actionBar=frameLayout {
                    id = actionBarId
                    messageChatWithoutLoginActionBarFragment = MessageChatWithoutLoginActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, messageChatWithoutLoginActionBarFragment!!).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }


                var centerScreenId = 3
                centerScreen=frameLayout {
                    id = centerScreenId
                    var messageChatWithoutLoginFragment = MessageChatWithoutLoginFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, messageChatWithoutLoginFragment!!).commit()
                    setOnClickListener(object :View.OnClickListener{

                        override fun onClick(v: View?) {
                            toast("xxx")
                            val intent = Intent(this@MessageChatWithoutLoginActivity, MessageChatRecordActivity::class.java)
                            startActivity(intent)
                        }

                    })
                }.lparams {
                    height = 0
                    weight=1f
                    width = matchParent
                }

            }.lparams() {
            width = matchParent
            height = matchParent
        }

        }

    }
}
