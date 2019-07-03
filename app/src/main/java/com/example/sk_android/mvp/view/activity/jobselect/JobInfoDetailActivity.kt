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
import anet.channel.util.Utils.context
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
import android.content.pm.ActivityInfo
import android.widget.Toast
import android.os.Parcelable
import android.content.pm.ResolveInfo
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat.startActivity
import android.content.ComponentName
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.shareboard.SnsPlatform
import com.umeng.socialize.utils.ShareBoardlistener
import java.util.*


class JobInfoDetailActivity : AppCompatActivity(), ShadowFragment.ShadowClick,
   JobInfoDetailSkillLabelFragment.JobInfoDetailSkillLabelSelect,
    JobInfoDetailActionBarFragment.ActionBarSelecter,
    BottomSelectDialogFragment.BottomSelectDialogSelect,
    ShareFragment.SharetDialogSelect
{


    var dataFromType=""


    //分享的选项
    override fun getSelectedItem(index: Int) {
        hideDialog()

        when(index){
            0 -> {
                toast("line")

                ShareAction(this@JobInfoDetailActivity)
                    .setPlatform(SHARE_MEDIA.LINE)//传入平台
                    .withText("hello")//分享内容
                    .setShareboardclickCallback(object : ShareBoardlistener{
                        override fun onclick(p0: SnsPlatform?, p1: SHARE_MEDIA?) {
                            println("11111111111111111111111111111111111111111 ")
                        }
                    })
                    .share()
            }
            1 -> {
                toast("twitter")

                PlatformConfig.setTwitter("43QQHUnU2xWEA3nZVbknCEFrl","PxRQDYcT1PVMeZsdjacRg8ToNOXuyQ84tnRm6kG6OaAziXtdjf")
                val share = ShareAction(this@JobInfoDetailActivity)
                    .setPlatform(SHARE_MEDIA.TWITTER)//传入平台
                    .withText("hello")//分享内容
                    .setCallback(shareListener)//回调监听器
                    .share()

            }
            else -> {
                toast("facebook")
                ShareAction(this@JobInfoDetailActivity)
                    .setPlatform(SHARE_MEDIA.FACEBOOK)//传入平台
                    .withText("hello")//分享内容
                    .setCallback(shareListener)//回调监听器
                    .share()
            }
        }

    }

    //点击取消按钮
    override fun getBottomSelectDialogSelect() {
        hideDialog()

    }

    override fun getback(index: Int,list : MutableList<String>) {
        hideDialog()

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

        hideDialog()

        var mTransaction = supportFragmentManager.beginTransaction()
        showShadow(mTransaction)


        //举报
        if(index==1){

            var strArray: MutableList<String> = mutableListOf("広告","嫌がらせ","詐欺情報","その他")

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
        hideDialog()
    }

    override fun onStart() {
        super.onStart()
        setActionBar(jobInfoDetailActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@JobInfoDetailActivity, 0, jobInfoDetailActionBarFragment.toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        jobInfoDetailActionBarFragment.toolbar1!!.setNavigationOnClickListener {

            var mIntent= Intent()
            mIntent.putExtra("position",jobInfoDetailActionBarFragment!!.getPosition())
            mIntent.putExtra("isCollection",jobInfoDetailActionBarFragment!!.getIsCollection())
            mIntent.putExtra("collectionId",jobInfoDetailActionBarFragment!!.getCollectionId())

            setResult(RESULT_OK,mIntent);
            finish()//返回
            overridePendingTransition(R.anim.right_out,R.anim.right_out)

        }



    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()
        UMConfigure.init(this,"5cdcc324570df3ffc60009c3"
            ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"")


        var intent=intent
        var fromType=intent.getStringExtra("fromType")
        dataFromType=fromType

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


                    setOnClickListener(object :View.OnClickListener{

                        override fun onClick(v: View?) {
                                if(dataFromType.equals("CHAT")){
                                    //从聊天界面转过来的
                                    finish()//返回
                                    overridePendingTransition(R.anim.right_out,R.anim.right_out)
                                }else{


                                }
                        }

                    })
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




    fun hideDialog( ) {
        var mTransaction=supportFragmentManager.beginTransaction()
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
        mTransaction.commit()
    }

    fun showShadow(mTransaction: FragmentTransaction){

        shadowFragment= ShadowFragment.newInstance()
        mTransaction.setCustomAnimations(
            R.anim.fade_in_out,  R.anim.fade_in_out)
        mTransaction.add(mainContainer.id,shadowFragment!!)

    }

    private val shareListener = object:UMShareListener
    {
        override fun onResult(p0: SHARE_MEDIA?) {
            Toast.makeText(this@JobInfoDetailActivity,"成功了",Toast.LENGTH_LONG).show()
        }

        override fun onCancel(p0: SHARE_MEDIA?) {

        }

        override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {

        }

        override fun onStart(p0: SHARE_MEDIA?) {

        }
    }
}
