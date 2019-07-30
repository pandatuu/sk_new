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
import com.example.sk_android.R
import com.example.sk_android.utils.BaseTool
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.inputmethod.InputMethodManager
import android.widget.Switch
import com.example.sk_android.mvp.view.activity.register.PersonInformationFourActivity
import android.widget.CompoundButton
import com.alibaba.fastjson.JSON
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.custom.layout.floatOnKeyboardLayout
import com.example.sk_android.mvp.model.register.Work
import com.example.sk_android.utils.BasisTimesUtils
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.toast
import java.io.Serializable
import java.text.SimpleDateFormat


class PthreeMainBodyFragment : Fragment() {
    private var mContext: Context? = null
    lateinit var companyEdit: EditText
    lateinit var companyLinearLayout: LinearLayout
    lateinit var positionEdit: EditText
    lateinit var positionLinearLayout: LinearLayout
    lateinit var startEdit: EditText
    lateinit var startLinearLayout: LinearLayout
    lateinit var endEdit: EditText
    lateinit var endLinearLayout: LinearLayout
    lateinit var descriptionEdit: EditText
    lateinit var mSwitch: Switch
    lateinit var tool: BaseTool
    var myAttributes = mapOf<String, Serializable>()
    var work = Work(myAttributes, "", false, "", "", "", "", "")
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    var resumeId = ""
    private lateinit var myDialog: MyDialog
    lateinit var intermediary: Intermediary

    companion object {
        fun newInstance(resumeId: String): PthreeMainBodyFragment {
            val fragment = PthreeMainBodyFragment()
            fragment.resumeId = resumeId
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val builder = MyDialog.Builder(activity!!)
            .setCancelable(false)
            .setCancelOutside(false)
        myDialog = builder.create()
        mContext = activity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()

        companyEdit.transformationMethod = HideReturnsTransformationMethod.getInstance()

        mSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked) {
                true -> companyEdit.transformationMethod = PasswordTransformationMethod.getInstance()
                false -> companyEdit.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
        })

        intermediary = activity as Intermediary

