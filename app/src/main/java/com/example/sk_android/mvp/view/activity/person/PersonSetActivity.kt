package com.example.sk_android.mvp.view.activity.person

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.person.PsActionBarFragment
import com.example.sk_android.mvp.view.fragment.person.PsMainBodyFragment
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import org.jetbrains.anko.*

class PersonSetActivity:AppCompatActivity() {
    lateinit var psActionBarFragment:PsActionBarFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mainScreenId=1
        PushAgent.getInstance(this).onAppStart();

        frameLayout {
            id=mainScreenId
            verticalLayout {
                //ActionBar
                var actionBarId=2
                frameLayout{

                    id=actionBarId
                    psActionBarFragment= PsActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,psActionBarFragment).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                var newFragmentId = 3
                frameLayout {
                    id = newFragmentId
                    val psMainBodyFragment = PsMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, psMainBodyFragment).commit()
                }.lparams(width = matchParent, height = matchParent)

            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }
    }



    override fun onStart() {
        super.onStart()
        setActionBar(psActionBarFragment.toolbar)
        StatusBarUtil.setTranslucentForImageView(this@PersonSetActivity, 0, psActionBarFragment.toolbar)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }

}