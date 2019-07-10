package com.example.sk_android.mvp.view.fragment.person

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
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.api.jobselect.JobApi
import com.example.sk_android.mvp.api.jobselect.RecruitInfoApi
import com.example.sk_android.mvp.api.person.Interview
import com.example.sk_android.mvp.model.jobselect.*
import com.example.sk_android.mvp.model.person.InterviewInfo
import com.example.sk_android.mvp.view.activity.jobselect.JobInfoDetailActivity
import com.example.sk_android.mvp.view.activity.person.FaceActivity
import com.example.sk_android.mvp.view.activity.person.FaceCloseActivity
import com.example.sk_android.mvp.view.adapter.company.BaseFragmentAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.RecruitInfoListAdapter
import com.example.sk_android.mvp.view.adapter.person.InterviewListAdapter
import com.example.sk_android.mvp.view.fragment.common.DialogLoading
import com.example.sk_android.mvp.view.fragment.jobselect.ProductDetailInfoBottomPartFragment
import com.example.sk_android.mvp.view.fragment.jobselect.RecruitInfoListFragment
import com.example.sk_android.utils.RetrofitUtils
import imui.jiguang.cn.imuisample.messages.MessageListActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import java.security.PrivateKey
import java.text.SimpleDateFormat
import java.util.*

class InterviewListFragmentRejected : Fragment() {


    private var mContext: Context? = null
    private var dataType: String = ""
    private var dataTypeInt: Int =3


    private var dialogLoading: DialogLoading? = null
    val main = 1


    //初始页数
    var pageNum: Int = 1
    //一夜最大容量
    var pageLimit: Int = 20


    var isFirstRequest = true
    var haveData = false


    var requestDataFinish=true

    lateinit var mainListView: LinearLayout
    lateinit var findNothing: LinearLayout



    var adapter: InterviewListAdapter? = null
    lateinit var recycler: RecyclerView

    var  isFirstInit=true




    var allTheData_REJECTED: MutableList<InterviewInfo> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    override fun onResume() {
        super.onResume()
        pageNum=1

    }

