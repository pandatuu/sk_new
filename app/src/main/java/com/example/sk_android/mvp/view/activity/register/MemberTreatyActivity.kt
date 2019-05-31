package com.example.sk_android.mvp.view.activity.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.register.MtActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.MtMainBodyFragment
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import org.jetbrains.anko.*

class MemberTreatyActivity:AppCompatActivity() {
    lateinit var mtActionBarFragment:MtActionBarFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        var mainScreenId=1
        frameLayout {
            backgroundColorResource = R.color.whiteFF
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

                var recycleViewParentId=3
                frameLayout {

                    id=recycleViewParentId
                    var mtMainBodyFragment= MtMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id,mtMainBodyFragment).commit()
                }.lparams {
                    height= matchParent
                    width= matchParent
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                    bottomMargin = dip(19)
                }
            }.lparams{
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