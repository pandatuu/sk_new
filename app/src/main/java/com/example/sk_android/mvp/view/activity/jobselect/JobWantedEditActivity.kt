package com.example.sk_android.mvp.view.activity.jobselect

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.jobselect.*
import org.jetbrains.anko.*
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent

class JobWantedEditActivity : AppCompatActivity(), ShadowFragment.ShadowClick, JobWantedListFragment.DeleteButton,
    JobWantedDialogFragment.ConfirmSelection {

    //类型 1修改/2添加
    var operateType:Int=1


    lateinit var mainScreen:FrameLayout
    var shadowFragment: ShadowFragment?=null
    var jobWantedDeleteDialogFragment:JobWantedDialogFragment?=null
    lateinit var themeActionBarFragment:ThemeActionBarFragment

    override fun confirmResult(b: Boolean) {
        var mTransaction=supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if(jobWantedDeleteDialogFragment!=null){
//            mTransaction.setCustomAnimations(
//                R.anim.fade_faster_in_out,  R.anim.fade_faster_in_out)
            mTransaction.remove(jobWantedDeleteDialogFragment!!)
            jobWantedDeleteDialogFragment=null
        }
        if(shadowFragment!=null){
//            mTransaction.setCustomAnimations(
//                R.anim.fade_in_out,  R.anim.fade_in_out)
            mTransaction.remove(shadowFragment!!)
            shadowFragment=null
        }
        mTransaction.commit()

        toast(b.toString())
    }

    override fun delete() {
        toast("xxxxx")
        var mTransaction=supportFragmentManager.beginTransaction()
        if(shadowFragment!=null || jobWantedDeleteDialogFragment!=null){
            return
        }

        shadowFragment= ShadowFragment.newInstance()
        jobWantedDeleteDialogFragment=JobWantedDialogFragment.newInstance(JobWantedDialogFragment.CANCLE)
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        mTransaction.add(mainScreen.id,shadowFragment!!)

        mTransaction.setCustomAnimations(
            R.anim.fade_in_out,  R.anim.fade_in_out)
        mTransaction.add(mainScreen.id,jobWantedDeleteDialogFragment!!).commit()

    }

    override fun shadowClicked() {
    }

    override fun onStart() {
        super.onStart()
        setActionBar(themeActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@JobWantedEditActivity, 0, themeActionBarFragment.toolbar1)

        themeActionBarFragment.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.right_out,R.anim.right_out)
        }


    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        PushAgent.getInstance(this).onAppStart();

        var intent=intent
        operateType=intent.getIntExtra("type",1)

//if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT){
//透明状态栏
//getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//透明导航栏
//getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//}
//getWindow().addFlags(WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE);
//注意要清除 FLAG_TRANSLUCENT_STATUS flag
//getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//getWindow().setStatusBarColor(getResources().getColor(android.R.color.holo_red_light))
//getWindow().setNavigationBarColor(getResources().getColor(android.R.color.holo_red_light))

        var mainScreenId=1
        mainScreen=frameLayout {
            backgroundColor=Color.WHITE
            id=mainScreenId
            verticalLayout {
                //ActionBar
                var actionBarId=2
               frameLayout{
                    id=actionBarId
                    themeActionBarFragment= ThemeActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,themeActionBarFragment).commit()


                }.lparams {
                    height= wrapContent
                    width= matchParent
                }
                //list
                var recycleViewParentId=3
                frameLayout {
                    id=recycleViewParentId
                    var jobWantedListFragment= JobWantedListFragment.newInstance(operateType);
                    supportFragmentManager.beginTransaction().replace(id,jobWantedListFragment!!).commit()
                }.lparams {
                    height=matchParent
                    width= matchParent
                }

            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }
//getActionBar()!!.setDisplayHomeAsUpEnabled(true);
//StatusBarUtil.setTranslucentForDrawerLayout(this, , 0)
//StatusBarUtil.setColor(this, R.color.transparent);
//StatusBarUtil.setColorForDrawerLayout(this, layout, 0)
    }
}
