package com.example.sk_android.mvp.view.activity.message

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.jobSelect.RecruitInfoBottomMenuFragment
import com.example.sk_android.mvp.view.fragment.message.MessageChatRecordActionBarFragment
import com.example.sk_android.mvp.view.fragment.message.MessageChatRecordListFragment
import com.example.sk_android.mvp.view.fragment.message.MessageChatRecordSearchActionBarFragment
import com.example.sk_android.mvp.view.fragment.message.MessageChatRecordSelectMenuFragment
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

class MessageChatRecordActivity : AppCompatActivity(), MessageChatRecordActionBarFragment.ActionBarSearch,
    RecruitInfoBottomMenuFragment.RecruitInfoBottomMenu,
    MessageChatRecordSelectMenuFragment.MenuSelect, MessageChatRecordSearchActionBarFragment.SendSearcherText
{
    override fun cancle() {

    }

    override fun sendMessage(msg: String) {

    }

    override fun getSelectedMenu() {

    }

    override fun getMenuSelect(index: Int) {

    }

    override fun searchGotClick() {
        var mTransaction=supportFragmentManager.beginTransaction()


        messageChatRecordSearchActionBarFragment = MessageChatRecordSearchActionBarFragment.newInstance();
        mTransaction.replace(actionBar.id, messageChatRecordSearchActionBarFragment!!)


        mTransaction.commit()

    }



    lateinit var mainContainer:FrameLayout
    lateinit var searcher:FrameLayout
    lateinit var actionBar:FrameLayout

    var messageChatRecordActionBarFragment:MessageChatRecordActionBarFragment?=null
    lateinit var messageChatRecordSelectMenuFragment:MessageChatRecordSelectMenuFragment
    lateinit var messageChatRecordListFragment:MessageChatRecordListFragment

    var messageChatRecordSearchActionBarFragment: MessageChatRecordSearchActionBarFragment?=null

    override fun onStart() {
        super.onStart()
        setActionBar(messageChatRecordActionBarFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@MessageChatRecordActivity, 0, messageChatRecordActionBarFragment!!.toolbar1)
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
                actionBar=frameLayout {
                    id = actionBarId
                    messageChatRecordActionBarFragment = MessageChatRecordActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, messageChatRecordActionBarFragment!!).commit()


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


                //searcher
                var searcherId = 4
                searcher=frameLayout {
                    id = searcherId

                    backgroundResource=R.color.originColor
                    textView {

                    }.lparams {
                        height=dip(8)
                        width= matchParent
                    }


                }.lparams {
                    height = wrapContent
                    width = matchParent
                }


                //list
                var listId = 5
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
                var bottomMenuId=6
                frameLayout {
                    id=bottomMenuId
                    var recruitInfoBottomMenuFragment= RecruitInfoBottomMenuFragment.newInstance(2);
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
