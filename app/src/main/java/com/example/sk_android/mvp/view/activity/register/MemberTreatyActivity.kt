package com.example.sk_android.mvp.view.activity.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.register.MtActionBarFragment
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

class MemberTreatyActivity:AppCompatActivity() {
    lateinit var mtActionBarFragment:MtActionBarFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var mainScreenId=1
        frameLayout {
            backgroundColorResource = R.color.splitLineColor
            id = mainScreenId

            verticalLayout {
                //ActionBar
                var actionBarId=2
                frameLayout{
                    id=actionBarId
                    mtActionBarFragment = MtActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,mtActionBarFragment).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }
            }.lparams(){
                width = matchParent
                height = matchParent
            }
        }
    }


    override fun onStart() {
        super.onStart()
        setActionBar(mtActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(this@MemberTreatyActivity, 0, mtActionBarFragment.TrpToolbar)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }
}