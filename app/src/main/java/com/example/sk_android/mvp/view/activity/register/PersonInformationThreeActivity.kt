package com.example.sk_android.mvp.view.activity.register

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.model.register.Education
import com.example.sk_android.mvp.model.register.Person
import com.example.sk_android.mvp.view.fragment.register.PthreeActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.PthreeMainBodyFragment
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent
import java.io.Serializable

class PersonInformationThreeActivity:AppCompatActivity() {

    lateinit var pthreeActionBarFragment:PthreeActionBarFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras!!.get("bundle") as Bundle
        val resumeId =bundle.getString("resumeId")

        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        var mainScreenId=1
        frameLayout {
            id = mainScreenId

            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {

                    id = actionBarId
                    pthreeActionBarFragment = PthreeActionBarFragment.newInstance(resumeId)
                    supportFragmentManager.beginTransaction().replace(id, pthreeActionBarFragment).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                var newFragmentId = 3
                frameLayout {
                    id = newFragmentId
                    val pthreeMainBodyFragment = PthreeMainBodyFragment.newInstance(resumeId)
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


    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if(event!!.keyCode == KeyEvent.KEYCODE_BACK){
            return true
        }else {
            return super.dispatchKeyEvent(event)
        }
    }


}