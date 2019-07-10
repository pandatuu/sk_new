package com.example.sk_android.mvp.view.fragment.company

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.api.company.CompanyInfoApi
import com.example.sk_android.mvp.api.jobselect.RecruitInfoApi
import com.example.sk_android.mvp.model.company.CompanyBriefInfo
import com.example.sk_android.mvp.model.jobselect.*
import com.example.sk_android.mvp.view.activity.company.CompanyInfoDetailActivity
import com.example.sk_android.mvp.view.activity.jobselect.JobInfoDetailActivity
import com.example.sk_android.mvp.view.adapter.company.CompanyInfoListAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.RecruitInfoListAdapter
import com.example.sk_android.mvp.view.fragment.common.DialogLoading
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import imui.jiguang.cn.imuisample.messages.MessageListActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import java.lang.Exception

class CompanyInfoListFragment : Fragment() {


    private var mContext: Context? = null
    var adapter: CompanyInfoListAdapter? = null
    lateinit var recycler: RecyclerView
    private var dialogLoading: DialogLoading? = null
    val mainId = 1

    var pageNum: Int = 1
    var pageLimit: Int = 20

    var haveData = true

    var requestDataFinish = true
    var isFirstRequest = true

    lateinit var mainListView: LinearLayout
    lateinit var findNothing: LinearLayout
    private var theCompanyName: String? = null


    //筛选的参数
    var filterParamAcronym: String? = null
    var filterParamSize: String? = null
    var filterParamFinancingStage: String? = null
    var filterParamType: String? = null
    var filterParamCoordinate: String? = null
    var filterParamRadius: Number? = null
    var filterParamIndustryId: String? = null
    var filterParamAreaId: String? = null

    var toastCanshow=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(companyName: String?,areaId: String?): CompanyInfoListFragment {
            val fragment = CompanyInfoListFragment()
            fragment.theCompanyName = companyName
            fragment.filterParamAreaId =areaId
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        return fragmentView
    }

    fun createView(): View {


        var view = UI {

            frameLayout {
                id = mainId
                findNothing = verticalLayout {

                    visibility = View.GONE
                    imageView {
                        setImageResource(R.mipmap.ico_find_nothing)
                    }.lparams {
                        width = dip(170)
                        height = dip(100)
                    }

                    textView {
                        text = "いかなる結果も検索できない"
                        textSize = 14f
                        textColorResource = R.color.gray5c
                    }.lparams {
                        topMargin = dip(25)
                    }
                }.lparams() {
                    width = wrapContent
                    height = wrapContent
                    gravity = Gravity.CENTER
                }
                mainListView = linearLayout {
                    backgroundColorResource = R.color.originColor
                    recycler = recyclerView {
                        overScrollMode = View.OVER_SCROLL_NEVER
                        var manager = LinearLayoutManager(this.getContext())
                        setLayoutManager(manager)
                        //manager.setStackFromEnd(true);
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }.view


        recycler.setOnTouchListener(object :View.OnTouchListener{

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                toastCanshow=true
                return false

            }

        })



        recycler.setOnScrollChangeListener(object : View.OnScrollChangeListener {
            override fun onScrollChange(v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                if (!recycler.canScrollVertically(1)) {
                    println("滑动改变")
                    println(scrollX.toString() +"---"+oldScrollX)
                    println(scrollY.toString() +"---"+oldScrollY)

                    if (haveData) {
                        reuqestCompanyInfoListData(
                            pageNum,
                            pageLimit,
                            theCompanyName,
                            filterParamAcronym,
                            filterParamSize,
                            filterParamFinancingStage,
                            filterParamType,
                            filterParamCoordinate,
                            filterParamRadius,
                            filterParamIndustryId,
                            filterParamAreaId
                        )
                    } else {
                        if(toastCanshow){
                            toast("没有数据了")
                        }
                    }
                }

            }

        })

        //请求数据
        reuqestCompanyInfoListData(
            pageNum, pageLimit, theCompanyName, null, null, null, null, null,
            null, null, filterParamAreaId
        )
        return view
    }

    //请求获取数据
    private fun reuqestCompanyInfoListData(
        _page: Int?, _limit: Int?, name: String?, acronym: String?,
        size: String?, financingStage: String?, type: String?,
        coordinate: String?, radius: Number?,industryId:String?,areaId:String?
    ) {
        if (requestDataFinish) {
            DialogUtils.showLoading(context!!)
            requestDataFinish = false
            println("公司信息请求.....")

            //用来装请求得到的数据，传递给adapter
            var companyBriefInfoList:MutableList<CompanyBriefInfo> = mutableListOf()

            var retrofitUils = RetrofitUtils(mContext!!, "https://org.sk.cgland.top/")
            retrofitUils.create(CompanyInfoApi::class.java)
                .getCompanyInfoList(
                    _page, _limit, name, acronym, size, financingStage, type, coordinate, radius,industryId,areaId
                )
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    //成功
                    println("公司信息请求成功!!!")
                    println(it)

                    var response = org.json.JSONObject(it.toString())
                    var data = response.getJSONArray("data")

                    if (isFirstRequest) {
                        isFirstRequest = false
                        if (data.length() == 0) {
                            noDataShow()
                        }else{
                            haveDataShow()
                        }
                    }


                    if (data.length() > 0) {
                        pageNum = 1 + pageNum
                    } else {
                       DialogUtils.hideLoading()
                        haveData = false
                    }
                    //数据
                    println("公司信息请求成功 大小")
                    println(data.length())

                    var requestFlag= mutableListOf<Boolean>()


                    for (i in 0..data.length() - 1) {
                        requestFlag.add(false)
                        companyBriefInfoList.add(CompanyBriefInfo("","","","","","","","",false,"","","",0))

                        var item = data.getJSONObject(i)
                        var id = item.getString("id")
                        //公司名
                        var name = item.getString("name")
                        //公司简称
                        var acronym = item.getString("acronym")
                        //公司logo
                        val logo = item.getString("logo")
                        //公司规模
                        val size = item.getString("size")
                        //公司的融资状态
                        val financingStage = item.getString("financingStage")
                        //公司类型
                        var type = item.getString("type")
                        //视频路径
                        val videoUrl = item.getString("videoUrl")
                        //审查状态：待审查，已通过，未通过
                        var auditState = item.getString("auditState")

                        var haveVideo = false
                        if (videoUrl != null && !videoUrl.equals("")) {
                            haveVideo = true
                        }
                        var positionNum = 0


                        var positionNameRequest =
                            RetrofitUtils(mContext!!, "https://organization-position.sk.cgland.top/")
                        positionNameRequest.create(CompanyInfoApi::class.java)
                            .getPositionNumberOfCompany(
                                id
                            )
                            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                            .subscribe({
                                println("公司的职位个数请求成功!!!")
                                println(it)


                                var json = JSONObject(it.toString())
                                positionNum = json.getInt("positionCount")


                                //
                                //组装数据
                                //
                                var companyBriefInfo = CompanyBriefInfo(
                                    id,
                                    name,
                                    acronym,
                                    logo,
                                    size,
                                    financingStage,
                                    type,
                                    "",
                                    haveVideo,
                                    "",
                                    "",
                                    "",
                                    positionNum

                                )
                                companyBriefInfoList.set(i,companyBriefInfo)
                                requestFlag.set(i,true)
                                for(i in 0..requestFlag.size-1 ){
                                    if(!requestFlag.get(i)){
                                        break
                                    }
                                    if(i==requestFlag.size-1){
                                        appendRecyclerData(companyBriefInfoList)
                                        DialogUtils.hideLoading()
                                    }
                                }

                            }, {

                                println("公司的职位个数请求失败!!!")
                                println(it)
                                //
                                //组装数据
                                //


                                var companyBriefInfo = CompanyBriefInfo(
                                    id,
                                    name,
                                    acronym,
                                    logo,
                                    size,
                                    financingStage,
                                    type,
                                    "",
                                    haveVideo,
                                    "",
                                    "",
                                    "",
                                    positionNum

                                )
                                companyBriefInfoList.set(i,companyBriefInfo)
                                requestFlag.set(i,true)
                                for(i in 0..requestFlag.size-1 ){
                                    if(!requestFlag.get(i)){
                                        break
                                    }
                                    if(i==requestFlag.size-1){
                                        appendRecyclerData(companyBriefInfoList)
                                        DialogUtils.hideLoading()
                                    }
                                }
                            })


                    }


                }, {
                    //失败
                    println("公司信息请求失败!!!!!")
                    println(it)
                    appendRecyclerData(companyBriefInfoList)
                    DialogUtils.hideLoading()
                })
        }
    }

