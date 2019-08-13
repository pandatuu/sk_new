package com.example.sk_android.mvp.view.fragment.person

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import click
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.jobselect.CityInfoApi
import com.example.sk_android.mvp.api.jobselect.JobApi
import com.example.sk_android.mvp.api.jobselect.UserApi
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.model.jobselect.EducationalBackground
import com.example.sk_android.mvp.model.jobselect.FavoriteType
import com.example.sk_android.mvp.model.jobselect.SalaryType
import com.example.sk_android.mvp.view.activity.jobselect.JobInfoDetailActivity
import com.example.sk_android.mvp.view.activity.message.MessageChatWithoutLoginActivity
import com.example.sk_android.mvp.view.activity.person.InterviewListActivity
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import imui.jiguang.cn.imuisample.messages.MessageListActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import withTrigger
import java.text.SimpleDateFormat
import java.util.*

class FaMainBodyFragment : Fragment() {
    private lateinit var myDialog: MyDialog
    private var mContext: Context? = null
    lateinit var tool: BaseTool
    lateinit var companyText: TextView
    lateinit var timeText: TextView
    lateinit var positionText: TextView
    lateinit var addressText: TextView
    lateinit var doText:TextView
    lateinit var remarksText:TextView
    lateinit var logoImage:ImageView
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    var addressLat = ""
    var addressLng = ""
    var addressName = ""

    var myPositionName = ""
    var mySalaryType = ""
    var myCurrencyType = ""
    var mySalaryMin = 0
    var mySalaryMax = 0
    var myShowSalaryMinToMax = ""
    var myWorkingExperience = ""
    var myEducationalBackground = ""
    var mySkill = ""
    var myContent = ""
    var myPlus = ""
    var myOrganizationId = ""
    var myCompanyName = ""
    var myCompanyLogo = ""
    var myAvatarURL = ""
    var myUserName = ""
    var myUserId = ""
    var myUserPositionName = ""
    var myPositionId = ""
    var myIsCollection = false
    var myCollectionId = ""
    var myAddressId = ""
    var myCompanyAddressName = ""

    var id = ""
    var type = "APPOINTING"
    var resumeType = "OFFLINE"
    var myRemarks = ""

    lateinit var interviewListActivity: InterviewListActivity

