package com.example.sk_android.mvp.view.fragment.person

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.model.register.Education
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.utils.RetrofitUtils
import com.example.sk_android.utils.roundImageView
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.toast
import java.text.SimpleDateFormat
import java.util.*

class FlMainBodyFragment : Fragment() {
    private lateinit var myDialog: MyDialog
    private var mContext: Context? = null
    lateinit var tool: BaseTool
    lateinit var companyText: TextView
    lateinit var workYear: TextView
    lateinit var positionText: TextView
    lateinit var educationText:TextView
    lateinit var addressText:TextView
    lateinit var headImageView:ImageView
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")

    var id = ""

    companion object {
        fun newInstance(id:String): FlMainBodyFragment {
            val fragment = FlMainBodyFragment()
            fragment.id = id
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val builder = MyDialog.Builder(activity!!)
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
                    positionText = textView {
                        text = "ベテランの視覚デザイナー"
                        textSize = 16f
                        textColorResource = R.color.black20
                    }.lparams(width = wrapContent,height = wrapContent){
                        gravity = Gravity.LEFT
                        weight = 1f
                    }

                    textView {
                        text = "停止招聘"
                        textSize = 11f
                        textColorResource = R.color.gray89
                    }.lparams(width = wrapContent,height = wrapContent){
                        gravity = Gravity.CENTER
                    }
                }.lparams(width = matchParent,height = wrapContent){
                    topMargin = dip(25)
                }

                linearLayout {
                    addressText = textView {
                        backgroundResource = R.drawable.text_blue
                        text = "東京"
                        textSize = 11f
                        textColorResource = R.color.blue0097D6
                    }.lparams(width = wrapContent,height = wrapContent){
                        rightMargin = dip(5)
                    }

                    workYear = textView {
                        backgroundResource = R.drawable.text_blue
                        text = "1～3"
                        textSize = 11f
                        textColorResource = R.color.blue0097D6
                    }.lparams(width = wrapContent,height = wrapContent){
                        rightMargin = dip(5)
                    }

                    educationText = textView {
                        backgroundResource = R.drawable.text_blue
                        text = "大卒"
                        textSize = 11f
                        textColorResource = R.color.blue0097D6
                    }.lparams(width = wrapContent,height = wrapContent){}
                }.lparams(width = matchParent,height = wrapContent){
                    topMargin = dip(8)
                }

                linearLayout {
                    gravity =Gravity.CENTER_VERTICAL
                    headImageView = roundImageView {
                        backgroundResource = R.mipmap.icon_tx_home
                    }.lparams(width = dip(28),height = matchParent){
                        rightMargin = dip(8)
                    }

                    companyText = textView {
                        text = "ジャさん·社長"
                        textSize = 11f
                        textColorResource = R.color.gray5c
                        gravity = Gravity.CENTER_VERTICAL
                    }.lparams(width = wrapContent,height = wrapContent){
                        rightMargin = dip(8)
                    }

                    imageView {
                        backgroundResource = R.mipmap.ico_grade
                    }.lparams(width = dip(13),height = dip(13))
                }.lparams(width = matchParent,height = dip(28)){
                    topMargin = dip(15)
                }

                view {
                    backgroundColorResource = R.color.graycc
                }.lparams(width = matchParent, height = dip(1)) {
                    topMargin = dip(12)
                }

                textView {
                    text = "该职位已关闭"
                    textSize = 17f
                    textColorResource = R.color.gray89
                }.lparams(width = wrapContent,height = wrapContent){
                    topMargin = dip(14)
                }


            }
        }.view
    }


    override fun onStart() {
        super.onStart()
        init()
    }

    @SuppressLint("CheckResult")
    private fun init() {
        myDialog.show()
        var retrofitUils = RetrofitUtils(activity!!, this.getString(R.string.interUrl))
        var workingExperience = ""
        var education = this.getString(R.string.educationOne)
        var areaId = ""
        // 获取用户工作状态
        retrofitUils.create(PersonApi::class.java)
            .getInterViewById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("查找面试信息")
                var gson = Gson()
                var res = it
                var recruitOrganizationName = ""
                var recruitPositionName = ""
                var recruitOrganizationId = it.get("recruitOrganizationId").toString().replace("\"", "")
                var recruitPositionId = it.get("recruitPositionId").toString().replace("\"", "")
                var recruitUserId = it.get("recruitUserId").toString().replace("\"", "")

                var newCondition: MutableList<Boolean> = mutableListOf()

                for (i in 0 until 2) {
                    newCondition.add(false)
                }

                var companyRetrofitUils = RetrofitUtils(activity!!, this.getString(R.string.orgUrl))
                // 获取公司name
                companyRetrofitUils.create(PersonApi::class.java)
                    .getCompanyName(recruitOrganizationId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        var companyName = it.get("name").toString().replace("\"", "")
                        newCondition.set(0, true)
                        recruitOrganizationName = companyName
                        for (i in 0 until 2) {
                            if (!newCondition[i]) {
                                break
                            }
                            if (i == 1) {
                                myDialog.dismiss()
                                initPage(recruitUserId,recruitOrganizationName, recruitPositionName,workingExperience,education,areaId, res)
                            }
                        }
                    }, {
                        myDialog.dismiss()
                        println("查询公司出错")
                        println(it)
                        toast("查询公司信息出错！")
                    })

                var positionRetrofitUils = RetrofitUtils(activity!!, this.getString(R.string.organizationUrl))

                // 获取招聘职位信息
                positionRetrofitUils.create(PersonApi::class.java)
                    .getPositionName(recruitPositionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        var positionName = it.get("organization").asJsonObject.get("name").toString().replace("\"", "")
                        workingExperience = it.get("organization").asJsonObject.get("workingExperience").toString().replace("\"", "")
                        var educationalBackground = it.get("organization").asJsonObject.get("educationalBackground").toString().replace("\"", "")
                        when(educationalBackground){
                            "MIDDLE_SCHOOL" -> education = this.getString(R.string.educationOne)
                            "HIGH_SCHOOL" -> education = this.getString(R.string.educationTwo)
                            "SHORT_TERM_COLLEGE" -> education = this.getString(R.string.educationThree)
                            "BACHELOR" -> education = this.getString(R.string.educationFour)
                            "MASTER" -> education = this.getString(R.string.educationFive)
                            "DOCTOR" -> education = this.getString(R.string.educationSix)
                        }
                        areaId = it.get("organization").asJsonObject.get("areaId").toString().replace("\"", "")
                        newCondition.set(1, true)
                        recruitPositionName = positionName
                        for (i in 0 until 2) {
                            if (!newCondition[i]) {
                                break
                            }
                            if (i == 1) {
                                myDialog.dismiss()
                                initPage(recruitUserId,recruitOrganizationName, recruitPositionName,workingExperience,education,areaId, res)
                            }
                        }
                    }, {
                        myDialog.dismiss()
                        println("下旬职业出粗")
                        println(it)
                        toast("查询职位信息出错！！")
                    })

            }, {
                myDialog.dismiss()
                toast("查询面试失败！")
            })
    }

    @SuppressLint("CheckResult")
    private fun initPage(recruitUserId:String, companyName: String, positionName: String, workingExperience:String, education: String, areaId:String, result: JsonObject) {
        println("*-*-*-*-*-*-*-*-")

        positionText.text = positionName
        workYear.text = "$workingExperience 年"
        educationText.text = education

        if(recruitUserId.isNullOrBlank()){
            companyText.text = companyName
        }else{
            var userRetrofitUils = RetrofitUtils(activity!!, this.getString(R.string.userUrl))

            userRetrofitUils.create(PersonApi::class.java)
                .getOtherPersonById(recruitUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    println("查询发布人信息成功！！")
                    var avatarUrl = it.get("avatarURL").toString().replace("\"","")
                    var displayName = it.get("displayName").toString().replace("\"","")

                    if(avatarUrl != ""){
                        Glide.with(this)
                            .asBitmap()
                            .load(avatarUrl)
                            .placeholder(R.mipmap.icon_tx_home)
                            .into(headImageView)
                    }

                    companyText.text = displayName
                },{
                    toast("查询发布人信息失败！！")
                })
        }



        var baseRetrofitUils = RetrofitUtils(activity!!, this.getString(R.string.baseUrl))

        baseRetrofitUils.create(RegisterApi::class.java)
            .getAreaById(areaId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                var areaName = it.get("name").toString().replace("\"", "")
                addressText.text = areaName
            },{
                toast("查询地址出错！！")
            })
    }
}