        return fragmentView
    }

    fun createView(): View {
        tool = BaseTool()
        var view = View.inflate(mContext, R.layout.radion_gender, null)
        return UI {
            verticalLayout {
                backgroundColorResource = R.color.whiteFF
                orientation = LinearLayout.VERTICAL
                leftPadding = dip(15)
                rightPadding = dip(15)

                onClick {
                    closeKeyfocus()
                }

                textView {
                    textResource = R.string.PthreeIntroduction
                    textSize = 18f
                    gravity = Gravity.LEFT
                    textColorResource = R.color.black33
                }.lparams(width = matchParent, height = dip(25)) {
                    topMargin = dip(20)
                }


                companyLinearLayout = linearLayout {
                    backgroundResource = R.drawable.input_border
                    textView {
                        textResource = R.string.company
                        textColorResource = R.color.black33
                        textSize = 15f
                        gravity = Gravity.CENTER_VERTICAL
                    }.lparams(width = dip(110), height = matchParent) {
                    }
                    companyEdit = editText {
                        backgroundColorResource = R.color.whiteFF
                        singleLine = true
                        hintResource = R.string.companyHint
                        hintTextColor = Color.parseColor("#B3B3B3")
                        textSize = 15f
                    }.lparams(width = matchParent, height = wrapContent) {
                        weight = 1f
                    }
                }.lparams(width = matchParent, height = dip(44)) {
                    topMargin = dip(20)
                }

                linearLayout {
                    textView {
                        textResource = R.string.companySelector
                        textSize = 12f
                        textColorResource = R.color.gray5c
                        gravity = Gravity.LEFT
                    }.lparams(width = wrapContent, height = matchParent) {
                        weight = 1f
                    }

                    mSwitch = switch {
                        setThumbResource(R.drawable.shape_switch_thumb)
                        setTrackResource(R.drawable.shape_switch_track_selector)
                    }.lparams(width = dip(26), height = dip(16)) {
                        weight = 1f
                        gravity = Gravity.RIGHT
                    }
                }.lparams(width = matchParent, height = dip(17)) {
                    topMargin = dip(8)
                }


                positionLinearLayout = linearLayout {
                    backgroundResource = R.drawable.input_border
                    textView {
                        textResource = R.string.position
                        textColorResource = R.color.black33
                        textSize = 15f
                        gravity = Gravity.CENTER_VERTICAL
                    }.lparams(width = dip(110), height = matchParent) {
                    }
                    positionEdit = editText {
                        backgroundColorResource = R.color.whiteFF
                        singleLine = true
                        hintResource = R.string.positionHint
                        hintTextColor = Color.parseColor("#B3B3B3")
                        textSize = 15f
                    }.lparams(width = matchParent, height = wrapContent) {
                        weight = 1f
                    }
                }.lparams(width = matchParent, height = dip(44)) {
                    topMargin = dip(17)
                }


                textView {
                    textResource = R.string.workTime
                    textColorResource = R.color.black33
                    textSize = 15f
                    gravity = Gravity.LEFT
                }.lparams(width = matchParent, height = wrapContent) {
                    topMargin = dip(16)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    startLinearLayout = linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        startEdit = editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            isFocusableInTouchMode = false
                            hintResource = R.string.startTime
                            hintTextColor = Color.parseColor("#5C5C5C")
                            rightPadding = dip(10)
                            textSize = 15f
                            onClick { intermediary.twoOnClick("start") }
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = matchParent, height = dip(11))
                        }.lparams(width = dip(6), height = matchParent)
                    }.lparams(width = matchParent, height = dip(44)) {
                        weight = 1f
                        rightMargin = dip(8)

                    }


                    endLinearLayout = linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        endEdit = editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            isFocusableInTouchMode = false
                            hintResource = R.string.endTime
                            hintTextColor = Color.parseColor("#5C5C5C")
                            rightPadding = dip(10)
                            textSize = 15f
                            onClick { intermediary.twoOnClick("end") }
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.register_select_nor
                            }.lparams(width = matchParent, height = dip(15))
                        }.lparams(width = dip(8), height = matchParent)
                    }.lparams(width = matchParent, height = matchParent) {
                        weight = 1f
                        leftMargin = dip(8)
                    }
                }.lparams(width = matchParent, height = dip(44)) {
                    topMargin = dip(16)
                }

                textView {
                    textResource = R.string.work
                    textSize = 15f
                    textColorResource = R.color.black33
                    gravity = Gravity.LEFT
                }.lparams(width = matchParent, height = dip(21)) {
                    topMargin = dip(17)
                }

                descriptionEdit = editText {
                    hintResource = R.string.workHint
                    textSize = 15f
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
                    setOnClickListener { submit() }
                }.lparams(width = matchParent, height = dip(47)) {
                    topMargin = dip(20)
                }

            }
        }.view
    }

    @SuppressLint("CheckResult")
    private fun submit() {
        myDialog.show()
        var companyName = tool.getEditText(companyEdit)
        var positionName = tool.getEditText(positionEdit)
        var start = tool.getEditText(startEdit)
        var startDate = ""
        var end = tool.getEditText(endEdit)
        var endDate = ""
        var myDescription = tool.getEditText(descriptionEdit)

        if (companyName == "") {
            companyLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            companyLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

        if (positionName == "") {
            positionLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            positionLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

        if (start == "") {
            startLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            startLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            startDate = stringToLong(start, "yyyy-MM").toString()
        }

        if (end == "") {
            endLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            endLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            endDate = stringToLong(end, "yyyy-MM").toString()
        }

        if (startDate != "" && endDate != "") {
            if (endDate.toLong() <= startDate.toLong()) {
                endLinearLayout.backgroundResource = R.drawable.edit_text_empty
                startLinearLayout.backgroundResource = R.drawable.edit_text_empty
            }
        }


        if (myDescription == "") {
            descriptionEdit.backgroundResource = R.drawable.edit_text_empty
        } else {
            descriptionEdit.backgroundResource = R.drawable.edit_text_no_empty
        }


        if (companyName != "" && positionName != "" && start != "" && end != "" && myDescription != ""
            && endDate.toLong() > startDate.toLong()
        ) {

            work.endDate = endDate
            work.hideOrganization = mSwitch.isChecked
            work.organizationName = companyName
            work.position = positionName
            work.responsibility = myDescription
            work.startDate = startDate

            // 查询公司id
            var schoolRetrofitUils = RetrofitUtils(mContext!!, "http://org.sk.cgland.top/")
            schoolRetrofitUils.create(RegisterApi::class.java)
                .getCompanyId(companyName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    var companyId = it.get("organizationId").toString()
                    work.organizationId = companyId
                }, {
                    println("该学校系统未录入")
                })

            val workParams = mutableMapOf(
                "attributes" to work.attributes,
                "endDate" to work.endDate,
                "hideOrganization" to work.hideOrganization,
                "organizationName" to work.organizationName,
                "position" to work.position,
                "responsibility" to work.responsibility,
                "startDate" to work.startDate
            )


            val workJson = JSON.toJSONString(workParams)

            val workBody = RequestBody.create(json, workJson)

            var workingRetrofitUils = RetrofitUtils(mContext!!, "https://job.sk.cgland.top/")

            workingRetrofitUils.create(RegisterApi::class.java)
                .createWorkHistory(workBody, resumeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    if (it.code() in 200..299) {
                        toast(this.getString(R.string.pthWorkSuccess))

                        var intent = Intent(activity, PersonInformationFourActivity::class.java)
                        var bundle = Bundle()
                        bundle.putString("resumeId", resumeId)
                        intent.putExtra("bundle", bundle)
                        startActivity(intent)
                        activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                        myDialog.dismiss()
                    } else {
                        toast(this.getString(R.string.pthWorkFail))
                        myDialog.dismiss()
                    }
                }, {
                    myDialog.dismiss()
                })
        } else {
            myDialog.dismiss()
        }
    }


    // 类型转换
    private fun stringToLong(str: String, format: String): Long {
        val date = SimpleDateFormat(format).parse(str)
        return date.time
    }

    private fun closeKeyfocus() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)

        companyEdit.clearFocus()
        positionEdit.clearFocus()
        descriptionEdit.clearFocus()
    }

    interface Intermediary {
        fun twoOnClick(condition: String)
    }

    fun setStart(result: String) {
        startEdit.setText(result.trim())
    }

    fun setEnd(result: String) {
        endEdit.setText(result.trim())
    }


}

