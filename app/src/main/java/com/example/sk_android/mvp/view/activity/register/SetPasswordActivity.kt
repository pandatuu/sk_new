package com.example.sk_android.mvp.view.activity.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.register.SpActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.SpMainBodyFragment
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

class SetPasswordActivity:AppCompatActivity() {
    lateinit var spActionBarFragment: SpActionBarFragment
    var phone:String = ""
    var code:String = ""

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        phone = intent.getStringExtra("phone")
        code = intent.getStringExtra("code")

        var mainScreenId=1
        frameLayout {
            backgroundColorResource = R.color.splitLineColor
            id = mainScreenId

            verticalLayout {
                //ActionBar
                var actionBarId=2
                frameLayout{
                    id=actionBarId
                    spActionBarFragment = SpActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,spActionBarFragment).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                frameLayout {
                    id = 3
                    var spMainBodyFragment= SpMainBodyFragment.newInstance(phone,code)
                    supportFragmentManager.beginTransaction().replace(id,spMainBodyFragment!!).commit()
                }.lparams() {
                    height= wrapContent
                    width= matchParent
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                    topMargin = dip(41)
                }


            }.lparams(){
                width = matchParent
                height = matchParent
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setActionBar(spActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(this@SetPasswordActivity, 0, spActionBarFragment.TrpToolbar)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}