package com.example.sk_android.mvp.view.fragment.jobselect

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import click
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobselect.UserJobIntention
import com.example.sk_android.mvp.view.activity.jobselect.CitySelectActivity
import com.example.sk_android.mvp.view.activity.jobselect.JobSelectActivity
import com.example.sk_android.mvp.view.activity.jobselect.JobWantedManageActivity
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.apache.commons.lang.StringUtils
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import withTrigger
import java.io.Serializable

class JobWantedListFragment : Fragment() {
    private var mContext: Context? = null
    private lateinit var deleteButton: DeleteButton
    var operateType: Int = 1

    private lateinit var hindText: TextView
    private lateinit var evaluationText: TextView
    private lateinit var jobIdText: TextView
    private lateinit var addressIdText: TextView
    private lateinit var wantJob: TextView
    private lateinit var city: TextView
    private lateinit var jobType: TextView
    private lateinit var salary: TextView
    private lateinit var recruitWay: TextView
    private lateinit var overseasRecruit: TextView
    private lateinit var buttonText: TextView

    var emptyArray = arrayListOf<String>()
    var emptyMutableList = mutableListOf<String>()
    var myAttributes = mapOf<String, Serializable>()
    var userJobIntention = UserJobIntention(
        emptyArray,
        emptyMutableList,
        myAttributes,
        "",
        "",
        "",
        "",
        "",
        emptyArray,
        emptyMutableList,
        "",
        "",
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        "",
        0,
        0,
        "",
        "",
        0,
        emptyArray
    )
    var condtion = 1
    lateinit var tool: BaseTool
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")

//    private val
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(userJobIntention: UserJobIntention, condtion: Int): JobWantedListFragment {
            val fragment = JobWantedListFragment()
            fragment.userJobIntention = userJobIntention
            fragment.condtion = condtion
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        deleteButton = activity as DeleteButton

        init()
        return fragmentView
    }

    override fun onDestroyView() {
        disposable.dispose()

        super.onDestroyView()
    }

