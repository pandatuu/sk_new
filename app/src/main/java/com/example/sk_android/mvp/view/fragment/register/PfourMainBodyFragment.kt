package com.example.sk_android.mvp.view.fragment.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import com.example.sk_android.mvp.view.activity.jobselect.JobSelectActivity
import com.example.sk_android.mvp.view.activity.jobselect.RecruitInfoShowActivity
import com.example.sk_android.mvp.view.activity.register.PersonInformationTwoActivity
import com.example.sk_android.utils.RetrofitUtils
import com.wlwl.os.listbottomsheetdialog.BottomSheetDialogUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.adapter.rxjava2.HttpException
import java.util.*
import kotlin.collections.ArrayList

class PfourMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var jobText: TextView
    lateinit var jobLinearLayout: LinearLayout
    lateinit var addressText:TextView
    lateinit var addressLinearLayout: LinearLayout
    lateinit var salaryText:TextView
    lateinit var salaryLinearLayout: LinearLayout
    lateinit var startText:TextView
    lateinit var startLinearLayout: LinearLayout
    lateinit var endText:TextView
    lateinit var endLinearLayout: LinearLayout
    lateinit var typeText:TextView
    lateinit var typeLinearLayout: LinearLayout
    lateinit var applyText:TextView
    lateinit var applyLinearLayout: LinearLayout
    lateinit var evaluationEdit:EditText
    lateinit var tool: BaseTool
    lateinit var mid:Mid
    lateinit var v:View
    var salarylist: MutableList<String> = mutableListOf()
    var moneyList: MutableList<String> = mutableListOf()
    var typeList: MutableList<String> = mutableListOf()
    var applyList: ArrayList<String> = arrayListOf()
    var first:ArrayList<String> = arrayListOf()
    lateinit var education:String
    lateinit var work:String
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")


    companion object {
        fun newInstance(first:ArrayList<String>,education:String,work:String): PfourMainBodyFragment {
            val fragment = PfourMainBodyFragment()
            fragment.first = first
            fragment.education = education
            fragment.work = work

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        salarylist.add(this.getString(R.string.hourly))
        salarylist.add(this.getString(R.string.daySalary))
        salarylist.add(this.getString(R.string.monthSalary))
        salarylist.add(this.getString(R.string.yearSalary))

        moneyList.add(this.getString(R.string.startHourly))
        moneyList.add(this.getString(R.string.endHourly))
        moneyList.add(this.getString(R.string.threeThousand))
        moneyList.add(this.getString(R.string.fourThousand))
        moneyList.add(this.getString(R.string.fiveThousand))
        moneyList.add(this.getString(R.string.sixThousand))

        typeList.add(this.getString(R.string.fullTime))
        typeList.add(this.getString(R.string.partTime))

        applyList.add(this.getString(R.string.employmentFormHint))
        applyList.add(this.getString(R.string.headHunting))
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        mid = activity as Mid
        return fragmentView
    }

    fun createView():View{
        tool= BaseTool()
        v =  UI {

            scrollView {
                verticalLayout {
                    backgroundColorResource = R.color.whiteFF
                    orientation = LinearLayout.VERTICAL
                    leftPadding = dip(15)
                    rightPadding = dip(15)

                    textView {
                        textResource = R.string.PfourIntroduction
                        textSize = 18f
                        gravity = Gravity.LEFT
                        textColorResource = R.color.black33
                    }.lparams(width = matchParent, height = dip(25)) {
                        topMargin = dip(20)
                    }


                    jobLinearLayout = linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.expectedPosition
                            textColorResource = R.color.black33
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = wrapContent, height = matchParent){
                        }
                        jobText = textView {
                            backgroundColorResource = R.color.whiteFF
                            hintResource = R.string.expectedPositionHint
                            hintTextColor = Color.parseColor("#333333")
                            textSize = 15f
                            gravity = Gravity.RIGHT
                            onClick { mid.confirmJob() }
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                            rightMargin = dip(28)
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = matchParent,height = dip(11))
                        }.lparams(width = dip(6),height = matchParent)
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    textView {
                        textResource = R.string.remuneration
                        textColorResource = R.color.black33
                        textSize = 15f
                        gravity = Gravity.LEFT
                    }.lparams(width = wrapContent,height = wrapContent){
                        topMargin = dip(17)
                    }

                     linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_money
                         salaryLinearLayout = linearLayout {
                            leftPadding = dip(15)
                            rightPadding = dip(15)
                            backgroundResource = R.drawable.input_money_one
                            orientation = LinearLayout.HORIZONTAL
                            onClick { aa() }

                            salaryText = textView {
                                hintResource = R.string.hourly
                                gravity = Gravity.CENTER_VERTICAL
                                textSize = 15f
                                textColorResource = R.color.black33
                            }.lparams(width = wrapContent,height = matchParent){
                                weight = 1f
                                rightMargin = dip(15)
                            }

                            linearLayout {
                                gravity = Gravity.CENTER
                                imageView {
                                    imageResource = R.mipmap.register_select_nor
                                }.lparams(width = matchParent,height = dip(15))
                            }.lparams(width = dip(8),height = matchParent)
                        }.lparams(width = wrapContent,height = matchParent){
                            weight = 1f
                        }

                        startLinearLayout = linearLayout {
                            leftPadding = dip(15)
                            rightPadding = dip(15)
                            backgroundResource = R.drawable.input_money_one
                            orientation = LinearLayout.HORIZONTAL
                            onClick { start() }

                            startText = textView {
                                hintResource = R.string.startHourly
                                gravity = Gravity.CENTER_VERTICAL
                                textSize = 15f
                                textColorResource = R.color.black33
                            }.lparams(width = wrapContent,height = matchParent){
                                weight = 1f
                                rightMargin = dip(15)
                            }

                            linearLayout {
                                gravity = Gravity.CENTER
                                imageView {
                                    imageResource = R.mipmap.register_select_nor
                                }.lparams(width = matchParent,height = dip(15))
                            }.lparams(width = dip(8),height = matchParent)

                        }.lparams(width = wrapContent,height = matchParent){
                            weight = 1f
                            leftMargin = dip(10)
                        }

                        endLinearLayout = linearLayout {
                            leftPadding = dip(15)
                            rightPadding = dip(15)
                            backgroundResource = R.drawable.input_money_one
                            orientation = LinearLayout.HORIZONTAL
                            onClick { end() }

                            endText = textView {
                                hintResource = R.string.endHourly
                                gravity = Gravity.CENTER_VERTICAL
                                textSize = 15f
                                textColorResource = R.color.black33
                            }.lparams(width = wrapContent,height = matchParent){
                                weight = 1f
                                rightMargin = dip(15)
                            }

                            linearLayout {
                                gravity = Gravity.CENTER
                                imageView {
                                    imageResource = R.mipmap.register_select_nor
                                }.lparams(width = matchParent,height = dip(15))
                            }.lparams(width = dip(8),height = matchParent)

                        }.lparams(width = wrapContent,height = matchParent){
                            weight = 1f
                            leftMargin = dip(10)
                        }
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(7)
                    }

                    typeLinearLayout = linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.desiredIndustry
                            textColorResource = R.color.black20
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = wrapContent, height = matchParent){
                        }
                        typeText = textView {
                            backgroundColorResource = R.color.whiteFF
                            hintResource = R.string.desiredIndustryHint
                            hintTextColor = Color.parseColor("#333333")
                            textSize = 15f
                            gravity = Gravity.RIGHT
                            onClick { fixType() }
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                            rightMargin = dip(28)
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = matchParent,height = dip(11))
                        }.lparams(width = dip(6),height = matchParent)
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    addressLinearLayout = linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.workAddress
                            textColorResource = R.color.black20
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = wrapContent, height = matchParent){
                        }
                        addressText = textView {
                            backgroundColorResource = R.color.whiteFF
                            hintResource = R.string.workAddressHint
                            hintTextColor = Color.parseColor("#333333")
                            textSize = 15f
                            gravity = Gravity.RIGHT
                            onClick { mid.confirmAddress() }
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                            rightMargin = dip(28)
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = matchParent,height = dip(11))
                        }.lparams(width = dip(6),height = matchParent)
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    applyLinearLayout = linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.employmentForm
                            textColorResource = R.color.black20
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = wrapContent, height = matchParent){
                        }
                        applyText = textView {
                            backgroundColorResource = R.color.whiteFF
                            hintResource = R.string.employmentFormHint
                            hintTextColor = Color.parseColor("#333333")
                            textSize = 15f
                            gravity = Gravity.RIGHT
                            onClick { fixApply() }
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                            rightMargin = dip(28)
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = matchParent,height = dip(11))
                        }.lparams(width = dip(6),height = matchParent)
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    textView {
                        textResource = R.string.advantage
                        textSize = 15f
                        textColorResource = R.color.black33
                        gravity = Gravity.LEFT
                    }.lparams(width = matchParent,height = dip(21)){
                        topMargin = dip(17)
                    }

                    evaluationEdit = editText {
                        hintResource = R.string.advantageHint
                        textSize = 13f
                        hintTextColor = Color.parseColor("#B3B3B3")
                        backgroundResource = R.drawable.input_border
                        maxHeight = dip(100)
                        gravity = Gravity.TOP
                    }.lparams(width = matchParent,height = dip(100)){
                        topMargin = dip(7)
                    }

                    button {
                        backgroundResource = R.drawable.button_border
                        textResource = R.string.PtwoButton
                        textColorResource = R.color.whiteFF
                        textSize = 16f
                        setOnClickListener { personEnd() }
                    }.lparams(width = matchParent,height = dip(47)){
                        topMargin = dip(20)
                        bottomMargin = dip(30)
                    }

                }.lparams(width = matchParent,height = wrapContent){}
            }

        }.view

        return v
    }

    private fun aa(){
        BottomSheetDialogUtil.init(activity, salarylist.toTypedArray()) { _, position ->
            salaryText.text = salarylist[position]
        }
            .show()
    }

    private fun bb(){
        var mIntent = Intent(mContext,JobSelectActivity::class.java)
        startActivityForResult(mIntent,1)
    }

    public interface Mid{
        fun confirmJob()
        fun confirmAddress()
    }

    fun setJob(job:String){
        jobText.setText(job)
    }

    fun setAddress(address:String){
        addressText.setText(address)
    }

    private fun start(){
        BottomSheetDialogUtil.init(activity, moneyList.toTypedArray()) { _, position ->
            startText.text = moneyList[position]
        }
            .show()
    }

    private fun end(){
        BottomSheetDialogUtil.init(activity, moneyList.toTypedArray()) { _, position ->
            endText.text = moneyList[position]
        }
            .show()
    }

    private fun fixType(){
        BottomSheetDialogUtil.init(activity, typeList.toTypedArray()) { _, position ->
            typeText.text = typeList[position]
        }
            .show()
    }

    private fun fixApply(){
        BottomSheetDialogUtil.init(activity, applyList.toTypedArray()) { _, position ->
            applyText.text = applyList[position]
        }
            .show()
    }

    @SuppressLint("CheckResult")
    private fun personEnd(){
        var job = tool.getText(jobText)
        var salary = tool.getText(salaryText)
        var startSalary = tool.getText(startText)
        var endSalary = tool.getText(endText)
        var type = tool.getText(typeText)
        var address = tool.getText(addressText)
        var apply = tool.getText(applyText)
        var evaluation = tool.getEditText(evaluationEdit)
        println(first)
        println(education)
        println(work)

        if(job.isNullOrBlank()){
            jobLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }else {
            jobLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

        if(salary.isNullOrBlank()){
            salaryLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }else {
            salaryLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

        if(startSalary.isNullOrBlank()){
            startLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }else {
            startLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

        if(endSalary.isNullOrBlank()){
            endLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }else {
            endLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

        if(type.isNullOrBlank()){
            typeLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }else {
            typeLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

//        if(address.isNullOrBlank()){
//            addressLinearLayout.backgroundResource = R.drawable.edit_text_empty
//        }else {
//            addressLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
//        }

        if(apply.isNullOrBlank()){
            applyLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }else {
            applyLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

        if(evaluation.isNullOrBlank()){
            evaluationEdit.backgroundResource = R.drawable.edit_text_empty
        }else {
            evaluationEdit.backgroundResource = R.drawable.edit_text_no_empty
        }

        var startWork = tool.StrToDateOne(first[3])
        val c1 = Calendar.getInstance()
        val c2 = Calendar.getInstance()
        c2.time = startWork


//                && !address.isNullOrBlank()
        if(!job.isNullOrBlank() && !salary.isNullOrBlank() && !startSalary.isNullOrBlank() && !endSalary.isNullOrBlank()
            && !type.isNullOrBlank() && !apply.isNullOrBlank() && !evaluation.isNullOrBlank()
        ){
            var currencyType = "JPN"
            var areaIds:Array<String> = arrayOf("9836ef49-b023-4644-a5e4-da6709012f10")
            var industryIds:Array<String> = arrayOf("75891889-cbdb-431d-946f-e1e0aa09cbdd")
            var recruitMethod = "FULL_TIME"
            var salaryMax = getInt(endSalary)
            var salaryMin = getInt(startSalary)
            var salaryType = getType(salary)
            var workNumber = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR)
            var workingTypes:Array<String> = arrayOf("REGULAR")

            val userBody = RequestBody.create(json, first[1])
            val statuBody = RequestBody.create(json,first[2])
            val educationBody = RequestBody.create(json,education)
            val workingBody = RequestBody.create(json,work)


            var resumeName = first[0] + this.getString(R.string.resumeSuffix)
            val resumeParams = mapOf(
                "name" to resumeName,
                "isDefault" to true,
                "type" to "ONLINE"
            )
            val resumeJson = JSON.toJSONString(resumeParams)
            val resumeBody = RequestBody.create(json,resumeJson)
            var retrofitUils = RetrofitUtils(activity!!, "https://job.sk.cgland.top/")

            retrofitUils.create(RegisterApi::class.java)
                .createOnlineResume(resumeBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    println("创建简历成功！！！")
                    println(it)
                    var resume = it
                    var userretrofitUils = RetrofitUtils(mContext!!, "https://user.sk.cgland.top/")
                    var userService = userretrofitUils.create(RegisterApi::class.java)
                    var personObservable = userService.perfectPerson(userBody)
                    var statuObservable = userService.UpdateWorkStatu(statuBody)
                    val merge = Observable.merge(personObservable,statuObservable)
                    merge.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                        .subscribe({
                            println("创建个人信息和工作状态成功！！！")
                            println(it)

                            var intenParams = mutableMapOf(
                                "areaIds" to areaIds,
                                "currencyType" to currencyType,
                                "evaluation" to evaluation,
                                "industryIds" to industryIds,
                                "recruitMethod" to recruitMethod,
                                "resumeId" to resume,
                                "salaryMax" to salaryMax,
                                "salaryMin" to salaryMin,
                                "salaryType" to salaryType,
                                "workingExperience" to workNumber,
                                "workingTypes" to workingTypes
                            )
                            val intenJson = JSON.toJSONString(intenParams)
                            val intenBody = RequestBody.create(json,intenJson)

                            var jobRetrofitUils = RetrofitUtils(mContext!!, "https://job.sk.cgland.top/")
                            var jobService = jobRetrofitUils.create(RegisterApi::class.java)
                            var educationObservable = jobService.createEducation(educationBody,resume)
                            var workObservable = jobService.createWorkHistory(workingBody,resume)
                            var intenObservable = jobService.creatWorkIntentions(intenBody)
                            val jobMerge = Observable.merge(educationObservable,workObservable,intenObservable)
                            jobMerge.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    println("88888888888888888888888888888888")
                                    println(it)
                                    startActivity<RecruitInfoShowActivity>()
                                },{
                                    println("9999999999999999999999999999999")
                                    println(it)
                                })
                        },{
                            println("创建个人信息和工作状态失败！！！")
                            println(it)
                        })
                },{
                    if(it is HttpException){
                        if(it.code() == 401){
                            println("跳转错误页面")
                        }else{
                            println("222222222222")
                            println("12")
                        }
                    }else{
                        println("33")
                    }
                })





        }


    }

    private fun getInt(money:String):Int{
        var moneyNumber:Int = 0
        when(money){
            "10k" -> moneyNumber = 10000
            "20k" -> moneyNumber = 20000
            "30k" -> moneyNumber = 30000
            "40k" -> moneyNumber = 40000
            "50k" -> moneyNumber = 50000
            "60k" -> moneyNumber = 60000
        }
        return moneyNumber
    }

    private fun getType(salary:String):String{
        var type = "HOURLY"
        when(salary){
            this.getString(R.string.hourly) -> type = "HOURLY"
            this.getString(R.string.daySalary) -> type = "DAILY"
            this.getString(R.string.monthSalary) -> type = "MONTHLY"
            this.getString(R.string.yearSalary) -> type = "YEARLY"
        }
        return type
    }

    //  生成线上简历
    private fun createResume():String{
        var resumeName = first[0] + this.getString(R.string.resumeSuffix)
        var resumeId =""
        val resumeParams = mapOf(
            "name" to resumeName,
            "isDefault" to true,
            "type" to "ONLINE"
        )
        val resumeJson = JSON.toJSONString(resumeParams)
        val resumeBody = RequestBody.create(json,resumeJson)
        var retrofitUils = RetrofitUtils(activity!!, "https://job.sk.cgland.top/")

        retrofitUils.create(RegisterApi::class.java)
            .createOnlineResume(resumeBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                resumeId = it
            },{
                if(it is HttpException){
                    if(it.code() == 401){
                        println("跳转错误页面")
                    }else{
                        println("222222222222")
                        println("12")
                    }
                }else{
                    println("33")
                }
            })
        return resumeId
    }

}

