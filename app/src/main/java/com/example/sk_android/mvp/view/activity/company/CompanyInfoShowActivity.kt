package com.example.sk_android.mvp.view.activity.company

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoListFragment
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoSelectBarMenuFragment
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoSelectbarFragment
import com.example.sk_android.mvp.view.fragment.jobselect.*
import org.jetbrains.anko.*
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent

class CompanyInfoShowActivity : AppCompatActivity(), ShadowFragment.ShadowClick,
    CompanyInfoSelectbarFragment.SelectBar,
    RecruitInfoBottomMenuFragment.RecruitInfoBottomMenu, CompanyInfoSelectBarMenuFragment.SelectBarMenuSelect {


    var selectBarShow1:String=""
    var selectBarShow2:String=""
    var selectBarShow3:String=""
    var selectBarShow4:String=""


    lateinit var mainBody:FrameLayout
    lateinit var selectBar:FrameLayout


    lateinit var companyInfoActionBarFragment:CompanyInfoActionBarFragment


    var companyInfoSelectBarMenuFragment1: CompanyInfoSelectBarMenuFragment?=null
    var companyInfoSelectBarMenuFragment2: CompanyInfoSelectBarMenuFragment?=null
    var companyInfoSelectBarMenuFragment3: CompanyInfoSelectBarMenuFragment?=null
    var companyInfoSelectBarMenuFragment4: CompanyInfoSelectBarMenuFragment?=null

    var shadowFragment: ShadowFragment?=null




    override fun getSelectedItems(index:Int,list: MutableList<String>?) {

        var mTransaction=supportFragmentManager.beginTransaction()

        var sizeString=""
        if(list!=null && list.size!=0){
            sizeString=list.size.toString()
            toast(list.toString())
        }


        if(index==0){
            selectBarShow1=sizeString
            fragmentTopOut(companyInfoSelectBarMenuFragment1)
            companyInfoSelectBarMenuFragment1=null
        }else if(index==1){
            selectBarShow2=sizeString
            fragmentTopOut(companyInfoSelectBarMenuFragment2)
            companyInfoSelectBarMenuFragment2=null
        }else  if(index==2){
            selectBarShow3=sizeString
            fragmentTopOut(companyInfoSelectBarMenuFragment3)
            companyInfoSelectBarMenuFragment3=null
        }else  if(index==3){
            selectBarShow4=sizeString
            fragmentTopOut(companyInfoSelectBarMenuFragment4)
            companyInfoSelectBarMenuFragment4=null
        }



        var companyInfoSelectbarFragment= CompanyInfoSelectbarFragment.newInstance(selectBarShow1,selectBarShow2,selectBarShow3,selectBarShow4);
        mTransaction.replace(selectBar.id,companyInfoSelectbarFragment!!)


        fragmentFadeOut(shadowFragment)
        shadowFragment=null

        mTransaction.commit()

    }




    override fun getSelectedMenu() {

    }

    //根据点击的类型，弹出不同的下拉框
    override fun getSelectBarItem(index:Int) {
        toast(index.toString())
        var mTransaction=supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        if(companyInfoSelectBarMenuFragment1!=null &&index.equals(0)){
            fragmentTopOut(companyInfoSelectBarMenuFragment1)
            companyInfoSelectBarMenuFragment1=null

            fragmentFadeOut(shadowFragment)
            shadowFragment=null
            mTransaction.commit()
            return
        }

        if(companyInfoSelectBarMenuFragment2!=null && index.equals(1)){
            fragmentTopOut(companyInfoSelectBarMenuFragment2)
            companyInfoSelectBarMenuFragment2=null


            fragmentFadeOut(shadowFragment)
            shadowFragment=null
            mTransaction.commit()
            return
        }

        if(companyInfoSelectBarMenuFragment3!=null && index.equals(2)){
            fragmentTopOut(companyInfoSelectBarMenuFragment3)
            companyInfoSelectBarMenuFragment3=null


            fragmentFadeOut(shadowFragment)
            shadowFragment=null
            mTransaction.commit()
            return
        }

        if(companyInfoSelectBarMenuFragment4!=null && index.equals(3)){
            fragmentTopOut(companyInfoSelectBarMenuFragment4)
            companyInfoSelectBarMenuFragment4=null

            fragmentFadeOut(shadowFragment)
            shadowFragment=null
            mTransaction.commit()
            return
        }


        if(companyInfoSelectBarMenuFragment1!=null){
            mTransaction.remove(companyInfoSelectBarMenuFragment1!!)
            companyInfoSelectBarMenuFragment1=null
        }
        if(companyInfoSelectBarMenuFragment2!=null){
            mTransaction.remove(companyInfoSelectBarMenuFragment2!!)
            companyInfoSelectBarMenuFragment2=null

        }
        if(companyInfoSelectBarMenuFragment3!=null){
            mTransaction.remove(companyInfoSelectBarMenuFragment3!!)
            companyInfoSelectBarMenuFragment3=null
        }
        if(companyInfoSelectBarMenuFragment4!=null){
            mTransaction.remove(companyInfoSelectBarMenuFragment4!!)
            companyInfoSelectBarMenuFragment4=null
        }

        if(shadowFragment==null){
            shadowFragment= ShadowFragment.newInstance();
            mTransaction.add(mainBody.id,shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.top_in,
            R.anim.top_in)

        if(index.equals(0)){

            var list=
                mutableListOf("全部","未融資","エンジェルラウンド","ラウンドA","ラウンドB","ラウンドC","ラウンドD及びその以上","上場してる","不需要融資")
                    .map { SelectedItem(it, false) }
                    .toMutableList()

            companyInfoSelectBarMenuFragment1= CompanyInfoSelectBarMenuFragment.newInstance(0,list);
            mTransaction.add(mainBody.id, companyInfoSelectBarMenuFragment1!!)
        }
        if(index.equals(1)){
            var list=
                mutableListOf("全部","0~20人","20~99人","100~499人","500~999人","10000人以上")
                    .map { SelectedItem(it, false) }
                    .toMutableList()
            companyInfoSelectBarMenuFragment2= CompanyInfoSelectBarMenuFragment.newInstance(1,list);
            mTransaction.add(mainBody.id, companyInfoSelectBarMenuFragment2!!)
        }
        if(index.equals(2)){
            var list=
                mutableListOf("全部","電子商取引","ゲーム","メディア","販売促進","データ分析","O2O","其它")
                    .map { SelectedItem(it, false) }
                    .toMutableList()
            companyInfoSelectBarMenuFragment3= CompanyInfoSelectBarMenuFragment.newInstance(2,list);
            mTransaction.add(mainBody.id, companyInfoSelectBarMenuFragment3!!)
        }
        if(index.equals(3)){
            var list=
                mutableListOf("全部","直接雇用","派遣会社経由","ヘッドハンティング会社経由")
                    .map { SelectedItem(it, false) }
                    .toMutableList()
            companyInfoSelectBarMenuFragment4= CompanyInfoSelectBarMenuFragment.newInstance(3,list);
            mTransaction.add(mainBody.id, companyInfoSelectBarMenuFragment4!!)
        }

        mTransaction.commit()
    }


    //收回下拉框
    override fun shadowClicked() {

        var mTransaction=supportFragmentManager.beginTransaction()
        if(companyInfoSelectBarMenuFragment1!=null){
            mTransaction.setCustomAnimations(
                R.anim.top_out,  R.anim.top_out)
            mTransaction.remove(companyInfoSelectBarMenuFragment1!!)
            companyInfoSelectBarMenuFragment1=null
        }
        if(companyInfoSelectBarMenuFragment2!=null){
            mTransaction.setCustomAnimations(
                R.anim.top_out,  R.anim.top_out)
            mTransaction.remove(companyInfoSelectBarMenuFragment2!!)
            companyInfoSelectBarMenuFragment2=null
        }
        if(companyInfoSelectBarMenuFragment3!=null){
            mTransaction.setCustomAnimations(
                R.anim.top_out,  R.anim.top_out)
            mTransaction.remove(companyInfoSelectBarMenuFragment3!!)
            companyInfoSelectBarMenuFragment3=null
        }
        if(companyInfoSelectBarMenuFragment4!=null){
            mTransaction.setCustomAnimations(
                R.anim.top_out,  R.anim.top_out)
            mTransaction.remove(companyInfoSelectBarMenuFragment4!!)
            companyInfoSelectBarMenuFragment4=null
        }

        fragmentFadeOut(shadowFragment)
        shadowFragment=null

        mTransaction.commit()

    }

    override fun onStart() {
        super.onStart()
        setActionBar(companyInfoActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@CompanyInfoShowActivity, 0, companyInfoActionBarFragment.toolbar1)
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
        PushAgent.getInstance(this).onAppStart();
        frameLayout {
            backgroundColor=Color.WHITE
            verticalLayout {
                //ActionBar
                var actionBarId=2
               frameLayout{
                    id=actionBarId
                   companyInfoActionBarFragment= CompanyInfoActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,companyInfoActionBarFragment).commit()
                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                //selectBar
                var selectBarId=3
                selectBar= frameLayout {
                    id=selectBarId
                    var companyInfoSelectbarFragment= CompanyInfoSelectbarFragment.newInstance("","","","");
                    supportFragmentManager.beginTransaction().replace(id,companyInfoSelectbarFragment!!).commit()
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
                            var infoListFragment= CompanyInfoListFragment.newInstance();
                            supportFragmentManager.beginTransaction().replace(id,infoListFragment!!).commit()
                        }.lparams {
                            height=0
                            weight=1f
                            width= matchParent
                        }
                        //menu
                        var bottomMenuId=5
                        frameLayout {
                            id=bottomMenuId
                            var recruitInfoBottomMenuFragment= RecruitInfoBottomMenuFragment.newInstance(1);
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


    fun fragmentTopOut(fra: Fragment?){
        var mTransaction=supportFragmentManager.beginTransaction()
        if(fra!=null){
            mTransaction.setCustomAnimations(
                R.anim.top_out,  R.anim.top_out)
            mTransaction.remove(fra!!)
        }
        mTransaction.commit()
    }


    fun fragmentFadeOut(fra: Fragment?){
        var mTransaction=supportFragmentManager.beginTransaction()
        if(fra!=null){
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)
            mTransaction.remove(fra!!)
        }
        mTransaction.commit()
    }

}
