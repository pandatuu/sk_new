package com.example.sk_android.mvp.view.fragment.person

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.jobselect.Job
import com.example.sk_android.mvp.model.jobselect.JobContainer
import com.example.sk_android.mvp.view.adapter.company.BaseFragmentAdapter
import com.example.sk_android.mvp.view.adapter.person.InterviewListAdapter
import com.example.sk_android.mvp.view.fragment.jobselect.ProductDetailInfoBottomPartFragment
import com.example.sk_android.mvp.view.fragment.jobselect.RecruitInfoListFragment

class InterviewListFragment : Fragment() {


    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): InterviewListFragment {
            val fragment = InterviewListFragment()
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
        return UI {
            linearLayout {
                verticalLayout {

                    backgroundColorResource=R.color.originColor
                    //backgroundColorResource=R.color.black20
                    recyclerView{
                        overScrollMode = View.OVER_SCROLL_NEVER
                        var manager=LinearLayoutManager(this.getContext())
                        setLayoutManager(manager)
                        //manager.setStackFromEnd(true);
                        setAdapter(InterviewListAdapter(this,  jobContainer) { item ->
                        })
                    }.lparams {

                    }
                }.lparams {
                    width= matchParent
                    height=matchParent
                }
            }
        }.view
    }



}

