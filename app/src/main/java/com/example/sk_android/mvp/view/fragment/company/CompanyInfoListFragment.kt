package com.example.sk_android.mvp.view.fragment.company

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.api.company.CompanyInfoApi
import com.example.sk_android.mvp.api.jobselect.RecruitInfoApi
import com.example.sk_android.mvp.model.jobselect.*
import com.example.sk_android.mvp.view.activity.company.CompanyInfoDetailActivity
import com.example.sk_android.mvp.view.activity.jobselect.JobInfoDetailActivity
import com.example.sk_android.mvp.view.adapter.company.CompanyInfoListAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.RecruitInfoListAdapter
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CompanyInfoListFragment : Fragment() {


    private var mContext: Context? = null
    lateinit var adapter: CompanyInfoListAdapter
    lateinit var recycler : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): CompanyInfoListFragment {
            val fragment = CompanyInfoListFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        return fragmentView
    }

    fun createView(): View {

        var companyList: MutableList<Company> = mutableListOf()

        var jc1= Company("株式会社日本電気","","","","","","","",true)
        var jc5= Company("株式会社日本電気","","","","","","","",true)
        var jc2=Company("株式会社日本電気","","","","","","","",true)
        var jc3= Company("株式会社日本電気","","","","","","","",true)
        var jc4= Company("株式会社日本電気","","","","","","","",true)


        companyList.add(jc1)
        companyList.add(jc2)
        companyList.add(jc3)
        companyList.add(jc4)
        companyList.add(jc5)


        var view=UI {
            linearLayout {
                linearLayout {
                    backgroundColorResource=R.color.originColor
                    recycler=recyclerView{
                        overScrollMode = View.OVER_SCROLL_NEVER
                        var manager=LinearLayoutManager(this.getContext())
                        setLayoutManager(manager)
                        //manager.setStackFromEnd(true);
                    }
                }.lparams {
                    width= matchParent
                    height=matchParent
                }
            }
        }.view

        //适配器
        adapter=CompanyInfoListAdapter(recycler,  companyList) { item ->
            //跳转到公司详情界面
            var intent = Intent(mContext, CompanyInfoDetailActivity::class.java)
            startActivity(intent)

        }
        //设置适配器
        recycler.setAdapter(adapter)
        //请求数据
        reuqestCompanyInfoListData(null,null,null,null,null,null,null,null,
            null)
        return view
    }

    //请求获取数据
    private fun reuqestCompanyInfoListData(_page:Int?,_limit:Int?,name:String?,acronym :String?,
                                           size:String?,financingStage:String?,type:String?,
                                           coordinate:String?,radius:Number?
    ){
        var retrofitUils = RetrofitUtils(mContext!!,"http://org.sk.cgland.top/")
        retrofitUils.create(CompanyInfoApi::class.java)
            .getCompanyInfoList(
                        _page,_limit,name,acronym,size,financingStage,type,coordinate,radius
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
                    var educationalBackground=item.getString("educationalBackground")
                    //职位
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
                    var unitType:Int=0
                    if(currencyType!=null && currencyType.equals("CNY")){
                        currencyTypeUnitTail="元"
                        unitType=1
                    }else if(currencyType!=null && currencyType.equals("JPY")){
                        currencyTypeUnitTail="円"
                        unitType=1
                    }else if(currencyType!=null && currencyType.equals("USD")){
                        currencyTypeUnitHead="$"
                        unitType=2
                    }


                    //教育背景

                    //工作经验
                    var experience:String?=null
                    if(workingExperience!=null && workingExperience!=0){
                        experience=workingExperience.toString()+"年"
                    }

                    //地址
                    var address:String?="东京"


                }




            }, {
                //失败
                println("8888888888888888888888888888888888888888888888888888888888888888888---ERROR")
                println("-->"+it)
            })
    }


    fun setRecyclerAdapter(list: MutableList<Company>){
        adapter.addCompanyInfoList(list)
    }


}