    companion object {
        fun newInstance(): InterviewListFragmentRejected {
            val fragment = InterviewListFragmentRejected()

                fragment.dataType = "FINISHED"

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        return fragmentView
    }




    fun createView(): View {

        var view= UI {
            frameLayout {
                id = main
                findNothing = verticalLayout {
                    gravity=Gravity.CENTER_HORIZONTAL

                    visibility = View.GONE
                    imageView {
                        setImageResource(R.mipmap.icon_empty)
                    }.lparams {
                        width = dip(170)
                        height = dip(100)
                    }

                    textView {
                        text = "現時点では関連内容はありません"
                        textSize = 14f
                        textColorResource = R.color.gray5c
                    }.lparams {
                        topMargin = dip(25)
                    }
                }.lparams() {
                    width = wrapContent
                    height = wrapContent
                    gravity = Gravity.CENTER
                    bottomMargin=dip(10)
                }
                mainListView = verticalLayout {

                    backgroundColorResource = R.color.originColor
                    //backgroundColorResource=R.color.black20
                    recycler=  recyclerView {
                        overScrollMode = View.OVER_SCROLL_NEVER
                        var manager = LinearLayoutManager(this.getContext())
                        setLayoutManager(manager)
                        //manager.setStackFromEnd(true);

                    }.lparams {

                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }.view

            showLoading()
            requestInterViewList()


        return view
    }


    fun requestInterViewList() {

        println(pageNum.toString())
        if (!dataType.equals("") && requestDataFinish) {
            requestDataFinish=false
            //请求面试列表
            var request = RetrofitUtils(activity!!, "https://interview.sk.cgland.top/")
            request.create(Interview::class.java)
                .getMyInterviewList(
                    pageNum, pageLimit, dataType, false
                )
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    println("请求面试列表请求成功"+dataType)
                    println(it)
                    var responseStr = org.json.JSONObject(it.toString())
                    var data = responseStr.getJSONArray("data")



                    if (isFirstRequest) {
                        isFirstRequest = false
                        if (data.length() == 0) {
                            mainListView.visibility = View.GONE
                            findNothing.visibility = View.VISIBLE
                        }
                    }

                    if (data.length() > 0) {
                        haveData = true
                    } else {
                        haveData = false
                        hideLoading()
                    }


                    println("面试列表请求大小" + data.length())
                    println(data.length())



                    for (i in 0..data.length() - 1) {

                        //公司请求完成
                        var requestCompanyComplete = false
                        //地址请求完成
                        var requestPositionComplete = false


                        var item = data.getJSONObject(i)


                        //面试类型
                        var type = ""
                        type = item.getString("type")
                        if (type != null && type.equals("ONLINE")) {
                            type = "线上"
                        } else   {
                            type = "面接"
                        }

                        //记录Id
                        var id = ""
                        id = item.getString("id")
                        //公司ID
                        var recruitOrganizationId = ""
                        recruitOrganizationId = item.getString("recruitOrganizationId")


                        //职位Id
                        var recruitPositionId = ""
                        recruitPositionId = item.getString("recruitPositionId")


                        //接收面试截止时间
                        var acceptDeadlineStr = ""
                        var acceptDeadline = 0L
                        acceptDeadline = item.getLong("acceptDeadline")


                        //预约开始时间
                        var appointedStartTimeStr = ""
                        var appointedStartTime = 0L
                        appointedStartTime = item.getLong("appointedStartTime")


                        //面试开始时间
                        var startTimeStr = ""
                        var startDateStr = ""
                        var startflag = ""

                        var startTime = 0L
                        if (item.has("appointedStartTime")) {
                            startTime = item.getLong("appointedStartTime")

                            var startDate = Date(startTime)
                            var startTime = startDate.time

                            var sdf1: SimpleDateFormat = SimpleDateFormat("MM月dd日")
                            startDateStr = sdf1.format(startDate)
                            var sdf2: SimpleDateFormat = SimpleDateFormat("HH:mm")
                            startTimeStr = sdf2.format(startDate)

                            var distanceADay =
                                startDate.hours * 1000 * 60 * 60 + startDate.minutes * 1000 * 60 + startDate.seconds * 1000

                            var nowTime = Date().time

                            if (startTime - nowTime > 0) {
                                var dateDistance =
                                    ((startTime - nowTime - distanceADay) / 1000 * 60 * 60 * 24).toInt() + 1
                                if (startTime - nowTime - distanceADay < 0) {
                                    startflag = "今日"
                                } else if (dateDistance == 1) {
                                    startflag = "明日"
                                } else if (dateDistance == 2) {
                                    startflag = "后日"
                                }
                            }

                        }

                        //公司名字
                        var companyName = ""
                        //公司logo
                        var companyLogo = ""
                        //职位名称
                        var positionName = ""
                        //薪水
                        var salaryMin=0
                        var salaryMax=0
                        //展示的在页面上的薪水拼接字符串
                        var showSalaryMinToMax=""


                        //请求公司信息
                        var requestCompany = RetrofitUtils(mContext!!, "https://org.sk.cgland.top/")
                        requestCompany.create(RecruitInfoApi::class.java)
                            .getCompanyInfo(
                                recruitOrganizationId
                                //"f71cc420-27b4-4575-8ad1-9485a4de305f"
                            )
                            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                            .subscribe({
                                println("获取单个公司信息请求成功")
                                println(it)
                                requestCompanyComplete = true
                                var json = org.json.JSONObject(it.toString())
                                companyName = json.getString("name")
                                companyLogo = json.getString("logo")

                                if (requestCompanyComplete && requestPositionComplete) {
                                    appendDateToList(id,companyName, companyLogo, type, positionName, showSalaryMinToMax,startTimeStr,startDateStr,startflag)
                                }

                            }, {
                                //失败
                                println("获取单个公司信息请求失败")
                                println(it)
                                requestCompanyComplete = true
                                if (requestCompanyComplete && requestPositionComplete) {
                                    appendDateToList(id,companyName, companyLogo, type, positionName, showSalaryMinToMax,startTimeStr,startDateStr,startflag)
                                }

                            })

                        //获取单个职位信息
                        val request = RetrofitUtils(mContext!!, "https://organization-position.sk.cgland.top/")
                        request.create(RecruitInfoApi::class.java)
                            .getRecruitInfoById(
                                recruitPositionId
                            )
                            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                            .subscribe({
                                println("获取单个职位信息请求成功")
                                println(it)
                                requestPositionComplete = true
                                val jsonOut = JSONObject(it.toString())

                                val json = jsonOut.getJSONObject("organization")


                                if (json.has("name")) {
                                    positionName = json.getString("name")
                                }
                                if (json.has("salaryMin")) {
                                    salaryMin = json.getInt("salaryMin")
                                }
                                if (json.has("salaryMax")) {
                                    salaryMax = json.getInt("salaryMax")
                                }


                                showSalaryMinToMax=getSalaryMinToMaxString(salaryMin,salaryMax,"","")

                                if (requestCompanyComplete && requestPositionComplete) {
                                    appendDateToList(id,companyName, companyLogo, type, positionName, showSalaryMinToMax,startTimeStr,startDateStr,startflag)
                                }

                            }, {
                                //失败
                                println("获取单个职位信息请求失败")
                                println(it)
                                requestPositionComplete = true
                                if (requestCompanyComplete && requestPositionComplete) {
                                    appendDateToList(id,companyName, companyLogo, type, positionName, showSalaryMinToMax,startTimeStr,startDateStr,startflag)
                                }

                            })


                    }
                    hideLoading()
                    requestDataFinish = true
                }, {
                    //失败
                    println("请求面试列表请求失败")
                    println(it)
                })
        }


    }




    fun appendDateToList(
        id:String,
        companyName:String,
        companyLogo:String,
        InterviewType:String,
        positionName:String,
        showSalaryMinToMax:String,
        startTimeStr: String,
        startDateStr: String,
        startflag: String
    ){






        var list: MutableList<InterviewInfo> = mutableListOf()

        var interviewInfo = InterviewInfo(
            id,
            companyName,
            companyLogo,
            InterviewType,
            positionName,
            showSalaryMinToMax,
            dataTypeInt,
            "",
            startTimeStr,
            startDateStr,
            startflag
        )




        list.add(interviewInfo)

        if (adapter == null) {
            //适配器
            adapter =  InterviewListAdapter(recycler, list,"取り消し") { item ->
                //跳转
                var intent = Intent(mContext, FaceCloseActivity::class.java)
                intent.putExtra("id", item.id)

                startActivity(intent)
                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

            }

            //设置适配器
            recycler.setAdapter(adapter)
        } else {
            adapter!!.addRecruitInfoList(list)

        }

















    }








    //得到薪资范围
    fun getSalaryMinToMaxString(
        salaryMin: Int?,
        salaryMax: Int?,
        currencyTypeUnitHead: String,
        currencyTypeUnitTail: String
    ): String {

        var min = salaryMin.toString();
        var max = salaryMax.toString();

        var thousand = ""
        var tenthousand = ""
        var million = ""


        thousand = "千"
        tenthousand = "万"
        million = "台"



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

    //弹出等待转圈窗口
    private fun showLoading() {
        val mTransaction = childFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        dialogLoading = DialogLoading.newInstance()
        mTransaction.add(main, dialogLoading!!)
        mTransaction.commitAllowingStateLoss()
    }

    //关闭等待转圈窗口
    private fun hideLoading() {
        val mTransaction = childFragmentManager.beginTransaction()
        if (dialogLoading != null) {
            mTransaction.remove(dialogLoading!!)
            dialogLoading = null
        }

        mTransaction.commitAllowingStateLoss()
    }




}

