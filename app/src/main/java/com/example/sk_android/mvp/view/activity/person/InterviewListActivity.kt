package com.example.sk_android.mvp.view.activity.person

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity;
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.common.ActionBarThemeFragment
import com.example.sk_android.mvp.view.fragment.person.InterviewListSelectShowFragment
import org.jetbrains.anko.*
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent

class InterviewListActivity : AppCompatActivity(), InterviewListSelectShowFragment.ViewPageSelectBar {


    //得到被选择的页面的title
    override fun getSelectedPageName(s: String) {
//        actionBar.setPageTitle(s)
    }

    val RESERVE = 1
    val WAIT = 2
    val FINISH = 3
    val CANCELED = 3


    lateinit var actionBar: ActionBarThemeFragment
    lateinit var listShow: InterviewListSelectShowFragment

    var type = 1

    override fun onStart() {
        super.onStart()
        setActionBar(actionBar.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@InterviewListActivity, 0, actionBar.toolbar1)


        actionBar.toolbar1!!.setNavigationOnClickListener {
            startActivity<PersonSetActivity>()
            finish()//返回
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }


    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        frameLayout {
            backgroundColor = Color.WHITE
            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {
                    id = actionBarId

                    var titleShow = "面接ステータス"

                    actionBar = ActionBarThemeFragment.newInstance(titleShow);
                    supportFragmentManager.beginTransaction().replace(id, actionBar).commit()


                }.lparams {
                    height = wrapContent
                    width = matchParent
                }


                var mainBodyId = 6
                frameLayout {
                    id = mainBodyId


                    listShow = InterviewListSelectShowFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, listShow!!).commit()


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
}