    fun createView(): View {
        tool = BaseTool()
        return UI {
            linearLayout {
                relativeLayout {
                    verticalLayout {
                        scrollView {
                            isVerticalScrollBarEnabled = false
                            verticalLayout() {
                                verticalLayout() {

                                    this.withTrigger().click {
                                        var intent = Intent(activity, JobSelectActivity::class.java)
                                        startActivityForResult(intent, 3)
                                        activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                                    }

                                    backgroundResource = R.drawable.text_view_bottom_border
                                    textView() {
                                        text = "期望职位"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        wantJob = textView() {
                                            textSize = 18f
                                            textColorResource = R.color.titleSon
                                            gravity = Gravity.CENTER
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentLeft()
                                        }
                                        verticalLayout() {
                                            gravity = Gravity.CENTER_VERTICAL
                                            imageView() {
                                                setImageResource(R.mipmap.icon_go_position)
                                            }.lparams() {
                                                width = wrapContent
                                                height = wrapContent
                                            }
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentRight()
                                        }
                                    }.lparams() {
                                        width = matchParent
                                        height = dip(35)
                                        bottomPadding = 50
                                        topPadding = 30
                                    }
                                }.lparams() {
                                    width = matchParent
                                    height = wrapContent
                                    topMargin = dip(10)
                                    rightMargin = dip(15)
                                    leftMargin = dip(15)
                                }

                                verticalLayout() {

                                    this.withTrigger().click {
                                            var intent = Intent(mContext, CitySelectActivity::class.java).also {
                                                startActivityForResult(it, 4)
                                            }
                                            activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

                                        }




                                    backgroundResource = R.drawable.text_view_bottom_border
                                    textView() {
                                        text = "工作城市"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        city = textView() {
                                            singleLine = true
                                            textSize = 18f
                                            textColorResource = R.color.titleSon
                                            gravity = Gravity.CENTER
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentLeft()
                                        }
                                        verticalLayout() {
                                            gravity = Gravity.CENTER_VERTICAL
                                            imageView() {
                                                setImageResource(R.mipmap.icon_go_position)
                                            }.lparams() {
                                                width = wrapContent
                                                height = wrapContent
                                            }
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentRight()
                                        }

                                    }.lparams() {
                                        width = matchParent
                                        height = dip(35)
                                        bottomPadding = 50
                                        topPadding = 30
                                    }
                                }.lparams() {
                                    width = matchParent
                                    height = wrapContent
                                    rightMargin = dip(15)
                                    leftMargin = dip(15)
                                }
                                verticalLayout() {
                                    backgroundResource = R.drawable.text_view_bottom_border
                                    onClick {
                                        deleteButton.oneDialogCLick("工作类别")
                                    }
                                    textView() {
                                        text = "工作类别"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        jobType = textView() {
                                            textSize = 18f
                                            textColorResource = R.color.titleSon
                                            gravity = Gravity.CENTER
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentLeft()
                                        }
                                        verticalLayout() {
                                            gravity = Gravity.CENTER_VERTICAL
                                            imageView() {
                                                setImageResource(R.mipmap.icon_go_position)
                                                onClick {
                                                    deleteButton.oneDialogCLick("工作类别")
                                                }
                                            }.lparams() {
                                                width = wrapContent
                                                height = wrapContent

                                            }
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentRight()
                                        }
                                    }.lparams() {
                                        width = matchParent
                                        height = dip(35)
                                        bottomPadding = 50
                                        topPadding = 30
                                    }
                                }.lparams() {
                                    width = matchParent
                                    height = wrapContent
                                    rightMargin = dip(15)
                                    leftMargin = dip(15)
                                }
                                verticalLayout() {
                                    backgroundResource = R.drawable.text_view_bottom_border
                                    onClick {
                                        deleteButton.twoDialogCLick("薪资要求")
                                    }
                                    textView() {
                                        text = "薪资要求"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        salary = textView() {
                                            textSize = 18f
                                            textColorResource = R.color.titleSon
                                            gravity = Gravity.CENTER
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentLeft()
                                        }
                                        verticalLayout() {
                                            gravity = Gravity.CENTER_VERTICAL
                                            imageView() {
                                                setImageResource(R.mipmap.icon_go_position)
                                                onClick {
                                                    deleteButton.twoDialogCLick("薪资要求")
                                                }
                                            }.lparams() {
                                                width = wrapContent
                                                height = wrapContent
                                            }
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentRight()
                                        }
                                    }.lparams() {
                                        width = matchParent
                                        height = dip(35)
                                        bottomPadding = 50
                                        topPadding = 30
                                    }
                                }.lparams() {
                                    width = matchParent
                                    height = wrapContent

                                    rightMargin = dip(15)
                                    leftMargin = dip(15)
                                }
                                verticalLayout() {
                                    backgroundResource = R.drawable.text_view_bottom_border
                                    onClick {
                                        deleteButton.oneDialogCLick("招聘方式")
                                    }
                                    textView() {
                                        text = "招聘方式"
                                        textColorResource = R.color.titleGrey
                                    }.lparams() {
                                        width = matchParent
                                        height = wrapContent
                                    }
                                    relativeLayout {
                                        recruitWay = textView {
                                            textSize = 18f
                                            textColorResource = R.color.titleSon
                                            gravity = Gravity.CENTER
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentLeft()
                                        }
                                        verticalLayout() {
                                            gravity = Gravity.CENTER_VERTICAL
                                            imageView() {
                                                setImageResource(R.mipmap.icon_go_position)
                                                onClick {
                                                    deleteButton.oneDialogCLick("招聘方式")
                                                }
                                            }.lparams() {
                                                width = wrapContent
                                                height = wrapContent
                                            }
                                        }.lparams() {
                                            width = wrapContent
                                            height = matchParent
                                            alignParentRight()
                                        }
                                    }.lparams() {
                                        width = matchParent
                                        height = dip(35)
                                        bottomPadding = 50
                                        topPadding = 30
                                    }
                                }.lparams() {
                                    width = matchParent
                                    height = wrapContent
                                    rightMargin = dip(15)
                                    leftMargin = dip(15)
                                }

                                hindText = textView {
                                    visibility = View.GONE
                                }

                                jobIdText = textView {
                                    visibility = View.GONE
                                }

                                addressIdText = textView {
                                    visibility = View.GONE
                                }

                                evaluationText = textView {
                                    text = "empty"
                                    visibility = View.GONE
                                }


                            }
                        }
                    }.lparams() {
                        alignParentTop()
                        width = matchParent
                        height = matchParent
                    }


                    buttonText = textView() {
                        text = "删除本条"
                        //  backgroundColorResource = R.color.buttonColor
                        backgroundResource = R.drawable.job_intention_radius_button
                        textColorResource = R.color.white
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                        visibility = View.GONE
                        gravity = Gravity.CENTER
                        if (operateType == 2) {
                            visibility = View.GONE
                        }
                        setOnClickListener(object : View.OnClickListener {
                            override fun onClick(v: View?) {
                                var id = hindText.text.toString()
                                deleteButton.delete(id)
                            }
                        })
                    }.lparams() {
                        width = matchParent
                        height = dip(47)
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                        bottomMargin = dip(15)
                        alignParentBottom()
                    }
                }.lparams(width = matchParent, height = matchParent) {

                }
            }
        }.view
    }

