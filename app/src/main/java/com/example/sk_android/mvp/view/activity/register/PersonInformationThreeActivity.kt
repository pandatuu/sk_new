package com.example.sk_android.mvp.view.activity.register

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.model.register.Education
import com.example.sk_android.mvp.model.register.Person
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.jobselect.RollTwoChooseFrag
import com.example.sk_android.mvp.view.fragment.register.PthreeActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.PthreeMainBodyFragment
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent
import java.io.Serializable
import java.util.*

class PersonInformationThreeActivity:AppCompatActivity(),PthreeMainBodyFragment.Intermediary,
    ShadowFragment.ShadowClick,RollTwoChooseFrag.DemoClick {

    lateinit var pthreeActionBarFragment:PthreeActionBarFragment
    var timeCondition = "start"
    var rolltwo: RollTwoChooseFrag? = null
    var shadowFragment: ShadowFragment? = null
    lateinit var baseFragment: FrameLayout
    lateinit var pthreeMainBodyFragment:PthreeMainBodyFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras!!.get("bundle") as Bundle
        val resumeId =bundle.getString("resumeId")

        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        var mainScreenId=1
        baseFragment = frameLayout {
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
                    pthreeMainBodyFragment = PthreeMainBodyFragment.newInstance(resumeId)
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

    override fun twoOnClick(condition: String) {
        timeCondition = condition
        // 弹出年月选择器
        var cd = Calendar.getInstance()
        var year = cd.get(Calendar.YEAR)
        var mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (shadowFragment != null) {
            shadowFragment = ShadowFragment.newInstance()
            mTransaction.add(baseFragment.id, shadowFragment!!)
        }

        val list1:MutableList<String> = mutableListOf()

        var number = 0
        for(index in 1970..year){
            list1.add(number,index.toString()+"年")
            number += 1
        }
        val list2 = mutableListOf("01月","02月","03月","04月","05月","06月","07月","08月","09月",
            "10月","11月","12月")

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in
        )

        if (rolltwo == null) {
            rolltwo = RollTwoChooseFrag.newInstance("", list1, list2)
        }

        mTransaction.add(baseFragment.id, rolltwo!!).commit()
    }

    override fun rollTwoCancel() {
        closeAlertDialog()
    }

    override fun rollTwoConfirm(text1: String, text2: String) {
        var year = text1.trim().substring(0,text1.trim().length-1)
        var month = text2.trim().substring(0,text2.trim().length-1)
        var result = "$year-$month"
        when(timeCondition){
            "start" -> pthreeMainBodyFragment.setStart(result)
            "end" -> pthreeMainBodyFragment.setEnd(result)
        }
    }


    override fun shadowClicked() {
        closeAlertDialog()
    }

    // 关闭弹窗
    fun closeAlertDialog() {
        var mTransaction = supportFragmentManager.beginTransaction()

        if (shadowFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null
        }

        if(rolltwo != null){
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,R.anim.bottom_out
            )
            mTransaction.remove(rolltwo!!)
            rolltwo = null
        }
        mTransaction.commit()
    }


}