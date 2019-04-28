package com.example.sk_android.mvp.view.activity


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import android.view.*
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.custom.layout.*
import com.example.sk_android.mvp.model.Industry
import com.example.sk_android.mvp.model.Job
import com.example.sk_android.mvp.view.adapter.JobListAdapter
import com.example.sk_android.mvp.view.adapter.ProvinceShowAdapter
import com.example.sk_android.mvp.view.fragment.jobSelect.ActionBarFragment


import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import com.jaeger.library.StatusBarUtil


import com.example.sk_android.mvp.view.fragment.jobSelect.JobSearcherFragment
import com.example.sk_android.mvp.view.fragment.jobSelect.SendSearcherText
import kotlinx.android.synthetic.main.fragment_base.*





class JobSelectActivity : AppCompatActivity(), SendSearcherText {




    override fun sendMessage(msg: String) {
       toast(msg)
    }

    lateinit var actionBarChildFragment:ActionBarFragment

    private lateinit var toolbar1: Toolbar
    var list = LinkedList<Map<String, Any>>()


    override fun onStart() {
        super.onStart()

        setActionBar(actionBarChildFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@JobSelectActivity, 0, actionBarChildFragment.toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }


    @SuppressLint("ResourceAsColor", "RestrictedApi", "ResourceType")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



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
                    var childFragment=JobSearcherFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,childFragment).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }


                var job: MutableList<Job> = mutableListOf()
                var p0= Job("インターネット/IT/电子/通信",
                    arrayOf("電子商取引","ソフトウエア","メディア","販売促進","データ分析","データ分析","移动インターネット","ソフトウエア","インターネット"))
                var p1= Job("金融",
                    arrayOf("银行","保险","证券/期货","基金","信托","互联网金融","投资/融资","租赁/拍卖/典当/担保"))
                var p2= Job("汽车",
                    arrayOf("汽车生产","汽车零部件","4S店/期后市场"))
                var p3= Job("建筑/房地产",
                    arrayOf("房地产开发","工程施工","建筑设计","装修装饰","建材","地产经纪/中介","物业服务"))

                job.add(p0)
                job.add(p1)
                job.add(p2)
                job.add(p3)



                //list
                var recycleViewParentId=3
                var recycleViewParent= verticalLayout {
                    backgroundColor=Color.RED
                    id=recycleViewParentId
                    var childFragment=JobSearcherFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,childFragment).commit()
                    recyclerView{
                       overScrollMode = View.OVER_SCROLL_NEVER
                        setLayoutManager(LinearLayoutManager(this.getContext()))
                        setAdapter(JobListAdapter(this,  professions) { item ->

                        })
                    }
                }.lparams {
                    height=wrapContent
                    width= matchParent
                    leftMargin=dip(15)
                    rightMargin=dip(15)
                }


        }








    }




}
