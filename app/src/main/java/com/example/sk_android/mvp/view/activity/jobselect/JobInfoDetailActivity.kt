package com.example.sk_android.mvp.view.activity.jobselect

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity
import android.view.View
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.common.AccusationActivity
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.common.ShareFragment
import com.example.sk_android.mvp.view.fragment.jobselect.*
import org.jetbrains.anko.*
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import imui.jiguang.cn.imuisample.messages.MessageListActivity

class JobInfoDetailActivity : AppCompatActivity(), ShadowFragment.ShadowClick,
   JobInfoDetailSkillLabelFragment.JobInfoDetailSkillLabelSelect,
    JobInfoDetailActionBarFragment.ActionBarSelecter,
    BottomSelectDialogFragment.BottomSelectDialogSelect,
    ShareFragment.SharetDialogSelect
{

    //分享的选项
    override fun getSelectedItem(index: Int) {

        hideDialog(null)


    }
    //点击取消按钮
    override fun getBottomSelectDialogSelect() {
        hideDialog(null)

    }

    override fun getback(index: Int,list : MutableList<String>) {
        hideDialog(null)


        var intent = Intent(this, AccusationActivity::class.java)
        intent.putExtra("type", list.get(index))
        startActivity(intent)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }


    lateinit var desInfo: FrameLayout
    lateinit var mainContainer: FrameLayout


    lateinit var jobInfoDetailActionBarFragment: JobInfoDetailActionBarFragment



    var shareFragment:ShareFragment? = null

    var jobInfoDetailDescribeInfoFragment: JobInfoDetailDescribeInfoFragment? = null
    var bottomSelectDialogFragment: BottomSelectDialogFragment? = null

    var shadowFragment: ShadowFragment? = null




    //action bar 上的图标 被选择
    override fun gerActionBarSelectedItem(index: Int) {


        var mTransaction = supportFragmentManager.beginTransaction()
        hideDialog(mTransaction)
        showShadow(mTransaction)


        //举报
        if(index==1){

            var strArray: MutableList<String> = mutableListOf("広告","ポルノ","法律違反","企業側身分偽造","プライバシー侵害","人身攻撃","虚偽の情報","その他")

            bottomSelectDialogFragment=BottomSelectDialogFragment.newInstance("告発",strArray)
            mTransaction.setCustomAnimations(
                R.anim.bottom_in,  R.anim.bottom_in)
            mTransaction.add(mainContainer.id,bottomSelectDialogFragment!!)

        }else if(index==2){
            //分享

            shareFragment= ShareFragment.newInstance()
            mTransaction.setCustomAnimations(
                R.anim.bottom_in,  R.anim.bottom_in)
            mTransaction.add(mainContainer.id,shareFragment!!)
        }



        mTransaction.commit()

    }

    //技能标签 被选择
    override fun getSelectedLabel(str: String) {
        toast(str)
    }





    //收回下拉框
    override fun shadowClicked() {
        hideDialog(null)
    }

    override fun onStart() {
        super.onStart()
        setActionBar(jobInfoDetailActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@JobInfoDetailActivity, 0, jobInfoDetailActionBarFragment.toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        jobInfoDetailActionBarFragment.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.right_out,R.anim.right_out)

        }




    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        var mainContainerId=1
        mainContainer=frameLayout {
            id=mainContainerId
            backgroundColorResource=R.color.white
            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {
                    id = actionBarId
                    jobInfoDetailActionBarFragment = JobInfoDetailActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, jobInfoDetailActionBarFragment).commit()


                }.lparams {
                    height = wrapContent
                    width = matchParent
                }


                scrollView {
                    overScrollMode = View.OVER_SCROLL_NEVER
                    verticalLayout {
                        var topInfoId = 10
                        frameLayout {
                            id = topInfoId
                            var jobInfoDetailTopInfoFragment = JobInfoDetailTopInfoFragment.newInstance();
                            supportFragmentManager.beginTransaction().replace(id, jobInfoDetailTopInfoFragment).commit()


                        }.lparams {
                            height = wrapContent
                            width = matchParent
                        }

                        var bossInfoId = 11
                        frameLayout {
                            id = bossInfoId
                            var jobInfoDetailBossInfoFragment = JobInfoDetailBossInfoFragment.newInstance();
                            supportFragmentManager.beginTransaction().replace(id, jobInfoDetailBossInfoFragment)
                                .commit()


                        }.lparams {
                            height = wrapContent
                            width = matchParent
                        }

                        var desInfoId = 12
                        desInfo = frameLayout {
                            id = desInfoId
                            jobInfoDetailDescribeInfoFragment =
                                JobInfoDetailDescribeInfoFragment.newInstance("1、バックグランドシステムの设计、开発作业を担当している;…");
                            supportFragmentManager.beginTransaction().replace(id, jobInfoDetailDescribeInfoFragment!!)
                                .commit()


                        }.lparams {
                            height = wrapContent
                            width = matchParent
                        }

                        var skillInfoId = 13
                        frameLayout {
                            id = skillInfoId
                            var jobInfoDetailSkillLabelFragment =
                                JobInfoDetailSkillLabelFragment.newInstance();
                            supportFragmentManager.beginTransaction().replace(id, jobInfoDetailSkillLabelFragment!!)
                                .commit()


                        }.lparams {
                            height = wrapContent
                            width = matchParent
                        }


                        var companyInfoId = 14
                        frameLayout {
                            id = companyInfoId
                            var jobInfoDetailCompanyInfoFragment = JobInfoDetailCompanyInfoFragment.newInstance();
                            supportFragmentManager.beginTransaction().replace(id, jobInfoDetailCompanyInfoFragment!!)
                                .commit()


                        }.lparams {
                            height = wrapContent
                            width = matchParent
                        }

                        var MapInfoId = 14
                        frameLayout {
                            id = MapInfoId
                            backgroundColor = Color.RED

                        }.lparams {
                            height = dip(230)
                            width = matchParent
                        }


                    }.lparams(matchParent)
                }.lparams {
                    height = 0
                    weight = 1f
                    width = matchParent
                }

                textView {
                    text = "すぐに連絡"
                    backgroundResource = R.drawable.radius_button_theme
                    gravity = Gravity.CENTER
                    textSize = 15f
                    textColor = Color.WHITE
                }.lparams {
                    height = dip(47)
                    width = matchParent
                    leftMargin = dip(23)
                    rightMargin = dip(23)
                    bottomMargin = dip(13)
                    topMargin = dip(14)
                }


            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }

    }


    fun getDetailData(){

    }




    fun hideDialog( m: FragmentTransaction?) {
        var mTransaction=m
        if(mTransaction==null){
            mTransaction=supportFragmentManager.beginTransaction()
        }

        if (bottomSelectDialogFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)
            mTransaction.remove(bottomSelectDialogFragment!!)
            bottomSelectDialogFragment = null

        }

        if (shareFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)
            mTransaction.remove(shareFragment!!)
            shareFragment = null

        }

        if (shadowFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null

        }
        if(mTransaction==null){
            mTransaction.commit()
        }


    }

    fun showShadow(mTransaction: FragmentTransaction){

        shadowFragment= ShadowFragment.newInstance()
        mTransaction.setCustomAnimations(
            R.anim.fade_in_out,  R.anim.fade_in_out)
        mTransaction.add(mainContainer.id,shadowFragment!!)

    }


}