    interface DeleteButton {
        fun delete(id: String)
        fun oneDialogCLick(s: String)
        fun twoDialogCLick(s: String)
    }


    fun setWantJobText(str: String) {
        wantJob.text = str
    }

    fun setJobIdText(str: String) {
        jobIdText.text = str
    }

    fun setAddressIdText(str: String) {
        addressIdText.text = str
    }

    fun setCity(str: String) {
        city.text = str
    }

    fun setJobtype(text: String) {
        jobType.text = text
    }

    fun setSalary(text: String) {
        salary.text = text
    }

    fun setRecruitWay(text: String) {
        recruitWay.text = text
    }

    fun setOverseasRecruit(text: String) {
        overseasRecruit.text = text
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        println("********************")
        println(condtion)
        if (condtion == 1) {

            wantJob.text = userJobIntention.industryName[0]
            jobIdText.text = userJobIntention.industryIds[0]

            var address = userJobIntention.areaName
            var myAddress = StringUtils.join(address, "●")
            city.text = myAddress

            var addressIds = userJobIntention.areaIds
            var newAddressArray = StringUtils.join(addressIds, ",")
            addressIdText.text = newAddressArray


            var recruitMethod = userJobIntention.recruitMethod
            var recrText = "フルタイム"
            when (recruitMethod) {
                "FULL_TIME" -> recrText = "フルタイム"
                "PART_TIME" -> recrText = "パートタイム"
            }
            jobType.text = recrText

            var mySalary = userJobIntention.salaryType

            var type = this.getString(R.string.hourly)
            when (mySalary) {
                "HOURLY" -> type = this.getString(R.string.hourly)
                "DAILY" -> type = this.getString(R.string.daySalary)
                "MONTHLY" -> type = this.getString(R.string.monthSalary)
                "YEARLY" -> type = this.getString(R.string.yearSalary)
            }

            var min = userJobIntention.salaryMin
            var max = userJobIntention.salaryMax

            salary.text = "$type:$min-$max"

            var myWorkType = this.getString(R.string.personFullTime)
            when (userJobIntention.workingTypes[0]) {
                "REGULAR" -> myWorkType = this.getString(R.string.personFullTime)
                "CONTRACT" -> myWorkType = this.getString(R.string.personContract)
                "DISPATCH" -> myWorkType = this.getString(R.string.personThree)
                "SHORT_TERM" -> myWorkType = this.getString(R.string.personShort)
                "OTHER" -> myWorkType = this.getString(R.string.personOther)
            }

            recruitWay.setText(myWorkType)

            hindText.text = userJobIntention.id

            evaluationText.text = userJobIntention.evaluation

            buttonText.visibility = View.VISIBLE

        }
    }

