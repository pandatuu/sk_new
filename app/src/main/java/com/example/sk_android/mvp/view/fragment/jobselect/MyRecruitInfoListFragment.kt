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
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.alibaba.fastjson.JSON
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
import com.example.sk_android.mvp.view.fragment.common.DialogLoading
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import imui.jiguang.cn.imuisample.messages.MessageListActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.sdk25.coroutines.onDrag
import org.jetbrains.anko.sdk25.coroutines.onSystemUiVisibilityChange
import org.jetbrains.anko.sdk25.coroutines.onTouch
import org.jetbrains.anko.support.v4.startActivity
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.adapter.rxjava2.HttpException
import java.lang.Exception
import java.lang.Thread.sleep
import android.support.v7.widget.RecyclerView.OnScrollListener as OnScrollListener1

class MyRecruitInfoListFragment : Fragment() {


    private var mContext: Context? = null
    var mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
    lateinit var recycler: RecyclerView
    var adapter: RecruitInfoListAdapter? = null
    private var dialogLoading: DialogLoading? = null

    //搜藏
    var collectionList: MutableList<String> = mutableListOf()
    //记录Id
    var collectionRecordIdList: MutableList<String> = mutableListOf()
    //收藏请求时否完成
    var isCollectionComplete = false


    var requestDataFinish = true


