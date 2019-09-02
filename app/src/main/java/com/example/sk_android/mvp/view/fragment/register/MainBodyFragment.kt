package com.example.sk_android.mvp.view.fragment.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.R
import com.example.sk_android.mvp.model.collection.CollectionModel
import com.example.sk_android.mvp.model.company.CompanyInfo
import com.example.sk_android.mvp.model.onlineresume.basicinformation.UserBasicInformation
import com.google.gson.JsonObject
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import com.example.sk_android.utils.RetrofitUtils
import com.example.sk_android.mvp.api.person.User
import com.example.sk_android.mvp.view.activity.jobselect.RecruitInfoShowActivity
import org.jetbrains.anko.support.v4.toast
import com.example.sk_android.mvp.api.onlineresume.OnlineResumeApi
import com.example.sk_android.mvp.api.company.CompanyInfoApi
import com.example.sk_android.mvp.api.collection.CollectionApi
import com.google.gson.Gson
import com.example.sk_android.mvp.view.activity.jobselect.JobInfoDetailActivity
import com.example.sk_android.mvp.model.jobselect.SalaryType
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class MainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var stateSharedPreferences: SharedPreferences
    var myCurrencyType = ""
    var mySalaryMin = 0
    var mySalaryMax = 0
    var mySalaryType = ""
    var myShowSalaryMinToMax = ""
    lateinit var com: CompanyInfo
    var collec: CollectionModel? = null
    lateinit var basic: UserBasicInformation
    var addr = ""
    var index = 0

    companion object {
        fun newInstance(): MainBodyFragment {
            val fragment = MainBodyFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = createView()
        return fragmentView
    }

    fun createView(): View {
        return UI{
            linearLayout {
               backgroundColorResource = R.color.trans
            }
        }.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PushAgent.getInstance(activity).onAppStart()
        stateSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        
        init()
    }

    @SuppressLint("CheckResult")
    private fun init() {
        val sp = PreferenceManager.getDefaultSharedPreferences(activity)
        val token = sp.getString("token", "")
        val mEditor: SharedPreferences.Editor = stateSharedPreferences.edit()
        mEditor.putInt("condition", 0)
        mEditor.putBoolean("isUpdate",true)
        mEditor.commit()
        if (token == "") {
            val i = Intent(activity, LoginActivity::class.java)
            i.putExtra("condition", 0)
            startActivity(i)
            activity!!.finish()
            activity!!.overridePendingTransition(R.anim.left_in, R.anim.right_out)
        } else {


            val requestUserInfo = RetrofitUtils(mContext!!, this.getString(R.string.userUrl))

            requestUserInfo.create(User::class.java)
                .selfInfo
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({

                    val cintent = activity!!.intent
                    if (Intent.ACTION_VIEW == cintent.action) {
                        val uri = cintent.data
                        val resume = uri.getQueryParameter("resume_id") ?: ""
                        val position = uri.getQueryParameter("position_id") ?: ""
                        if (resume.isNotEmpty()) {
                            println("简历啊啊啊-----$resume")
                            //分享简历点击不用做什么操作
                        }
                        //测试ID:8f293702-84ec-4aaf-bb8e-a73c7263588b
                        if (position.isNotEmpty() && index==0) {
                            println("职位啊啊啊-----$position")
                            index++
                            //跳转到职位详情界面
                            getPosition(position)

                            return@subscribe
                        }
                    }
//
                    val intent = Intent(activity, RecruitInfoShowActivity::class.java)
                    startActivity(intent)
                    activity!!.finish()
                    activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                }, {
                    if (it is HttpException) {
                        if (it.code() == 404) {
                            val i = Intent(activity, LoginActivity::class.java)
                            startActivity(i)
                            activity!!.finish()
                            activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                        } else {
                            toast("ネットワーク異常")
                        }
                    }
                })


        }
    }

    private fun getPosition(id: String) {
        val requestUserInfo =
            RetrofitUtils(mContext!!, this.getString(R.string.organizationUrl))

        requestUserInfo.create(OnlineResumeApi::class.java)
            .getRecruitInfoById(id)
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({

                val newCondition: MutableList<Boolean> = mutableListOf()

                for (i in 0 until 4) {
                    newCondition.add(false)
                }

                val position = it.body()!!.asJsonObject.get("organization").asJsonObject
                //拼接薪资范围
                println("-----正在查找数据")
                //根据获得的公司ID查询公司
                var retrofitUils = RetrofitUtils(mContext!!, this.getString(R.string.orgUrl))
                retrofitUils.create(CompanyInfoApi::class.java)
                    .getCompanyById(position.get("organizationId").asString.replace("\"", ""))
                    .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        val model = it.body()!!.asJsonObject
                        val address = mutableListOf<ArrayList<String>>()
                        val coordinate = mutableListOf<ArrayList<String>>()
                        val companyIntroduce =
                            if (model.get("attributes").asJsonObject.get("companyIntroduce") != null) model.get("attributes").asJsonObject.get(
                                "companyIntroduce"
                            ).asString else ""
                        val startTime =
                            if (model.get("attributes").asJsonObject.get("startTime") != null) model.get("attributes").asJsonObject.get(
                                "startTime"
                            ).asString else ""
                        val endTime =
                            if (model.get("attributes").asJsonObject.get("endTime") != null) model.get("attributes").asJsonObject.get(
                                "endTime"
                            ).asString else ""
                        com = CompanyInfo(
                            model.get("id").asString,
                            model.get("videoUrl").asString,
                            model.get("logo").asString,
                            model.get("name").asString,
                            model.get("size").asString,
                            model.get("financingStage").asString,
                            model.get("type").asString,
                            model.get("website").asString,
                            model.get("benifits").asJsonArray.map { it.asString } as MutableList<String>,
                            companyIntroduce,
                            address,
                            coordinate,
                            model.get("imageUrls").asJsonArray.map {
                                if (it.asString.indexOf(";") != -1) it.asString.split(
                                    ";"
                                )[0] else it.asString
                            } as MutableList<String>,
                            startTime,
                            endTime
                        )
                        newCondition.set(0, true)
                        for (i in 0 until 4) {
                            if (!newCondition[i]) {
                                break
                            }
                            if (i == 3) {
                                getModel(position)
                            }
                        }
                    }, {})
                //根据职位ID查询是否被收藏
                retrofitUils = RetrofitUtils(mContext!!, this.getString(R.string.jobUrl))
                retrofitUils.create(CollectionApi::class.java)
                    .getFavoritesCompany(position.get("id").asString.replace("\"", ""), "ORGANIZATION_POSITION")
                    .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        val model = it.body()!!.asJsonObject.get("data").asJsonArray
                        if (model.size() > 0)
                            collec = Gson().fromJson<CollectionModel>(model, CollectionModel::class.java)
                        else
                            collec = null
                        newCondition.set(1, true)
                        for (i in 0 until 4) {
                            if (!newCondition[i]) {
                                break
                            }
                            if (i == 3) {
                                getModel(position)
                            }
                        }
                    }, {})
                //查询用户信息
                retrofitUils =
                    RetrofitUtils(mContext!!, this.getString(R.string.userUrl))
                retrofitUils.create(OnlineResumeApi::class.java)
                    .getUserSelf()
                    .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        val model = it.body()!!.asJsonObject
                        basic = Gson().fromJson<UserBasicInformation>(model, UserBasicInformation::class.java)
                        newCondition.set(2, true)
                        for (i in 0 until 4) {
                            if (!newCondition[i]) {
                                break
                            }
                            if (i == 3) {
                                getModel(position)
                            }
                        }
                    }, {})
                //查询职位地址
                retrofitUils =
                    RetrofitUtils(mContext!!, this.getString(R.string.baseUrl))
                retrofitUils.create(CompanyInfoApi::class.java)
                    .getAreaInfo(position.get("areaId").asString.replace("\"", ""))
                    .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        addr = it.body()!!.get("name").toString().replace("\"", "")
                        newCondition.set(3, true)
                        for (i in 0 until 4) {
                            if (!newCondition[i]) {
                                break
                            }
                            if (i == 3) {
                                getModel(position)
                            }
                        }
                    }, {})


            }, {})
    }

    private fun getModel(model: JsonObject) {
        println("-----数据或得")
        val pt = model
        val positionName = pt.get("name").toString().replace("\"", "")
        mySalaryType = pt.get("salaryType").toString().replace("\"", "")
        myCurrencyType =
            pt.get("currencyType").toString().replace("\"", "")
        mySalaryMin =
            pt.get("salaryMin").toString().replace("\"", "").toInt()
        mySalaryMax =
            pt.get("salaryMax").toString().replace("\"", "").toInt()
        val myWorkingExperience =
            pt.get("workingExperience").toString().replace("\"", "") + "年間"
        val myEducationalBackground =
            pt.get("educationalBackground").toString().replace("\"", "")
        val mySkill = pt.get("skill").toString().replace("\"", "")
        val myContent = pt.get("content").toString().replace("\"", "")
        val myPlus = pt.get("plus").toString().replace("\"", "")
        val myUserId = pt.get("userId").toString().replace("\"", "")
        val myPositionId = pt.get("id").toString().replace("\"", "")
        val collId: String = if (collec != null) collec?.id.toString() else ""

        getMinAndMax()
        val intent = Intent(activity, JobInfoDetailActivity::class.java)
        intent.putExtra("positionName", positionName)
        intent.putExtra("salaryType", mySalaryType)
        intent.putExtra("showSalaryMinToMax", myShowSalaryMinToMax)
        intent.putExtra("address", addr)
        intent.putExtra("workingExperience", myWorkingExperience)
        intent.putExtra("educationalBackground", myEducationalBackground)
        intent.putExtra("skill", mySkill)
        intent.putExtra("content", myContent)
        intent.putExtra("organizationId", com.id)
        intent.putExtra("companyName", com.name)
        intent.putExtra("userName", basic.displayName)
        intent.putExtra("userPositionName", com.name)
        intent.putExtra("avatarURL", basic.avatarURL)
        intent.putExtra("userId", myUserId)
        intent.putExtra("isCollection", if (collec != null) 1 else 0)
        intent.putExtra("recruitMessageId", myPositionId)
        intent.putExtra("collectionId", collId)
        intent.putExtra("position", 0)
        intent.putExtra("fromType", "recruitList")
        intent.putExtra("plus", myPlus)
        intent.putExtra("positionId", myPositionId)
        intent.putExtra("main", true)

        startActivityForResult(intent, 1)
        activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    private fun getMinAndMax() {
        var currencyTypeUnitHead: String = ""
        var currencyTypeUnitTail: String = ""
        var unitType: Int = 0
        if (myCurrencyType != "" && myCurrencyType.equals("CNY")) {
            // currencyTypeUnitTail="元"
            unitType = 1
        } else if (myCurrencyType != "" && myCurrencyType.equals("JPY")) {
            //  currencyTypeUnitTail="円"
            unitType = 1
        } else if (myCurrencyType != "" && myCurrencyType.equals("USD")) {
            //  currencyTypeUnitHead="$"
            unitType = 2
        }

        getSalaryMinToMaxString(mySalaryMin, mySalaryMax, currencyTypeUnitHead, currencyTypeUnitTail, unitType)
        if (mySalaryType != "") {
            when (mySalaryType) {
                "HOURLY" -> mySalaryType = SalaryType.Value.时.toString()
                "DAILY" -> mySalaryType = SalaryType.Value.日.toString()
                "MONTHLY" -> mySalaryType = SalaryType.Value.月.toString()
                "YEARLY" -> mySalaryType = SalaryType.Value.年.toString()
            }
        }

    }

    //得到薪资范围
    private fun getSalaryMinToMaxString(
        salaryMin: Int?,
        salaryMax: Int?,
        currencyTypeUnitHead: String,
        currencyTypeUnitTail: String,
        unitType: Int
    ) {

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

        myShowSalaryMinToMax =
            currencyTypeUnitHead + min + currencyTypeUnitTail + "~" + currencyTypeUnitHead + max + currencyTypeUnitTail
    }

}