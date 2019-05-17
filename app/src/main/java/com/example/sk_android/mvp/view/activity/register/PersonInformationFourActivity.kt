package com.example.sk_android.mvp.view.activity.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.example.sk_android.mvp.tool.BaseTool
import com.example.sk_android.mvp.view.fragment.register.PfourActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.PfourMainBodyFragment
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

class PersonInformationFourActivity:AppCompatActivity(),PfourActionBarFragment.mm {
    lateinit var pfourActionBarFragment:PfourActionBarFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var mainScreenId=1
        frameLayout {
            id = mainScreenId

            verticalLayout {
                //ActionBar
                //ActionBar
                var actionBarId = 2
                frameLayout {

                    id = actionBarId
                    pfourActionBarFragment = PfourActionBarFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, pfourActionBarFragment).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                var newFragmentId = 3
                frameLayout {
                    id = newFragmentId
                    val pfourMainBodyFragment = PfourMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, pfourMainBodyFragment).commit()
                }.lparams(width = matchParent, height = matchParent)


            }.lparams(){
                width = matchParent
                height = matchParent
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setActionBar(pfourActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(this@PersonInformationFourActivity, 0, pfourActionBarFragment.TrpToolbar)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun goback() {
        alert ("密码不可为空"){
            yesButton { toast("Yes!!!") }
            noButton { }
        }.show()
    }

}