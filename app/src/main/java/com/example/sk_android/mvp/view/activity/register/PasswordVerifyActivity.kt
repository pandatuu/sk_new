package com.example.sk_android.mvp.view.activity.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.register.PvActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.PvMainBodyFragment
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import org.jetbrains.anko.*

class PasswordVerifyActivity:AppCompatActivity() {
    private lateinit var pvActionBarFragment:PvActionBarFragment

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        var phone = intent.getStringExtra("phone")
        var country = intent.getStringExtra("country")
        val mainScreenId=1
        frameLayout {
            backgroundColorResource = R.color.splitLineColor
            id = mainScreenId

            verticalLayout {
                //ActionBar
                val actionBarId=2
                frameLayout{
                    id=actionBarId
                    pvActionBarFragment = PvActionBarFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id,pvActionBarFragment).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                frameLayout {
                    id = 3
                    val pvMainBodyFragment= PvMainBodyFragment.newInstance(phone,country)
                    supportFragmentManager.beginTransaction().replace(id, pvMainBodyFragment).commit()
                }.lparams {
                    height= wrapContent
                    width= matchParent
                    leftMargin = dip(10)
                    rightMargin = dip(10)
                    topMargin = dip(60)
                }
            }.lparams {
                width = matchParent
                height = matchParent
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setActionBar(pvActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(this@PasswordVerifyActivity, 0, pvActionBarFragment.TrpToolbar)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}