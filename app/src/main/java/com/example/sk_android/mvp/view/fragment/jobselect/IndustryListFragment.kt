package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.jobselect.Job
import com.example.sk_android.mvp.model.jobselect.JobContainer
import com.example.sk_android.mvp.view.adapter.jobselect.IndustryListAdapter

class IndustryListFragment : Fragment() {

    private lateinit var itemSelected:ItemSelected
    private var mContext: Context? = null

    private lateinit var recycler:RecyclerView
    private lateinit var  adapter:IndustryListAdapter


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



        var view= UI {
            linearLayout {
                recycler=recyclerView{
                    overScrollMode = View.OVER_SCROLL_NEVER
                    setLayoutManager(LinearLayoutManager(this.getContext()))

                }.lparams {
                }
            }
        }.view



        adapter=IndustryListAdapter(recycler,  jobContainer) { item,index ->
            adapter.selectData(index)
            itemSelected.getSelectedItem(item)
        }


        recycler.setAdapter(adapter)

        return  view

    }

    public interface ItemSelected {

        fun getSelectedItem(item:JobContainer )
    }

}



