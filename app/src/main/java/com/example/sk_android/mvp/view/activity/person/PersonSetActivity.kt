package com.example.sk_android.mvp.view.activity.person

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoSelectBarMenuFragment
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeBackgroundFragment
import com.example.sk_android.mvp.view.fragment.person.JobListFragment
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.mvp.view.fragment.person.PsActionBarFragment
import com.example.sk_android.mvp.view.fragment.person.PsMainBodyFragment
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.utils.RetrofitUtils
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.startActivity

class PersonSetActivity:AppCompatActivity(), PsMainBodyFragment.JobWanted, JobListFragment.CancelTool
    {

    lateinit var baseFragment: FrameLayout
    var wsBackgroundFragment: ResumeBackgroundFragment? = null
    var wsListFragment: JobListFragment? = null
    var mTransaction: FragmentTransaction? = null


    lateinit var psActionBarFragment:PsActionBarFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mainScreenId=1

        PushAgent.getInstance(this).onAppStart()

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
        initData()
        setActionBar(psActionBarFragment.toolbar)
        StatusBarUtil.setTranslucentForImageView(this@PersonSetActivity, 0, psActionBarFragment.toolbar)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
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


    @SuppressLint("CheckResult")
    private fun initData(){
        var personMap = mapOf<String,String>()
        var retrofitUils = RetrofitUtils(this,this.getString(R.string.userUrl))
        retrofitUils.create(PersonApi::class.java)
            .information
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                var imageUrl = it.get("avatarURL").toString().replace("\"","")
                var name = it.get("displayName").toString().replace("\"","")
                psActionBarFragment.changePage(imageUrl,name)
            },{
                println("**********************2")
                println(it)
            })
    }
}