package com.example.sk_android.mvp.view.activity.jobselect


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.view.*
import android.widget.*
import cn.jiguang.imui.chatinput.emoji.EmoticonsKeyboardUtils
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobselect.Job
import com.example.sk_android.mvp.model.jobselect.JobContainer
import com.example.sk_android.mvp.model.jobselect.JobSearchResult
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.jobselect.*
import org.jetbrains.anko.*
import java.util.*
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent


class
JobSelectActivity : AppCompatActivity(), JobSearcherFragment.SendSearcherText, IndustryListFragment.ItemSelected,
    JobTypeDetailFragment.JobItemSelected,
    ShadowFragment.ShadowClick, ActionBarFragment.ActionBarSaveButton, JobSearchResultFragment.JobSearchResultModel
{





    var jobTypeDetailFragment:JobTypeDetailFragment?=null
    var shadowFragment: ShadowFragment?=null
    var industryListFragment:IndustryListFragment?=null
    var jobSearchResultFragment:JobSearchResultFragment?=null
    var jobSearcherFragment:JobSearcherFragment?=null


    lateinit var actionBarChildFragment:ActionBarFragment
    lateinit var recycleViewParent:FrameLayout
    private lateinit var toolbar1: Toolbar
    var list = LinkedList<Map<String, Any>>()


    private var theSelectedJobIten:Job?=null


    //保存按钮被点击
    override fun saveButtonClicked() {
        if(theSelectedJobIten!=null){

            var mIntent = Intent()
            mIntent.putExtra("jobName", theSelectedJobIten!!.name)
            mIntent.putExtra("jobId", theSelectedJobIten!!.id)

            setResult(AppCompatActivity.RESULT_OK, mIntent)
            finish()//返回
            overridePendingTransition(R.anim.left_in,R.anim.right_out)


        }else{
            toast("你还没有选择!")
        }
    }





    //隐藏键盘
    override fun hideSoftKeyboard() {
        EmoticonsKeyboardUtils.closeSoftKeyboard(jobSearcherFragment!!.getEditTextView())

    }


    //得到最终选择的行业
    override fun getSelectedJobItem(item: Job) {
        theSelectedJobIten=item

    }


    //从搜索结果中得到的选项
    override fun getSearchResultSelectedItem(item: JobSearchResult) {
        var job=Job(item.name,1,item.id)
        theSelectedJobIten=job

    }


    /**
     * 阴影部分被点击，职业详情列表隐藏
     */
    override fun shadowClicked() {
        var mTransaction=supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        if(jobTypeDetailFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.right_out,  R.anim.right_out)
            mTransaction.remove(jobTypeDetailFragment!!)

        }

        if(shadowFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)
            mTransaction.remove(shadowFragment!!)

        }

        mTransaction.commit()
    }

    /**
     * 选中，职业详情列表展示
     */
    override fun getSelectedItem(item: JobContainer) {
        toast(item.containerName)
        var mTransaction=supportFragmentManager.beginTransaction()
        if(jobTypeDetailFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.right_out,
                R.anim.right_out)
            mTransaction.remove(jobTypeDetailFragment!!)
        }
        if(shadowFragment!=null)
            mTransaction.remove(shadowFragment!!)



        jobTypeDetailFragment= JobTypeDetailFragment.newInstance(item);

        shadowFragment= ShadowFragment.newInstance();
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
       // mTransaction.add(recycleViewParent.id,shadowFragment!!)
        mTransaction.setCustomAnimations(
            R.anim.right_in,
            R.anim.right_out)
        mTransaction.add(recycleViewParent.id, jobTypeDetailFragment!!).commit()
    }

    /**
     * 搜索职位
     */
    override fun sendMessage(msg: String) {
        var mTransaction=supportFragmentManager.beginTransaction()
        if(jobTypeDetailFragment!=null)
            mTransaction.remove(jobTypeDetailFragment!!)
        if(shadowFragment!=null)
            mTransaction.remove(shadowFragment!!)
        if(industryListFragment!=null)
            mTransaction.remove(industryListFragment!!)

        if(msg.trim().isEmpty()){
            //复原
            industryListFragment= IndustryListFragment.newInstance()
            mTransaction.replace(recycleViewParent.id,industryListFragment!!)
        }else{

            var list:MutableList<JobSearchResult> = mutableListOf()
            for(i in 0..IndustryListFragment.dataList.size-1){
               var itemJobContainer =IndustryListFragment.dataList.get(i)
               for(j in 0..itemJobContainer.item.size-1){
                   var jobItem=itemJobContainer.item.get(j)
                   if(jobItem.name.contains(msg)){
                       //找到了
                       var resultItem=JobSearchResult(jobItem.name,jobItem.id,itemJobContainer.containerName,1)
                       list.add(resultItem)
                   }
               }
            }


            //展示搜索结果
            jobSearchResultFragment=JobSearchResultFragment.newInstance(list)
            mTransaction.replace(recycleViewParent.id,jobSearchResultFragment!!)

        }
        mTransaction.commit()
    }

    override fun onStart() {
        super.onStart()
        setActionBar(actionBarChildFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@JobSelectActivity, 0, actionBarChildFragment.toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)


        actionBarChildFragment.toolbar1?.setNavigationOnClickListener {
            finish()//返回
           overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }


    }

    @SuppressLint("ResourceAsColor", "RestrictedApi", "ResourceType")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        verticalLayout {
                backgroundColor = Color.WHITE
                //ActionBar
                var actionBarId=2
                frameLayout{
                    id=actionBarId
                    actionBarChildFragment= ActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,actionBarChildFragment).commit()


                }.lparams {
                    height= wrapContent
                    width= matchParent
                }
                //搜索框
                var searchId=1
                frameLayout{
                    id=searchId
                    jobSearcherFragment=JobSearcherFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,jobSearcherFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                //list
                var recycleViewParentId=3
                recycleViewParent=frameLayout {
                    id=recycleViewParentId
                    industryListFragment= IndustryListFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,industryListFragment!!).commit()


                }.lparams {
                    height=matchParent
                    width= matchParent
                }


        }

    }



}
