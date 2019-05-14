package com.example.sk_android.mvp.view.activity.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.sk_android.mvp.view.fragment.register.PthreeActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.PthreeMainBodyFragment
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent

class PersonInformationThreeActivity:AppCompatActivity() {

    lateinit var pthreeActionBarFragment:PthreeActionBarFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var mainScreenId=1
        frameLayout {
            id = mainScreenId

            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {

                    id = actionBarId
                    pthreeActionBarFragment = PthreeActionBarFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, pthreeActionBarFragment).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                var newFragmentId = 3
                frameLayout {
                    id = newFragmentId
                    val pthreeMainBodyFragment = PthreeMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, pthreeMainBodyFragment).commit()
                }.lparams(width = matchParent, height = matchParent)

            }.lparams(){
                width = matchParent
                height = matchParent
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setActionBar(pthreeActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(this@PersonInformationThreeActivity, 0, pthreeActionBarFragment.TrpToolbar)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

}