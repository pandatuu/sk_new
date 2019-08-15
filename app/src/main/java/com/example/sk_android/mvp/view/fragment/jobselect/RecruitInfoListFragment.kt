package com.example.sk_android.mvp.view.fragment.jobselect

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
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.biao.pulltorefresh.OnRefreshListener
import com.biao.pulltorefresh.PtrHandler
import com.biao.pulltorefresh.PtrLayout
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.jobselect.CityInfoApi
import com.example.sk_android.mvp.api.jobselect.JobApi
import com.example.sk_android.mvp.api.jobselect.RecruitInfoApi
import com.example.sk_android.mvp.api.jobselect.UserApi
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.model.jobselect.*
import com.example.sk_android.mvp.view.activity.jobselect.JobInfoDetailActivity
import com.example.sk_android.mvp.view.activity.message.MessageChatWithoutLoginActivity
import com.example.sk_android.mvp.view.adapter.jobselect.RecruitInfoListAdapter
import com.example.sk_android.mvp.view.fragment.common.DialogLoading
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import com.facebook.react.bridge.UiThreadUtil
import com.facebook.react.bridge.UiThreadUtil.runOnUiThread
import com.google.gson.JsonObject
import imui.jiguang.cn.imuisample.messages.MessageListActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Thread.sleep
import android.support.v7.widget.RecyclerView.OnScrollListener as OnScrollListener1

class RecruitInfoListFragment : Fragment() {


    private var mContext: Context? = null
    var mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
    lateinit var recycler: RecyclerView
    var adapter: RecruitInfoListAdapter? = null
    private var dialogLoading: DialogLoading? = null
    var haveData = false

    //搜藏
    var collectionList: MutableList<String> = mutableListOf()
    //记录Id
    var collectionRecordIdList: MutableList<String> = mutableListOf()
    //收藏请求时否完成
    var isCollectionComplete = false
    //初始页数
    var pageNum: Int = 1
    //一夜最大容量
    var pageLimit: Int = 20


    //按条件搜索(职位名)
    var thePositonName: String? = null
    //筛选(公司id)
    var theOrganizationId: String? = null


    var requestDataFinish = true

    var isFirstRequest = true


    lateinit var mainListView: LinearLayout
    lateinit var findNothing: LinearLayout

    //加载中的图标
    var thisDialog: MyDialog?=null
    //下面是筛选的条件
    var filterParamRecruitMethod: String? = null
    var filterParamWorkingType: String? = null
    var filterParamWorkingExperience: Int? = null
    var filterParamCurrencyType: String? = null
    var filterParamSalaryType: String? = null
    var filterParamSalaryMin: Int? = null
    var filterParamSalaryMax: Int? = null
    var filterParamAuditState: String? = null
    var filterParamEducationalBackground: String? = null
    var filterParamIndustryId: String? = null
    var filterParamAddress: String? = null
    var filterParamRadius: Number? = null
    var filterParamFinancingStage: String? = null
    var filterParamSize: String? = null
    var filterPJobWantedIndustryId: String? = null
    var filterParamOrganizationCategory: String? = null

    lateinit var ptrLayout: PtrLayout
    lateinit var header: View
    lateinit var footer: View
    /////
    val main = 1


    var toastCanshow = false

    var useChache = false
    var selfInit=false

    var canAddToCache = false


    //数据在adapter中的位置
    var adapterPosition = 0

    //公司 id-pisition
    var organizationSubData = JSONArray()

    //用户  id-pisition
    var userSubData = JSONArray()

    //地区
    var areaSubData = JSONArray()

    //角色
    var userRoleSubData = JSONArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

        recruiteInfoApi = RetrofitUtils(context!!, "https://organization-position.sk.cgland.top/")
            .create(RecruitInfoApi::class.java)
        companyInfoAPi = RetrofitUtils(context!!, "https://org.sk.cgland.top/")
            .create(RecruitInfoApi::class.java)
        cityInfoApi = RetrofitUtils(context!!, "https://basic-info.sk.cgland.top/")
            .create(CityInfoApi::class.java)
        userApi = RetrofitUtils(context!!, "https://user.sk.cgland.top/")
            .create(UserApi::class.java)
        userRoleMappingApi = RetrofitUtils(context!!, "https://org.sk.cgland.top/")
            .create(UserApi::class.java)

        organizationIdSubscription = organizationIdSubject
            .filter {
                it.isNotBlank()
            }
            .filter {

                var json = JSONObject(it)

                if (organizations.containsKey(json.getString("id"))) {

                    var jsonObject = organizations.get(json.getString("id"))

                    //数据位置
                    var position = json.getInt("position")
                    //公司名
                    var companyName = jsonObject?.optString("name")
                    //福利
                    var benifitsStr = jsonObject?.optString("benifits")
                    //食堂
                    var haveCanteen = false
                    //俱乐部
                    var haveClub = false
                    //社保
                    var haveSocialInsurance = false
                    //交通补助
                    var haveTraffic = false

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

                    var dataJson = JSONObject()
                    dataJson.put("companyName", companyName)
                    dataJson.put("haveCanteen", haveCanteen)
                    dataJson.put("haveClub", haveClub)
                    dataJson.put("haveSocialInsurance", haveSocialInsurance)
                    dataJson.put("haveTraffic", haveTraffic)


                    println("dataJson" + dataJson.toString())
                    println("runOnUiThread" + position.toString())

                    UiThreadUtil.runOnUiThread(Runnable {

                        if (position < 5) {
                            sleep(200)
                        } else {
                            sleep(50)
                        }
                        adapter?.addOrganizationSubDataInfo(dataJson, position)

                    })


                }

                !organizations.containsKey(json.getString("id"))
            }
            .doOnNext {

                println("--------- 获取公司：$it")
                var json = JSONObject(it)
                organizationSubData.put(json)

            }
            .distinctUntilChanged()
            .concatMap {
                var json = JSONObject(it)
                companyInfoAPi.getCompanyInfo(json.getString("id"))
            }
            .onErrorReturn {

                println("--------- 获取公司错误`：$it")

                JsonObject()
            }
            .filter { it.size() > 0 }
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                // TODO: 获取内容，保存

                println("获取公司信息成功！")
                println(it)

                val jsonObject = JSONObject(it.toString())
                val id = jsonObject.optString("id", "")
                if (id.isNotBlank()) {
                    organizations.put(id, jsonObject)
                }


