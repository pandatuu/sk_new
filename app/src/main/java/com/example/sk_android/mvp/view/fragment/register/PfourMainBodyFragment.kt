package com.example.sk_android.mvp.view.fragment.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.AndroidException
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
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
import com.wlwl.os.listbottomsheetdialog.BottomSheetDialogUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.adapter.rxjava2.HttpException
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
    var typeList: MutableList<String> = mutableListOf()
    var applyList: ArrayList<String> = arrayListOf()
    var myAttributes = mapOf<String, Serializable>()
    var education = Education(myAttributes, "", "", "", "", "", "")
    var work = Work(myAttributes, "", false, "", "", "", "", "")
    var condition: Int = 0

    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    private lateinit var myDialog: MyDialog


    companion object {
        fun newInstance(education: Education, work: Work, condition: Int): PfourMainBodyFragment {
            val fragment = PfourMainBodyFragment()
            fragment.education = education
            fragment.work = work
            fragment.condition = condition

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

        val builder = MyDialog.Builder(activity!!)
            .setCancelable(false)
            .setCancelOutside(false)
        myDialog = builder.create()

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
        v = UI {

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
                        }.lparams(width = wrapContent, height = matchParent) {
                        }
                        jobText = textView {
                            backgroundColorResource = R.color.whiteFF
                            hintResource = R.string.expectedPositionHint
                            hintTextColor = Color.parseColor("#333333")
                            textSize = 15f
                            gravity = Gravity.RIGHT
                            onClick { mid.confirmJob() }
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
                            hintTextColor = Color.parseColor("#333333")
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
                            hintTextColor = Color.parseColor("#333333")
                            textSize = 15f
                            gravity = Gravity.RIGHT
                            onClick { mid.confirmAddress() }
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
                            textResource = R.string.employmentForm
                            textColorResource = R.color.black20
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = wrapContent, height = matchParent) {
                        }
                        applyText = textView {
                            backgroundColorResource = R.color.whiteFF
                            hintResource = R.string.employmentFormHint
                            hintTextColor = Color.parseColor("#333333")
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
                        setOnClickListener { personEnd() }
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

        return v
    }

    private fun aa() {
        BottomSheetDialogUtil.init(activity, salarylist.toTypedArray()) { _, position ->
            salaryText.text = salarylist[position]
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
        BottomSheetDialogUtil.init(activity, moneyList.toTypedArray()) { _, position ->
            startText.text = moneyList[position]
        }
            .show()
    }

    private fun end() {
        BottomSheetDialogUtil.init(activity, moneyList.toTypedArray()) { _, position ->
            endText.text = moneyList[position]
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
        }

        if (endSalary.isNullOrBlank()) {
            endLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            endLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
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


        if (!job.isNullOrBlank() && !salary.isNullOrBlank() && !startSalary.isNullOrBlank() && !endSalary.isNullOrBlank()
            && !type.isNullOrBlank() && !apply.isNullOrBlank() && !evaluation.isNullOrBlank()
        ) {
            myDialog.show()

            var currencyType = "JPN"
            var areaIds = myAddressId.split(",")
            var industryIds = myJobId.split(",")
            var recruitMethod = myTypeString
            var salaryMax = getInt(endSalary)
            var salaryMin = getInt(startSalary)
            var salaryType = getType(salary)
            var workNumber = 1
            var workingTypes: Array<String> = arrayOf(myApplyType)


            val educationParams = mutableMapOf(
                "attributes" to education.attributes,
                "educationalBackground" to education.educationalBackground,
                "endDate" to education.endDate,
                "major" to education.major,
                "schoolName" to education.schoolName,
                "startDate" to education.startDate
            )

            val workParams = mutableMapOf(
                "attributes" to work.attributes,
                "endDate" to work.endDate,
                "hideOrganization" to work.hideOrganization,
                "organizationName" to work.organizationName,
                "position" to work.position,
                "responsibility" to work.responsibility,
                "startDate" to work.startDate
            )

            val educationJson = JSON.toJSONString(educationParams)
            val workJson = JSON.toJSONString(workParams)

            val educationBody = RequestBody.create(json, educationJson)
            val workBody = RequestBody.create(json, workJson)

            var resumeName = UUID.randomUUID().toString().replace("-", "").toLowerCase()
            val resumeParams = mapOf(
                "name" to resumeName,
                "isDefault" to true,
                "type" to "ONLINE"
            )
            val resumeJson = JSON.toJSONString(resumeParams)
            val resumeBody = RequestBody.create(json, resumeJson)


            var retrofitUils = RetrofitUtils(activity!!, this.getString(R.string.jobUrl))
            retrofitUils.create(RegisterApi::class.java)
                .createOnlineResume(resumeBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    println("创建简历成功，获得简历Id")
                    var resume = it
                    val intenParams = mutableMapOf(
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
                    val intenBody = RequestBody.create(json, intenJson)

                    var jobRetrofitUils = RetrofitUtils(mContext!!, this.getString(R.string.jobUrl))
                    jobRetrofitUils.create(RegisterApi::class.java)
                        .createEducation(educationBody, resume)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                        .subscribe({
                            println("创建教育经理成功")
                            jobRetrofitUils.create(RegisterApi::class.java)
                                .creatWorkIntentions(intenBody)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                                .subscribe({
                                    println("创建工作期望成功")
                                    println(it)

                                    if (condition == 0) {
                                        myDialog.dismiss()
                                        var intent = Intent(activity, RecruitInfoShowActivity::class.java)
                                        intent.putExtra("condition", 0)
                                        startActivity(intent)
                                        activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

                                    } else {
                                        jobRetrofitUils.create(RegisterApi::class.java)
                                            .createWorkHistory(workBody, resume)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                                            .subscribe({
                                                myDialog.dismiss()
                                                println("创建工作尽力成功")
                                                var intent = Intent(activity, RecruitInfoShowActivity::class.java)
                                                intent.putExtra("condition", 0)
                                                startActivity(intent)
                                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

                                            }, {
                                                myDialog.dismiss()
                                            })
                                    }
                                }, {
                                    myDialog.dismiss()
                                })
                        }, {
                            myDialog.dismiss()
                        })
                }, {
                    myDialog.dismiss()
                    println("简历错误")
                    println(it)
                })

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

}

