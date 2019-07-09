package com.example.sk_android.mvp.view.activity.person

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.person.FlActionBarFragment
import com.example.sk_android.mvp.view.fragment.person.FlMainBodyFragment
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent

class FaceCloseActivity:AppCompatActivity() {
    lateinit var flActionBarFragment:FlActionBarFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myId = intent.getStringExtra("id")

        var mainScreenId=1
        frameLayout {
            id=mainScreenId
            verticalLayout {
                //ActionBar
                var actionBarId=2
                frameLayout{

                    id=actionBarId
                    flActionBarFragment= FlActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,flActionBarFragment).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                var newFragmentId = 3
                frameLayout {
                    id = newFragmentId
                    val flMainBodyFragment = FlMainBodyFragment.newInstance(myId)
                    supportFragmentManager.beginTransaction().replace(id, flMainBodyFragment).commit()
                }.lparams(width = matchParent, height = matchParent){}

            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }
    }

    override fun onStart() {
        super.onStart()
        setActionBar(flActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(this@FaceCloseActivity, 0, flActionBarFragment.TrpToolbar)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        flActionBarFragment.TrpToolbar!!.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }
}