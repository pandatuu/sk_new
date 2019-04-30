package com.example.sk_android.mvp.view.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.jobSelect.*
import org.jetbrains.anko.*
import com.jaeger.library.StatusBarUtil

class RecruitInfoShowActivity : AppCompatActivity(), ShadowFragment.ShadowClick,
    JobWantedDialogFragment.ConfirmSelection, RecruitInfoSelectbarFragment.SelectBar,
    RecruitInfoBottomMenuFragment.RecruitInfoBottomMenu {

    lateinit var mainBody:FrameLayout
    lateinit var recruitInfoActionBarFragment:RecruitInfoActionBarFragment

    var recruitInfoSelectBarMenuPlaceFragment:RecruitInfoSelectBarMenuPlaceFragment?=null
    var shadowFragment: ShadowFragment?=null
    var jobWantedDeleteDialogFragment:JobWantedDialogFragment?=null


    override fun getSelectedMenu() {
    }


    override fun getSelectBarItem(index:Int) {
        toast(index.toString())
        var mTransaction=supportFragmentManager.beginTransaction()
        if(recruitInfoSelectBarMenuPlaceFragment!=null)
            mTransaction.remove(recruitInfoSelectBarMenuPlaceFragment!!)
        if(shadowFragment!=null)
            mTransaction.remove(shadowFragment!!)

        recruitInfoSelectBarMenuPlaceFragment= RecruitInfoSelectBarMenuPlaceFragment.newInstance();
        shadowFragment= ShadowFragment.newInstance();
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        mTransaction.add(mainBody.id,shadowFragment!!)
        mTransaction.setCustomAnimations(
            R.anim.top_in,
            R.anim.top_in)
        mTransaction.add(mainBody.id, recruitInfoSelectBarMenuPlaceFragment!!).commit()
    }

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


    override fun shadowClicked() {
    }

    override fun onStart() {
        super.onStart()
        setActionBar(recruitInfoActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@RecruitInfoShowActivity, 0, recruitInfoActionBarFragment.toolbar1)
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        frameLayout {
            backgroundColor=Color.WHITE
            verticalLayout {
                //ActionBar
                var actionBarId=2
               frameLayout{
                    id=actionBarId
                     recruitInfoActionBarFragment= RecruitInfoActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,recruitInfoActionBarFragment).commit()


                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                //selectBar
                var selectBarId=3
                frameLayout {
                    id=selectBarId
                    var recruitInfoSelectbarFragment= RecruitInfoSelectbarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,recruitInfoSelectbarFragment!!).commit()
                }.lparams {
                    height=wrapContent
                    width= matchParent
                }

                var mainBodyId=6
                mainBody= frameLayout{
                    id=mainBodyId
                    verticalLayout {
                        //list
                        var listParentId=4
                          frameLayout {
                            id=listParentId
                            var recruitInfoListFragment= RecruitInfoListFragment.newInstance();
                            supportFragmentManager.beginTransaction().replace(id,recruitInfoListFragment!!).commit()
                        }.lparams {
                            height=0
                            weight=1f
                            width= matchParent
                        }
                        //menu
                        var bottomMenuId=5
                        frameLayout {
                            id=bottomMenuId
                            var recruitInfoBottomMenuFragment= RecruitInfoBottomMenuFragment.newInstance();
                            supportFragmentManager.beginTransaction().replace(id,recruitInfoBottomMenuFragment!!).commit()
                        }.lparams {
                            height=wrapContent
                            width= matchParent
                        }

                    }.lparams {
                        height=matchParent
                        width= matchParent
                    }
                }.lparams {
                    height=0
                    weight=1f
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
