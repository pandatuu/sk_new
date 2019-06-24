package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.alibaba.fastjson.JSON
import com.example.sk_android.custom.layout.MyDialog

import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.api.jobselect.CityInfoApi
import com.example.sk_android.mvp.api.jobselect.JobApi
import com.example.sk_android.mvp.api.jobselect.RecruitInfoApi
import com.example.sk_android.mvp.api.jobselect.UserApi
import com.example.sk_android.mvp.model.jobselect.*
import com.example.sk_android.mvp.model.message.ChatRecordModel
import com.example.sk_android.mvp.view.activity.jobselect.JobInfoDetailActivity
import com.example.sk_android.mvp.view.activity.jobselect.JobSearchWithHistoryActivity
import com.example.sk_android.mvp.view.activity.register.ImproveInformationActivity
import com.example.sk_android.mvp.view.adapter.jobselect.RecruitInfoListAdapter
import com.example.sk_android.mvp.view.adapter.message.MessageChatRecordListAdapter
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.RetrofitUtils
import imui.jiguang.cn.imuisample.messages.MessageListActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.sdk25.coroutines.onDrag
import org.jetbrains.anko.sdk25.coroutines.onTouch
import org.jetbrains.anko.support.v4.startActivity
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.adapter.rxjava2.HttpException
import android.support.v7.widget.RecyclerView.OnScrollListener as OnScrollListener1

class RecruitInfoListFragment : Fragment() {


    private var mContext: Context? = null
    var mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
    lateinit var recycler: RecyclerView
    var adapter: RecruitInfoListAdapter? = null
    private var myDialog: MyDialog? = null
    var haveData = false

    //搜藏
    var collectionList: MutableList<String> = mutableListOf()
    //记录Id
    var collectionRecordIdList: MutableList<String> = mutableListOf()


    var isCollectionComplete = false

