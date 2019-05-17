package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.jobselect.Job
import com.example.sk_android.mvp.model.jobselect.JobContainer
import com.example.sk_android.mvp.view.adapter.jobselect.IndustryListAdapter

class IndustryListFragment : Fragment() {

    private lateinit var itemSelected:ItemSelected
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): IndustryListFragment {
            val fragment = IndustryListFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        itemSelected =  activity as ItemSelected
        return fragmentView
    }


    fun createView(): View {


        var job: MutableList<Job> = mutableListOf()
        var p0= Job("销售管理",
            arrayOf("团杜经理","销售总监","城市经理","販売促進","データ分析","データ分析","移动インターネット","ソフトウエア","インターネット"))
        var p1= Job("销售",
            arrayOf("销售专员","销售顾问","销售经理","电话销售","信托","互联网金融","投资/融资","租赁/拍卖/典当/担保"))
        var p2= Job("行政",
            arrayOf("前台","后倾","4S店/期后市场"))
        var p3= Job("财务",
            arrayOf("会计","工程施工","建筑设计","装修装饰","建材","地产经纪/中介","物业服务"))
        var p4= Job("广告",
            arrayOf("策划经理","文案","没接投放","广告创意","广告审核","地产经纪/中介","物业服务"))

        var jobContainer: MutableList<JobContainer> = mutableListOf()

        var jc1= JobContainer("销售",
            arrayOf(p0,p2,p4))
        var jc5= JobContainer("人士",
            arrayOf(p1,p3,p2))
        var jc2= JobContainer("高级经理",
            arrayOf(p2,p3,p4))
        var jc3= JobContainer("技术",
            arrayOf(p3,p3,p2))
        var jc4= JobContainer("金融",
            arrayOf(p4,p2,p1))


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
                recyclerView{
                    overScrollMode = View.OVER_SCROLL_NEVER
                    setLayoutManager(LinearLayoutManager(this.getContext()))
                    setAdapter(IndustryListAdapter(this,  jobContainer) { item ->
                        itemSelected.getSelectedItem(item)
                    })
                }.lparams {
                    leftMargin=dip(15)
                    rightMargin=dip(15)
                }
            }
        }.view

    }

    public interface ItemSelected {

        fun getSelectedItem(item:JobContainer )
    }

}



