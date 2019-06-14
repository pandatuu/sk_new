package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.api.jobselect.RecruitInfoApi
import com.example.sk_android.mvp.model.jobselect.Job
import com.example.sk_android.mvp.model.jobselect.JobContainer
import com.example.sk_android.mvp.model.message.ChatRecordModel
import com.example.sk_android.mvp.view.activity.register.ImproveInformationActivity
import com.example.sk_android.mvp.view.adapter.jobselect.RecruitInfoListAdapter
import com.example.sk_android.mvp.view.adapter.message.MessageChatRecordListAdapter
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.adapter.rxjava2.HttpException

class RecruitInfoListFragment : Fragment() {


    private var mContext: Context? = null
    var mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
    lateinit var recycler : RecyclerView
    lateinit var adapter: RecruitInfoListAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): RecruitInfoListFragment {
            val fragment = RecruitInfoListFragment()
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

        reuqestRecruitInfoData(null,null,null,null,null,null,null,null,
            null,null,null,null,null,null)
        //界面
        var view=UI {
            linearLayout {
                linearLayout {
                    backgroundColorResource=R.color.originColor
                    recycler=recyclerView{
                        overScrollMode = View.OVER_SCROLL_NEVER
                        var manager=LinearLayoutManager(this.getContext())
                        setLayoutManager(manager)
                        //manager.setStackFromEnd(true);

                    }.lparams {
                        leftMargin=dip(12)
                        rightMargin=dip(12)
                    }
                }.lparams {
                    width= matchParent
                    height=matchParent
                }
            }
        }.view

        jobContainer= mutableListOf()
        //适配器
        adapter=RecruitInfoListAdapter(recycler,  jobContainer) { item ->



        }
        //设置适配器
        recycler.setAdapter(adapter)
        return view
    }


    //请求获取数据
    private fun reuqestRecruitInfoData(_page:Int?,_limit:Int?,recruitMethod:String?,workingType :String?,
                                       workingExperience:Int?,currencyType:String?,salaryType:String?,
                                       salaryMin:Int?,salaryMax:Int?,auditState:String?,educationalBackground:String?,
                                       industryId:String?,address:String?,radius:Number?
    ){
                var retrofitUils = RetrofitUtils(mContext!!,"https://organization-position.sk.cgland.top/")
                retrofitUils.create(RecruitInfoApi::class.java)
                    .getRecruitInfoList(
//                        _page,_limit,recruitMethod,workingType,workingExperience,currencyType,salaryType,salaryMin,salaryMax,auditState,educationalBackground,industryId,address,radius
                    )
                    .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        //成功
                        println("8888888888888888888888888888888888888888888888888888888888888888888===")
                        println("-->"+it)
                    }, {
                        //失败
                        println("8888888888888888888888888888888888888888888888888888888888888888888---ERROR")
                        println("-->"+it)
                    })
    }




    fun setRecyclerAdapter(jobContainer: MutableList<JobContainer>){
        adapter.addRecruitInfoList(jobContainer)
    }


}

