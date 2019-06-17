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
import com.alibaba.fastjson.JSONObject
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.api.jobselect.RecruitInfoApi
import com.example.sk_android.mvp.model.jobselect.Job
import com.example.sk_android.mvp.model.jobselect.JobContainer
import com.example.sk_android.mvp.model.jobselect.RecruitInfo
import com.example.sk_android.mvp.model.jobselect.SalaryType
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

        var list: MutableList<RecruitInfo> = mutableListOf()
        //适配器
        adapter=RecruitInfoListAdapter(recycler,  list) { item ->



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

                        var response=org.json.JSONObject(it.toString())
                        var data=response.getJSONArray("data")
                        //数据
                        var list: MutableList<RecruitInfo> = mutableListOf()
                        println(data.length())
                        for(i in 0..data.length()-1){
                            var item=data.getJSONObject(i)
                            //
                            var emergency=item.getBoolean("emergency")
                            //招聘方式
                            var recruitMethod=item.getString("recruitMethod")
                            //工作经验
                            val workingExperience=item.getInt("workingExperience")
                            //工作方式类型
                            val workingType=item.getString("workingType")
                            //货币类型
                            val currencyType=item.getString("currencyType")
                            //薪水类型
                            var salaryType=item.getString("salaryType")
                            //时薪Min
                            var salaryHourlyMin:Int?=null
                            if(item.get("salaryHourlyMin")!=null && !item.get("salaryHourlyMin").toString().equals("null")){
                                salaryHourlyMin=item.getInt("salaryHourlyMin")
                            }
                            //时薪Max
                            var salaryHourlyMax:Int?=null
                            if(item.get("salaryHourlyMax")!=null && !item.get("salaryHourlyMax").toString().equals("null")){
                                salaryHourlyMax=item.getInt("salaryHourlyMax")
                            }
                            //日薪Min
                            var salaryDailyMin:Int?=null
                            if(item.get("salaryDailyMin")!=null && !item.get("salaryDailyMin").toString().equals("null")){
                                salaryDailyMin=item.getInt("salaryDailyMin")
                            }
                            //日薪Max
                            var salaryDailyMax:Int?=null
                            if(item.get("salaryDailyMax")!=null  && !item.get("salaryDailyMax").toString().equals("null")){
                                salaryDailyMax=item.getInt("salaryDailyMax")
                            }
                            //月薪Min
                            var salaryMonthlyMin:Int?=null
                            if(item.get("salaryMonthlyMin")!=null  && !item.get("salaryMonthlyMin").toString().equals("null")){
                                salaryMonthlyMin=item.getInt("salaryMonthlyMin")
                            }
                            //月薪Max
                            var salaryMonthlyMax:Int?=null
                            if(item.get("salaryMonthlyMax")!=null  && !item.get("salaryMonthlyMax").toString().equals("null")){
                                salaryMonthlyMax=item.getInt("salaryMonthlyMax")
                            }
                            //年薪Min
                            var salaryYearlyMin:Int?=null
                            if(item.get("salaryYearlyMin")!=null && !item.get("salaryYearlyMin").toString().equals("null")){
                                salaryYearlyMin=item.getInt("salaryYearlyMin")
                            }
                            //年薪Max
                            var salaryYearlyMax:Int?=null
                            if(item.get("salaryYearlyMax")!=null && !item.get("salaryYearlyMax").toString().equals("null")){
                                salaryYearlyMax=item.getInt("salaryYearlyMax")
                            }
                            //
                            val calculateSalary=item.getBoolean("calculateSalary")
                            //教育背景
                            val educationalBackground=item.getString("educationalBackground")
                            //
                            val content=item.getString("content")
                            //
                            val state=item.getString("state")
                            //
                            val resumeOnly=item.getBoolean("resumeOnly")
                            //是最新的吗
                            val isNew:Boolean=true

                            //
                            //组装数据
                            //

                            var currencyTypeUnitHead:String=""
                            var currencyTypeUnitTail:String=""

                            if(currencyType!=null && currencyType.equals("CNY")){
                                currencyTypeUnitTail="元"
                            }else if(currencyType!=null && currencyType.equals("JPY")){
                                currencyTypeUnitTail="円"
                            }else if(currencyType!=null && currencyType.equals("USD")){
                                currencyTypeUnitHead="$"
                            }

                            ""
                            //拼接薪水范围
                            var showSalaryMinToMax:String=""
                            if(salaryType!=null && salaryType.equals(SalaryType.Key.HOURLY.toString())){
                                showSalaryMinToMax=getSalaryMinToMaxString(salaryHourlyMin,salaryHourlyMax,currencyTypeUnitHead,currencyTypeUnitTail)
                                salaryType=SalaryType.Value.时.toString()
                            }else if(salaryType!=null && salaryType.equals(SalaryType.Key.DAILY.toString())){
                                showSalaryMinToMax=getSalaryMinToMaxString(salaryDailyMin,salaryDailyMax,currencyTypeUnitHead,currencyTypeUnitTail)
                                salaryType=SalaryType.Value.天.toString()
                            }else if(salaryType!=null && salaryType.equals(SalaryType.Key.MONTHLY.toString())){
                                showSalaryMinToMax=getSalaryMinToMaxString(salaryMonthlyMin,salaryMonthlyMax,currencyTypeUnitHead,currencyTypeUnitTail)
                                salaryType=SalaryType.Value.月.toString()
                            }else if(salaryType!=null && salaryType.equals(SalaryType.Key.YEARLY.toString())){
                                showSalaryMinToMax=getSalaryMinToMaxString(salaryYearlyMin,salaryYearlyMax,currencyTypeUnitHead,currencyTypeUnitTail)
                                salaryType=SalaryType.Value.年.toString()
                            }
                            //地址
                            var address:String=""

                            var recruitInfo:RecruitInfo=RecruitInfo(
                                                emergency,
                                                recruitMethod,
                                                workingExperience,
                                                workingType,
                                                currencyType,
                                                salaryType,
                                                salaryHourlyMin,
                                                salaryHourlyMax,
                                                salaryDailyMin,
                                                salaryDailyMax,
                                                salaryMonthlyMin,
                                                salaryMonthlyMax,
                                                salaryYearlyMin,
                                                salaryYearlyMax,
                                                showSalaryMinToMax,
                                                calculateSalary,
                                                educationalBackground,
                                                address,
                                                content,
                                                state,
                                                resumeOnly,
                                                isNew,
                                    true
                            )
                            list.add(recruitInfo)

                        }


                        setRecyclerAdapter(list)


                    }, {
                        //失败
                        println("8888888888888888888888888888888888888888888888888888888888888888888---ERROR")
                        println("-->"+it)
                    })
    }

    fun setRecyclerAdapter(recruitInfo: MutableList<RecruitInfo>){
        adapter.addRecruitInfoList(recruitInfo)
    }





    fun getSalaryMinToMaxString(salaryHourlyMin:Int?,salaryHourlyMax:Int?,currencyTypeUnitHead:String,currencyTypeUnitTail:String):String{

        var min=salaryHourlyMin.toString();
        var max=salaryHourlyMax.toString();

        if(salaryHourlyMin!!>10000){
            min=(salaryHourlyMin/10000).toString()+"万"
        }else if(salaryHourlyMin>1000){
            min=(salaryHourlyMin/1000).toString()+"千"
        }

        if(salaryHourlyMax!!>10000){
            max=(salaryHourlyMax/10000).toString()+"万"
        }else if(salaryHourlyMax>1000){
            max=(salaryHourlyMax/1000).toString()+"千"
        }

        var showSalaryMinToMax=currencyTypeUnitHead+min+currencyTypeUnitTail+"~"+currencyTypeUnitHead+max+currencyTypeUnitTail
        return  showSalaryMinToMax
    }
}