    lateinit var mainListView: LinearLayout
    lateinit var findNothing: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }


    companion object {
        fun newInstance(): MyRecruitInfoListFragment {
            val fragment = MyRecruitInfoListFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        return fragmentView
    }

    fun createView(): View {

        getCollection()
        val main = 1
        //界面
        var view = UI {


            relativeLayout {
                findNothing = verticalLayout {
                    id = main
                    visibility = View.GONE
                    imageView {
                        setImageResource(R.mipmap.ico_find_nothing)
                    }.lparams {
                        width = dip(170)
                        height = dip(100)
                    }

                    textView {
                        text = "データーがありません"
                        textSize = 14f
                        textColorResource = R.color.gray5c
                    }.lparams {
                        topMargin = dip(25)
                    }
                }.lparams() {
                    width = wrapContent
                    height = wrapContent
                    centerInParent()
                }

                mainListView = linearLayout {
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


        recycler.onTouch { v, event ->


        }





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

                collectionList.clear()
                collectionRecordIdList.clear()
//                var list:MutableList<RecruitInfo> = mutableListOf()
//                if(adapter!=null){
//                    list = adapter!!.getAdapterData()
//                }


                for (i in 0..soucangData.length() - 1) {
                    var item = soucangData.getJSONObject(i)
                    var targetEntityId = item.getString("targetEntityId")
                    var id = item.getString("id")

                    collectionList.add(targetEntityId)
                    collectionRecordIdList.add(id)


//                    for(i in 0..list.size-1){
//                        var item=list.get(i)
//                        if(item.recruitMessageId.equals(targetEntityId)){
//                            item.isCollection=true
//                            item.collectionId=id
//                        }
//                    }

                }

//                if(adapter!=null){
//                    adapter!!.refreshData()
//                }


                isCollectionComplete = true


            }, {
                //失败
                println("搜藏请求失败")
                println(it)
            })

    }


    fun loadTheLoading(){
        if (activity == null) {
        }else{
            DialogUtils.showLoading(activity!!)

        }
    }


    //请求获取数据
    fun reuqestRecruitInfoData(
        positionIdListParam: MutableList<String>
    ) {
        var findPosition = false
        loadTheLoading()
        println(positionIdListParam)
        if (requestDataFinish) {
            requestDataFinish = false
            println("职位信息列表.....")

            println(positionIdListParam.size)


            if (positionIdListParam.size == 0) {
                DialogUtils.hideLoading()
                findNothing.visibility = View.VISIBLE
                mainListView.visibility = View.GONE
            }

            var flag = mutableListOf<Boolean>()
            for (j in 0..positionIdListParam.size - 1) {
                flag.add(false)
            }

            for (j in 0..positionIdListParam.size - 1) {
                val retrofitUils = RetrofitUtils(mContext!!, "https://organization-position.sk.cgland.top/")
                val it = retrofitUils.create(RecruitInfoApi::class.java)
                    .getRecruitInfoById(positionIdListParam.get(j))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        println("请求单个职位成功")
                        println(it)
                        findPosition = true
                        //公司请求完成
                        var requestCompanyComplete = false
                        //地址请求完成
                        var requestAddressComplete = false
                        //用户请求完成
                        var requestUserComplete = false
                        //用户角色请求完成
                        var requestUserPositionComplete = false


                        var itemContainer = JSONObject(it.toString())

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
                        if (item.has("salaryHourlyMin") && item.get("salaryHourlyMin") != null && !item.get("salaryHourlyMin").toString().equals(
                                "null"
                            )
                        ) {
                            salaryHourlyMin = item.getInt("salaryHourlyMin")
                        }
                        //时薪Max
                        var salaryHourlyMax: Int? = null
                        if (item.has("salaryHourlyMax") && item.get("salaryHourlyMax") != null && !item.get("salaryHourlyMax").toString().equals(
                                "null"
                            )
                        ) {
                            salaryHourlyMax = item.getInt("salaryHourlyMax")
                        }
                        //日薪Min
                        var salaryDailyMin: Int? = null
                        if (item.has("salaryDailyMin") && item.get("salaryDailyMin") != null && !item.get("salaryDailyMin").toString().equals(
                                "null"
                            )
                        ) {
                            salaryDailyMin = item.getInt("salaryDailyMin")
                        }
                        //日薪Max
                        var salaryDailyMax: Int? = null
                        if (item.has("salaryDailyMax") && item.get("salaryDailyMax") != null && !item.get("salaryDailyMax").toString().equals(
                                "null"
                            )
                        ) {
                            salaryDailyMax = item.getInt("salaryDailyMax")
                        }
                        //月薪Min
                        var salaryMonthlyMin: Int? = null
                        if (item.has("salaryMonthlyMin") && item.get("salaryMonthlyMin") != null && !item.get("salaryMonthlyMin").toString().equals(
                                "null"
                            )
                        ) {
                            salaryMonthlyMin = item.getInt("salaryMonthlyMin")
                        }
                        //月薪Max
                        var salaryMonthlyMax: Int? = null
                        if (item.has("salaryMonthlyMax") && item.get("salaryMonthlyMax") != null && !item.get("salaryMonthlyMax").toString().equals(
                                "null"
                            )
                        ) {
                            salaryMonthlyMax = item.getInt("salaryMonthlyMax")
                        }
                        //年薪Min
                        var salaryYearlyMin: Int? = null
                        if (item.has("salaryYearlyMin") && item.get("salaryYearlyMin") != null && !item.get("salaryYearlyMin").toString().equals(
                                "null"
                            )
                        ) {
                            salaryYearlyMin = item.getInt("salaryYearlyMin")
                        }
                        //年薪Max
                        var salaryYearlyMax: Int? = null
                        if (item.has("salaryYearlyMax") && item.get("salaryYearlyMax") != null && !item.get("salaryYearlyMax").toString().equals(
                                "null"
                            )
                        ) {
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
                        println("获取职位ID:" + id)
                        //技能要求
                        val skill = item.getString("skill")


                        var isCollection = false
                        //搜藏记录的Id
                        var collectionId = ""

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
                        //加分项
                        var plus = item.getString("plus")


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


                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete && requestUserPositionComplete) {
                                    //存在问题 ,暂时这样做
                                    if (isCollectionComplete) {
                                        for (i in 0..collectionList.size - 1) {
                                            if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
                                                isCollection = true
                                                collectionId = collectionRecordIdList.get(i)
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
                                        organizationId,
                                        collectionId,
                                        plus
                                    )
                                    flag.set(j, true)
                                    for (i in 0..flag.size - 1) {
                                        if (!flag.get(i)) {
                                            break
                                        }
                                        if (i == flag.size - 1) {
                                            DialogUtils.hideLoading()
                                            if (!findPosition) {
                                                findNothing.visibility = View.VISIBLE
                                                mainListView.visibility = View.GONE
                                            }
                                        }
                                    }


                                }

                            }, {
                                //失败
                                println("公司信息请求失败")
                                println(it)
                                requestCompanyComplete = true

                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete && requestUserPositionComplete) {
                                    //存在问题 ,暂时这样做
                                    if (isCollectionComplete) {
                                        for (i in 0..collectionList.size - 1) {
                                            if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
                                                isCollection = true
                                                collectionId = collectionRecordIdList.get(i)

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
                                        organizationId,
                                        collectionId,
                                        plus
                                    )
                                    flag.set(j, true)
                                    for (i in 0..flag.size - 1) {
                                        if (!flag.get(i)) {
                                            break
                                        }
                                        if (i == flag.size - 1) {
                                            DialogUtils.hideLoading()
                                            if (!findPosition) {
                                                findNothing.visibility = View.VISIBLE
                                                mainListView.visibility = View.GONE
                                            }
                                        }
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
                                                collectionId = collectionRecordIdList.get(i)

                                            }
                                        }
                                    }

                                    if (requestCompanyComplete && requestAddressComplete && requestUserComplete && requestUserPositionComplete) {

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
                                            organizationId,
                                            collectionId,
                                            plus
                                        )
                                        flag.set(j, true)
                                        for (i in 0..flag.size - 1) {
                                            if (!flag.get(i)) {
                                                break
                                            }
                                            if (i == flag.size - 1) {
                                                DialogUtils.hideLoading()
                                                if (!findPosition) {
                                                    findNothing.visibility = View.VISIBLE
                                                    mainListView.visibility = View.GONE
                                                }
                                            }
                                        }

                                    }
                                }

                            }, {
                                //失败
                                //返回404就是没查到
                                println("地址信息请求失败")
                                println(it)
                                requestAddressComplete = true

                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete && requestUserPositionComplete) {
                                    //存在问题 ,暂时这样做
                                    if (isCollectionComplete) {
                                        for (i in 0..collectionList.size - 1) {
                                            if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
                                                isCollection = true
                                                collectionId = collectionRecordIdList.get(i)

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
                                        organizationId,
                                        collectionId,
                                        plus
                                    )
                                    flag.set(j, true)
                                    for (i in 0..flag.size - 1) {
                                        if (!flag.get(i)) {
                                            break
                                        }
                                        if (i == flag.size - 1) {
                                            DialogUtils.hideLoading()
                                            if (!findPosition) {
                                                findNothing.visibility = View.VISIBLE
                                                mainListView.visibility = View.GONE
                                            }
                                        }
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
                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete && requestUserPositionComplete) {
                                    //存在问题 ,暂时这样做
                                    if (isCollectionComplete) {
                                        for (i in 0..collectionList.size - 1) {
                                            if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
                                                isCollection = true
                                                collectionId = collectionRecordIdList.get(i)

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
                                        organizationId,
                                        collectionId,
                                        plus
                                    )
                                    flag.set(j, true)
                                    for (i in 0..flag.size - 1) {
                                        if (!flag.get(i)) {
                                            break
                                        }
                                        if (i == flag.size - 1) {
                                            DialogUtils.hideLoading()
                                            if (!findPosition) {
                                                findNothing.visibility = View.VISIBLE
                                                mainListView.visibility = View.GONE
                                            }
                                        }
                                    }

                                }

                            }, {
                                //失败
                                println("用户信息请求失败")
                                println(it)
                                requestUserComplete = true

                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete && requestUserPositionComplete) {
                                    //存在问题 ,暂时这样做
                                    if (isCollectionComplete) {
                                        for (i in 0..collectionList.size - 1) {
                                            if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
                                                isCollection = true
                                                collectionId = collectionRecordIdList.get(i)

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
                                        organizationId,
                                        collectionId,
                                        plus
                                    )
                                    flag.set(j, true)
                                    for (i in 0..flag.size - 1) {
                                        if (!flag.get(i)) {
                                            break
                                        }
                                        if (i == flag.size - 1) {
                                            DialogUtils.hideLoading()
                                            if (!findPosition) {
                                                findNothing.visibility = View.VISIBLE
                                                mainListView.visibility = View.GONE
                                            }
                                        }
                                    }

                                }
                            })


                        //用户角色信息
                        var requestUserPosition = RetrofitUtils(mContext!!, "https://org.sk.cgland.top/")
                        requestUserPosition.create(UserApi::class.java)
                            .getUserPosition(
                                organizationId, userId
                            )
                            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                            .subscribe({
                                println("用户角色信息请求成功")
                                println(it)
                                var itemJson = JSONObject(it.toString())
                                userPositionName = itemJson.getString("name")

                                requestUserPositionComplete = true

                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete && requestUserPositionComplete) {
                                    //存在问题 ,暂时这样做
                                    if (isCollectionComplete) {
                                        for (i in 0..collectionList.size - 1) {
                                            if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
                                                isCollection = true
                                                collectionId = collectionRecordIdList.get(i)

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
                                        organizationId,
                                        collectionId,
                                        plus
                                    )
                                    flag.set(j, true)
                                    for (i in 0..flag.size - 1) {
                                        if (!flag.get(i)) {
                                            break
                                        }
                                        if (i == flag.size - 1) {
                                            DialogUtils.hideLoading()
                                            if (!findPosition) {
                                                findNothing.visibility = View.VISIBLE
                                                mainListView.visibility = View.GONE
                                            }
                                        }
                                    }

                                }

                            }, {
                                //失败
                                println("用户角色信息请求失败")
                                println(it)

                                requestUserPositionComplete = true
                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete && requestUserPositionComplete) {
                                    //存在问题 ,暂时这样做
                                    if (isCollectionComplete) {
                                        for (i in 0..collectionList.size - 1) {
                                            if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
                                                isCollection = true
                                                collectionId = collectionRecordIdList.get(i)

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
                                        organizationId,
                                        collectionId,
                                        plus
                                    )
                                    flag.set(j, true)
                                    for (i in 0..flag.size - 1) {
                                        if (!flag.get(i)) {
                                            break
                                        }
                                        if (i == flag.size - 1) {
                                            DialogUtils.hideLoading()
                                            if (!findPosition) {
                                                findNothing.visibility = View.VISIBLE
                                                mainListView.visibility = View.GONE
                                            }
                                        }
                                    }

                                }
                            })


                    }, {
                        println("请求单个职位失败")
                        println(it)
                        flag.set(j, true)
                        for (i in 0..flag.size - 1) {
                            if (!flag.get(i)) {
                                break
                            }
                            if (i == flag.size - 1) {
                                DialogUtils.hideLoading()
                                if (!findPosition) {
                                    findNothing.visibility = View.VISIBLE
                                    mainListView.visibility = View.GONE
                                }
                            }
                        }
                    })


            }


        }

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
        skill: String,
        organizationId: String,
        collectionId: String,
        plus: String

    ) {

        requestDataFinish = true

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
            organizationId,
            collectionId,
            plus
        )
        list.add(recruitInfo)

        if (adapter == null) {
            //适配器
            adapter = RecruitInfoListAdapter(recycler, list, { item, position ->

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
                intent.putExtra("companyName", item.companyName)
                intent.putExtra("userName", item.userName)
                intent.putExtra("userPositionName", item.userPositionName)
                intent.putExtra("avatarURL", item.avatarURL)
                intent.putExtra("userId", item.userId)
                intent.putExtra("isCollection", item.isCollection)
                intent.putExtra("recruitMessageId", item.recruitMessageId)
                intent.putExtra("collectionId", item.collectionId)
                intent.putExtra("position", position)
                intent.putExtra("fromType", "recruitList")


                startActivityForResult(intent, 1)
                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)


            }, { item ->

                //跳转到聊天界面
                var intent = Intent(mContext, MessageListActivity::class.java)
                intent.putExtra("hisId", item.userId)
                intent.putExtra("companyName", item.companyName)
                intent.putExtra("company_id", item.organizationId)
                intent.putExtra("hisName", item.userName)
                intent.putExtra("position_id", item.recruitMessageId)
                intent.putExtra("hislogo", item.avatarURL)


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
        DialogUtils.showLoading(context!!)
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
                DialogUtils.hideLoading()
                adapter!!.UpdatePositionCollectiont(position, isCollection, it.toString())
            }, {
                //失败
                println("创建搜藏失败")
                println(it)
            })

    }


    //取消搜藏职位
    fun unlikeAPositionInfo(id: String, position: Int, isCollection: Boolean) {
        DialogUtils.showLoading(context!!)
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
                DialogUtils.hideLoading()
                adapter!!.UpdatePositionCollectiont(position, isCollection, "")
            }, {
                //失败
                println("取消搜藏失败")
                println(it)
            })

    }


    //重新返回次页面时,获取最新的搜藏信息
    fun getCallBackData(position: Int, isCollection: Boolean, collectionId: String) {

        if (adapter != null) {

            adapter!!.UpdatePositionCollectiont(position, isCollection, collectionId)

        }

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
            result = EducationalBackground.Value.MIDDLE_SCHOOL.text
        } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.HIGH_SCHOOL.toString())) {
            result = EducationalBackground.Value.HIGH_SCHOOL.text
        } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.SHORT_TERM_COLLEGE.toString())) {
            result = EducationalBackground.Value.SHORT_TERM_COLLEGE.text
        } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.BACHELOR.toString())) {
            result = EducationalBackground.Value.BACHELOR.text
        } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.MASTER.toString())) {
            result = EducationalBackground.Value.MASTER.text
        } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.DOCTOR.toString())) {
            result = EducationalBackground.Value.DOCTOR.text
        }
        return result
    }
}