    //筛选查询数据
    fun filterData(
        acronym: String?,
        size: String?, financingStage: String?, type: String?,
        coordinate: String?, radius: Number?, industryId: String?,areaId: String?
    ) {
        pageNum = 1
        haveData = false
        isFirstRequest = true
        toastCanshow=false

        if (adapter != null) {
            adapter!!.clearData()
        }

        filterParamAcronym = acronym
        filterParamSize = size
        filterParamFinancingStage = financingStage
        filterParamType = type
        filterParamCoordinate = coordinate
        filterParamRadius = radius
        filterParamIndustryId=industryId
        filterParamAreaId=areaId

        reuqestCompanyInfoListData(
            pageNum,
            pageLimit,
            theCompanyName, acronym, size, financingStage, type, coordinate, radius,industryId,areaId
        )

    }



    fun noDataShow() {
        mainListView.visibility = View.GONE
        findNothing.visibility = View.VISIBLE
    }

    fun haveDataShow() {
        mainListView.visibility = View.VISIBLE
        findNothing.visibility = View.GONE
    }

    fun appendRecyclerData(
        list: MutableList<CompanyBriefInfo>
    ) {



        requestDataFinish = true

        if(list==null ||  list.size==0){
            return
        }

        for(item in list){
            if(item.id.equals("")){
                list.remove(item)
            }
        }


        if (adapter == null) {
            //适配器
            adapter = CompanyInfoListAdapter(
                recycler,
                list
            ) { (id1, name1, acronym1, logo1, size1, financingStage1, type1, industry1, haveVideo1, cityName1, countyName1, streetName1,positionNum1) ->
                //跳转到公司详情界面
                var intent = Intent(mContext, CompanyInfoDetailActivity::class.java)
                intent.putExtra("companyId", id1)
                intent.putExtra("positionNum", positionNum1)
                startActivity(intent)
                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

            }
            adapter!!.setHasStableIds(true)
            //设置适配器
            recycler.setAdapter(adapter)
        } else {
            adapter!!.addCompanyInfoList(list)

        }
    }
}