    var pageNum: Int = 1
    var pageLimit: Int = 20


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
        var fragmentView = createView()
        return fragmentView
    }

    fun createView(): View {

        getCollection()

        //界面
        var view = UI {
            linearLayout {
                linearLayout {
                    backgroundColorResource = R.color.originColor
                    recycler = recyclerView {
                        overScrollMode = View.OVER_SCROLL_NEVER
                        var manager = LinearLayoutManager(this.getContext())
                        setLayoutManager(manager)
                        //manager.setStackFromEnd(true);

                    }.lparams {
                        leftMargin = dip(12)
                        rightMargin = dip(12)
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }.view


        recycler.setOnScrollChangeListener(object : View.OnScrollChangeListener {
            override fun onScrollChange(v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                if (!recycler.canScrollVertically(1)) {
                    if (haveData) {
                        showLoading("加载中...")
                        reuqestRecruitInfoData(
                            pageNum, pageLimit, null, null, null, null, null, null,
                            null, null, null, null, null, null
                        )
                    } else {
                        showNormalDialog("没有数据了")
                    }
                }

            }

        })

        println("加载中...")
        showLoading("加载中...")
        reuqestRecruitInfoData(
            pageNum, pageLimit, null, null, null, null, null, null,
            null, null, null, null, null, null
        )

        return view
    }


    private fun getCollection() {

        //请求搜藏
        var requestAddress = RetrofitUtils(mContext!!, "https://job.sk.cgland.top/")
        requestAddress.create(JobApi::class.java)
            .getFavorites(
                1, 1000000, FavoriteType.Key.ORGANIZATION_POSITION.toString()
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("搜藏请求成功")
                println(it)
                var responseStr = org.json.JSONObject(it.toString())
                var soucangData = responseStr.getJSONArray("data")


                for (i in 0..soucangData.length() - 1) {
                    var item = soucangData.getJSONObject(i)
                    var targetEntityId = item.getString("targetEntityId")
                    var id = item.getString("id")

                    collectionList.add(targetEntityId)
                    collectionRecordIdList.add(id)
                }

                isCollectionComplete = true


            }, {
                //失败
                println("搜藏请求失败")
                println(it)
            })

    }

    //请求获取数据
    private fun reuqestRecruitInfoData(
        _page: Int?, _limit: Int?, recruitMethod: String?, workingType: String?,
        workingExperience: Int?, currencyType: String?, salaryType: String?,
        salaryMin: Int?, salaryMax: Int?, auditState: String?, educationalBackground: String?,
        industryId: String?, address: String?, radius: Number?
    ) {


        println("职位信息列表.....")
        var retrofitUils = RetrofitUtils(mContext!!, "https://organization-position.sk.cgland.top/")
        retrofitUils.create(RecruitInfoApi::class.java)
            .getRecruitInfoList(
                _page,
                _limit,
                recruitMethod,
                workingType,
                workingExperience,
                currencyType,
                salaryType,
                salaryMin,
                salaryMax,
                auditState,
                educationalBackground,
                industryId,
                address,
                radius
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                //成功
                println("职位信息列表请求成功")
                println(it)

                var response = org.json.JSONObject(it.toString())
                var data = response.getJSONArray("data")
                //如果有数据则可能还有下一页
                if (data.length() > 0) {
                    pageNum = 1 + pageNum
                    haveData=true
                } else {
                    haveData = false
                    hideLoading()
                }
                println("大小")
                println(data.length())
                for (i in 0..data.length() - 1) {

                    println("循环!!!!!")
                    //公司请求完成
                    var requestCompanyComplete = false
                    //地址请求完成
                    var requestAddressComplete = false
                    //用户请求完成
                    var requestUserComplete = false


                    var itemContainer = data.getJSONObject(i)

                    var item = itemContainer.getJSONObject("organization")

                    //是否是最新
                    var isNew = itemContainer.getBoolean("new")
                    //是否加急
                    var emergency = false
                    if (item.has("emergency")) {
                        emergency = item.getBoolean("emergency")
                    }
                    //招聘方式
                    var recruitMethod = ""
                    if (item.has("recruitMethod")) {
                        recruitMethod = item.getString("recruitMethod")
                    }
                    //工作经验
                    var workingExperience = 0
                    if (item.has("workingExperience")) {
                        workingExperience = item.getInt("workingExperience")
                    }
                    //工作方式类型
                    var workingType = ""
                    if (item.has("workingType")) {
                        workingType = item.getString("workingType")
                    }
                    //货币类型
                    var currencyType = ""
                    if (item.has("currencyType")) {
                        currencyType = item.getString("currencyType")
                    }
                    //薪水类型
                    var salaryType = ""
                    if (item.has("salaryType")) {
                        salaryType = item.getString("salaryType")
                    }
                    //时薪Min
                    var salaryHourlyMin: Int? = null
                    if (item.get("salaryHourlyMin") != null && !item.get("salaryHourlyMin").toString().equals("null")) {
                        salaryHourlyMin = item.getInt("salaryHourlyMin")
                    }
                    //时薪Max
                    var salaryHourlyMax: Int? = null
                    if (item.get("salaryHourlyMax") != null && !item.get("salaryHourlyMax").toString().equals("null")) {
                        salaryHourlyMax = item.getInt("salaryHourlyMax")
                    }
                    //日薪Min
                    var salaryDailyMin: Int? = null
                    if (item.get("salaryDailyMin") != null && !item.get("salaryDailyMin").toString().equals("null")) {
                        salaryDailyMin = item.getInt("salaryDailyMin")
                    }
                    //日薪Max
                    var salaryDailyMax: Int? = null
                    if (item.get("salaryDailyMax") != null && !item.get("salaryDailyMax").toString().equals("null")) {
                        salaryDailyMax = item.getInt("salaryDailyMax")
                    }
                    //月薪Min
                    var salaryMonthlyMin: Int? = null
                    if (item.get("salaryMonthlyMin") != null && !item.get("salaryMonthlyMin").toString().equals("null")) {
                        salaryMonthlyMin = item.getInt("salaryMonthlyMin")
                    }
                    //月薪Max
                    var salaryMonthlyMax: Int? = null
                    if (item.get("salaryMonthlyMax") != null && !item.get("salaryMonthlyMax").toString().equals("null")) {
                        salaryMonthlyMax = item.getInt("salaryMonthlyMax")
                    }
                    //年薪Min
                    var salaryYearlyMin: Int? = null
                    if (item.get("salaryYearlyMin") != null && !item.get("salaryYearlyMin").toString().equals("null")) {
                        salaryYearlyMin = item.getInt("salaryYearlyMin")
                    }
                    //年薪Max
                    var salaryYearlyMax: Int? = null
                    if (item.get("salaryYearlyMax") != null && !item.get("salaryYearlyMax").toString().equals("null")) {
                        salaryYearlyMax = item.getInt("salaryYearlyMax")
                    }
                    //
                    val calculateSalary = item.getBoolean("calculateSalary")
                    //教育背景
                    var educationalBackground = item.getString("educationalBackground")
                    //职位
                    val content = item.getString("content")
                    //
                    val state = item.getString("state")
                    //
                    val resumeOnly = item.getBoolean("resumeOnly")

                    //职位名称
                    val name: String = item.getString("name")
                    //公司Id
                    val organizationId: String = item.getString("organizationId")
                    //地区ID
                    val areaId: String = item.getString("areaId")
                    //用户Id
                    val userId: String = item.getString("userId")
                    //职位信息Id
                    val id: String = item.getString("id")
                    println("获取职位ID:"+id)
                    //技能要求
                    val skill= item.getString("skill")



                    var isCollection = false


                    //
                    //组装数据
                    //

                    var currencyTypeUnitHead: String = ""
                    var currencyTypeUnitTail: String = ""
                    var unitType: Int = 0
                    if (currencyType != null && currencyType.equals("CNY")) {
                        // currencyTypeUnitTail="元"
                        unitType = 1
                    } else if (currencyType != null && currencyType.equals("JPY")) {
                        //  currencyTypeUnitTail="円"
                        unitType = 1
                    } else if (currencyType != null && currencyType.equals("USD")) {
                        //  currencyTypeUnitHead="$"
                        unitType = 2
                    }

                    ""
                    //拼接薪水范围
                    var showSalaryMinToMax: String = ""
                    if (salaryType != null && salaryType.equals(SalaryType.Key.HOURLY.toString())) {
                        showSalaryMinToMax = getSalaryMinToMaxString(
                            salaryHourlyMin,
                            salaryHourlyMax,
                            currencyTypeUnitHead,
                            currencyTypeUnitTail,
                            unitType
                        )
                        salaryType = SalaryType.Value.时.toString()
                    } else if (salaryType != null && salaryType.equals(SalaryType.Key.DAILY.toString())) {
                        showSalaryMinToMax = getSalaryMinToMaxString(
                            salaryDailyMin,
                            salaryDailyMax,
                            currencyTypeUnitHead,
                            currencyTypeUnitTail,
                            unitType
                        )
                        salaryType = SalaryType.Value.天.toString()
                    } else if (salaryType != null && salaryType.equals(SalaryType.Key.MONTHLY.toString())) {
                        showSalaryMinToMax = getSalaryMinToMaxString(
                            salaryMonthlyMin,
                            salaryMonthlyMax,
                            currencyTypeUnitHead,
                            currencyTypeUnitTail,
                            unitType
                        )
                        salaryType = SalaryType.Value.月.toString()
                    } else if (salaryType != null && salaryType.equals(SalaryType.Key.YEARLY.toString())) {
                        showSalaryMinToMax = getSalaryMinToMaxString(
                            salaryYearlyMin,
                            salaryYearlyMax,
                            currencyTypeUnitHead,
                            currencyTypeUnitTail,
                            unitType
                        )
                        salaryType = SalaryType.Value.年.toString()
                    }

                    //教育背景
                    educationalBackground = getEducationalBackground(educationalBackground)

                    //工作经验
                    var experience: String? = null
                    if (workingExperience != null && workingExperience != 0) {
                        experience = workingExperience.toString() + "年"
                    }

                    //地址
                    var address: String? = ""


                    //有食堂吗
                    var haveCanteen: Boolean = false
                    //有俱乐部吗
                    var haveClub: Boolean = false
                    //有社保吗
                    var haveSocialInsurance: Boolean = false
                    //有交通补助吗
                    var haveTraffic: Boolean = false
                    //公司名称
                    var companyName: String = ""
                    //底部的事件需要显示吗
                    var bottomShow = false
                    //user职位名称
                    var userPositionName: String = ""
                    //用户头像
                    var avatarURL: String = ""
                    //用户名字
                    var userName: String = ""

                    //请求公司信息
                    var requestCompany = RetrofitUtils(mContext!!, "https://org.sk.cgland.top/")
                    requestCompany.create(RecruitInfoApi::class.java)
                        .getCompanyInfo(
                            organizationId
                            //"f71cc420-27b4-4575-8ad1-9485a4de305f"
                        )
                        .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                        .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                        .subscribe({
                            println("公司信息请求成功")
                            println(it)
                            requestCompanyComplete = true
                            var json = org.json.JSONObject(it.toString())
                            companyName = json.getString("name")

                            userPositionName = json.getString("position")

                            var benifitsStr = json.getString("benifits")
                            if (benifitsStr != null && !benifitsStr.equals("null")) {
                                var benifits = JSONArray(benifitsStr)
                                for (i in 0..benifits.length() - 1) {
                                    var str = benifits.get(i).toString()
                                    if (str != null && str.equals(Benifits.Key.CANTEEN.toString())) {
                                        haveCanteen = true
                                    } else if (str != null && str.equals(Benifits.Key.CLUB.toString())) {
                                        haveClub = true
                                    } else if (str != null && str.equals(Benifits.Key.SOCIAL_INSURANCE.toString())) {
                                        haveSocialInsurance = true
                                    } else if (str != null && str.equals(Benifits.Key.TRAFFIC.toString())) {
                                        haveTraffic = true
                                    }
                                }
                            }


                            if (requestCompanyComplete && requestAddressComplete && requestUserComplete) {
                                //存在问题 ,暂时这样做
                                if (isCollectionComplete) {
                                    for (i in 0..collectionList.size - 1) {
                                        if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
                                            isCollection = true
                                        }
                                    }
                                }
                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete) {

                                    appendRecyclerData(
                                        emergency,
                                        recruitMethod,
                                        experience,
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
                                        bottomShow,
                                        name,
                                        companyName,
                                        haveCanteen,
                                        haveClub,
                                        haveSocialInsurance,
                                        haveTraffic,
                                        userPositionName,
                                        avatarURL,
                                        userId,
                                        userName,
                                        isCollection,
                                        id,
                                        skill,
                                        organizationId
                                    )
                                    if (i == data.length() - 1) {
                                        hideLoading()
                                    }

                                }
                            }

                        }, {
                            //失败
                            println("公司信息请求失败")
                            println(it)
                            requestCompanyComplete = true

                            if (requestCompanyComplete && requestAddressComplete && requestUserComplete) {
                                //存在问题 ,暂时这样做
                                if (isCollectionComplete) {
                                    for (i in 0..collectionList.size - 1) {
                                        if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
                                            isCollection = true
                                        }
                                    }
                                }

                                appendRecyclerData(
                                    emergency,
                                    recruitMethod,
                                    experience,
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
                                    bottomShow,
                                    name,
                                    companyName,
                                    haveCanteen,
                                    haveClub,
                                    haveSocialInsurance,
                                    haveTraffic,
                                    userPositionName,
                                    avatarURL,
                                    userId,
                                    userName,
                                    isCollection,
                                    id,
                                    skill,
                                    organizationId
                                )
                                if (i == data.length() - 1) {
                                    hideLoading()
                                }
                            }
                        })


                    //请求地址
                    var requestAddress = RetrofitUtils(mContext!!, "https://basic-info.sk.cgland.top/")
                    requestAddress.create(CityInfoApi::class.java)
                        .getAreaInfo(
                            areaId
                        )
                        .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                        .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                        .subscribe({
                            println("地址信息请求成功")
                            println(it)

                            address = JSONObject(it.toString()).getString("name")

                            requestAddressComplete = true
                            if (requestCompanyComplete && requestAddressComplete && requestUserComplete) {
                                //存在问题 ,暂时这样做
                                if (isCollectionComplete) {
                                    for (i in 0..collectionList.size - 1) {
                                        if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
                                            isCollection = true
                                        }
                                    }
                                }

                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete) {

                                    appendRecyclerData(
                                        emergency,
                                        recruitMethod,
                                        experience,
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
                                        bottomShow,
                                        name,
                                        companyName,
                                        haveCanteen,
                                        haveClub,
                                        haveSocialInsurance,
                                        haveTraffic,
                                        userPositionName,
                                        avatarURL,
                                        userId,
                                        userName,
                                        isCollection,
                                        id,
                                        skill,
                                        organizationId
                                    )
                                    if (i == data.length() - 1) {
                                        hideLoading()
                                    }
                                }
                            }

                        }, {
                            //失败
                            //返回404就是没查到
                            println("地址信息请求失败")
                            println(it)
                            requestAddressComplete = true

                            if (requestCompanyComplete && requestAddressComplete && requestUserComplete) {
                                //存在问题 ,暂时这样做
                                if (isCollectionComplete) {
                                    for (i in 0..collectionList.size - 1) {
                                        if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
                                            isCollection = true
                                        }
                                    }
                                }

                                appendRecyclerData(
                                    emergency,
                                    recruitMethod,
                                    experience,
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
                                    bottomShow,
                                    name,
                                    companyName,
                                    haveCanteen,
                                    haveClub,
                                    haveSocialInsurance,
                                    haveTraffic,
                                    userPositionName,
                                    avatarURL,
                                    userId,
                                    userName,
                                    isCollection,
                                    id,
                                    skill,
                                    organizationId
                                )
                                if (i == data.length() - 1) {
                                    hideLoading()
                                }
                            }
                        })


                    //用户信息请求
                    var requestUser = RetrofitUtils(mContext!!, "https://user.sk.cgland.top/")
                    requestUser.create(UserApi::class.java)
                        .getUserInfo(
                            userId
                        )
                        .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                        .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                        .subscribe({
                            println("用户信息请求成功")
                            println(it)

                            avatarURL = JSONObject(it.toString()).getString("avatarURL")
                            userName = JSONObject(it.toString()).getString("displayName")

                            requestUserComplete = true
                            if (requestCompanyComplete && requestAddressComplete && requestUserComplete) {
                                //存在问题 ,暂时这样做
                                if (isCollectionComplete) {
                                    for (i in 0..collectionList.size - 1) {
                                        if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
                                            isCollection = true
                                        }
                                    }
                                }

                                appendRecyclerData(
                                    emergency,
                                    recruitMethod,
                                    experience,
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
                                    bottomShow,
                                    name,
                                    companyName,
                                    haveCanteen,
                                    haveClub,
                                    haveSocialInsurance,
                                    haveTraffic,
                                    userPositionName,
                                    avatarURL,
                                    userId,
                                    userName,
                                    isCollection,
                                    id,
                                    skill,
                                    organizationId
                                )
                                if (i == data.length() - 1) {
                                    hideLoading()
                                }
                            }

                        }, {
                            //失败
                            println("用户信息请求失败")
                            println(it)
                            requestUserComplete = true

                            if (requestCompanyComplete && requestAddressComplete && requestUserComplete) {
                                //存在问题 ,暂时这样做
                                if (isCollectionComplete) {
                                    for (i in 0..collectionList.size - 1) {
                                        if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
                                            isCollection = true
                                        }
                                    }
                                }

                                appendRecyclerData(
                                    emergency,
                                    recruitMethod,
                                    experience,
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
                                    bottomShow,
                                    name,
                                    companyName,
                                    haveCanteen,
                                    haveClub,
                                    haveSocialInsurance,
                                    haveTraffic,
                                    userPositionName,
                                    avatarURL,
                                    userId,
                                    userName,
                                    isCollection,
                                    id,
                                    skill,
                                    organizationId
                                )
                                if (i == data.length() - 1) {
                                    hideLoading()
                                }
                            }
                        })

                }


            }, {
                //失败
                println("职位信息列表请求失败")
                println(it)
            })
    }


    fun appendRecyclerData(
        emergency: Boolean,
        recruitMethod: String,
        workingExperience: String?,
        workingType: String,
        currencyType: String,
        salaryType: String,
        salaryHourlyMin: Int?,
        salaryHourlyMax: Int?,
        salaryDailyMin: Int?,
        salaryDailyMax: Int?,
        salaryMonthlyMin: Int?,
        salaryMonthlyMax: Int?,
        salaryYearlyMin: Int?,
        salaryYearlyMax: Int?,
        showSalaryMinToMax: String,
        calculateSalary: Boolean,
        educationalBackground: String?,
        address: String?,
        content: String,
        state: String,
        resumeOnly: Boolean,
        isNew: Boolean,
        bottomShow: Boolean,
        name: String,
        companyName: String,
        haveCanteen: Boolean,
        haveClub: Boolean,
        haveSocialInsurance: Boolean,
        haveTraffic: Boolean,
        userPositionName: String,
        avatarURL: String,
        userId: String,
        userName: String,
        isCollection: Boolean,
        id: String,
        skill:String,
        organizationId:String

    ) {
        var list: MutableList<RecruitInfo> = mutableListOf()

        var recruitInfo = RecruitInfo(
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
            bottomShow,
            name,
            companyName,
            haveCanteen,
            haveClub,
            haveSocialInsurance,
            haveTraffic,
            userPositionName,
            avatarURL,
            userId,
            userName,
            isCollection,
            id,
            skill,
            organizationId
        )
        list.add(recruitInfo)

        if (adapter == null) {
            //适配器
            adapter = RecruitInfoListAdapter(recycler, list, { item ->

                //跳转到职位详情界面
                var intent = Intent(mContext, JobInfoDetailActivity::class.java)
                intent.putExtra("positionName", item.name)
                intent.putExtra("salaryType", item.salaryType)
                intent.putExtra("showSalaryMinToMax", item.showSalaryMinToMax)
                intent.putExtra("address", item.address)
                intent.putExtra("workingExperience", item.workingExperience)
                intent.putExtra("educationalBackground", item.educationalBackground)
                intent.putExtra("skill", item.skill)
                intent.putExtra("content", item.content)
                intent.putExtra("organizationId", item.organizationId)


                startActivity(intent)
                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)


            }, { item ->

                //跳转到聊天界面
                var intent = Intent(mContext, MessageListActivity::class.java)
                intent.putExtra("hisId", item.userId)
                intent.putExtra("companyName", item.companyName)
                intent.putExtra("hisName", item.userName)

                startActivity(intent)
                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)


            }, { item, position, isCollection ->
                if (isCollection) {
                    //搜藏/取消搜藏
                    toCollectAPositionInfo(item.recruitMessageId, position, isCollection)
                } else {
                    var recordId = ""
                    if (isCollectionComplete) {
                        for (i in 0..collectionList.size - 1) {
                            if (collectionList.get(i) != null && collectionList.get(i).equals(item.recruitMessageId)) {
                                recordId = collectionRecordIdList.get(i)
                            }
                        }
                    }

                    unlikeAPositionInfo(recordId, position, isCollection)
                }


            })
            //设置适配器
            recycler.setAdapter(adapter)
        } else {
            adapter!!.addRecruitInfoList(list)

        }
    }


    //搜藏职位
    fun toCollectAPositionInfo(id: String, position: Int, isCollection: Boolean) {
        showLoading("搜藏成功")
        val request = JSONObject()
        val detail = JSONObject()
        detail.put("targetEntityId", id)
        detail.put("targetEntityType", FavoriteType.Key.ORGANIZATION_POSITION.toString())
        request.put("body", detail)

        val body = RequestBody.create(mediaType, detail.toString())
        //请求搜藏职位
        var requestAddress = RetrofitUtils(mContext!!, "https://job.sk.cgland.top/")
        requestAddress.create(JobApi::class.java)
            .addFavorite(
                body
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("创建搜藏成功")
                println(it)
                hideLoading()
                adapter!!.UpdatePositionCollectiont(position, isCollection)
            }, {
                //失败
                println("创建搜藏失败")
                println(it)
            })

    }


    //取消搜藏职位
    fun unlikeAPositionInfo(id: String, position: Int, isCollection: Boolean) {
        showLoading("取消搜藏")
        //取消搜藏职位
        var requestAddress = RetrofitUtils(mContext!!, "https://job.sk.cgland.top/")
        requestAddress.create(JobApi::class.java)
            .unlikeFavorite(
                id
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("取消搜藏成功")
                println(it.toString())
                hideLoading()
                adapter!!.UpdatePositionCollectiont(position, isCollection)
            }, {
                //失败
                println("取消搜藏失败")
                println(it)
            })

    }


    //关闭等待转圈窗口
    private fun hideLoading() {
        if (myDialog!!.isShowing()) {
            myDialog!!.dismiss()
        }
    }


    private fun showNormalDialog(str: String) {
        showLoading(str)
        //延迟3秒关闭
        Handler().postDelayed({ hideLoading() }, 800)
    }

    //弹出等待转圈窗口
    private fun showLoading(str: String) {
        if (myDialog != null && myDialog!!.isShowing()) {
            myDialog!!.dismiss()
            val builder = MyDialog.Builder(context!!)
                .setMessage(str)
                .setCancelable(false)
                .setCancelOutside(false)
            myDialog = builder.create()

        } else {
            val builder = MyDialog.Builder(context!!)
                .setMessage(str)
                .setCancelable(false)
                .setCancelOutside(false)

            myDialog = builder.create()
        }
        myDialog!!.show()
    }


    //得到薪资范围
    fun getSalaryMinToMaxString(
        salaryMin: Int?,
        salaryMax: Int?,
        currencyTypeUnitHead: String,
        currencyTypeUnitTail: String,
        unitType: Int
    ): String {

        var min = salaryMin.toString();
        var max = salaryMax.toString();

        var thousand = ""
        var tenthousand = ""
        var million = ""


        thousand = "千"
        tenthousand = "万"
        million = "台"

//        if(unitType==1){
//            thousand="千"
//            tenthousand="万"
//        }else if(unitType==2){
//            thousand="k"
//            tenthousand="0k"
//        }


        if (salaryMin!! >= 1000000) {
            min = (salaryMin / 1000000).toString() + million
        } else if (salaryMin >= 10000) {
            min = (salaryMin / 10000).toString() + tenthousand
        } else if (salaryMin >= 1000) {
            min = (salaryMin / 1000).toString() + thousand
        }


        if (salaryMax!! >= 1000000) {
            max = (salaryMax / 1000000).toString() + million
        } else if (salaryMax >= 10000) {
            max = (salaryMax / 10000).toString() + tenthousand
        } else if (salaryMax >= 1000) {
            max = (salaryMax / 1000).toString() + thousand
        }

        var showSalaryMinToMax =
            currencyTypeUnitHead + min + currencyTypeUnitTail + "~" + currencyTypeUnitHead + max + currencyTypeUnitTail
        return showSalaryMinToMax
    }


    //得打教育背景
    fun getEducationalBackground(educationalBackground: String): String? {

        var result: String? = null
        if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.MIDDLE_SCHOOL.toString())) {
            result = EducationalBackground.Value.中学.toString()
        } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.HIGH_SCHOOL.toString())) {
            result = EducationalBackground.Value.高中.toString()
        } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.SHORT_TERM_COLLEGE.toString())) {
            result = EducationalBackground.Value.专科.toString()
        } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.BACHELOR.toString())) {
            result = EducationalBackground.Value.本科.toString()
        } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.MASTER.toString())) {
            result = EducationalBackground.Value.硕士.toString()
        } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.DOCTOR.toString())) {
            result = EducationalBackground.Value.博士.toString()
        }
        return result
    }
}

