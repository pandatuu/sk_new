package com.example.sk_android.mvp.view.activity.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.register.LoginMainBodyFragment
import com.example.sk_android.mvp.view.fragment.register.LoginThemeActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.RliMainBodyFragment
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import com.yatoooon.screenadaptation.ScreenAdapterTools.getInstance
import org.jetbrains.anko.*

class RegisterLoginActivity : AppCompatActivity(){

    private lateinit var themeActionBarFragment:LoginThemeActionBarFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        View.inflate(this, R.layout.radion, null)
        val mainScreenId=1
        PushAgent.getInstance(this).onAppStart()

        frameLayout {
            backgroundResource= R.mipmap.login_background
            id=mainScreenId
            verticalLayout {
                //ActionBar
                val actionBarId=2
                frameLayout{

                    id=actionBarId
                    themeActionBarFragment= LoginThemeActionBarFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id,themeActionBarFragment).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                linearLayout {
                    gravity = Gravity.CENTER
                    imageView {
                        imageResource = R.mipmap.logo
                    }.lparams(width = dip(123), height = dip(94))
                }.lparams(width = matchParent, height = dip(164))

                val recycleViewParentId=3
                frameLayout {

                    id=recycleViewParentId
                    val rliMainBodyFragment= RliMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, rliMainBodyFragment).commit()
                }.lparams {
                    height= matchParent
                    width= matchParent
                    bottomMargin = dip(0)
                    leftMargin = dip(10)
                    rightMargin = dip(10)
                }


            }.lparams {
                width = matchParent
                height = matchParent
            }

        }
        getInstance().loadView(window.decorView)


    }

    override fun onStart() {
        super.onStart()
        setActionBar(themeActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@RegisterLoginActivity, 0, themeActionBarFragment.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        themeActionBarFragment.toolbar1!!.setNavigationOnClickListener {
            System.exit(0)
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }



}