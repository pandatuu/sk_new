package com.example.sk_android.mvp.view.activity.register

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.register.SpvActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.SpvMainBodyFragment
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

class SetPasswordVerifyActivity:AppCompatActivity() {
    private lateinit var spvActionBarFragment: SpvActionBarFragment

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val phone = intent.getStringExtra("phone")
        val country = intent.getStringExtra("country")
        val password = intent.getStringExtra("password")
        val mainScreenId=1
        frameLayout {
            backgroundColorResource = R.color.splitLineColor
            id = mainScreenId

            verticalLayout {
                //ActionBar
                val actionBarId=2
                frameLayout{
                    id=actionBarId
                    spvActionBarFragment = SpvActionBarFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id,spvActionBarFragment).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                frameLayout {
                    id = 3
                    val spvMainBodyFragment= SpvMainBodyFragment.newInstance(phone,country,password)
                    supportFragmentManager.beginTransaction().replace(id, spvMainBodyFragment).commit()
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        setActionBar(spvActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(this@SetPasswordVerifyActivity, 0, spvActionBarFragment.TrpToolbar)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        spvActionBarFragment.TrpToolbar!!.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }
}