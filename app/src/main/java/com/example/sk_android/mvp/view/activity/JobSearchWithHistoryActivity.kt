package com.example.sk_android.mvp.view.activity


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.view.*
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.mvp.model.JobContainer
import com.example.sk_android.mvp.model.JobSearchResult
import com.example.sk_android.mvp.model.JobSearchUnderSearching
import com.example.sk_android.mvp.view.fragment.jobSelect.*
import org.jetbrains.anko.*
import java.util.*
import com.jaeger.library.StatusBarUtil


class JobSearchWithHistoryActivity : AppCompatActivity(), JobSearcherWithHistoryFragment.SendSearcherText, JobSearcherHistoryFragment.HistoryText,
    JobSearchSelectbarFragment.JobSearchSelectBar,
    JobSearchUnderSearchingDisplayFragment.UnderSearching,ShadowFragment.ShadowClick,RecruitInfoSelectBarMenuCompanyFragment.RecruitInfoSelectBarMenuCompanySelect,
RecruitInfoSelectBarMenuRequireFragment.RecruitInfoSelectBarMenuRequireSelect {


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

    var selectBarShow3:String=""
    var selectBarShow4:String=""

    var list = LinkedList<Map<String, Any>>()
    var histroyList: Array<String> = arrayOf("電子商取引","ソフトウエア","メディア","販売促進","データ分析","移动インターネット","ソフトウエア","インターネット")



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

        mTransaction.commit()
    }

    //退出
    override fun cancle() {
        toast("退出")
    }

    //选中 搜索中展示的结果
    override fun getUnderSearchingItem(item: JobSearchUnderSearching) {
        toast(item.toString())
        var mTransaction=supportFragmentManager.beginTransaction()
        if(jobSearcherHistoryFragment!=null)
            mTransaction.remove(jobSearcherHistoryFragment!!)
        if(recruitInfoListFragment!=null)
            mTransaction.remove(recruitInfoListFragment!!)
        if(jobSearchSelectbarFragment!=null)
            mTransaction.remove(jobSearchSelectbarFragment!!)

        jobSearchSelectbarFragment= JobSearchSelectbarFragment.newInstance("","");
        mTransaction.replace(searchBarParent.id,jobSearchSelectbarFragment!!)


        recruitInfoListFragment= RecruitInfoListFragment.newInstance();
        mTransaction.replace(recycleViewParent.id,recruitInfoListFragment!!)

        jobSearcherWithHistoryFragment!!.editText.clearFocus()
        jobSearcherWithHistoryFragment!!.delete.visibility=View.INVISIBLE
        mTransaction.commit()
    }

    /**
     * 选中 历史搜索
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

        jobSearchSelectbarFragment= JobSearchSelectbarFragment.newInstance("","");
        mTransaction.replace(searchBarParent.id,jobSearchSelectbarFragment!!)

        recruitInfoListFragment= RecruitInfoListFragment.newInstance();
        mTransaction.replace(recycleViewParent.id,recruitInfoListFragment!!)

        jobSearcherWithHistoryFragment!!.editText.clearFocus()
        jobSearcherWithHistoryFragment!!.delete.visibility=View.INVISIBLE
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
}
