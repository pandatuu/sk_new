package com.example.sk_android.mvp.view.activity.Register

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.Register.IiActionBarFragment
import com.example.sk_android.mvp.view.fragment.Register.IiMainBodyFragment
import com.example.sk_android.mvp.view.fragment.Register.WsBackgroundFragment
import com.example.sk_android.mvp.view.fragment.Register.WsListFragment
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

class ImproveInformationActivity : AppCompatActivity() ,IiMainBodyFragment.Middleware,WsListFragment.CancelTool{
    lateinit var iiActionBarFragment: IiActionBarFragment
    lateinit var baseFragment: FrameLayout

    var wsBackgroundFragment:WsBackgroundFragment?=null
    var wsListFragment:WsListFragment?=null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mainScreenId = 1
        baseFragment = frameLayout {
            backgroundColorResource = R.color.splitLineColor
            id = mainScreenId

            verticalLayout {
                //ActionBar
                val actionBarId = 2
                frameLayout {

                    id = actionBarId
                    iiActionBarFragment = IiActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, iiActionBarFragment).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                frameLayout {
                    id = 3
                    val iiMainBodyFragment = IiMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, iiMainBodyFragment).commit()
                }.lparams(width = matchParent, height = matchParent)

            }.lparams {
                width = matchParent
                height = matchParent
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setActionBar(iiActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(this@ImproveInformationActivity, 0, iiActionBarFragment.TrpToolbar)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    @SuppressLint("ResourceType")
    override fun addListFragment() {

        var mTransaction=supportFragmentManager.beginTransaction()

        wsBackgroundFragment = WsBackgroundFragment.newInstance()

        mTransaction.add(baseFragment.id, wsBackgroundFragment!!)

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,  R.anim.bottom_in)


        wsListFragment = WsListFragment.newInstance()
        mTransaction.add(baseFragment.id, wsListFragment!!)

        mTransaction.commit()
    }

    override fun cancelList() {
        var mTransaction=supportFragmentManager.beginTransaction()

        if (wsListFragment != null){

            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)

            mTransaction.remove(wsListFragment!!)
            wsListFragment = null
        }

        if (wsBackgroundFragment != null){

            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)

            mTransaction.remove(wsBackgroundFragment!!)
            wsBackgroundFragment = null

        }


        mTransaction.commit()
    }

}