    @SuppressLint("CheckResult")
    fun getResult() {
        var myJobId = tool.getText(jobIdText)
        var myAddressId = tool.getText(addressIdText)
        var myWantJob = tool.getText(wantJob)
        var myCity = tool.getText(city)
        var myJobType = tool.getText(jobType)
        var personJobType = "FULL_TIME"
        var mySalary = tool.getText(salary)
        var salaryType = ""
        var salaryMin = 0
        var salaryMax = 0
        var myRecruitWay = tool.getText(recruitWay)
        var personRecruitWay = ""
        var hindId = tool.getText(hindText)
        var emptyYear: Int = 1
        var myEvaluation = tool.getText(evaluationText)

        if (myWantJob.isNullOrBlank()) {
            toast("职位不可为空！！")
            return
        }

        if (myCity.isNullOrBlank()) {
            toast("城市不可为空！！")
            return
        }

        if (myJobType.isNullOrBlank()) {
            toast("请选择工作类型")
            return
        } else {
            when (myJobType) {
                this.getString(R.string.fullTime) -> personJobType = "FULL_TIME"
                this.getString(R.string.partTime) -> personJobType = "PART_TIME"
            }
        }

        if (mySalary.isNullOrBlank()) {
            toast("请选择薪酬范围")
            return
        } else {
            var str = mySalary.split(":")
            var sType = str[0].trim()
            when (sType) {
                this.getString(R.string.hourly) -> salaryType = "HOURLY"
                this.getString(R.string.daySalary) -> salaryType = "DAILY"
                this.getString(R.string.monthSalary) -> salaryType = "MONTHLY"
                this.getString(R.string.yearSalary) -> salaryType = "YEARLY"
            }
            var strMoney = str[1].trim()
            var personMoney = strMoney.split("-")
            salaryMin = personMoney[0].trim().toInt()
            salaryMax = personMoney[1].trim().toInt()
        }

        if (myRecruitWay.isNullOrBlank()) {
            toast("请选择对应的招聘方式")
            return
        } else {
            when (myRecruitWay) {
                this.getString(R.string.personFullTime) -> personRecruitWay = "REGULAR"
                this.getString(R.string.personContract) -> personRecruitWay = "CONTRACT"
                this.getString(R.string.personThree) -> personRecruitWay = "DISPATCH"
                this.getString(R.string.personShort) -> personRecruitWay = "SHORT_TERM"
                this.getString(R.string.personOther) -> personRecruitWay = "OTHER"
            }
        }

        var areaIds = myAddressId.split(",")
        var industryIds = myJobId.split(",")
        var workingTypes: Array<String> = arrayOf(personRecruitWay)

        val intenParams = mutableMapOf(
            "areaIds" to areaIds,
            "currencyType" to "JPN",
            "evaluation" to myEvaluation,
            "industryIds" to industryIds,
            "recruitMethod" to personJobType,
            "salaryMax" to salaryMax,
            "salaryMin" to salaryMin,
            "salaryType" to salaryType,
            "workingExperience" to emptyYear,
            "workingTypes" to workingTypes
        )

        val intenJson = JSON.toJSONString(intenParams)
        val intenBody = RequestBody.create(json, intenJson)
        var jobRetrofitUils = RetrofitUtils(mContext!!, this.getString(R.string.jobUrl))

        //类型 1修改/2添加
        if (condtion == 1) {
            jobRetrofitUils.create(PersonApi::class.java)
                .updateJobIntention(hindId, intenBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    println("++++++++++++++++++")
                    println(it)
                    if (it.code() in 200..299) {
                        println("更新工作期望成功")
                        startActivity<JobWantedManageActivity>()
                    }
                }, {

                })
        } else {

            jobRetrofitUils.create(RegisterApi::class.java)
                .creatWorkIntentions(intenBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    println("--------------------")
                    println(it)
                    if (it.code() in 200..299) {
                        println("创建工作期望成功")
                        startActivity<JobWantedManageActivity>()
                    }
                }, {

                })
        }


    }

}

