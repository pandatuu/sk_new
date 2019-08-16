package com.example.sk_android.mvp.view.activity.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import cn.jiguang.imui.chatinput.emoji.EmoticonsKeyboardUtils
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.common.ExitActivity
import com.example.sk_android.mvp.view.activity.person.PersonSetActivity
import com.example.sk_android.mvp.view.fragment.register.LoginMainBodyFragment
import com.example.sk_android.mvp.view.fragment.register.LoginThemeActionBarFragment
import com.jaeger.library.StatusBarUtil
import com.sahooz.library.Country
import com.sahooz.library.PickActivity
import com.umeng.message.PushAgent
import com.yatoooon.screenadaptation.ScreenAdapterTools.getInstance
import org.jetbrains.anko.*

class LoginActivity : AppCompatActivity(),LoginMainBodyFragment.logMid{
    private lateinit var themeActionBarFragment:LoginThemeActionBarFragment

    var loginMainBodyFragment:LoginMainBodyFragment?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var condition = intent.getIntExtra("condition",0)
        View.inflate(this, R.layout.radion, null)
        val mainScreenId=1
        PushAgent.getInstance(this).onAppStart()

        frameLayout {

            setOnClickListener(object:View.OnClickListener{
                override fun onClick(v: View?) {
                    if(loginMainBodyFragment!=null){
                        EmoticonsKeyboardUtils.closeSoftKeyboard(loginMainBodyFragment!!.account)
                        EmoticonsKeyboardUtils.closeSoftKeyboard(loginMainBodyFragment!!.password)
                    }
                }

            })




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
                        imageResource = R.mipmap.icon_launcher
                    }.lparams(width = dip(123), height = dip(94))
                }.lparams(width = matchParent, height = dip(164))

                val recycleViewParentId=3
                frameLayout {

                    id=recycleViewParentId
                    loginMainBodyFragment= LoginMainBodyFragment.newInstance(condition)
                    supportFragmentManager.beginTransaction().replace(id, loginMainBodyFragment!!).commit()
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
        StatusBarUtil.setTranslucentForImageView(this@LoginActivity, 0, themeActionBarFragment.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        themeActionBarFragment.toolbar1!!.setNavigationOnClickListener {
              System.exit(0)
        }
    }

    //得到国家区号,跳转区号页面
    override fun getCountryCode() {
        startActivityForResult(Intent(applicationContext, PickActivity::class.java), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 111 && resultCode === Activity.RESULT_OK) {
            val country = Country.fromJson(data!!.getStringExtra("country"))
            var countryCode =  "+" + country.code
            loginMainBodyFragment!!.setCountryCode(countryCode)
        }
    }


}