    companion object {
        fun newInstance(myId:String,myType:String): FaMainBodyFragment {
            val fragment = FaMainBodyFragment()
            fragment.id = myId
            fragment.type = myType
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val builder = MyDialog.Builder(activity!!)
            .setMessage(this.getString(R.string.loadingHint))
            .setCancelable(false)
            .setCancelOutside(false)
        myDialog = builder.create()

        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        return fragmentView
    }

    fun createView(): View {
        tool = BaseTool()
        return UI {
            verticalLayout {
                leftPadding = dip(15)
                rightPadding = dip(15)
                linearLayout {
                    linearLayout {
                        gravity = Gravity.CENTER
                        logoImage = imageView {

                        }.lparams(width = dip(60), height = dip(60))
                    }.lparams(width = dip(60), height = matchParent)

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL
                        orientation = LinearLayout.VERTICAL
                        companyText = textView {
//                            textResource = R.string.faceCompany
                            textSize = 17f
                            textColorResource = R.color.black20
                        }.lparams(height = wrapContent)

                        textView {
                            textResource = R.string.reserved
                            textSize = 13f
                            textColorResource = R.color.gray99
                        }.lparams(height = wrapContent) {

                        }
                    }.lparams(height = matchParent) {
                        weight = 1f
                        leftMargin = dip(10)
                    }
                }.lparams(width = matchParent, height = dip(90))

                linearLayout {
                    linearLayout {
                        this.withTrigger().click {
                            callPosition()
                        }
                        gravity = Gravity.CENTER
                        orientation = LinearLayout.VERTICAL
                        imageView {
                            imageResource = R.mipmap.face_work
                        }.lparams(width = dip(26), height = dip(26))
                        textView {
                            textResource = R.string.facePosition
                            textColorResource = R.color.gray5c
                            textSize = 13f
                        }.lparams(width = wrapContent, height = wrapContent) {
                            topMargin = dip(11)
                        }
                    }.lparams(width = wrapContent, height = matchParent) {
                        weight = 1f
                    }

                    linearLayout {
                        this.withTrigger().click {
                            callMap()
                        }
                        gravity = Gravity.CENTER
                        orientation = LinearLayout.VERTICAL
                        imageView {
                            imageResource = R.mipmap.face_navigation
                        }.lparams(width = dip(26), height = dip(26))
                        textView {
                            textResource = R.string.faceNavi
                            textColorResource = R.color.gray5c
                            textSize = 13f
                        }.lparams(width = wrapContent, height = wrapContent) {
                            topMargin = dip(11)
                        }
                    }.lparams(width = wrapContent, height = matchParent) {
                        weight = 1f
                    }

                    linearLayout {
                        gravity = Gravity.CENTER
                        orientation = LinearLayout.VERTICAL
                        this.withTrigger().click {
                            callChat()
                        }
                        imageView {
                            imageResource = R.mipmap.face_chat
                        }.lparams(width = dip(26), height = dip(26))
                        textView {
                            textResource = R.string.faceChat
                            textColorResource = R.color.gray5c
                            textSize = 13f
                        }.lparams(width = wrapContent, height = wrapContent) {
                            topMargin = dip(11)
                        }
                    }.lparams(width = wrapContent, height = matchParent) {
                        weight = 1f
                    }
                }.lparams(width = matchParent, height = dip(105))

                view {
                    backgroundColorResource = R.color.grayEBEAEB
                }.lparams(width = matchParent, height = dip(1)) {}

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                    imageView {
                        imageResource = R.mipmap.face_time
                    }.lparams(width = dip(16), height = dip(16))
                    timeText = textView {
//                        textResource = R.string.faceTime
                        textColorResource = R.color.black20
                        textSize = 14f
                    }.lparams(width = wrapContent, height = wrapContent) {
                        weight = 1f
                        leftMargin = dip(11)
                    }
                }.lparams(width = matchParent, height = dip(20)) {
                    topMargin = dip(23)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                    imageView {
                        imageResource = R.mipmap.face_job
                    }.lparams(width = dip(16), height = dip(16))
                    positionText = textView {
//                        textResource = R.string.faceTask
                        textColorResource = R.color.black20
                        textSize = 14f
                    }.lparams(width = wrapContent, height = wrapContent) {
                        weight = 1f
                        leftMargin = dip(11)
                    }
                }.lparams(width = matchParent, height = dip(20)) {
                    topMargin = dip(15)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                    imageView {
                        imageResource = R.mipmap.face_address
                    }.lparams(width = dip(16), height = dip(16))
                    addressText = textView {
//                        textResource = R.string.faceAddress
                        textColorResource = R.color.black20
                        textSize = 14f
                    }.lparams(width = wrapContent, height = wrapContent) {
                        weight = 1f
                        leftMargin = dip(11)
                    }
                }.lparams(width = matchParent, height = dip(20)) {
                    topMargin = dip(15)
                }

                textView {
                    textResource = R.string.faceRemarks
                    textSize = 14f
                }.lparams(width = matchParent,height = wrapContent){
                    topMargin = dip(15)
                }

                remarksText = textView {
                    textSize = 14f
                    isVerticalScrollBarEnabled = true
                    isHorizontalScrollBarEnabled = false
                    textColorResource = R.color.black20
                }.lparams(width = matchParent,height = wrapContent){
                    topMargin = dip(10)
                }

                linearLayout {
                }.lparams(width = matchParent, height = wrapContent) {
                    weight = 1f
                }

                doText = textView {
                    gravity = Gravity.CENTER_HORIZONTAL
                    textResource = R.string.cancelInterView
                    textSize = 12f
                    textColorResource = R.color.gray89
                    visibility = View.GONE
                    setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            afterShowLoading()
                        }

                    })

                }.lparams(width = matchParent, height = wrapContent) {
                    bottomMargin = dip(52)
                }
            }
        }.view
    }

    //弹出更新窗口
    fun afterShowLoading() {
        val faceDialog = FaceDialog(activity!!)
        faceDialog.show()
        var confirmButton = faceDialog.getButton()
        var reasonEdit = faceDialog.getEdit()
        var reason = reasonEdit.text.trim()
        confirmButton.setOnClickListener {
            val resonParams = mapOf(
                "state" to "REJECTED",
                "cancelReason" to reason
            )
            val reasonJson = JSON.toJSONString(resonParams)
            val reasonBody = RequestBody.create(json, reasonJson)

            var retrofitUils = RetrofitUtils(activity!!, this.getString(R.string.interUrl))


            retrofitUils.create(PersonApi::class.java)
                .changeInterViewSchedule(id,reasonBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.code() in 200..299){
                        toast(this.getString(R.string.faStateChangeSuccess))
                        startActivity<InterviewListActivity>()
                        activity!!.finish()
                        activity!!.overridePendingTransition(R.anim.left_in,R.anim.right_out)

                    }else{
                        toast(this.getString(R.string.faStateChangeFail))
                    }
                }, {

                })
        }
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    @SuppressLint("CheckResult")
    private fun init() {
        myDialog.show()
        var retrofitUils = RetrofitUtils(activity!!, this.getString(R.string.interUrl))
        // 获取面试信息
        retrofitUils.create(PersonApi::class.java)
            .getInterViewById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println(it)
                println("查找面试信息")
                var gson = Gson()
                var res = it
                var recruitOrganizationName = ""
                var recruitPositionName = ""
                var recruitOrganizationId = it.get("recruitOrganizationId").toString().replace("\"", "")
                var recruitPositionId = it.get("recruitPositionId").toString().replace("\"", "")
                if(res.get("attributes") != null && res.get("attributes").asJsonObject.get("remarks")!= null){
                    myRemarks = res.get("attributes").asJsonObject.get("remarks").toString().replace("\"","")
                    remarksText.text = myRemarks
                }
                resumeType = it.get("type").toString().replace("\"", "")
                if(resumeType == "OFFLINE"){
                    addressLat = res.get("attributes").asJsonObject.get("addressLat").toString().replace("\"","")
                    addressLng = res.get("attributes").asJsonObject.get("addressLng").toString().replace("\"","")
                }


                var newCondition: MutableList<Boolean> = mutableListOf()

                for (i in 0 until 3) {
                    newCondition.add(false)
                }

                var companyRetrofitUils = RetrofitUtils(activity!!, this.getString(R.string.orgUrl))
                // 获取公司name
                companyRetrofitUils.create(PersonApi::class.java)
                    .getCompanyName(recruitOrganizationId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        var com = it
                        println(com)
                        var companyName = it.get("name").toString().replace("\"", "")
                        var companyLogo = it.get("logo").toString().replace("\"","")
                        myOrganizationId = it.get("id").toString().replace("\"", "")
                        myCompanyName = companyName
                        myCompanyLogo = companyLogo
                        newCondition.set(0, true)
                        recruitOrganizationName = companyName
                        for (i in 0 until 3) {
                            if (!newCondition[i]) {
                                break
                            }
                            if (i == 2) {
                                myDialog.dismiss()
                                initPage(recruitOrganizationName,myCompanyLogo, recruitPositionName, addressName, res)
                                two()
                                getMinAndMax()
                            }
                        }
                    }, {
                        myDialog.dismiss()
                        println("查询公司出错")
                        println(it)
                        toast(this.getString(R.string.faFindCompanyFail))
                    })

                var positionRetrofitUils = RetrofitUtils(activity!!, this.getString(R.string.organizationUrl))
                // 获取职业name
                positionRetrofitUils.create(PersonApi::class.java)
                    .getPositionName(recruitPositionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        var pt = it
                        println(it)
                        var positionName = it.get("organization").asJsonObject.get("name").toString().replace("\"", "")
                        myPositionName = positionName
                        mySalaryType = it.get("organization").asJsonObject.get("salaryType").toString().replace("\"","")
                        myCurrencyType = it.get("organization").asJsonObject.get("currencyType").toString().replace("\"","")
                        mySalaryMin = it.get("organization").asJsonObject.get("salaryMin").toString().replace("\"","").toInt()
                        mySalaryMax = it.get("organization").asJsonObject.get("salaryMax").toString().replace("\"","").toInt()
                        myWorkingExperience = it.get("organization").asJsonObject.get("workingExperience").toString().replace("\"","") + "年間"
                        myEducationalBackground = it.get("organization").asJsonObject.get("educationalBackground").toString().replace("\"","")
                        mySkill = it.get("organization").asJsonObject.get("skill").toString().replace("\"","")
                        myContent = it.get("organization").asJsonObject.get("content").toString().replace("\"","")
                        myPlus = it.get("organization").asJsonObject.get("plus").toString().replace("\"","")
                        var uid = it.get("organization").asJsonObject.get("userId").toString().replace("\"","")
                        myUserId = uid
                        myPositionId = it.get("organization").asJsonObject.get("id").toString().replace("\"","")
                        myAddressId = it.get("organization").asJsonObject.get("areaId").toString().replace("\"","")
                        newCondition.set(1, true)
                        recruitPositionName = positionName
                        for (i in 0 until 3) {
                            if (!newCondition[i]) {
                                break
                            }
                            if (i == 2) {
                                myDialog.dismiss()
                                initPage(recruitOrganizationName,myCompanyLogo, recruitPositionName, addressName, res)
                                two()
                                getMinAndMax()
                            }
                        }

                    }, {
                        myDialog.dismiss()
                        println("下旬职业出粗")
                        println(it)
                        toast(this.getString(R.string.faFindPositionFail))
                    })


                if (resumeType.equals("OFFLINE")) {
                    var firstAddressName = res.get("attributes").asJsonObject.get("addressName").toString().replace("\"","")
                    addressName = firstAddressName
                    newCondition.set(2, true)
                    for (i in 0 until 3) {
                        if (!newCondition[i]) {
                            break
                        }
                        if (i == 2) {
                            myDialog.dismiss()
                            initPage(recruitOrganizationName, myCompanyLogo, recruitPositionName, addressName, res)
                            two()
                            getMinAndMax()
                        }
                    }
                } else {
                    newCondition.set(2, true)
                    for (i in 0 until 3) {
                        if (!newCondition[i]) {
                            break
                        }
                        if (i == 2) {
                            myDialog.dismiss()
                            initPage(recruitOrganizationName, myCompanyLogo, recruitPositionName, addressName, res)
                            two()
                            getMinAndMax()
                        }
                    }
                }


            }, {
                myDialog.dismiss()
                toast(this.getString(R.string.faFindIntenFail))
            })
    }

    private fun initPage(companyName: String, companyLogo: String, positionName: String, addressName: String, result: JsonObject) {
        println("*-*-*-*-*-*-*-*-")
        companyText.text = companyName
        positionText.text = positionName
        addressText.text = addressName
        val start = result.get("appointedStartTime").toString().replace("\"", "").toLong()
        timeText.text = longToString(start)

        if(companyLogo.isNotBlank()){
            Glide.with(this)
                .asBitmap()
                .load(companyLogo)
                .placeholder(R.mipmap.no_pic_show)
                .into(logoImage)
        }

        if(type == "APPOINTING" || type == "APPOINTED"){
            doText.visibility = View.VISIBLE
        }
        println(companyName)
        println(positionName)
        println(addressName)
        println(result)
        println("++++++++++++++++++++++++")
    }


    // 类型转换
    private fun longToString(long: Long): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(long))
    }

    @SuppressLint("CheckResult")
    fun two(){
        // 用户信息
        var requestUser = RetrofitUtils(mContext!!, this.getString(R.string.userUrl))
        requestUser.create(UserApi::class.java)
            .getUserInfo(
                myUserId
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                myAvatarURL = JSONObject(it.toString()).getString("avatarURL")
                val arra=myAvatarURL.split(",")
                if(arra.size>0){
                    myAvatarURL=arra[0]
                }

                myUserName = JSONObject(it.toString()).getString("displayName")
            },{})

        //用户角色信息
        var requestUserPosition = RetrofitUtils(mContext!!, this.getString(R.string.orgUrl))
        requestUserPosition.create(UserApi::class.java)
            .getUserPosition(
                myOrganizationId, myUserId
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                var itemJson = JSONObject(it.toString())
                myUserPositionName = itemJson.getString("name")
            },{})

        // 公司相关收藏信息
        var requestCompany = RetrofitUtils(mContext!!, this.getString(R.string.jobUrl))
        requestCompany.create(JobApi::class.java)
            .getMyFavorites(
                1, 1, myPositionId
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                if(it.get("total").toString().replace("\"","").toInt() > 0){
                    myIsCollection = true
                    myCollectionId = it.get("data").asJsonObject.get("id").toString().replace("\"","")
                }
            },{})

        // 公司地址信息
        var requestAddress = RetrofitUtils(mContext!!, this.getString(R.string.baseUrl))
        requestAddress.create(CityInfoApi::class.java)
            .getAreaInfo(
                myAddressId
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("地址信息请求成功")
                println(it)
                var newAddress = it.get("name").toString().replace("\"","")
                println(newAddress)
                myCompanyAddressName = it.get("name").toString().replace("\"","")
            },{})
    }

    // 跳转到地图app,导航页面
    fun callMap(){
        if(resumeType == "OFFLINE"){
            var intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse("geo:$addressLat,$addressLng?q=$addressName")
            startActivity(intent)
        }
    }

    // 跳转到职位页面
    fun callPosition(){

            getEducationalBackground(myEducationalBackground)

            //跳转到职位详情界面
            var intent = Intent(mContext, JobInfoDetailActivity::class.java)
            intent.putExtra("positionName", myPositionName)
            intent.putExtra("salaryType", mySalaryType)
            intent.putExtra("showSalaryMinToMax", myShowSalaryMinToMax)
            intent.putExtra("address", myCompanyAddressName)
            intent.putExtra("workingExperience", myWorkingExperience)
            intent.putExtra("educationalBackground", myEducationalBackground)
            intent.putExtra("skill", mySkill)
            intent.putExtra("content", myContent)
            intent.putExtra("organizationId", myOrganizationId)
            intent.putExtra("companyName", myCompanyName)
            intent.putExtra("userName", myUserName)
            intent.putExtra("userPositionName", myUserPositionName)
            intent.putExtra("avatarURL", myAvatarURL)
            intent.putExtra("userId", myUserId)
            intent.putExtra("isCollection", myIsCollection)
            intent.putExtra("recruitMessageId", myPositionId)
            intent.putExtra("collectionId", myCollectionId)
            intent.putExtra("position", 0)
            intent.putExtra("fromType", "recruitList")
            intent.putExtra("plus", myPlus)

            startActivityForResult(intent, 1)
            activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

    }

    // 跳转到聊天界面,未登录不可聊天
    fun callChat(){

            lateinit var intent: Intent
            if (App.getInstance()!!.getMessageLoginState()) {
                //跳转到聊天界面
                intent = Intent(mContext, MessageListActivity::class.java)
                intent.putExtra("hisId", myUserId)
                intent.putExtra("companyName", myCompanyName)
                intent.putExtra("company_id", myOrganizationId)
                intent.putExtra("hisName", myUserName)
                intent.putExtra("position_id", myPositionId)
                intent.putExtra("hislogo", myAvatarURL)


            } else {
                intent = Intent(mContext, MessageChatWithoutLoginActivity::class.java)
            }

            startActivity(intent)
            activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

    }

    fun getMinAndMax(){
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

        getSalaryMinToMaxString(mySalaryMin,mySalaryMax,currencyTypeUnitHead,currencyTypeUnitTail,unitType)
        if (mySalaryType != "") {
            when(mySalaryType){
                "HOURLY" -> mySalaryType = SalaryType.Value.时.toString()
                "DAILY" -> mySalaryType = SalaryType.Value.日.toString()
                "MONTHLY" -> mySalaryType = SalaryType.Value.月.toString()
                "YEARLY" -> mySalaryType = SalaryType.Value.年.toString()
            }
        }

    }

    //得到薪资范围
    fun getSalaryMinToMaxString(
        salaryMin: Int?,
        salaryMax: Int?,
        currencyTypeUnitHead: String,
        currencyTypeUnitTail: String,
        unitType: Int
    ){

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

    //得打教育背景
    fun getEducationalBackground(educationalBackground: String) {

        if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.MIDDLE_SCHOOL.toString())) {
            myEducationalBackground = EducationalBackground.Value.MIDDLE_SCHOOL.text
        } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.HIGH_SCHOOL.toString())) {
            myEducationalBackground = EducationalBackground.Value.HIGH_SCHOOL.text
        } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.SHORT_TERM_COLLEGE.toString())) {
            myEducationalBackground = EducationalBackground.Value.SHORT_TERM_COLLEGE.text
        } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.BACHELOR.toString())) {
            myEducationalBackground = EducationalBackground.Value.BACHELOR.text
        } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.MASTER.toString())) {
            myEducationalBackground = EducationalBackground.Value.MASTER.text
        } else if (educationalBackground != null && educationalBackground.equals(EducationalBackground.Key.DOCTOR.toString())) {
            myEducationalBackground = EducationalBackground.Value.DOCTOR.text
        }
    }
}

