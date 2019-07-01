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
import org.jetbrains.anko.support.v4.onPageChangeListener

class InterviewListSelectShowFragment : Fragment() {


    private var mContext: Context? = null

    lateinit var  viewPageSelectBar:ViewPageSelectBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
        viewPageSelectBar= activity as ViewPageSelectBar

    }

    companion object {
        fun newInstance(): InterviewListSelectShowFragment {
            val fragment = InterviewListSelectShowFragment()
            return fragment
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        return fragmentView
    }

    fun createView(): View {
        var job: MutableList<Job> = mutableListOf()

        var p0=
            mutableListOf("团杜经理","销售总监","城市经理","販売促進","データ分析","データ分析","移动インターネット","ソフトウエア","インターネット").map {
                Job(it,1,"xxxx")
            }.toMutableList()
        var p1=
            mutableListOf("销售专员","销售顾问","销售经理","电话销售","信托","互联网金融","投资/融资","租赁/拍卖/典当/担保").map {
                Job(it,1,"xxxx")
            }.toMutableList()
        var p2=
            mutableListOf("前台","后倾","4S店/期后市场").map {
                Job(it,1,"xxxx")
            }.toMutableList()
        var p3=
            mutableListOf("会计","工程施工","建筑设计","装修装饰","建材","地产经纪/中介","物业服务").map {
                Job(it,1,"xxxx")
            }.toMutableList()
        var p4=
            mutableListOf("策划经理","文案","没接投放","广告创意","广告审核","地产经纪/中介","物业服务").map {
                Job(it,1,"xxxx")
            }.toMutableList()

        var jobContainer: MutableList<JobContainer> = mutableListOf()

        var jc1= JobContainer("销售",1,p0)
        var jc5= JobContainer("人士",1,p2)
        var jc2= JobContainer("高级经理",1,p1)
        var jc3= JobContainer("技术",1,p3)
        var jc4= JobContainer("金融",1,p4)


        jobContainer.add(jc1)
        jobContainer.add(jc2)
        jobContainer.add(jc3)
        jobContainer.add(jc4)
        jobContainer.add(jc5)




        var page=  LayoutInflater.from(context).inflate(R.layout.page_view, null)
        val viewPager = page.findViewById(R.id.myViewPager) as ViewPager

        var view= UI {
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



                    //    var mTitles = arrayOf("予約済み", "承認待ち","完了","取り消し")

                        var mTitles = arrayOf("予約済み", "承認待ち","完了","取り消し")

                        val interviewListFragmentAppointed = InterviewListFragmentAppointed.newInstance()

                        mFragments.add(interviewListFragmentAppointed)

                        val interviewListFragmentAppointing= InterviewListFragmentAppointing.newInstance()
                       mFragments.add(interviewListFragmentAppointing)

                        val interviewListFragmentFinished = InterviewListFragmentFinished.newInstance()
                        mFragments.add(interviewListFragmentFinished)

                        val interviewListFragmentRejected = InterviewListFragmentRejected.newInstance()
                        mFragments.add(interviewListFragmentRejected)


                        val adapter = BaseFragmentAdapter(getFragmentManager(), mFragments, mTitles)
                        viewPager.setAdapter(adapter)

                        viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
                            override fun onPageScrollStateChanged(p0: Int) {

                            }

                            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {


                            }

                            override fun onPageSelected(i: Int) {
                                viewPageSelectBar.getSelectedPageName(mTitles.get(i))
                                viewPager.setCurrentItem(i,true)
                            }

                        })

                        viewPager.setOffscreenPageLimit(4)

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



        return view

    }


    interface  ViewPageSelectBar{
        fun getSelectedPageName(s:String)
    }


}

