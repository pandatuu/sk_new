package com.example.sk_android.mvp.view.activity.resume

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.model.resume.Resume
import com.example.sk_android.mvp.view.fragment.resume.SrActionBarFragment
import com.example.sk_android.mvp.view.fragment.resume.SrMainBodyFragment
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent

class SendResumeActivity :AppCompatActivity(){
    lateinit var srActionBarFragment:SrActionBarFragment
    lateinit var condition:String
    override fun onCreate(savedInstanceState: Bundle?) {

        val bundle = intent.extras!!.get("bundle") as Bundle
        val resume = bundle.getParcelable<Parcelable>("resume") as Resume

        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        var mainScreenId=1

        frameLayout {
            id = mainScreenId

            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {
                    id = actionBarId
                    srActionBarFragment = SrActionBarFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, srActionBarFragment).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                var newFragmentId = 3
                frameLayout {
                    id = newFragmentId
                    val srMainBodyFragment = SrMainBodyFragment.newInstance(resume)
                    supportFragmentManager.beginTransaction().replace(id, srMainBodyFragment).commit()
                }.lparams(width = matchParent, height = matchParent)

            }.lparams(){
                width = matchParent
                height = matchParent
            }
        }

        init()
    }

    override fun onStart() {
        super.onStart()
        setActionBar(srActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@SendResumeActivity, 0, srActionBarFragment.toolbar1)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        srActionBarFragment.toolbar1!!.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.right_out, R.anim.right_out)
        }
    }

    private fun init(){

    }

}