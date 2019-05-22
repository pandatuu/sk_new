package com.example.sk_android.mvp.view.activity.person

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeBackgroundFragment
import com.example.sk_android.mvp.view.fragment.person.JobListFragment
import com.example.sk_android.mvp.view.fragment.person.PsActionBarFragment
import com.example.sk_android.mvp.view.fragment.person.PsMainBodyFragment
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

class PersonSetActivity:AppCompatActivity(), PsMainBodyFragment.JobWanted, JobListFragment.CancelTool {

    lateinit var baseFragment: FrameLayout
    var wsBackgroundFragment: ResumeBackgroundFragment? = null
    var wsListFragment: JobListFragment? = null
    var mTransaction: FragmentTransaction? = null

    lateinit var psActionBarFragment:PsActionBarFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mainScreenId=1
        baseFragment = frameLayout {
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

    override fun jobItem() {
        if (mTransaction == null) {
            mTransaction = supportFragmentManager.beginTransaction()
            wsBackgroundFragment = ResumeBackgroundFragment.newInstance()
            mTransaction!!.add(baseFragment.id, wsBackgroundFragment!!)
            mTransaction!!.setCustomAnimations(
                R.anim.bottom_in, R.anim.bottom_in
            )
            wsListFragment = JobListFragment.newInstance()
            mTransaction!!.add(baseFragment.id, wsListFragment!!)
            mTransaction!!.commit()
        }
    }

    override fun cancelList() {
        mTransaction = supportFragmentManager.beginTransaction()
        if (wsListFragment != null) {
            mTransaction!!.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction!!.remove(wsListFragment!!)
            wsListFragment = null
        }
        if (wsBackgroundFragment != null) {
            mTransaction!!.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction!!.remove(wsBackgroundFragment!!)
            wsBackgroundFragment = null
        }
        mTransaction!!.commit()
        mTransaction = null
    }
}