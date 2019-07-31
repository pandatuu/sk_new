package com.example.sk_android.mvp.view.activity.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.register.TrpActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.TrpMainBodyFragment
import com.jaeger.library.StatusBarUtil
import com.sahooz.library.Country
import com.sahooz.library.PickActivity
import com.umeng.message.PushAgent
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class TelephoneResetPasswordActivity : AppCompatActivity(),TrpMainBodyFragment.trpMid {
    private lateinit var trpActionBarFragment: TrpActionBarFragment
    private lateinit var trpMainBodyFragment:TrpMainBodyFragment

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

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
                    trpMainBodyFragment = TrpMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, trpMainBodyFragment).commit()
                }.lparams {
                    height = wrapContent
                    width = matchParent
                    leftMargin = dip(10)
                    rightMargin = dip(10)
                    topMargin = dip(60)
                }

                onClick {
                    trpMainBodyFragment.closeKeyfocus()
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

        trpActionBarFragment.TrpToolbar!!.setNavigationOnClickListener {
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
            trpMainBodyFragment!!.setTrpCountryCode(countryCode)
        }
    }

}