                for (i in 0..organizationSubData.length() - 1) {

                    var item = organizationSubData.getJSONObject(i)
                    if (item.getString("id") == id) {
                        //数据位置
                        var position = item.getInt("position")
                        //公司名
                        var companyName = jsonObject.optString("name")
                        //福利
                        var benifitsStr = jsonObject.optString("benifits")
                        //食堂
                        var haveCanteen = false
                        //俱乐部
                        var haveClub = false
                        //社保
                        var haveSocialInsurance = false
                        //交通补助
                        var haveTraffic = false

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

                        var dataJson = JSONObject()
                        dataJson.put("companyName", companyName)
                        dataJson.put("haveCanteen", haveCanteen)
                        dataJson.put("haveClub", haveClub)
                        dataJson.put("haveSocialInsurance", haveSocialInsurance)
                        dataJson.put("haveTraffic", haveTraffic)


                        adapter?.addOrganizationSubDataInfo(dataJson, position)

                        //发现一个添加一个到页面 删除一个id-position对 循环终止
                        organizationSubData.remove(i)
                        break
                    }
                }
            }, {

                println("获取公司信息失败！")
                println(it)

            })

        userIdSubscription = userIdSubject
            .filter { it.isNotBlank() }
            .filter {
                var json = JSONObject(it)


                if (users.containsKey(json.getString("id"))) {

                    println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx1")
                    var jsonObject = users.get(json.getString("id"))
                    println(jsonObject)
                    println(json)
                    println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx2")


                    //数据位置
                    var position = json.getInt("position")
                    //头像
                    var avatarURL = jsonObject?.getString("avatarURL")
                    if (avatarURL != null) {
                        var arra = avatarURL.split(",")
                        if (arra != null && arra.size > 0) {
                            avatarURL = arra[0]
                        }
                    }
                    //用户名
                    var userName = jsonObject?.getString("displayName")

                    var dataJson = JSONObject()
                    dataJson.put("avatarURL", avatarURL)
                    dataJson.put("userName", userName)



                    UiThreadUtil.runOnUiThread(Runnable {

                        sleep(50)
                        adapter?.addUserSubDataInfo(dataJson, position)
                    })

                }
                !users.containsKey(json?.getString("id"))
            }
            .doOnNext {
                println("--------- 获取用户：$it")
                var json = JSONObject(it)
                userSubData.put(json)
            }
            .distinctUntilChanged()
            .concatMap {
                var json = JSONObject(it)
                userApi.getUserInfo(json.getString("id"))

            }
            .onErrorReturn {
                println("--------- 获取用户错误`：$it")
                JsonObject()
            }
            .filter { it.size() > 0 }
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                // TODO: 获取内容，保存

                println("获取用户信息成功！")
                println(it)

                val jsonObject = JSONObject(it.toString())
                val id = jsonObject.optString("id", "")
                if (id.isNotBlank()) {
                    users.put(id, jsonObject)
                }




                for (i in 0..userSubData.length() - 1) {

                    var item = userSubData.getJSONObject(i)
                    if (item.getString("id") == id) {

                        //数据位置
                        var position = item.getInt("position")
                        //头像
                        var avatarURL = jsonObject.getString("avatarURL")
                        if (avatarURL != null) {
                            var arra = avatarURL.split(",")
                            if (arra != null && arra.size > 0) {
                                avatarURL = arra[0]
                            }
                        }
                        //用户名
                        var userName = jsonObject.getString("displayName")

                        var dataJson = JSONObject()
                        dataJson.put("avatarURL", avatarURL)
                        dataJson.put("userName", userName)



                        adapter?.addUserSubDataInfo(dataJson, position)

                        //发现一个添加一个到页面 删除一个id-position对 循环终止
                        userSubData.remove(i)
                        break
                    }
                }


            }, {

                println("获取用户信息失败！")
                println(it)

            })

        areaIdSubscription = areaIdSubject
            .filter { it.isNotBlank() }
            .filter {
                var json = JSONObject(it)

                if (areas.containsKey(json.getString("id"))) {
                    var jsonObject = areas.get(json.getString("id"))


                    //数据位置
                    var position = json.getInt("position")
                    //地址
                    var address = jsonObject?.getString("name")

                    var dataJson = JSONObject()
                    dataJson.put("address", address)


                    UiThreadUtil.runOnUiThread(Runnable {

                        sleep(10)
                        adapter?.addAreaSubDataInfo(dataJson, position)
                    })


                }




                !areas.containsKey(json.getString("id"))
            }
            .doOnNext {
                println("--------- 获取地区：$it")
                var json = JSONObject(it)
                areaSubData.put(json)
            }
            .distinctUntilChanged()
            .concatMap {
                var json = JSONObject(it)
                cityInfoApi.getAreaInfo(json.getString("id"))
            }
            .onErrorReturn {
                println("--------- 获取地区错误`：$it")
                JsonObject()
            }
            .filter { it.size() > 0 }
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                // TODO: 获取内容，保存
                val jsonObject = JSONObject(it.toString())


                println("获取地区信息成功！")
                println(it)


                val id = jsonObject.optString("id", "")
                if (id.isNotBlank()) {
                    areas.put(id, jsonObject)
                }


                for (i in 0..areaSubData.length() - 1) {

                    var item = areaSubData.getJSONObject(i)
                    if (item.getString("id") == id) {

                        //数据位置
                        var position = item.getInt("position")
                        //地址
                        var address = jsonObject.getString("name")

                        var dataJson = JSONObject()
                        dataJson.put("address", address)



                        adapter?.addAreaSubDataInfo(dataJson, position)

                        //发现一个添加一个到页面 删除一个id-position对 循环终止
                        areaSubData.remove(i)
                        break
                    }
                }


            }, {
                println("获取地区信息失败！")
                println(it)
            })

        userRoleMappingIdsSubscription = userRoleMappingIdsSubject
            .filter { it.isNotBlank() }
            .filter {
                var json = JSONObject(it)


                if (userRoleMappings.containsKey(json.getString("orgId") + json.getString("userId"))) {


                    var jsonObject = userRoleMappings.get(json.getString("orgId") + json.getString("userId"))
                    //数据位置
                    var position = json.getInt("position")
                    //角色名
                    var userPositionName = jsonObject?.getString("name")

                    var dataJson = JSONObject()
                    dataJson.put("userPositionName", userPositionName)


                    UiThreadUtil.runOnUiThread(Runnable {

                        sleep(10)
                        adapter?.addRoleSubDataInfo(dataJson, position)
                    })


                }

                !userRoleMappings.containsKey(json.getString("orgId") + json.getString("userId"))
            }
            .doOnNext {
                println("--------- 获取用户角色：$it")
                var json = JSONObject(it)
                userRoleSubData.put(json)
            }
            .distinctUntilChanged()
            .concatMap {
                var json = JSONObject(it)
                userRoleMappingApi.getUserPosition(json.getString("orgId"), json.getString("userId"))
            }
            .onErrorReturn {
                println("--------- 获取用户角色错误`：$it")
                JsonObject()
            }
            .filter { it.size() > 0 }
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                // TODO: 获取内容，保存


                println("获取角色信息成功！")
                println(it)


                val jsonObject = JSONObject(it.toString())
                val organizationId = jsonObject.optString("organizationId", "")
                val userId = jsonObject.optString("userId", "")

                if (organizationId.isNotBlank()
                    && userId.isNotBlank()
                    && organizationId.equals("00000000-0000-0000-0000-000000000000")
                    && userId.equals("00000000-0000-0000-0000-000000000000")
                ) {
                    userRoleMappings.put(
                        jsonObject.getString("organizationId") + jsonObject.getString("userId"),
                        jsonObject
                    )
                }

                for (i in 0..userRoleSubData.length() - 1) {

                    var item = userRoleSubData.getJSONObject(i)
                    if (item.getString("userId") == userId && item.getString("orgId") == organizationId) {

                        //数据位置
                        var position = item.getInt("position")
                        //角色名
                        var userPositionName = jsonObject.getString("name")

                        var dataJson = JSONObject()
                        dataJson.put("userPositionName", userPositionName)



                        adapter?.addRoleSubDataInfo(dataJson, position)

                        //发现一个添加一个到页面 删除一个id-position对 循环终止
                        userRoleSubData.remove(i)
                        break
                    }
                }


            }, {
                println("获取角色信息失败！")
                println(it)
            })
    }

    override fun onDestroy() {
        userRoleMappingIdsSubscription?.dispose()
        userRoleMappingIdsSubscription = null

        areaIdSubscription?.dispose()
        areaIdSubscription = null

        userIdSubscription?.dispose()
        userIdSubscription = null

        organizationIdSubscription?.dispose()
        organizationIdSubscription = null

        super.onDestroy()
    }


    companion object {

        var ChacheData: MutableList<RecruitInfo> = mutableListOf()


        fun newInstance(
            selfInit:Boolean,
            cache: Boolean,
            positonName: String?,
            organizationId: String?,
            areaId: String?
        ): RecruitInfoListFragment {
            val fragment = RecruitInfoListFragment()
            fragment.selfInit=selfInit
            fragment.useChache = cache
            fragment.thePositonName = positonName
            fragment.theOrganizationId = organizationId
            fragment.filterParamAddress = areaId

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        return fragmentView
    }


    fun createView(): View {
        println("gggggggggggggggggggggggggg")

        getCollection()

        var pullToRefreshContainer =
            LayoutInflater.from(context).inflate(R.layout.springback_recycler_view, null);
        ptrLayout = pullToRefreshContainer as PtrLayout

        //顶部刷新显示
        header =
            LayoutInflater.from(context).inflate(R.layout.fresh_header, null)
        //底部刷新显示
        footer =
            LayoutInflater.from(context).inflate(R.layout.fresh_footer, null)

        //顶部刷新，展示的文字
        var freshText = header.findViewById<TextView>(R.id.freshText)
        //底部刷新展示的文字
        var footerFreshText = footer.findViewById<TextView>(R.id.footerFreshText)

        ptrLayout.setHeaderView(header)
        ptrLayout.setFooterView(footer)

        var pullingFlag = true

        ptrLayout.setHeaderPtrHandler(object : PtrHandler {
            /** when refresh pulling  */
            override fun onPercent(percent: Float) {

                if (percent == 0.0f && !pullingFlag) {
                    pullingFlag = true
                    freshText.setText("スライドでロード")//下拉刷新
                }

                if (percent == 1.0f && pullingFlag) {
                    pullingFlag = false
                    freshText.setText("リリースでロード")//释放更新
                }

            }

            /** when refresh end  */
            override fun onRefreshEnd() {


            }

            /** when refresh begin  */
            override fun onRefreshBegin() {
                freshText.setText("ローディング...")//加载中

            }

        })


        ptrLayout.setFootererPtrHandler(object : PtrHandler {
            /** when refresh pulling  */
            override fun onPercent(percent: Float) {


                if (percent == 0.0f && !pullingFlag) {
                    pullingFlag = true
                    footerFreshText.setText("スライドでロード")//上拉刷新
                }

                if (percent == 1.0f && pullingFlag) {
                    pullingFlag = false
                    footerFreshText.setText("リリースでロード")//释放更新
                }

            }

            /** when refresh end  */
            override fun onRefreshEnd() {


            }

            /** when refresh begin  */
            override fun onRefreshBegin() {
                footerFreshText.setText("ローディング...")//加载中

            }

        })


        ptrLayout.setMode(PtrLayout.MODE_ALL_MOVE)
        ptrLayout.setDuration(10)



        ptrLayout.setOnPullDownRefreshListener(object : OnRefreshListener {
            override fun onRefresh() {
                canAddToCache = true
                filterData(
                    filterParamRecruitMethod,
                    filterParamWorkingType,
                    filterParamWorkingExperience,
                    filterParamCurrencyType,
                    filterParamSalaryType,
                    filterParamSalaryMin,
                    filterParamSalaryMax,
                    filterParamAuditState,
                    filterParamEducationalBackground,
                    filterParamIndustryId,
                    filterParamAddress,
                    filterParamRadius,
                    filterParamFinancingStage,
                    filterParamSize,
                    filterPJobWantedIndustryId,
                    filterParamOrganizationCategory
                )
            }

        })


        ptrLayout.setOnPullUpRefreshListener(object : OnRefreshListener {
            override fun onRefresh() {

                reuqestRecruitInfoData(
                    false,
                    pageNum,
                    pageLimit,
                    theOrganizationId,
                    thePositonName,
                    filterParamRecruitMethod,
                    filterParamWorkingType,
                    filterParamWorkingExperience,
                    null,
                    filterParamSalaryType,
                    filterParamSalaryMin,
                    filterParamSalaryMax,
                    null,
                    filterParamEducationalBackground,
                    filterParamIndustryId,
                    filterParamAddress,
                    null,
                    filterParamFinancingStage,
                    filterParamSize,
                    filterPJobWantedIndustryId,
                    filterParamOrganizationCategory
                )


            }

        })


        recycler =
            pullToRefreshContainer.findViewById(R.id.SBRecyclerView) as RecyclerView

        recycler.overScrollMode = View.OVER_SCROLL_NEVER
        recycler.setLayoutManager(LinearLayoutManager(pullToRefreshContainer.getContext()))

        println("gggggggggggggggggggggggggg")

        //界面
        var view = UI {


            frameLayout {
                id = main
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
//                    recycler = recyclerView {
//                        overScrollMode = View.OVER_SCROLL_NEVER
//                        var manager = LinearLayoutManager(this.getContext())
//                        setLayoutManager(manager)
//                        //manager.setStackFromEnd(true);
//
//                    }.lparams {
//                        leftMargin = dip(12)
//                        rightMargin = dip(12)
//                    }
                    addView(pullToRefreshContainer)

                }.lparams {
                    width = matchParent
                    height = matchParent
                }


            }
        }.view



        recycler.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                toastCanshow = true
                return false

            }

        })

