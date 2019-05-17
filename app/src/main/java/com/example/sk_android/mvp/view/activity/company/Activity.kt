package com.example.sk_android.mvp.view.activity.company

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity;
import com.example.sk_android.R
import com.example.sk_android.mvp.view.adapter.company.BaseFragmentAdapter
import com.example.sk_android.mvp.view.fragment.jobselect.ProductDetailInfoBottomPartFragment
import com.example.sk_android.mvp.view.fragment.jobselect.RecruitInfoListFragment
import java.util.ArrayList


class Activity : AppCompatActivity() {




    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.jike_topic_detail_layout)


         var mFragments: MutableList<Fragment> = mutableListOf()



         var mTitles = arrayOf("精选", "广场")


        for (i in mTitles.indices) {
            val listFragment = RecruitInfoListFragment.newInstance()
            mFragments.add(listFragment)
        }

        val adapter = BaseFragmentAdapter(supportFragmentManager, mFragments, mTitles)

        val viewPager = findViewById(R.id.view_pager) as ViewPager
        viewPager.setAdapter(adapter)

        val tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(viewPager)


    }



}
