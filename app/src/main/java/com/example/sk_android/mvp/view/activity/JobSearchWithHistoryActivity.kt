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
    JobSearchUnderSearchingDisplayFragment.UnderSearching {

    var jobSearcherHistoryFragment:JobSearcherHistoryFragment?=null
    var jobSearchUnderSearchingDisplayFragment:JobSearchUnderSearchingDisplayFragment?=null
    lateinit var recycleViewParent:FrameLayout
    var list = LinkedList<Map<String, Any>>()
    var histroyList: Array<String> = arrayOf("電子商取引","ソフトウエア","メディア","販売促進","データ分析","移动インターネット","ソフトウエア","インターネット")

    //退出
    override fun cancle() {
        toast("退出")
    }

    //选中搜索中展示的结果
    override fun getUnderSearchingItem(item: JobSearchUnderSearching) {
        toast(item.toString())
    }

    /**
     * 选中历史搜索
     */
    override fun sendHistoryText(item: String) {

        toast(item)
        var mTransaction=supportFragmentManager.beginTransaction()
        if(jobSearcherHistoryFragment!=null)
            mTransaction.remove(jobSearcherHistoryFragment!!)

       // jobTypeDetailFragment= JobTypeDetailFragment.newInstance(item);
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
      //  mTransaction.add(recycleViewParent.id, jobTypeDetailFragment!!)
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
     * 输入框搜索职位
     */
    override fun sendMessage(msg: String) {
        toast(msg)
        var mTransaction=supportFragmentManager.beginTransaction()
        if(jobSearcherHistoryFragment!=null)
            mTransaction.remove(jobSearcherHistoryFragment!!)

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
                    var childFragment=JobSearcherWithHistoryFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,childFragment).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                //list
                var recycleViewParentId=3
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
