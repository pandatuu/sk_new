package com.example.sk_android.mvp.view.fragment.jobSelect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.jobSelect.Job
import com.example.sk_android.mvp.model.jobSelect.JobContainer
import com.example.sk_android.mvp.view.adapter.message.MessageChatRecordListAdapter

class MessageChatRecordListFragment : Fragment() {


    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): MessageChatRecordListFragment {
            val fragment = MessageChatRecordListFragment()
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

        var jc1= JobContainer("株式会社日本電気",
            arrayOf(p0,p2,p4))
        var jc5= JobContainer("成都アニメバレー",
            arrayOf(p1,p3,p2))
        var jc2= JobContainer("株式会社日本電気",
            arrayOf(p2,p3,p4))
        var jc3= JobContainer("株式会社日本電気",
            arrayOf(p3,p3,p2))
        var jc4= JobContainer("株式会社日本電気",
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
                linearLayout {
                    recyclerView{
                        overScrollMode = View.OVER_SCROLL_NEVER
                        var manager=LinearLayoutManager(this.getContext())
                        setLayoutManager(manager)
                        //manager.setStackFromEnd(true);
                        setAdapter(MessageChatRecordListAdapter(this,  jobContainer) { item ->
                        })
                    }.lparams {
                        topMargin=dip(8)
                        leftMargin=dip(14)
                        rightMargin=dip(14)
                    }
                }.lparams {
                    width= matchParent
                    height=matchParent
                }
            }
        }.view
    }



}

