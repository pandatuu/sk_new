package com.example.sk_android.mvp.view.activity.register

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.register.MrActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.MrMainBodyFragment
import com.jaeger.library.StatusBarUtil
import com.sahooz.library.Country
import com.sahooz.library.PickActivity
import com.umeng.message.PushAgent
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MemberRegistActivity: AppCompatActivity(),MrMainBodyFragment.mrMid {
    private lateinit var mrActionBarFragment:MrActionBarFragment
    private lateinit var mrMainBodyFragment:MrMainBodyFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        val mainScreenId=1
        frameLayout {
            backgroundResource= R.mipmap.login_background
            id=mainScreenId
            verticalLayout {
                //ActionBar
                val actionBarId=2
                frameLayout{

                    id=actionBarId
                    mrActionBarFragment= MrActionBarFragment.newInstance()
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


                val recycleViewParentId=3
                frameLayout {

                    id=recycleViewParentId
                    mrMainBodyFragment= MrMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id,mrMainBodyFragment).commit()
                }.lparams {
                    height= matchParent
                    width= matchParent
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                    bottomMargin = dip(0)
                }
                onClick {
                    mrMainBodyFragment.closeKeyfocus()
                }
            }.lparams {
                width = matchParent
                height = matchParent
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        setActionBar(mrActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@MemberRegistActivity, 0, mrActionBarFragment.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        mrActionBarFragment.toolbar1!!.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }

    override fun getCountryCode() {
        startActivityForResult(Intent(applicationContext, PickActivity::class.java), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 111 && resultCode === Activity.RESULT_OK) {
            val country = Country.fromJson(data!!.getStringExtra("country"))
            var countryCode =  "+" + country.code
            mrMainBodyFragment!!.setMrCountryCode(countryCode)
        }
    }
}