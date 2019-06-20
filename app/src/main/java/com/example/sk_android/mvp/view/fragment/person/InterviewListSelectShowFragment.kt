package com.example.sk_android.mvp.view.fragment.person

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.content.res.ColorStateList
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.jobselect.Job
import com.example.sk_android.mvp.model.jobselect.JobContainer
import com.example.sk_android.mvp.view.adapter.company.BaseFragmentAdapter
import com.example.sk_android.mvp.view.adapter.person.InterviewListAdapter
import com.example.sk_android.mvp.view.fragment.jobselect.ProductDetailInfoBottomPartFragment
import com.example.sk_android.mvp.view.fragment.jobselect.RecruitInfoListFragment

class InterviewListSelectShowFragment : Fragment() {


    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): InterviewListSelectShowFragment {
            val fragment = InterviewListSelectShowFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        return fragmentView
    }

    fun createView(): View {
        var job: MutableList<Job> = mutableListOf()
        var p0= Job("销售管理",
            mutableListOf("团杜经理","销售总监","城市经理","販売促進","データ分析","データ分析","移动インターネット","ソフトウエア","インターネット"))
        var p1= Job("销售",
            mutableListOf("销售专员","销售顾问","销售经理","电话销售","信托","互联网金融","投资/融资","租赁/拍卖/典当/担保"))
        var p2= Job("行政",
            mutableListOf("前台","后倾","4S店/期后市场"))
        var p3= Job("财务",
            mutableListOf("会计","工程施工","建筑设计","装修装饰","建材","地产经纪/中介","物业服务"))
        var p4= Job("广告",
            mutableListOf("策划经理","文案","没接投放","广告创意","广告审核","地产经纪/中介","物业服务"))

        var jobContainer: MutableList<JobContainer> = mutableListOf()

        var jc1= JobContainer("株式会社日本電気",
            mutableListOf(p0,p2,p4))
        var jc5= JobContainer("成都アニメバレー",
            mutableListOf(p1,p3,p2))
        var jc2= JobContainer("株式会社日本電気",
            mutableListOf(p2,p3,p4))
        var jc3= JobContainer("株式会社日本電気",
            mutableListOf(p3,p3,p2))
        var jc4= JobContainer("株式会社日本電気",
            mutableListOf(p4,p2,p1))


        jobContainer.add(jc1)
        jobContainer.add(jc2)
        jobContainer.add(jc3)
        jobContainer.add(jc4)
        jobContainer.add(jc5)

        jobContainer.add(jc1)
        jobContainer.add(jc2)
        jobContainer.add(jc3)
        jobContainer.add(jc3)

        jobContainer.add(jc3)

        var page=  LayoutInflater.from(context).inflate(R.layout.page_view, null)
        val viewPager = page.findViewById(R.id.myViewPager) as ViewPager


        return UI {
            linearLayout {
                verticalLayout {


                    backgroundResource=R.color.originColor
                    frameLayout {

                        var tab=  LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
                        addView(tab)
                        val tabLayout = tab.findViewById(R.id.myTab) as TabLayout
                        tabLayout.setTabRippleColor(ColorStateList.valueOf(getContext().getResources().getColor(R.color.white)));


                        tabLayout.setupWithViewPager(viewPager)
                    }.lparams {
                        width=matchParent
                        height= wrapContent
                    }

                    frameLayout {


                        addView(page)


                        var mFragments: MutableList<Fragment> = mutableListOf()



                        var mTitles = arrayOf("予約済み", "承認待ち","完了","取り消し")



                        val listFragment1 = InterviewListFragment.newInstance()
                        mFragments.add(listFragment1)

                        val listFragment2 = InterviewListFragment.newInstance()
                        mFragments.add(listFragment2)

                        val listFragment3 = InterviewListFragment.newInstance()
                        mFragments.add(listFragment3)

                        val listFragment4 = InterviewListFragment.newInstance()
                        mFragments.add(listFragment4)



                        val adapter = BaseFragmentAdapter(getFragmentManager(), mFragments, mTitles)
                        viewPager.setAdapter(adapter)

                    }.lparams {
                        width=matchParent
                        height= wrapContent
                        topMargin=dip(8)
                    }

                }.lparams {
                    width= matchParent
                    height=matchParent
                }
            }
        }.view
    }



}

