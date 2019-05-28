package com.example.sk_android.mvp.view.activity.jobselect


import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.sk_android.R

import com.example.sk_android.mvp.model.jobselect.JobSearchUnderSearching
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoListFragment
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoSelectBarMenuFragment
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoSelectbarFragment
import com.example.sk_android.mvp.view.fragment.jobselect.*
import org.jetbrains.anko.*
import java.util.*



class JobSearchWithHistoryActivity : AppCompatActivity(), JobSearcherWithHistoryFragment.SendSearcherText, JobSearcherHistoryFragment.HistoryText,
    JobSearchSelectbarFragment.JobSearchSelectBar,
    JobSearchUnderSearchingDisplayFragment.UnderSearching,ShadowFragment.ShadowClick,RecruitInfoSelectBarMenuCompanyFragment.RecruitInfoSelectBarMenuCompanySelect,
RecruitInfoSelectBarMenuRequireFragment.RecruitInfoSelectBarMenuRequireSelect,
    CompanyInfoSelectbarFragment.SelectBar, CompanyInfoSelectBarMenuFragment.SelectBarMenuSelect
{


    var type_job_or_company_search=2

    var jobSearcherWithHistoryFragment:JobSearcherWithHistoryFragment?=null
    var jobSearcherHistoryFragment:JobSearcherHistoryFragment?=null
    var recruitInfoListFragment:RecruitInfoListFragment?=null
    var jobSearchUnderSearchingDisplayFragment:JobSearchUnderSearchingDisplayFragment?=null
    var jobSearchSelectbarFragment:JobSearchSelectbarFragment?=null
    var recruitInfoSelectBarMenuCompanyFragment:RecruitInfoSelectBarMenuCompanyFragment?=null
    var recruitInfoSelectBarMenuRequireFragment:RecruitInfoSelectBarMenuRequireFragment?=null

    var shadowFragment: ShadowFragment?=null

    lateinit var recycleViewParent:FrameLayout
    lateinit var searchBarParent:FrameLayout


    var selectBarShow1:String=""
    var selectBarShow2:String=""

    var selectBarShow3:String=""
    var selectBarShow4:String=""

    var list = LinkedList<Map<String, Any>>()
    var histroyList: Array<String> = arrayOf("電子商取引","ソフトウエア","メディア","販売促進","データ分析","移动インターネット","ソフトウエア","インターネット")






    var companyInfoSelectBarMenuFragment1: CompanyInfoSelectBarMenuFragment?=null
    var companyInfoSelectBarMenuFragment2: CompanyInfoSelectBarMenuFragment?=null
    var companyInfoSelectBarMenuFragment3: CompanyInfoSelectBarMenuFragment?=null
    var companyInfoSelectBarMenuFragment4: CompanyInfoSelectBarMenuFragment?=null


    //更改 公司搜索 的 select bar 的显示
    override fun getSelectedItems(index: Int, list: MutableList<String>?) {
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
        mTransaction.replace(searchBarParent.id,companyInfoSelectbarFragment!!)


        fragmentFadeOut(shadowFragment)
        shadowFragment=null

        mTransaction.commit()

    }



    //职位搜索 条件选择
    override fun getRequireSelectedItems(map: MutableMap<String, String>?) {
        var mTransaction=supportFragmentManager.beginTransaction()

        if(map!=null && map.keys.size!=0){
            selectBarShow4=map.keys.size.toString()
            toast(map.toString())
        }else{
            selectBarShow4=""
        }

        jobSearchSelectbarFragment= JobSearchSelectbarFragment.newInstance(selectBarShow3,selectBarShow4);
        mTransaction.replace(searchBarParent.id,jobSearchSelectbarFragment!!)

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

    //职位搜索 条件选择
    override fun getCompanySelectedItems(map: MutableMap<String, String>?) {
        var mTransaction=supportFragmentManager.beginTransaction()

        if(map!=null && map.keys.size!=0){
            selectBarShow3=map.keys.size.toString()
            toast(map.toString())
        }else{
            selectBarShow3=""
        }

        jobSearchSelectbarFragment= JobSearchSelectbarFragment.newInstance(selectBarShow3,selectBarShow4);
        mTransaction.replace(searchBarParent.id,jobSearchSelectbarFragment!!)

        if(recruitInfoSelectBarMenuCompanyFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.top_out,  R.anim.top_out)
            mTransaction.remove(recruitInfoSelectBarMenuCompanyFragment!!)
            recruitInfoSelectBarMenuCompanyFragment=null
        }
        if(shadowFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)
            mTransaction.remove(shadowFragment!!)
            shadowFragment=null

        }
        mTransaction.commit()
    }



    override fun shadowClicked() {
        var mTransaction=supportFragmentManager.beginTransaction()

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


    override fun getSelectBarItem(index: Int) {
        toast(index.toString())
        var mTransaction=supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)



       if(type_job_or_company_search==1){
           if(recruitInfoSelectBarMenuCompanyFragment!=null && index.equals(2)){
               if(recruitInfoSelectBarMenuCompanyFragment!=null){
                   mTransaction.setCustomAnimations(
                       R.anim.top_out,  R.anim.top_out)
                   mTransaction.remove(recruitInfoSelectBarMenuCompanyFragment!!)
                   recruitInfoSelectBarMenuCompanyFragment=null
               }
               if(shadowFragment!=null){
                   mTransaction.setCustomAnimations(
                       R.anim.fade_in_out,  R.anim.fade_in_out)
                   mTransaction.remove(shadowFragment!!)
                   shadowFragment=null

               }
               mTransaction.commit()
               return
           }

           if(recruitInfoSelectBarMenuRequireFragment!=null && index.equals(3)){
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
               return
           }


           if(recruitInfoSelectBarMenuCompanyFragment!=null){
               mTransaction.remove(recruitInfoSelectBarMenuCompanyFragment!!)
               recruitInfoSelectBarMenuCompanyFragment=null
           }
           if(recruitInfoSelectBarMenuRequireFragment!=null){
               mTransaction.remove(recruitInfoSelectBarMenuRequireFragment!!)
               recruitInfoSelectBarMenuRequireFragment=null
           }

           if(shadowFragment==null){
               shadowFragment= ShadowFragment.newInstance();
               mTransaction.add(recycleViewParent.id,shadowFragment!!)
           }

           mTransaction.setCustomAnimations(
               R.anim.top_in,
               R.anim.top_in)


           if(index.equals(2)){
               recruitInfoSelectBarMenuCompanyFragment= RecruitInfoSelectBarMenuCompanyFragment.newInstance();
               mTransaction.add(recycleViewParent.id, recruitInfoSelectBarMenuCompanyFragment!!)
           }
           if(index.equals(3)){
               recruitInfoSelectBarMenuRequireFragment= RecruitInfoSelectBarMenuRequireFragment.newInstance();
               mTransaction.add(recycleViewParent.id, recruitInfoSelectBarMenuRequireFragment!!)
           }

       }else if(type_job_or_company_search==2){
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
               mTransaction.add(recycleViewParent.id,shadowFragment!!)
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
               mTransaction.add(recycleViewParent.id, companyInfoSelectBarMenuFragment1!!)
           }
           if(index.equals(1)){
               var list=
                   mutableListOf("全部","0~20人","20~99人","100~499人","500~999人","10000人以上")
                       .map { SelectedItem(it, false) }
                       .toMutableList()
               companyInfoSelectBarMenuFragment2= CompanyInfoSelectBarMenuFragment.newInstance(1,list);
               mTransaction.add(recycleViewParent.id, companyInfoSelectBarMenuFragment2!!)
           }
           if(index.equals(2)){
               var list=
                   mutableListOf("全部","電子商取引","ゲーム","メディア","販売促進","データ分析","O2O","其它")
                       .map { SelectedItem(it, false) }
                       .toMutableList()
               companyInfoSelectBarMenuFragment3= CompanyInfoSelectBarMenuFragment.newInstance(2,list);
               mTransaction.add(recycleViewParent.id, companyInfoSelectBarMenuFragment3!!)
           }
           if(index.equals(3)){
               var list=
                   mutableListOf("全部","直接雇用","派遣会社経由","ヘッドハンティング会社経由")
                       .map { SelectedItem(it, false) }
                       .toMutableList()
               companyInfoSelectBarMenuFragment4= CompanyInfoSelectBarMenuFragment.newInstance(3,list);
               mTransaction.add(recycleViewParent.id, companyInfoSelectBarMenuFragment4!!)
           }

       }



        mTransaction.commit()
    }

    //退出
    override fun cancle() {
        toast("退出")
    }

    //选中 搜索中展示的结果   展示出主信息
    override fun getUnderSearchingItem(item: JobSearchUnderSearching) {
        toast(item.toString())
        var mTransaction=supportFragmentManager.beginTransaction()
        if(jobSearcherHistoryFragment!=null)
            mTransaction.remove(jobSearcherHistoryFragment!!)
        if(recruitInfoListFragment!=null)
            mTransaction.remove(recruitInfoListFragment!!)
        if(jobSearchSelectbarFragment!=null)
            mTransaction.remove(jobSearchSelectbarFragment!!)


        if(type_job_or_company_search==1){
            jobSearchSelectbarFragment= JobSearchSelectbarFragment.newInstance("","");
            mTransaction.replace(searchBarParent.id,jobSearchSelectbarFragment!!)

            recruitInfoListFragment= RecruitInfoListFragment.newInstance();
            mTransaction.replace(recycleViewParent.id,recruitInfoListFragment!!)
        }else if(type_job_or_company_search==2){
            var companyInfoSelectbarFragment= CompanyInfoSelectbarFragment.newInstance("","","","");
            mTransaction.replace(searchBarParent.id,companyInfoSelectbarFragment!!)
            var infoListFragment= CompanyInfoListFragment.newInstance();
            mTransaction.replace(recycleViewParent.id,infoListFragment!!)
        }



        var imm: InputMethodManager=getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(jobSearcherWithHistoryFragment!!.editText.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        jobSearcherWithHistoryFragment!!.editText.clearFocus()
        mTransaction.commit()
    }

    /**
     * 选中 历史搜索  展示出主信息
     */
    override fun sendHistoryText(item: String) {

        toast(item)
        var mTransaction=supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if(jobSearcherHistoryFragment!=null)
            mTransaction.remove(jobSearcherHistoryFragment!!)
        if(recruitInfoListFragment!=null)
            mTransaction.remove(recruitInfoListFragment!!)
        if(jobSearchSelectbarFragment!=null)
            mTransaction.remove(jobSearchSelectbarFragment!!)



        if(type_job_or_company_search==1){
            jobSearchSelectbarFragment= JobSearchSelectbarFragment.newInstance("","");
            mTransaction.replace(searchBarParent.id,jobSearchSelectbarFragment!!)

            recruitInfoListFragment= RecruitInfoListFragment.newInstance();
            mTransaction.replace(recycleViewParent.id,recruitInfoListFragment!!)
        }else if(type_job_or_company_search==2){
            var companyInfoSelectbarFragment= CompanyInfoSelectbarFragment.newInstance("","","","");
            mTransaction.replace(searchBarParent.id,companyInfoSelectbarFragment!!)
            var infoListFragment= CompanyInfoListFragment.newInstance();
            mTransaction.replace(recycleViewParent.id,infoListFragment!!)
        }



        var imm: InputMethodManager=getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(jobSearcherWithHistoryFragment!!.editText.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


        jobSearcherWithHistoryFragment!!.editText.clearFocus()
        mTransaction.commit()
    }

    override fun clearHistroy() {
        var mTransaction=supportFragmentManager.beginTransaction()
        if(jobSearcherHistoryFragment!=null)
            mTransaction.remove(jobSearcherHistoryFragment!!)

        var list: Array<String> = arrayOf()
        jobSearcherHistoryFragment= JobSearcherHistoryFragment.newInstance(list)
        mTransaction.replace(recycleViewParent.id,jobSearcherHistoryFragment!!)
        mTransaction.commit()
    }

    /**
     * 输入框 搜索职位
     */
    override fun sendMessage(msg: String) {
        toast(msg)
        var mTransaction=supportFragmentManager.beginTransaction()
        if(jobSearcherHistoryFragment!=null)
            mTransaction.remove(jobSearcherHistoryFragment!!)
        if(recruitInfoListFragment!=null)
            mTransaction.remove(recruitInfoListFragment!!)
        if(jobSearchSelectbarFragment!=null)
            mTransaction.remove(jobSearchSelectbarFragment!!)

        if(msg.trim().isEmpty()){
            //复原
            jobSearcherHistoryFragment= JobSearcherHistoryFragment.newInstance(histroyList)
            mTransaction.replace(recycleViewParent.id,jobSearcherHistoryFragment!!)
        }else{
            //展示搜索结果
            var j1=JobSearchUnderSearching("PHP","技術サーバー開発")
            var j2=JobSearchUnderSearching("PHP","教育-IT")
            var j3=JobSearchUnderSearching("PHP","网站")
            var list:Array<JobSearchUnderSearching> = arrayOf<JobSearchUnderSearching>(j1,j2,j3,j2,j1)

            jobSearchUnderSearchingDisplayFragment=JobSearchUnderSearchingDisplayFragment.newInstance(list)
            mTransaction.replace(recycleViewParent.id,jobSearchUnderSearchingDisplayFragment!!)

        }
        mTransaction.commit()
    }



    override fun onStart() {
        super.onStart()
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }

    @SuppressLint("ResourceAsColor", "RestrictedApi", "ResourceType")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            verticalLayout {
                backgroundColor = Color.WHITE

                //搜索框
                var searchId=1
                frameLayout{
                    id=searchId
                    jobSearcherWithHistoryFragment=JobSearcherWithHistoryFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,jobSearcherWithHistoryFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                //search bar
                var searchBarParentId=3
                searchBarParent=frameLayout {
                    id=searchBarParentId

                }.lparams {
                    height=wrapContent
                    width= matchParent
                }

                //list
                var recycleViewParentId=4
                recycleViewParent=frameLayout {
                    id=recycleViewParentId
                    jobSearcherHistoryFragment= JobSearcherHistoryFragment.newInstance(histroyList);
                    supportFragmentManager.beginTransaction().replace(id,jobSearcherHistoryFragment!!).commit()

                }.lparams {
                    height=matchParent
                    width= matchParent
                }
        }
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