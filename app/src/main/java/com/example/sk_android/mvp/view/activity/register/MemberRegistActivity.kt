package com.example.sk_android.mvp.view.activity.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.register.MrActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.MrMainBodyFragment
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import org.jetbrains.anko.*

class MemberRegistActivity: AppCompatActivity() {
    lateinit var mrActionBarFragment:MrActionBarFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        var mainScreenId=1
        frameLayout {
            backgroundResource= R.mipmap.login_background
            id=mainScreenId
            verticalLayout {
                //ActionBar
                var actionBarId=2
                frameLayout{

                    id=actionBarId
                    mrActionBarFragment= MrActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,mrActionBarFragment).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                linearLayout {
                    gravity = Gravity.CENTER
                    imageView {
                        imageResource = R.mipmap.logo
                    }.lparams(width = dip(123), height = dip(94))
                }.lparams(width = matchParent, height = dip(165))


                var recycleViewParentId=3
                frameLayout {

                    id=recycleViewParentId
                    var mrMainBodyFragment= MrMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id,mrMainBodyFragment!!).commit()
                }.lparams() {
                    height= matchParent
                    width= matchParent
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                    bottomMargin = dip(0)
                }
            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }

    }

    override fun onStart() {
        super.onStart()
        setActionBar(mrActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@MemberRegistActivity, 0, mrActionBarFragment.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}