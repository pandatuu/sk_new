package com.example.sk_android.mvp.view.activity.message

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.jobSelect.RecruitInfoBottomMenuFragment
import com.example.sk_android.mvp.view.fragment.message.MessageChatRecordActionBarFragment
import com.example.sk_android.mvp.view.fragment.message.MessageChatRecordListFragment
import com.example.sk_android.mvp.view.fragment.message.MessageChatRecordSelectMenuFragment
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

class MessageChatRecordActivity : AppCompatActivity(), MessageChatRecordActionBarFragment.ActionBarSearch,
    RecruitInfoBottomMenuFragment.RecruitInfoBottomMenu,
    MessageChatRecordSelectMenuFragment.MenuSelect
{
    override fun getSelectedMenu() {

    }

    override fun getMenuSelect(index: Int) {

    }

    override fun searchGotClick() {

    }



    lateinit var mainContainer:FrameLayout
    lateinit var messageChatRecordActionBarFragment:MessageChatRecordActionBarFragment
    lateinit var messageChatRecordSelectMenuFragment:MessageChatRecordSelectMenuFragment
    lateinit var messageChatRecordListFragment:MessageChatRecordListFragment


    override fun onStart() {
        super.onStart()
        setActionBar(messageChatRecordActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@MessageChatRecordActivity, 0, messageChatRecordActionBarFragment.toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var mainContainerId=1
        mainContainer=frameLayout {
            id=mainContainerId
            backgroundColorResource= R.color.white
            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {
                    id = actionBarId
                    messageChatRecordActionBarFragment = MessageChatRecordActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, messageChatRecordActionBarFragment).commit()


                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                //select menu
                var selectMenurId = 3
                frameLayout {
                    id = selectMenurId
                    messageChatRecordSelectMenuFragment = MessageChatRecordSelectMenuFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, messageChatRecordSelectMenuFragment).commit()


                }.lparams {
                    height = wrapContent
                    width = matchParent
                }


                //list
                var listId = 4
                frameLayout {
                    id = listId
                    messageChatRecordListFragment = MessageChatRecordListFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, messageChatRecordListFragment).commit()


                }.lparams {
                    height = 0
                    weight=1f
                    width = matchParent
                }


                // bottom menu
                var bottomMenuId=5
                frameLayout {
                    id=bottomMenuId
                    var recruitInfoBottomMenuFragment= RecruitInfoBottomMenuFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,recruitInfoBottomMenuFragment!!).commit()
                }.lparams {
                    height=wrapContent
                    width= matchParent
                }








            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }

    }
}
