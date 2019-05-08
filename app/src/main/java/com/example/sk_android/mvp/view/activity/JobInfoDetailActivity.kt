package com.example.sk_android.mvp.view.activity

import android.annotation.SuppressLint
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
import com.example.sk_android.mvp.model.SelectedItem
import com.example.sk_android.mvp.view.fragment.jobSelect.*
import org.jetbrains.anko.*
import com.jaeger.library.StatusBarUtil

class JobInfoDetailActivity : AppCompatActivity(), ShadowFragment.ShadowClick,
    JobInfoDetailDescribeInfoFragment.GetMoreButton
  {


    lateinit var desInfo:FrameLayout
    lateinit var selectBar:FrameLayout


    lateinit var jobInfoDetailActionBarFragment:JobInfoDetailActionBarFragment

    var recruitInfoSelectBarMenuOtherFragment:RecruitInfoSelectBarMenuOtherFragment?=null
    var recruitInfoSelectBarMenuPlaceFragment:RecruitInfoSelectBarMenuPlaceFragment?=null
    var recruitInfoSelectBarMenuCompanyFragment:RecruitInfoSelectBarMenuCompanyFragment?=null
    var recruitInfoSelectBarMenuRequireFragment:RecruitInfoSelectBarMenuRequireFragment?=null

    var jobInfoDetailDescribeInfoFragment:JobInfoDetailDescribeInfoFragment?=null

    var shadowFragment: ShadowFragment?=null

    //查看更多
    override fun getMoreOnClick() {
        jobInfoDetailDescribeInfoFragment!!.desContent.text="1、バックグランドシステムの设计、开発作业を担当している; \n2、ウェブサイト机能のメンテナンス、最适化と再构筑を担当する; \n3、ゲームの技术のドッキングを担当し、ゲーム开発チームと需要を疎通させ、速やかに开発を実现する…"
    }


    //收回下拉框
    override fun shadowClicked() {

        var mTransaction=supportFragmentManager.beginTransaction()
        if(recruitInfoSelectBarMenuOtherFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.top_out,  R.anim.top_out)
            mTransaction.remove(recruitInfoSelectBarMenuOtherFragment!!)
            recruitInfoSelectBarMenuOtherFragment=null
        }
        if(recruitInfoSelectBarMenuPlaceFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.top_out,  R.anim.top_out)
            mTransaction.remove(recruitInfoSelectBarMenuPlaceFragment!!)
            recruitInfoSelectBarMenuPlaceFragment=null
        }
        if(recruitInfoSelectBarMenuCompanyFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.top_out,  R.anim.top_out)
            mTransaction.remove(recruitInfoSelectBarMenuCompanyFragment!!)
            recruitInfoSelectBarMenuCompanyFragment=null
        }
        if(recruitInfoSelectBarMenuRequireFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.top_out,  R.anim.top_out)
            mTransaction.remove(recruitInfoSelectBarMenuRequireFragment!!)
            recruitInfoSelectBarMenuRequireFragment=null
        }

        if(shadowFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)
            mTransaction.remove(shadowFragment!!)
            shadowFragment=null

        }
        mTransaction.commit()

    }

    override fun onStart() {
        super.onStart()
        setActionBar(jobInfoDetailActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@JobInfoDetailActivity, 0, jobInfoDetailActionBarFragment.toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        frameLayout {
            backgroundColor=Color.WHITE
            verticalLayout {
                //ActionBar
                var actionBarId=2
               frameLayout{
                    id=actionBarId
                   jobInfoDetailActionBarFragment= JobInfoDetailActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,jobInfoDetailActionBarFragment).commit()


                }.lparams {
                    height= wrapContent
                    width= matchParent
                }


                scrollView {
                    verticalLayout {
                        var topInfoId=10
                        frameLayout{
                            id=topInfoId
                            var jobInfoDetailTopInfoFragment= JobInfoDetailTopInfoFragment.newInstance();
                            supportFragmentManager.beginTransaction().replace(id,jobInfoDetailTopInfoFragment).commit()


                        }.lparams {
                            height= wrapContent
                            width= matchParent
                        }

                        var bossInfoId=11
                        frameLayout{
                            id=bossInfoId
                            var jobInfoDetailBossInfoFragment= JobInfoDetailBossInfoFragment.newInstance();
                            supportFragmentManager.beginTransaction().replace(id,jobInfoDetailBossInfoFragment).commit()


                        }.lparams {
                            height= wrapContent
                            width= matchParent
                        }

                        var desInfoId=12
                        desInfo=frameLayout{
                            id=desInfoId
                            jobInfoDetailDescribeInfoFragment= JobInfoDetailDescribeInfoFragment.newInstance("1、バックグランドシステムの设计、开発作业を担当している;…");
                            supportFragmentManager.beginTransaction().replace(id,jobInfoDetailDescribeInfoFragment!!).commit()


                        }.lparams {
                            height= wrapContent
                            width= matchParent
                        }



                    }.lparams(matchParent)
                }.lparams {
                    height= 0
                    weight=1f
                    width= matchParent
                }

                textView {
                    text="すぐに連絡"
                    backgroundResource=R.drawable.radius_button_blue
                    gravity=Gravity.CENTER
                    textSize=15f
                    textColor=Color.WHITE
                }.lparams {
                    height=dip(47)
                    width= matchParent
                    leftMargin=dip(23)
                    rightMargin=dip(23)
                    bottomMargin=dip(13)
                    topMargin=dip(14)
                }








            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }

    }
}
