package com.example.sk_android.mvp.view.activity.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.register.TrpActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.TrpMainBodyFragment
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

class TelephoneResetPasswordActivity : AppCompatActivity() {

    private lateinit var trpActionBarFragment: TrpActionBarFragment

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainScreenId = 1
        frameLayout {
            backgroundColorResource = R.color.splitLineColor
            id = mainScreenId

            verticalLayout {
                //ActionBar
                val actionBarId = 2
                frameLayout {
                    id = actionBarId
                    trpActionBarFragment = TrpActionBarFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, trpActionBarFragment).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                frameLayout {
                    id = 3
                    val trpMainBodyFragment = TrpMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, trpMainBodyFragment).commit()
                }.lparams {
                    height = wrapContent
                    width = matchParent
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
        setActionBar(trpActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(
            this@TelephoneResetPasswordActivity,
            0,
            trpActionBarFragment.TrpToolbar
        )
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}