//
//        if (useChache && ChacheData.size > 0) {
//            DialogUtils.showLoading(mContext!!)
//            appendRecyclerData(ChacheData, true,false)
//            pageNum = 2
//            DialogUtils.hideLoading()
//        } else {
//            canAddToCache = true
        if(selfInit){
            reuqestRecruitInfoData(
                false,
                pageNum,
                pageLimit,
                theOrganizationId,
                thePositonName,
                filterParamRecruitMethod,
                filterParamWorkingType,
                filterParamWorkingExperience,
                null,
                filterParamSalaryType,
                filterParamSalaryMin,
                filterParamSalaryMax,
                null,
                filterParamEducationalBackground,
                filterParamIndustryId,
                filterParamAddress,
                null,
                filterParamFinancingStage,
                filterParamSize,
                filterPJobWantedIndustryId,
                filterParamOrganizationCategory
            )
        }

//        }
//


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

    private val organizations: MutableMap<String, JSONObject> = mutableMapOf()
    private val users: MutableMap<String, JSONObject> = mutableMapOf()
    private val areas: MutableMap<String, JSONObject> = mutableMapOf()
    private val userRoleMappings: MutableMap<String, JSONObject> = mutableMapOf()

    private val organizationIdSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private val userIdSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private val areaIdSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private val userRoleMappingIdsSubject: BehaviorSubject<String> =
        BehaviorSubject.createDefault("")

    private var organizationIdSubscription: Disposable? = null
    private var userIdSubscription: Disposable? = null
    private var areaIdSubscription: Disposable? = null
    private var userRoleMappingIdsSubscription: Disposable? = null

    private lateinit var recruiteInfoApi: RecruitInfoApi
    private lateinit var companyInfoAPi: RecruitInfoApi
    private lateinit var cityInfoApi: CityInfoApi
    private lateinit var userApi: UserApi
    private lateinit var userRoleMappingApi: UserApi

    private fun reuqestRecruitInfoData(
        isClear: Boolean,
        _page: Int?,
        _limit: Int?,
        organizationId: String?,
        pName: String?,
        recruitMethod: String?,
        workingType: String?,
        workingExperience: Int?,
        currencyType: String?,
        salaryType: String?,
        salaryMin: Int?,
        salaryMax: Int?,
        auditState: String?,
        educationalBackground: String?,
        industryId: String?,
        address: String?,
        radius: Number?,
        financingStage: String?,
        size: String?,
        jobWantedIndustryId: String?,
        organizationCategory: String?
    ) {

        try{
            thisDialog=DialogUtils.showLoading(mContext!!)
        }catch (e:Exception){
            e.printStackTrace()
        }

        GlobalScope.launch {
            if (!requestDataFinish) {
                return@launch
            }

            requestDataFinish = false
            println("职位信息列表.....")


            val recruitInfoList: MutableList<RecruitInfo> = mutableListOf()

            try {
                val recruiteInfoListJsonObject = recruiteInfoApi.getRecruitInfoList(
                    _page,
                    _limit,
                    organizationId,
                    pName,
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
                    radius,
                    financingStage,
                    size,
                    jobWantedIndustryId,
                    organizationCategory
                )
                    .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                    .awaitSingle()

                println("---------- $recruiteInfoListJsonObject")


                var isOriginal=false
                if (
                    organizationId == null &&
                    pName == null &&
                    recruitMethod == null &&
                    workingType == null &&
                    workingExperience == null &&
                    currencyType == null &&
                    salaryType == null &&
                    salaryMin == null &&
                    salaryMax == null &&
                    auditState == null &&
                    educationalBackground == null &&
                    industryId == null &&
                    address == null &&
                    radius == null &&
                    financingStage == null &&
                    size == null &&
                    jobWantedIndustryId == null &&
                    organizationCategory == null
                ) {

                    isOriginal=true

                }


                val response = JSONObject(recruiteInfoListJsonObject.toString())
                val data = response.getJSONArray("data")
                //如果有数据则可能还有下一页

                if (isFirstRequest) {
                    isFirstRequest = false
                    if (data.length() == 0) {
                        noDataShow()
                    } else {
                        haveDataShow()
                    }
                }

                if (data.length() > 0) {
                    pageNum = 1 + pageNum
                    haveData = true
                } else {

                    haveData = false
                    requestDataFinish = true

                    if (toastCanshow) {
                        withContext(Dispatchers.Main) {
                            val toast = Toast.makeText(activity!!, " これ以上データーがありません", Toast.LENGTH_SHORT)//没有数据了
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }

                    }

                    hideHeaderAndFooter()
                }
                println("职位信息列表请求大小" + data.length())
                println(data.length())

                for (i in (0 until data.length())) {

                    val itemContainer = data.getJSONObject(i)
                    val item = itemContainer.getJSONObject("organization")

                    //职位信息Id
                    val id = item.getString("id")

                    //职位名称
                    val name: String = item.getString("name")


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
                    //是否是最新
                    var isNew = itemContainer.getBoolean("new")


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
                        salaryType = SalaryType.Value.日.toString()
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
                        experience = workingExperience.toString() + "年間"
                    }


                    //职位
                    val content = item.getString("content")
                    //
                    val state = item.getString("state")
                    //
                    val resumeOnly = item.getBoolean("resumeOnly")

                    //公司Id
                    val organizationId: String = item.getString("organizationId")

                    //技能要求
                    val skill = item.getString("skill")

                    //加分项
                    val plus = item.getString("plus")
                    //用户Id
                    val userId = item.optString("userId", "")
                    //公司Id
                    val orgId = item.optString("organizationId", "")
                    //地区ID
                    val areaId = item.optString("areaId", "")
                    // TODO: 获取招聘信息
                    recruitInfoList.add(
                        RecruitInfo(
                            false,
                            "",
                            experience,
                            "",
                            "",
                            salaryType,
                            0,
                            0,
                            0,
                            0,
                            salaryMonthlyMin,
                            0,
                            0,
                            0,
                            showSalaryMinToMax,
                            false,
                            educationalBackground,
                            "",
                            content,
                            state,
                            resumeOnly,
                            isNew,
                            false,
                            name,
                            "",
                            false,
                            false,
                            false,
                            false,
                            "",
                            "",
                            userId,
                            "",
                            false,
                            id,
                            skill,
                            organizationId,
                            "",
                            plus
                        )
                    )
                    //每添加一个数据 position加1


                    var position = adapterPosition

                    var organizationJsonParam = JSONObject()
                    organizationJsonParam.put("id", orgId)
                    organizationJsonParam.put("position", position)
                    // 获取公司
                    organizationIdSubject.onNext(organizationJsonParam.toString())

                    var userJsonParam = JSONObject()
                    userJsonParam.put("id", userId)
                    userJsonParam.put("position", position)
                    // 获取用户
                    userIdSubject.onNext(userJsonParam.toString())

                    var areaJsonParam = JSONObject()
                    areaJsonParam.put("id", areaId)
                    areaJsonParam.put("position", position)
                    // 获取区域
                    areaIdSubject.onNext(areaJsonParam.toString())


                    var userRoleJsonParam = JSONObject()
                    userRoleJsonParam.put("userId", userId)
                    userRoleJsonParam.put("orgId", orgId)
                    userRoleJsonParam.put("position", position)
                    // 获取用户角色映射
                    //val pair = userId to orgId
                    userRoleMappingIdsSubject.onNext(userRoleJsonParam.toString())
                    adapterPosition = adapterPosition + 1
                }


                withContext(Dispatchers.Main) {
                    appendRecyclerData(recruitInfoList, isClear,isOriginal)
                }
            } finally {
                requestDataFinish = true
            }
        }
    }


    fun appendRecyclerData(
        pList: MutableList<RecruitInfo>, isClear: Boolean,isOriginal : Boolean
    ) {


        if(pList.size==0){

            DialogUtils.hideLoading(thisDialog)
            return

        }
        //isOriginal 是否是原始数据，没有条件查询出来的


        //需要用到缓存，且初次请求
        if (useChache && pageNum == 2 && canAddToCache && isOriginal) {
            ChacheData = pList
            canAddToCache = false
        }

        var list: MutableList<RecruitInfo> = mutableListOf()
        for (item in pList) {
            if (item.recruitMessageId != null && !item.recruitMessageId.equals("")) {
                list.add(item)
            }
        }

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
                intent.putExtra("plus", item.plus)

                startActivityForResult(intent, 1)
                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)


            }, { item ->

                lateinit var intent: Intent
                if (App.getInstance()!!.getMessageLoginState()) {
                    //跳转到聊天界面
                    intent = Intent(mContext, MessageListActivity::class.java)
                    intent.putExtra("hisId", item.userId)
                    intent.putExtra("companyName", item.companyName)
                    intent.putExtra("company_id", item.organizationId)
                    intent.putExtra("hisName", item.userName)
                    intent.putExtra("position_id", item.recruitMessageId)
                    intent.putExtra("hislogo", item.avatarURL)


                } else {
                    intent = Intent(mContext, MessageChatWithoutLoginActivity::class.java)
                }

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
            if (isClear) {
                adapter!!.clearRecruitInfoList()
            }
            adapter!!.addRecruitInfoList(list)


        }

        UiThreadUtil.runOnUiThread(Runnable {
            hideHeaderAndFooter()
            sleep(200)
            DialogUtils.hideLoading(thisDialog)
        })

    }


    fun hideHeaderAndFooter() {
        header.postDelayed(Runnable {
            ptrLayout.onRefreshComplete()
        }, 200)

        footer.postDelayed(Runnable {
            ptrLayout.onRefreshComplete()
        }, 200)

        DialogUtils.hideLoading(thisDialog)
    }

    //搜藏职位
    fun toCollectAPositionInfo(id: String, position: Int, isCollection: Boolean) {
        thisDialog=DialogUtils.showLoading(mContext!!)
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
                DialogUtils.hideLoading(thisDialog)
                requestDataFinish = true
                adapter!!.UpdatePositionCollectiont(position, isCollection, it.toString())

                var toast = Toast.makeText(activity!!, "フォロー済み", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()

            }, {
                //失败
                println("创建搜藏失败")
                println(it)
                DialogUtils.hideLoading(thisDialog)
            })

    }


    //取消搜藏职位
    fun unlikeAPositionInfo(id: String, position: Int, isCollection: Boolean) {
        thisDialog=DialogUtils.showLoading(mContext!!)
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
                DialogUtils.hideLoading(thisDialog)
                requestDataFinish = true
                adapter!!.UpdatePositionCollectiont(position, isCollection, "")

                var toast = Toast.makeText(activity!!, "フォロー解除済み", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }, {
                //失败
                println("取消搜藏失败")
                println(it)
                DialogUtils.hideLoading(thisDialog)
            })
    }


    fun noDataShow() {
        UiThreadUtil.runOnUiThread(Runnable {
            mainListView.visibility = View.GONE
            findNothing.visibility = View.VISIBLE
        })
    }

    fun haveDataShow() {
        UiThreadUtil.runOnUiThread(Runnable {
            mainListView.visibility = View.VISIBLE
            findNothing.visibility = View.GONE
        })
    }

    //重新返回次页面时,获取最新的搜藏信息
    fun getCallBackData(position: Int, isCollection: Boolean, collectionId: String) {
        if (adapter != null) {
            adapter!!.UpdatePositionCollectiont(position, isCollection, collectionId)
        }
    }


    fun filterData(
        recruitMethod: String?,
        workingType: String?,
        workingExperience: Int?,
        currencyType: String?,
        salaryType: String?,
        salaryMin: Int?,
        salaryMax: Int?,
        auditState: String?,
        educationalBackground: String?,
        industryId: String?,
        address: String?,
        radius: Number?,
        financingStage: String?,
        size: String?,
        jobWantedIndustryId: String?,
        organizationCategory: String?
    ) {
        adapterPosition = 0
        pageNum = 1
        haveData = false
        isFirstRequest = true
        toastCanshow = false


        filterParamRecruitMethod = recruitMethod
        filterParamWorkingType = workingType
        filterParamWorkingExperience = workingExperience
        filterParamCurrencyType = currencyType
        filterParamSalaryType = salaryType
        filterParamSalaryMin = salaryMin
        filterParamSalaryMax = salaryMax
        filterParamAuditState = auditState
        filterParamEducationalBackground = educationalBackground
        filterParamIndustryId = industryId
        filterParamAddress = address
        filterParamRadius = radius
        filterParamFinancingStage = financingStage
        filterParamSize = size
        filterPJobWantedIndustryId = jobWantedIndustryId
        filterParamOrganizationCategory = organizationCategory


        reuqestRecruitInfoData(
            true,
            pageNum,
            pageLimit,
            theOrganizationId,
            thePositonName,
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
            radius,
            financingStage,
            size,
            jobWantedIndustryId,
            organizationCategory
        )

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


        thousand = "千円"
        tenthousand = "万円"
        million = "台円"

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






    //请求获取数据
//    private fun reuqestRecruitInfoData3(
//        isClear: Boolean,
//        _page: Int?,
//        _limit: Int?,
//        organizationId: String?,
//        pName: String?,
//        recruitMethod: String?,
//        workingType: String?,
//        workingExperience: Int?,
//        currencyType: String?,
//        salaryType: String?,
//        salaryMin: Int?,
//        salaryMax: Int?,
//        auditState: String?,
//        educationalBackground: String?,
//        industryId: String?,
//        address: String?,
//        radius: Number?,
//        financingStage: String?,
//        size: String?,
//        jobWantedIndustryId: String?,
//        organizationCategory: String?
//    ) {
//        if (requestDataFinish) {
//            requestDataFinish = false
//            println("职位信息列表.....")
//            DialogUtils.showLoading(mContext!!)
//
//            var recruitInfoList: MutableList<RecruitInfo> = mutableListOf()
//
//            var retrofitUils = RetrofitUtils(mContext!!, "https://organization-position.sk.cgland.top/")
//            retrofitUils.create(RecruitInfoApi::class.java)
//                .getRecruitInfoList(
//                    _page,
//                    _limit,
//                    organizationId,
//                    pName,
//                    recruitMethod,
//                    workingType,
//                    workingExperience,
//                    currencyType,
//                    salaryType,
//                    salaryMin,
//                    salaryMax,
//                    auditState,
//                    educationalBackground,
//                    industryId,
//                    address,
//                    radius,
//                    financingStage,
//                    size,
//                    jobWantedIndustryId,
//                    organizationCategory
//                )
//                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
//                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
//                .subscribe({
//                    //成功
//                    println("职位信息列表请求成功")
//                    println(it)
//
//                    var response = org.json.JSONObject(it.toString())
//                    var data = response.getJSONArray("data")
//                    //如果有数据则可能还有下一页
//
//                    if (isFirstRequest) {
//                        isFirstRequest = false
//                        if (data.length() == 0) {
//                            noDataShow()
//                        } else {
//                            haveDataShow()
//                        }
//                    }
//
//                    if (data.length() > 0) {
//                        pageNum = 1 + pageNum
//                        haveData = true
//                    } else {
//                        haveData = false
//                        DialogUtils.hideLoading()
//                        requestDataFinish = true
//
//                        if (toastCanshow) {
//                            var toast = Toast.makeText(activity!!, " これ以上データーがありません", Toast.LENGTH_SHORT)//没有数据了
//                            toast.setGravity(Gravity.CENTER, 0, 0)
//                            toast.show()
//                        }
//
//                        hideHeaderAndFooter()
//                    }
//                    println("职位信息列表请求大小" + data.length())
//                    println(data.length())
//
//                    var requestFlag = mutableListOf<Boolean>()
//
//                    var flag_haveCompanyPosition = false
//                    for (i in 0..data.length() - 1) {
//
//                        requestFlag.add(false)
//                        recruitInfoList.add(
//                            RecruitInfo(
//                                false,
//                                "",
//                                "",
//                                "",
//                                "",
//                                "",
//                                0,
//                                0,
//                                0,
//                                0,
//                                0,
//                                0,
//                                0,
//                                0,
//                                "",
//                                false,
//                                "",
//                                "",
//                                "",
//                                "",
//                                false,
//                                false,
//                                false,
//                                "",
//                                "",
//                                false,
//                                false,
//                                false,
//                                false,
//                                "",
//                                "",
//                                "",
//                                "",
//                                false,
//                                "",
//                                "",
//                                "",
//                                "",
//                                ""
//                            )
//                        )
//
//
//                        println("循环!!!!!")
//                        //公司请求完成
//                        var requestCompanyComplete = false
//                        //地址请求完成
//                        var requestAddressComplete = false
//                        //用户请求完成
//                        var requestUserComplete = false
//                        //用户角色请求完成
//                        var requestUserPositionComplete = false
//
//
//                        var itemContainer = data.getJSONObject(i)
//
//                        var item = itemContainer.getJSONObject("organization")
//
//                        //是否是最新
//                        var isNew = itemContainer.getBoolean("new")
//                        //是否加急
//                        var emergency = false
//                        if (item.has("emergency")) {
//                            emergency = item.getBoolean("emergency")
//                        }
//                        //招聘方式
//                        var recruitMethod = ""
//                        if (item.has("recruitMethod")) {
//                            recruitMethod = item.getString("recruitMethod")
//                        }
//                        //工作经验
//                        var workingExperience = 0
//                        if (item.has("workingExperience")) {
//                            workingExperience = item.getInt("workingExperience")
//                        }
//                        //工作方式类型
//                        var workingType = ""
//                        if (item.has("workingType")) {
//                            workingType = item.getString("workingType")
//                        }
//                        //货币类型
//                        var currencyType = ""
//                        if (item.has("currencyType")) {
//                            currencyType = item.getString("currencyType")
//                        }
//                        //薪水类型
//                        var salaryType = ""
//                        if (item.has("salaryType")) {
//                            salaryType = item.getString("salaryType")
//                        }
//                        //时薪Min
//                        var salaryHourlyMin: Int? = null
//                        if (item.has("salaryHourlyMin") && item.get("salaryHourlyMin") != null && !item.get("salaryHourlyMin").toString().equals(
//                                "null"
//                            )
//                        ) {
//                            salaryHourlyMin = item.getInt("salaryHourlyMin")
//                        }
//                        //时薪Max
//                        var salaryHourlyMax: Int? = null
//                        if (item.has("salaryHourlyMax") && item.get("salaryHourlyMax") != null && !item.get("salaryHourlyMax").toString().equals(
//                                "null"
//                            )
//                        ) {
//                            salaryHourlyMax = item.getInt("salaryHourlyMax")
//                        }
//                        //日薪Min
//                        var salaryDailyMin: Int? = null
//                        if (item.has("salaryDailyMin") && item.get("salaryDailyMin") != null && !item.get("salaryDailyMin").toString().equals(
//                                "null"
//                            )
//                        ) {
//                            salaryDailyMin = item.getInt("salaryDailyMin")
//                        }
//                        //日薪Max
//                        var salaryDailyMax: Int? = null
//                        if (item.has("salaryDailyMax") && item.get("salaryDailyMax") != null && !item.get("salaryDailyMax").toString().equals(
//                                "null"
//                            )
//                        ) {
//                            salaryDailyMax = item.getInt("salaryDailyMax")
//                        }
//                        //月薪Min
//                        var salaryMonthlyMin: Int? = null
//                        if (item.has("salaryMonthlyMin") && item.get("salaryMonthlyMin") != null && !item.get("salaryMonthlyMin").toString().equals(
//                                "null"
//                            )
//                        ) {
//                            salaryMonthlyMin = item.getInt("salaryMonthlyMin")
//                        }
//                        //月薪Max
//                        var salaryMonthlyMax: Int? = null
//                        if (item.has("salaryMonthlyMax") && item.get("salaryMonthlyMax") != null && !item.get("salaryMonthlyMax").toString().equals(
//                                "null"
//                            )
//                        ) {
//                            salaryMonthlyMax = item.getInt("salaryMonthlyMax")
//                        }
//                        //年薪Min
//                        var salaryYearlyMin: Int? = null
//                        if (item.has("salaryYearlyMin") && item.get("salaryYearlyMin") != null && !item.get("salaryYearlyMin").toString().equals(
//                                "null"
//                            )
//                        ) {
//                            salaryYearlyMin = item.getInt("salaryYearlyMin")
//                        }
//                        //年薪Max
//                        var salaryYearlyMax: Int? = null
//                        if (item.has("salaryYearlyMax") && item.get("salaryYearlyMax") != null && !item.get("salaryYearlyMax").toString().equals(
//                                "null"
//                            )
//                        ) {
//                            salaryYearlyMax = item.getInt("salaryYearlyMax")
//                        }
//                        //
//                        val calculateSalary = item.getBoolean("calculateSalary")
//                        //教育背景
//                        var educationalBackground = item.getString("educationalBackground")
//                        //职位
//                        val content = item.getString("content")
//                        //
//                        val state = item.getString("state")
//                        //
//                        val resumeOnly = item.getBoolean("resumeOnly")
//
//                        //职位名称
//                        val name: String = item.getString("name")
//                        //公司Id
//                        val organizationId: String = item.getString("organizationId")
//                        //地区ID
//                        val areaId: String = item.getString("areaId")
//                        //用户Id
//                        val userId: String = item.getString("userId")
//                        //职位信息Id
//                        val id: String = item.getString("id")
//                        //技能要求
//                        val skill = item.getString("skill")
//
//
//                        var isCollection = false
//                        //搜藏记录的Id
//                        var collectionId = ""
//
//                        //
//                        //组装数据
//                        //
//
//                        var currencyTypeUnitHead: String = ""
//                        var currencyTypeUnitTail: String = ""
//                        var unitType: Int = 0
//                        if (currencyType != null && currencyType.equals("CNY")) {
//                            // currencyTypeUnitTail="元"
//                            unitType = 1
//                        } else if (currencyType != null && currencyType.equals("JPY")) {
//                            //  currencyTypeUnitTail="円"
//                            unitType = 1
//                        } else if (currencyType != null && currencyType.equals("USD")) {
//                            //  currencyTypeUnitHead="$"
//                            unitType = 2
//                        }
//
//                        ""
//                        //拼接薪水范围
//                        var showSalaryMinToMax: String = ""
//                        if (salaryType != null && salaryType.equals(SalaryType.Key.HOURLY.toString())) {
//                            showSalaryMinToMax = getSalaryMinToMaxString(
//                                salaryHourlyMin,
//                                salaryHourlyMax,
//                                currencyTypeUnitHead,
//                                currencyTypeUnitTail,
//                                unitType
//                            )
//                            salaryType = SalaryType.Value.时.toString()
//                        } else if (salaryType != null && salaryType.equals(SalaryType.Key.DAILY.toString())) {
//                            showSalaryMinToMax = getSalaryMinToMaxString(
//                                salaryDailyMin,
//                                salaryDailyMax,
//                                currencyTypeUnitHead,
//                                currencyTypeUnitTail,
//                                unitType
//                            )
//                            salaryType = SalaryType.Value.日.toString()
//                        } else if (salaryType != null && salaryType.equals(SalaryType.Key.MONTHLY.toString())) {
//                            showSalaryMinToMax = getSalaryMinToMaxString(
//                                salaryMonthlyMin,
//                                salaryMonthlyMax,
//                                currencyTypeUnitHead,
//                                currencyTypeUnitTail,
//                                unitType
//                            )
//                            salaryType = SalaryType.Value.月.toString()
//                        } else if (salaryType != null && salaryType.equals(SalaryType.Key.YEARLY.toString())) {
//                            showSalaryMinToMax = getSalaryMinToMaxString(
//                                salaryYearlyMin,
//                                salaryYearlyMax,
//                                currencyTypeUnitHead,
//                                currencyTypeUnitTail,
//                                unitType
//                            )
//                            salaryType = SalaryType.Value.年.toString()
//                        }
//
//                        //教育背景
//                        educationalBackground = getEducationalBackground(educationalBackground)
//
//                        //工作经验
//                        var experience: String? = null
//                        if (workingExperience != null && workingExperience != 0) {
//                            experience = workingExperience.toString() + "年間"
//                        }
//
//                        //地址
//                        var address: String? = ""
//
//
//                        //有食堂吗
//                        var haveCanteen: Boolean = false
//                        //有俱乐部吗
//                        var haveClub: Boolean = false
//                        //有社保吗
//                        var haveSocialInsurance: Boolean = false
//                        //有交通补助吗
//                        var haveTraffic: Boolean = false
//                        //公司名称
//                        var companyName: String = ""
//                        //底部的事件需要显示吗
//                        var bottomShow = false
//                        //user职位名称
//                        var userPositionName: String = ""
//                        //用户头像
//                        var avatarURL: String = ""
//                        //用户名字
//                        var userName: String = ""
//                        //加分项
//                        var plus: String = item.getString("plus")
//
//
//
//                        if (theOrganizationId != null) {
//                            //筛选公司下面的职位
//                            if (!theOrganizationId.equals(organizationId)) {
//                                if (i == data.length() - 1 && !flag_haveCompanyPosition) {
//                                    //最后一次循环还没有匹配到一个
//                                    DialogUtils.hideLoading()
//                                    requestDataFinish = true
//                                }
//                                noDataShow()
//                                continue
//                            } else {
//                                flag_haveCompanyPosition = true
//                            }
//
//                        }
//
//
//                        //请求公司信息
//                        var requestCompany = RetrofitUtils(mContext!!, "https://org.sk.cgland.top/")
//                        requestCompany.create(RecruitInfoApi::class.java)
//                            .getCompanyInfo(
//                                organizationId
//                                //"f71cc420-27b4-4575-8ad1-9485a4de305f"
//                            )
//                            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
//                            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
//                            .subscribe({
//                                println("公司信息请求成功")
//                                println(it)
//                                requestCompanyComplete = true
//                                var json = org.json.JSONObject(it.toString())
//                                companyName = json.getString("name")
//
//                                var benifitsStr = json.getString("benifits")
//                                if (benifitsStr != null && !benifitsStr.equals("null")) {
//                                    var benifits = JSONArray(benifitsStr)
//                                    for (i in 0..benifits.length() - 1) {
//                                        var str = benifits.get(i).toString()
//                                        if (str != null && str.equals(Benifits.Key.CANTEEN.toString())) {
//                                            haveCanteen = true
//                                        } else if (str != null && str.equals(Benifits.Key.CLUB.toString())) {
//                                            haveClub = true
//                                        } else if (str != null && str.equals(Benifits.Key.SOCIAL_INSURANCE.toString())) {
//                                            haveSocialInsurance = true
//                                        } else if (str != null && str.equals(Benifits.Key.TRAFFIC.toString())) {
//                                            haveTraffic = true
//                                        }
//                                    }
//                                }
//
//
//                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete && requestUserPositionComplete) {
//                                    //存在问题 ,暂时这样做
//                                    if (isCollectionComplete) {
//                                        for (i in 0..collectionList.size - 1) {
//                                            if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
//                                                isCollection = true
//                                                collectionId = collectionRecordIdList.get(i)
//                                            }
//                                        }
//                                    }
//
//                                    var recruitInfo = RecruitInfo(
//                                        emergency,
//                                        recruitMethod,
//                                        experience,
//                                        workingType,
//                                        currencyType,
//                                        salaryType,
//                                        salaryHourlyMin,
//                                        salaryHourlyMax,
//                                        salaryDailyMin,
//                                        salaryDailyMax,
//                                        salaryMonthlyMin,
//                                        salaryMonthlyMax,
//                                        salaryYearlyMin,
//                                        salaryYearlyMax,
//                                        showSalaryMinToMax,
//                                        calculateSalary,
//                                        educationalBackground,
//                                        address,
//                                        content,
//                                        state,
//                                        resumeOnly,
//                                        isNew,
//                                        bottomShow,
//                                        name,
//                                        companyName,
//                                        haveCanteen,
//                                        haveClub,
//                                        haveSocialInsurance,
//                                        haveTraffic,
//                                        userPositionName,
//                                        avatarURL,
//                                        userId,
//                                        userName,
//                                        isCollection,
//                                        id,
//                                        skill,
//                                        organizationId,
//                                        collectionId,
//                                        plus
//                                    )
//                                    recruitInfoList.set(i, recruitInfo)
//                                    requestFlag.set(i, true)
//                                    for (i in 0..requestFlag.size - 1) {
//                                        if (!requestFlag.get(i)) {
//                                            break
//                                        }
//                                        if (i == requestFlag.size - 1) {
//                                            appendRecyclerData(recruitInfoList, isClear)
//                                            DialogUtils.hideLoading()
//                                            requestDataFinish = true
//                                        }
//                                    }
//
//
//                                }
//
//                            }, {
//                                //失败
//                                println("公司信息请求失败")
//                                println(it)
//                                requestCompanyComplete = true
//
//                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete && requestUserPositionComplete) {
//                                    //存在问题 ,暂时这样做
//                                    if (isCollectionComplete) {
//                                        for (i in 0..collectionList.size - 1) {
//                                            if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
//                                                isCollection = true
//                                                collectionId = collectionRecordIdList.get(i)
//
//                                            }
//                                        }
//                                    }
//
//                                    var recruitInfo = RecruitInfo(
//                                        emergency,
//                                        recruitMethod,
//                                        experience,
//                                        workingType,
//                                        currencyType,
//                                        salaryType,
//                                        salaryHourlyMin,
//                                        salaryHourlyMax,
//                                        salaryDailyMin,
//                                        salaryDailyMax,
//                                        salaryMonthlyMin,
//                                        salaryMonthlyMax,
//                                        salaryYearlyMin,
//                                        salaryYearlyMax,
//                                        showSalaryMinToMax,
//                                        calculateSalary,
//                                        educationalBackground,
//                                        address,
//                                        content,
//                                        state,
//                                        resumeOnly,
//                                        isNew,
//                                        bottomShow,
//                                        name,
//                                        companyName,
//                                        haveCanteen,
//                                        haveClub,
//                                        haveSocialInsurance,
//                                        haveTraffic,
//                                        userPositionName,
//                                        avatarURL,
//                                        userId,
//                                        userName,
//                                        isCollection,
//                                        id,
//                                        skill,
//                                        organizationId,
//                                        collectionId,
//                                        plus
//                                    )
//                                    recruitInfoList.set(i, recruitInfo)
//                                    requestFlag.set(i, true)
//                                    for (i in 0..requestFlag.size - 1) {
//                                        if (!requestFlag.get(i)) {
//                                            break
//                                        }
//                                        if (i == requestFlag.size - 1) {
//                                            appendRecyclerData(recruitInfoList, isClear)
//                                            DialogUtils.hideLoading()
//                                            requestDataFinish = true
//                                        }
//                                    }
//                                }
//                            })
//
//
//                        //请求地址
//                        var requestAddress = RetrofitUtils(mContext!!, "https://basic-info.sk.cgland.top/")
//                        requestAddress.create(CityInfoApi::class.java)
//                            .getAreaInfo(
//                                areaId
//                            )
//                            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
//                            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
//                            .subscribe({
//                                println("地址信息请求成功")
//                                println(it)
//
//                                address = JSONObject(it.toString()).getString("name")
//
//                                requestAddressComplete = true
//                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete) {
//                                    //存在问题 ,暂时这样做
//                                    if (isCollectionComplete) {
//                                        for (i in 0..collectionList.size - 1) {
//                                            if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
//                                                isCollection = true
//                                                collectionId = collectionRecordIdList.get(i)
//
//                                            }
//                                        }
//                                    }
//
//                                    if (requestCompanyComplete && requestAddressComplete && requestUserComplete && requestUserPositionComplete) {
//
//                                        var recruitInfo = RecruitInfo(
//                                            emergency,
//                                            recruitMethod,
//                                            experience,
//                                            workingType,
//                                            currencyType,
//                                            salaryType,
//                                            salaryHourlyMin,
//                                            salaryHourlyMax,
//                                            salaryDailyMin,
//                                            salaryDailyMax,
//                                            salaryMonthlyMin,
//                                            salaryMonthlyMax,
//                                            salaryYearlyMin,
//                                            salaryYearlyMax,
//                                            showSalaryMinToMax,
//                                            calculateSalary,
//                                            educationalBackground,
//                                            address,
//                                            content,
//                                            state,
//                                            resumeOnly,
//                                            isNew,
//                                            bottomShow,
//                                            name,
//                                            companyName,
//                                            haveCanteen,
//                                            haveClub,
//                                            haveSocialInsurance,
//                                            haveTraffic,
//                                            userPositionName,
//                                            avatarURL,
//                                            userId,
//                                            userName,
//                                            isCollection,
//                                            id,
//                                            skill,
//                                            organizationId,
//                                            collectionId,
//                                            plus
//                                        )
//                                        recruitInfoList.set(i, recruitInfo)
//                                        requestFlag.set(i, true)
//                                        for (i in 0..requestFlag.size - 1) {
//                                            if (!requestFlag.get(i)) {
//                                                break
//                                            }
//                                            if (i == requestFlag.size - 1) {
//                                                appendRecyclerData(recruitInfoList, isClear)
//                                                DialogUtils.hideLoading()
//                                                requestDataFinish = true
//                                            }
//                                        }
//                                    }
//                                }
//
//                            }, {
//                                //失败
//                                //返回404就是没查到
//                                println("地址信息请求失败")
//                                println(it)
//                                requestAddressComplete = true
//
//                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete && requestUserPositionComplete) {
//                                    //存在问题 ,暂时这样做
//                                    if (isCollectionComplete) {
//                                        for (i in 0..collectionList.size - 1) {
//                                            if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
//                                                isCollection = true
//                                                collectionId = collectionRecordIdList.get(i)
//
//                                            }
//                                        }
//                                    }
//
//                                    var recruitInfo = RecruitInfo(
//                                        emergency,
//                                        recruitMethod,
//                                        experience,
//                                        workingType,
//                                        currencyType,
//                                        salaryType,
//                                        salaryHourlyMin,
//                                        salaryHourlyMax,
//                                        salaryDailyMin,
//                                        salaryDailyMax,
//                                        salaryMonthlyMin,
//                                        salaryMonthlyMax,
//                                        salaryYearlyMin,
//                                        salaryYearlyMax,
//                                        showSalaryMinToMax,
//                                        calculateSalary,
//                                        educationalBackground,
//                                        address,
//                                        content,
//                                        state,
//                                        resumeOnly,
//                                        isNew,
//                                        bottomShow,
//                                        name,
//                                        companyName,
//                                        haveCanteen,
//                                        haveClub,
//                                        haveSocialInsurance,
//                                        haveTraffic,
//                                        userPositionName,
//                                        avatarURL,
//                                        userId,
//                                        userName,
//                                        isCollection,
//                                        id,
//                                        skill,
//                                        organizationId,
//                                        collectionId,
//                                        plus
//                                    )
//                                    recruitInfoList.set(i, recruitInfo)
//                                    requestFlag.set(i, true)
//                                    for (i in 0..requestFlag.size - 1) {
//                                        if (!requestFlag.get(i)) {
//                                            break
//                                        }
//                                        if (i == requestFlag.size - 1) {
//                                            appendRecyclerData(recruitInfoList, isClear)
//                                            DialogUtils.hideLoading()
//                                            requestDataFinish = true
//                                        }
//                                    }
//                                }
//                            })
//
//
//                        //用户信息请求
//                        var requestUser = RetrofitUtils(mContext!!, "https://user.sk.cgland.top/")
//                        requestUser.create(UserApi::class.java)
//                            .getUserInfo(
//                                userId
//                            )
//                            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
//                            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
//                            .subscribe({
//                                println("用户信息请求成功")
//                                println(it)
//
//
//                                avatarURL = JSONObject(it.toString()).getString("avatarURL")
//                                if (avatarURL != null) {
//                                    var arra = avatarURL.split(",")
//                                    if (arra != null && arra.size > 0) {
//                                        avatarURL = arra[0]
//                                    }
//                                }
//
//
//
//                                userName = JSONObject(it.toString()).getString("displayName")
//
//                                requestUserComplete = true
//                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete && requestUserPositionComplete) {
//                                    //存在问题 ,暂时这样做
//                                    if (isCollectionComplete) {
//                                        for (i in 0..collectionList.size - 1) {
//                                            if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
//                                                isCollection = true
//                                                collectionId = collectionRecordIdList.get(i)
//
//                                            }
//                                        }
//                                    }
//
//                                    var recruitInfo = RecruitInfo(
//                                        emergency,
//                                        recruitMethod,
//                                        experience,
//                                        workingType,
//                                        currencyType,
//                                        salaryType,
//                                        salaryHourlyMin,
//                                        salaryHourlyMax,
//                                        salaryDailyMin,
//                                        salaryDailyMax,
//                                        salaryMonthlyMin,
//                                        salaryMonthlyMax,
//                                        salaryYearlyMin,
//                                        salaryYearlyMax,
//                                        showSalaryMinToMax,
//                                        calculateSalary,
//                                        educationalBackground,
//                                        address,
//                                        content,
//                                        state,
//                                        resumeOnly,
//                                        isNew,
//                                        bottomShow,
//                                        name,
//                                        companyName,
//                                        haveCanteen,
//                                        haveClub,
//                                        haveSocialInsurance,
//                                        haveTraffic,
//                                        userPositionName,
//                                        avatarURL,
//                                        userId,
//                                        userName,
//                                        isCollection,
//                                        id,
//                                        skill,
//                                        organizationId,
//                                        collectionId,
//                                        plus
//                                    )
//                                    recruitInfoList.set(i, recruitInfo)
//                                    requestFlag.set(i, true)
//                                    for (i in 0..requestFlag.size - 1) {
//                                        if (!requestFlag.get(i)) {
//                                            break
//                                        }
//                                        if (i == requestFlag.size - 1) {
//                                            appendRecyclerData(recruitInfoList, isClear)
//                                            DialogUtils.hideLoading()
//                                            requestDataFinish = true
//                                        }
//                                    }
//                                }
//
//                            }, {
//                                //失败
//                                println("用户信息请求失败")
//                                println(it)
//                                requestUserComplete = true
//
//                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete && requestUserPositionComplete) {
//                                    //存在问题 ,暂时这样做
//                                    if (isCollectionComplete) {
//                                        for (i in 0..collectionList.size - 1) {
//                                            if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
//                                                isCollection = true
//                                                collectionId = collectionRecordIdList.get(i)
//
//                                            }
//                                        }
//                                    }
//
//                                    var recruitInfo = RecruitInfo(
//                                        emergency,
//                                        recruitMethod,
//                                        experience,
//                                        workingType,
//                                        currencyType,
//                                        salaryType,
//                                        salaryHourlyMin,
//                                        salaryHourlyMax,
//                                        salaryDailyMin,
//                                        salaryDailyMax,
//                                        salaryMonthlyMin,
//                                        salaryMonthlyMax,
//                                        salaryYearlyMin,
//                                        salaryYearlyMax,
//                                        showSalaryMinToMax,
//                                        calculateSalary,
//                                        educationalBackground,
//                                        address,
//                                        content,
//                                        state,
//                                        resumeOnly,
//                                        isNew,
//                                        bottomShow,
//                                        name,
//                                        companyName,
//                                        haveCanteen,
//                                        haveClub,
//                                        haveSocialInsurance,
//                                        haveTraffic,
//                                        userPositionName,
//                                        avatarURL,
//                                        userId,
//                                        userName,
//                                        isCollection,
//                                        id,
//                                        skill,
//                                        organizationId,
//                                        collectionId,
//                                        plus
//                                    )
//                                    recruitInfoList.set(i, recruitInfo)
//                                    requestFlag.set(i, true)
//                                    for (i in 0..requestFlag.size - 1) {
//                                        if (!requestFlag.get(i)) {
//                                            break
//                                        }
//                                        if (i == requestFlag.size - 1) {
//                                            appendRecyclerData(recruitInfoList, isClear)
//                                            DialogUtils.hideLoading()
//                                            requestDataFinish = true
//                                        }
//                                    }
//                                }
//                            })
//
//
//                        //用户角色信息
//                        var requestUserPosition = RetrofitUtils(mContext!!, "https://org.sk.cgland.top/")
//                        requestUserPosition.create(UserApi::class.java)
//                            .getUserPosition(
//                                organizationId, userId
//                            )
//                            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
//                            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
//                            .subscribe({
//                                println("用户角色信息请求成功")
//                                println(it)
//                                var itemJson = JSONObject(it.toString())
//                                userPositionName = itemJson.getString("name")
//
//                                requestUserPositionComplete = true
//
//                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete && requestUserPositionComplete) {
//                                    //存在问题 ,暂时这样做
//                                    if (isCollectionComplete) {
//                                        for (i in 0..collectionList.size - 1) {
//                                            if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
//                                                isCollection = true
//                                                collectionId = collectionRecordIdList.get(i)
//
//                                            }
//                                        }
//                                    }
//
//                                    var recruitInfo = RecruitInfo(
//                                        emergency,
//                                        recruitMethod,
//                                        experience,
//                                        workingType,
//                                        currencyType,
//                                        salaryType,
//                                        salaryHourlyMin,
//                                        salaryHourlyMax,
//                                        salaryDailyMin,
//                                        salaryDailyMax,
//                                        salaryMonthlyMin,
//                                        salaryMonthlyMax,
//                                        salaryYearlyMin,
//                                        salaryYearlyMax,
//                                        showSalaryMinToMax,
//                                        calculateSalary,
//                                        educationalBackground,
//                                        address,
//                                        content,
//                                        state,
//                                        resumeOnly,
//                                        isNew,
//                                        bottomShow,
//                                        name,
//                                        companyName,
//                                        haveCanteen,
//                                        haveClub,
//                                        haveSocialInsurance,
//                                        haveTraffic,
//                                        userPositionName,
//                                        avatarURL,
//                                        userId,
//                                        userName,
//                                        isCollection,
//                                        id,
//                                        skill,
//                                        organizationId,
//                                        collectionId,
//                                        plus
//                                    )
//                                    recruitInfoList.set(i, recruitInfo)
//                                    requestFlag.set(i, true)
//                                    for (i in 0..requestFlag.size - 1) {
//                                        if (!requestFlag.get(i)) {
//                                            break
//                                        }
//                                        if (i == requestFlag.size - 1) {
//                                            appendRecyclerData(recruitInfoList, isClear)
//                                            DialogUtils.hideLoading()
//                                            requestDataFinish = true
//                                        }
//                                    }
//                                }
//
//                            }, {
//                                //失败
//                                println("用户角色信息请求失败")
//                                println(it)
//
//                                requestUserPositionComplete = true
//                                if (requestCompanyComplete && requestAddressComplete && requestUserComplete && requestUserPositionComplete) {
//                                    //存在问题 ,暂时这样做
//                                    if (isCollectionComplete) {
//                                        for (i in 0..collectionList.size - 1) {
//                                            if (collectionList.get(i) != null && collectionList.get(i).equals(id)) {
//                                                isCollection = true
//                                                collectionId = collectionRecordIdList.get(i)
//
//                                            }
//                                        }
//                                    }
//
//                                    var recruitInfo = RecruitInfo(
//                                        emergency,
//                                        recruitMethod,
//                                        experience,
//                                        workingType,
//                                        currencyType,
//                                        salaryType,
//                                        salaryHourlyMin,
//                                        salaryHourlyMax,
//                                        salaryDailyMin,
//                                        salaryDailyMax,
//                                        salaryMonthlyMin,
//                                        salaryMonthlyMax,
//                                        salaryYearlyMin,
//                                        salaryYearlyMax,
//                                        showSalaryMinToMax,
//                                        calculateSalary,
//                                        educationalBackground,
//                                        address,
//                                        content,
//                                        state,
//                                        resumeOnly,
//                                        isNew,
//                                        bottomShow,
//                                        name,
//                                        companyName,
//                                        haveCanteen,
//                                        haveClub,
//                                        haveSocialInsurance,
//                                        haveTraffic,
//                                        userPositionName,
//                                        avatarURL,
//                                        userId,
//                                        userName,
//                                        isCollection,
//                                        id,
//                                        skill,
//                                        organizationId,
//                                        collectionId,
//                                        plus
//                                    )
//                                    recruitInfoList.set(i, recruitInfo)
//                                    requestFlag.set(i, true)
//                                    for (i in 0..requestFlag.size - 1) {
//                                        if (!requestFlag.get(i)) {
//                                            break
//                                        }
//                                        if (i == requestFlag.size - 1) {
//                                            appendRecyclerData(recruitInfoList, isClear)
//                                            DialogUtils.hideLoading()
//                                            requestDataFinish = true
//                                        }
//                                    }
//                                }
//                            })
//
//
//                    }
//
//
//                }, {
//                    //失败
//                    println("职位信息列表请求失败")
//                    println(it)
//                    requestDataFinish = true
//
//                    UiThreadUtil.runOnUiThread(Runnable {
//                        hideHeaderAndFooter()
//                        sleep(200)
//                        DialogUtils.hideLoading()
//                    })
//                })
//        }
//
//    }


}

