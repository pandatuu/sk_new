package com.example.sk_android.mvp.view.fragment.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.AndroidException
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import click
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.custom.layout.floatOnKeyboardLayout
import com.example.sk_android.mvp.api.onlineresume.OnlineResumeApi
import com.example.sk_android.mvp.api.person.User
import com.example.sk_android.mvp.model.onlineresume.basicinformation.BasicAttribute
import com.example.sk_android.mvp.model.onlineresume.basicinformation.UserBasicInformation
import com.example.sk_android.mvp.model.register.Education
import com.example.sk_android.mvp.model.register.Person
import com.example.sk_android.mvp.model.register.Work
import com.example.sk_android.utils.BaseTool
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import com.example.sk_android.mvp.view.activity.jobselect.JobSelectActivity
import com.example.sk_android.mvp.view.activity.jobselect.RecruitInfoShowActivity
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.JsonObject
import com.wlwl.os.listbottomsheetdialog.BottomSheetDialogUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import retrofit2.adapter.rxjava2.HttpException
import withTrigger
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PfourMainBodyFragment : Fragment() {
    private var mContext: Context? = null
    lateinit var jobText: TextView
    lateinit var jobLinearLayout: LinearLayout
    lateinit var addressText: TextView
    lateinit var addressLinearLayout: LinearLayout
    lateinit var salaryText: TextView
    lateinit var salaryLinearLayout: LinearLayout
    lateinit var startText: TextView
    lateinit var startLinearLayout: LinearLayout
    lateinit var endText: TextView
    lateinit var endLinearLayout: LinearLayout
    lateinit var typeText: TextView
    lateinit var typeLinearLayout: LinearLayout
    lateinit var applyText: TextView
    lateinit var applyLinearLayout: LinearLayout
    lateinit var evaluationEdit: EditText
    lateinit var tool: BaseTool
    lateinit var mid: Mid
    lateinit var v: View
    lateinit var jobIdText: TextView
    lateinit var addressIdText: TextView
    var salarylist: MutableList<String> = mutableListOf()
    var moneyList: MutableList<String> = mutableListOf()
    var basic = JsonObject()

    var hour = arrayOf("300", "600", "750", "900", "1000", "1200")
    var day = arrayOf("2400", "4800", "6500", "7000", "8000", "9000")
    var month = arrayOf("90000", "120000", "150000", "180000", "210000", "240000")
    var year = arrayOf("900000", "1200000", "1500000", "1800000", "2100000", "2400000")
    var typeList: MutableList<String> = mutableListOf()
    var applyList: ArrayList<String> = arrayListOf()
    var myAttributes = mapOf<String, Serializable>()
    var resumeId = ""
    var resultList: Array<String> = hour
    lateinit var minMoneyMap: MutableMap<String, String>
    lateinit var maxMoneyMap: MutableMap<String, String>

    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    private lateinit var myDialog: MyDialog
    var resultMap = mutableMapOf(
        "hour" to hour,
        "day" to day,
        "month" to month,
        "year" to year
    )


    companion object {
        fun newInstance(resumeId: String): PfourMainBodyFragment {
            val fragment = PfourMainBodyFragment()
            fragment.resumeId = resumeId

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

        applyList.add(this.getString(R.string.personFullTime))
        applyList.add(this.getString(R.string.personContract))
        applyList.add(this.getString(R.string.personThree))
        applyList.add(this.getString(R.string.personShort))
        applyList.add(this.getString(R.string.personOther))

        val builder = MyDialog.Builder(activity!!)
            .setCancelable(false)
            .setCancelOutside(false)
        myDialog = builder.create()

        getPerson()

        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mid = activity as Mid
        return fragmentView
    }

    fun createView(): View {
        tool = BaseTool()
        return UI {
            scrollView {
                backgroundColor = Color.BLUE
                linearLayout {
                    backgroundColor = Color.LTGRAY
                    orientation = LinearLayout.VERTICAL
                    backgroundColorResource = R.color.whiteFF
                    orientation = LinearLayout.VERTICAL
                    leftPadding = dip(15)
                    rightPadding = dip(15)

                    onClick {
                        closeKeyfocus()
                    }

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
                        }.lparams(width = wrapContent, height = matchParent) {
                        }
                        jobText = textView {
                            backgroundColorResource = R.color.whiteFF
                            hintResource = R.string.expectedPositionHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                            gravity = Gravity.RIGHT
                            this.withTrigger().click { mid.confirmJob() }
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                            rightMargin = dip(28)
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = matchParent, height = dip(11))
                        }.lparams(width = dip(6), height = matchParent)
                    }.lparams(width = matchParent, height = dip(44)) {
                        topMargin = dip(20)
                    }

                    textView {
                        textResource = R.string.remuneration
                        textColorResource = R.color.black33
                        textSize = 15f
                        gravity = Gravity.LEFT
                    }.lparams(width = wrapContent, height = wrapContent) {
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
                            }.lparams(width = wrapContent, height = matchParent) {
                                weight = 1f
                                rightMargin = dip(15)
                            }

                            linearLayout {
                                gravity = Gravity.CENTER
                                imageView {
                                    imageResource = R.mipmap.register_select_nor
                                }.lparams(width = matchParent, height = dip(15))
                            }.lparams(width = dip(8), height = matchParent)
                        }.lparams(width = wrapContent, height = matchParent) {
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
                            }.lparams(width = wrapContent, height = matchParent) {
                                weight = 1f
                                rightMargin = dip(15)
                            }

                            linearLayout {
                                gravity = Gravity.CENTER
                                imageView {
                                    imageResource = R.mipmap.register_select_nor
                                }.lparams(width = matchParent, height = dip(15))
                            }.lparams(width = dip(8), height = matchParent)

                        }.lparams(width = wrapContent, height = matchParent) {
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
                            }.lparams(width = wrapContent, height = matchParent) {
                                weight = 1f
                                rightMargin = dip(15)
                            }

                            linearLayout {
                                gravity = Gravity.CENTER
                                imageView {
                                    imageResource = R.mipmap.register_select_nor
                                }.lparams(width = matchParent, height = dip(15))
                            }.lparams(width = dip(8), height = matchParent)

                        }.lparams(width = wrapContent, height = matchParent) {
                            weight = 1f
                            leftMargin = dip(10)
                        }
                    }.lparams(width = matchParent, height = dip(44)) {
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
                        }.lparams(width = wrapContent, height = matchParent) {
                        }
                        typeText = textView {
                            backgroundColorResource = R.color.whiteFF
                            hintResource = R.string.desiredIndustryHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                            gravity = Gravity.RIGHT
                            onClick { fixType() }
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                            rightMargin = dip(28)
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = matchParent, height = dip(11))
                        }.lparams(width = dip(6), height = matchParent)
                    }.lparams(width = matchParent, height = dip(44)) {
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
                        }.lparams(width = wrapContent, height = matchParent) {
                        }
                        addressText = textView {
                            backgroundColorResource = R.color.whiteFF
                            hintResource = R.string.workAddressHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                            singleLine = true
                            maxEms = 5
                            ellipsize = TextUtils.TruncateAt.END
                            gravity = Gravity.RIGHT
                            this.withTrigger().click { mid.confirmAddress() }
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                            rightMargin = dip(28)
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = matchParent, height = dip(11))
                        }.lparams(width = dip(6), height = matchParent)
                    }.lparams(width = matchParent, height = dip(44)) {
                        topMargin = dip(20)
                    }

                    applyLinearLayout = linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.jlFindType
                            textColorResource = R.color.black20
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = wrapContent, height = matchParent) {
                        }
                        applyText = textView {
                            backgroundColorResource = R.color.whiteFF
                            hintResource = R.string.personFullTime
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                            gravity = Gravity.RIGHT
                            onClick { fixApply() }
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                            rightMargin = dip(28)
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = matchParent, height = dip(11))
                        }.lparams(width = dip(6), height = matchParent)
                    }.lparams(width = matchParent, height = dip(44)) {
                        topMargin = dip(20)
                    }

                    textView {
                        textResource = R.string.advantage
                        textSize = 15f
                        textColorResource = R.color.black33
                        gravity = Gravity.LEFT
                    }.lparams(width = matchParent, height = dip(21)) {
                        topMargin = dip(17)
                    }

                    evaluationEdit = editText {
                        hintResource = R.string.advantageHint
                        textSize = 13f
                        hintTextColor = Color.parseColor("#B3B3B3")
                        backgroundResource = R.drawable.input_border
                        maxHeight = dip(100)
                        gravity = Gravity.TOP
                    }.lparams(width = matchParent, height = dip(100)) {
                        topMargin = dip(7)
                    }

                    button {
                        backgroundResource = R.drawable.button_border
                        textResource = R.string.PtwoButton
                        textColorResource = R.color.whiteFF
                        textSize = 16f
                        this.withTrigger().click { personEnd() }
                    }.lparams(width = matchParent, height = dip(47)) {
                        topMargin = dip(20)
                        bottomMargin = dip(30)
                    }

                    jobIdText = textView {
                        visibility = View.GONE
                    }

                    addressIdText = textView {
                        visibility = View.GONE
                    }

                }.lparams(width = matchParent, height = wrapContent) {}
            }
        }.view
    }

    private fun aa() {
        BottomSheetDialogUtil.init(activity, salarylist.toTypedArray()) { _, position ->
            salaryText.text = salarylist[position]
            if (salarylist[position] == this.getString(R.string.hourly)) {
                resultList = hour
            }

            if (salarylist[position] == this.getString(R.string.daySalary)) {
                resultList = day
            }

            if (salarylist[position] == this.getString(R.string.monthSalary)) {
                resultList = month
            }

            if (salarylist[position] == this.getString(R.string.yearSalary)) {
                resultList = year
            }
        }
            .show()
    }

    private fun bb() {
        var mIntent = Intent(mContext, JobSelectActivity::class.java)
        startActivityForResult(mIntent, 1)
    }

    public interface Mid {
        fun confirmJob()
        fun confirmAddress()
    }

    fun setJob(job: String) {
        jobText.setText(job)
    }

    fun setAddress(address: String) {
        addressText.setText(address)
    }

    private fun start() {
        BottomSheetDialogUtil.init(activity, resultList) { _, position ->
            minMoneyMap = tool.moneyToString(resultList[position])
            startText.text = minMoneyMap.get("result")
        }
            .show()
    }

    private fun end() {
        BottomSheetDialogUtil.init(activity, resultList) { _, position ->
            maxMoneyMap = tool.moneyToString(resultList[position])
            endText.text = maxMoneyMap.get("result")
        }
            .show()
    }

    private fun fixType() {
        BottomSheetDialogUtil.init(activity, typeList.toTypedArray()) { _, position ->
            typeText.text = typeList[position]
        }
            .show()
    }

    private fun fixApply() {
        BottomSheetDialogUtil.init(activity, applyList.toTypedArray()) { _, position ->
            applyText.text = applyList[position]
        }
            .show()
    }

    @SuppressLint("CheckResult")
    private fun personEnd() {
        myDialog.show()

        var job = tool.getText(jobText)
        var salary = tool.getText(salaryText)
        var startSalary = tool.getText(startText)
        var endSalary = tool.getText(endText)
        var myJobId = tool.getText(jobIdText)
        var myAddressId = tool.getText(addressIdText)
        var type = tool.getText(typeText)
        var myTypeString = "FULL_TIME"
        var address = tool.getText(addressText)
        var apply = tool.getText(applyText)
        var myApplyType = "REGULAR"
        var evaluation = tool.getEditText(evaluationEdit)

        if (job.isNullOrBlank()) {
            jobLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            jobLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

        if (salary.isNullOrBlank()) {
            salaryLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            salaryLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

        if (startSalary.isNullOrBlank()) {
            startLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            startLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            startSalary = minMoneyMap.get("money")!!.trim()
        }

        if (endSalary.isNullOrBlank()) {
            endLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            endLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            endSalary = maxMoneyMap.get("money")!!.trim()
        }

        if (type.isNullOrBlank()) {
            typeLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            typeLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            when (type) {
                this.getString(R.string.fullTime) -> myTypeString = "FULL_TIME"
                this.getString(R.string.partTime) -> myTypeString = "PART_TIME"
            }
        }

        if (address.isNullOrBlank()) {
            addressLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            addressLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

        if (apply.isNullOrBlank()) {
            applyLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            applyLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            when (apply) {
                this.getString(R.string.personFullTime) -> myApplyType = "REGULAR"
                this.getString(R.string.personContract) -> myApplyType = "CONTRACT"
                this.getString(R.string.personThree) -> myApplyType = "DISPATCH"
                this.getString(R.string.personShort) -> myApplyType = "SHORT_TERM"
                this.getString(R.string.personOther) -> myApplyType = "OTHER"
            }
        }

        if (evaluation.isNullOrBlank()) {
            evaluationEdit.backgroundResource = R.drawable.edit_text_empty
        } else {
            evaluationEdit.backgroundResource = R.drawable.edit_text_no_empty
        }

        if (startSalary.toInt() > endSalary.toInt()) {
            toast(this.getString(R.string.startMinEnd))
            startLinearLayout.backgroundResource = R.drawable.edit_text_empty
            endLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            startLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            endLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }


        if (!job.isNullOrBlank() && !salary.isNullOrBlank() && !startSalary.isNullOrBlank() && !endSalary.isNullOrBlank()
            && !type.isNullOrBlank() && !apply.isNullOrBlank() && !evaluation.isNullOrBlank()
        ) {


            var currencyType = "JPN"
            var areaIds = myAddressId.split(",")
            var industryIds = myJobId.split(",")
            var recruitMethod = myTypeString
            var salaryMax = endSalary.toInt()
            var salaryMin = startSalary.toInt()
            var salaryType = getType(salary)
            var workNumber = 1
            var workingTypes: Array<String> = arrayOf(myApplyType)


            val intenParams = mutableMapOf(
                "areaIds" to areaIds,
                "currencyType" to currencyType,
                "evaluation" to evaluation,
                "industryIds" to industryIds,
                "recruitMethod" to recruitMethod,
                "resumeId" to resumeId,
                "salaryMax" to salaryMax,
                "salaryMin" to salaryMin,
                "salaryType" to salaryType,
                "workingExperience" to workNumber,
                "workingTypes" to workingTypes
            )
            val intenJson = JSON.toJSONString(intenParams)
            val intenBody = RequestBody.create(json, intenJson)

            println(basic)
            println(basic.get("userSkill").toString().trim())
            println(basic.get("jobSkill").toString().trim())
            var myBasicAttribute = BasicAttribute(
                basic.get("userSkill").toString().replace("\"", "").trim(),
                basic.get("jobSkill").toString().replace("\"", "").trim(),
                evaluation
            )

            println("暂停")
            val params = mapOf(
                "attributes" to myBasicAttribute
            )

            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(json, userJson)

            var jobRetrofitUils = RetrofitUtils(mContext!!, this.getString(R.string.jobUrl))

            jobRetrofitUils.create(RegisterApi::class.java)
                .creatWorkIntentions(intenBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    if (it.code() in 200..299) {
                        toast(this.getString(R.string.pfIntenSuccess))

                        var userRetrofitUils = RetrofitUtils(mContext!!, this.getString(R.string.userUrl))

                        userRetrofitUils.create(OnlineResumeApi::class.java)
                            .updateUserSelf(body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                            .subscribe({
                                println(it)
                                println("456")
                                if (it.code() in 200..299) {
                                    println("更新个人优势成功")

                                    var intent = Intent(activity, RecruitInfoShowActivity::class.java)
                                    intent.putExtra("condition", 0)
                                    startActivity(intent)
                                    activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                                    myDialog.dismiss()
                                }
                            }, {
                                println("更新个人信息出粗")
                                println(it)
                                println("123")
                            })
                    } else {
                        println(it)
                        toast(this.getString(R.string.pfIntenFail))
                        myDialog.dismiss()
                    }


                }, {
                    myDialog.dismiss()
                })
        } else {
            myDialog.dismiss()
        }


    }

    private fun getInt(money: String): Int {
        var moneyNumber: Int = 0
        when (money) {
            "10K" -> moneyNumber = 10000
            "20K" -> moneyNumber = 20000
            "30K" -> moneyNumber = 30000
            "40K" -> moneyNumber = 40000
            "50K" -> moneyNumber = 50000
            "60K" -> moneyNumber = 60000
        }
        return moneyNumber
    }

    private fun getType(salary: String): String {
        var type = "HOURLY"
        when (salary) {
            this.getString(R.string.hourly) -> type = "HOURLY"
            this.getString(R.string.daySalary) -> type = "DAILY"
            this.getString(R.string.monthSalary) -> type = "MONTHLY"
            this.getString(R.string.yearSalary) -> type = "YEARLY"
        }
        return type
    }

    // 类型转换
    private fun longToString(long: Long, format: String): String {
        val str = SimpleDateFormat(format).format(Date(long))
        return str
    }

    fun setJobIdText(jobId: String) {
        jobIdText.text = jobId
    }

    fun setAddressIdText(addressId: String) {
        addressIdText.text = addressId
    }


    private fun closeKeyfocus() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)

        evaluationEdit.clearFocus()
    }

    @SuppressLint("CheckResult")
    fun getPerson() {
        // 通过token,获取个人信息
        var requestUserInfo = RetrofitUtils(context!!, this.getString(R.string.userUrl))

        requestUserInfo.create(User::class.java)
            .getSelfInfo()
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println(it.get("attributes"))
                println("ceshi")
                basic = it.get("attributes").asJsonObject
            }, {})


    }

}

