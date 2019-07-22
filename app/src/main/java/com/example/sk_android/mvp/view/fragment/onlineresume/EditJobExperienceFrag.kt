package com.example.sk_android.mvp.view.fragment.onlineresume

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.model.onlineresume.jobexperience.CompanyModel
import com.example.sk_android.mvp.model.onlineresume.jobexperience.JobExperienceModel
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.toast
import java.text.SimpleDateFormat
import java.util.*
import android.support.v4.content.ContextCompat.getSystemService
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import click
import withTrigger


class EditJobExperienceFrag : Fragment() {
    interface EditJob {
        fun startDate()
        fun endDate()
        fun addText(s: CharSequence?)
        fun addJobType()
    }

    lateinit var mContext: Context
    lateinit var editJob: EditJob
    lateinit var uri: String
    var addJobEx: JobExperienceModel? = null
    var comList: MutableList<CompanyModel>? = null
    var companyId: UUID? = null //公司ID,如果查询到公司,才有

    lateinit var companyName: EditText //公司名字
    lateinit var jobType: TextView //职位类型
    lateinit var jobName: EditText //职位名字
    lateinit var department: EditText //所属部门
    lateinit var startDate: TextView //开始日期
    lateinit var endDate: TextView //结束日期
    lateinit var primaryJob: EditText //主要工作
    lateinit var isShowCompanyName: Switch //隐藏公司名

    companion object {
        fun newInstance(context: Context): EditJobExperienceFrag {
            val fragment = EditJobExperienceFrag()
            fragment.mContext = context
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        editJob = activity as EditJob
        var fragmentView = createView()

        return fragmentView
    }

    fun setJobExperience(obj: JobExperienceModel) {
        companyName.text = SpannableStringBuilder(obj.organizationName)
        if (obj.attributes.jobType != null && obj.attributes.jobType != "")
            jobType.text = SpannableStringBuilder(obj.attributes.jobType)
        jobName.text = SpannableStringBuilder(obj.position)
        if (obj.attributes.department != null && obj.attributes.department != "")
            department.text = SpannableStringBuilder(obj.attributes.department)
        startDate.text = longToString(obj.startDate)
        endDate.text = longToString(obj.endDate)
        if (obj.responsibility != "")
            primaryJob.text = SpannableStringBuilder(obj.responsibility)
        isShowCompanyName.isChecked = obj.hideOrganization
    }

    fun getJobExperience(): Map<String, Any?>? {

        //验证非空 (所属部门可空)
        if (companyName.text.equals("")) {
            val toast = Toast.makeText(activity!!.applicationContext, "公司名字为空", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            return null
        }
        if (jobType.text.equals("")) {
            val toast = Toast.makeText(activity!!.applicationContext, "职位类型为空", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            return null
        }
        if (jobName.text.equals("")) {
            val toast = Toast.makeText(activity!!.applicationContext, "职位名字为空", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            return null
        }
        if (primaryJob.text.equals("")) {
            val toast = Toast.makeText(activity!!.applicationContext, "主要工作内容为空", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            return null
        }
        //验证公司名字字符长度 5-30
        val cLength = companyName.text.length
        if (cLength !in 5..30) {
            val toast = Toast.makeText(activity!!.applicationContext, "公司名字长度应为5-30", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            return null
        }

        //验证职位名字字符长度 2-30
        val jLength = jobName.text.length
        if (jLength !in 2..30) {
            val toast = Toast.makeText(activity!!.applicationContext, "职位名字长度应为2-30", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            return null
        }

        //验证所属部门字符长度 2-30
        val dLength = department.text.length
        if (dLength !in 2..30) {
            if (dLength !in 2..30) {
                val toast = Toast.makeText(activity!!.applicationContext, "所属部门长度应为2-30", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()
                return null
            }
        }

        // 验证开始日期大于结束日期
        if(startDate.text.toString()!="" && endDate.text.toString()!=""){
            val start = stringToLong(startDate.text.toString().trim())
            val end = stringToLong(endDate.text.toString().trim())
            if (end < start) {
                val toast = Toast.makeText(activity!!.applicationContext, "終了時間は開始時間より遅く設定してください", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()
                return null
            }
        }else{
            val toast = Toast.makeText(activity!!.applicationContext, "开始日期或结束日期未填写", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            return null
        }

        // 验证主要工作内容不超过2000字
        val pLength = primaryJob.text.length
        if (pLength !in 2..2000) {
            val toast = Toast.makeText(activity!!.applicationContext, "主要工作内容长度应为2-2000", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            return null
        }


        return mapOf(
            "attributes" to mapOf(
                "department" to department.text.toString().trim(),
                "jobType" to jobType.text.toString().trim()
            ),
            "endDate" to stringToLong(endDate.text.toString().trim()).toString(),
            "hideOrganization" to isShowCompanyName.isChecked,
            if (companyId != null) "organizationId" to companyId else
                "organizationName" to companyName.text.toString().trim(),
            "position" to jobName.text.toString().trim(),
            "responsibility" to primaryJob.text.toString().trim(),
            "startDate" to stringToLong(startDate.text.toString().trim()).toString()
        )
    }

    fun setStartDate(date: String) {
        startDate.text = date
    }

    fun setEndDate(date: String) {
        endDate.text = date
    }

    fun setJobType(job: String) {
        jobType.text = job
    }

    fun setCompany(companyList: MutableList<CompanyModel>) {
        comList = companyList
    }

    private fun createView(): View? {
        return UI {
            linearLayout {
                scrollView {
                    isVerticalScrollBarEnabled = false
                    overScrollMode = View.OVER_SCROLL_NEVER
                    verticalLayout {
                        // 会社名
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "会社名"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            companyName = editText {
                                background = null
                                padding = dip(1)
                                text = SpannableStringBuilder("")
                                textSize = 17f
                                textColor = Color.parseColor("#FF333333")
                                singleLine = true
                                addTextChangedListener(object : TextWatcher {
                                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                        editJob.addText(s)
                                    }

                                    override fun beforeTextChanged(
                                        s: CharSequence?,
                                        start: Int,
                                        count: Int,
                                        after: Int
                                    ) {

                                    }

                                    override fun afterTextChanged(s: Editable?) {

                                    }

                                })
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(45)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(85)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        // 職種
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "職種"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                jobType = textView {
                                    text = ""
                                    textSize = 17f
                                    textColor = Color.parseColor("#FF333333")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(15)
                                    centerVertically()
                                }
                                imageView {
                                    imageResource = R.mipmap.icon_go_position
                                    this.withTrigger().click {
                                        closeKeyfocus()
                                        editJob.addJobType()
                                    }
                                }.lparams {
                                    width = dip(6)
                                    height = dip(11)
                                    alignParentRight()
                                    centerVertically()
                                }
                                this.withTrigger().click {
                                    editJob.addJobType()
                                }
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                                topMargin = dip(25)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        // 職名
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "役職"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            jobName = editText {
                                background = null
                                padding = dip(1)
                                text = SpannableStringBuilder("")
                                textSize = 17f
                                textColor = Color.parseColor("#FF333333")
                                singleLine = true
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(45)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        // 属する部門
                        relativeLayout {
                            textView {
                                text = "所属部門"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            department = editText {
                                background = null
                                padding = dip(1)
                                text = SpannableStringBuilder("")
                                textSize = 17f
                                textColor = Color.parseColor("#FF333333")
                                singleLine = true
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(45)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        view {
                            backgroundColor = Color.parseColor("#FFF6F6F6")
                        }.lparams {
                            width = matchParent
                            height = dip(8)
                        }
                        // 開始時間
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "開始時間"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                startDate = textView {
                                    text = ""
                                    textSize = 17f
                                    textColor = Color.parseColor("#FF333333")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(15)
                                    centerVertically()
                                }
                                imageView {
                                    imageResource = R.mipmap.icon_go_position
                                    this.withTrigger().click {
                                        closeKeyfocus()
                                        editJob.startDate()
                                    }
                                }.lparams {
                                    width = dip(6)
                                    height = dip(11)
                                    alignParentRight()
                                    centerVertically()
                                }
                                this.withTrigger().click {
                                    closeKeyfocus()
                                    editJob.startDate()
                                }
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                                topMargin = dip(25)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        // 終了時間
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "終了時間"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                endDate = textView {
                                    text = ""
                                    textSize = 17f
                                    textColor = Color.parseColor("#FF333333")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(15)
                                    centerVertically()
                                }
                                imageView {
                                    imageResource = R.mipmap.icon_go_position
                                    this.withTrigger().click {
                                        closeKeyfocus()
                                        editJob.endDate()
                                    }
                                }.lparams {
                                    width = dip(6)
                                    height = dip(11)
                                    alignParentRight()
                                    centerVertically()
                                }
                                this.withTrigger().click {
                                    closeKeyfocus()
                                    editJob.endDate()
                                }
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                                topMargin = dip(25)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        // 主要役職
                        relativeLayout {
                            textView {
                                text = "業務内容"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            val edit = 11
                            primaryJob = editText {
                                id = edit
                                backgroundResource = R.drawable.area_text
                                gravity = top
                                padding = dip(10)
                                setOnTouchListener(object: View.OnTouchListener{
                                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                                        if(event!!.action == MotionEvent.ACTION_DOWN
                                            || event!!.action == MotionEvent.ACTION_MOVE){
                                            //按下或滑动时请求父节点不拦截子节点
                                            v!!.parent.parent.parent.requestDisallowInterceptTouchEvent(true);
                                        }
                                        if(event!!.action == MotionEvent.ACTION_UP){
                                            //抬起时请求父节点拦截子节点
                                            v!!.parent.parent.parent.requestDisallowInterceptTouchEvent(false);
                                        }
                                        return false
                                    }
                                })
                            }.lparams {
                                width = matchParent
                                height = dip(170)
                                topMargin = dip(45)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(220)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }

                        //滑动框1
                        relativeLayout {
                            textView {
                                text = "会社名非表示"
                                textSize = 17f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                                centerVertically()
                            }
                            isShowCompanyName = switch {
                                setThumbResource(R.drawable.thumb)
                                setTrackResource(R.drawable.track)
                                isChecked = true
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                alignParentRight()
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(55)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        this.withTrigger().click {
                           closeKeyfocus()
                       }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                    setOnScrollChangeListener(object: View.OnScrollChangeListener{
                        override fun onScrollChange(
                            v: View?,
                            scrollX: Int,
                            scrollY: Int,
                            oldScrollX: Int,
                            oldScrollY: Int
                        ) {
                            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
                        }

                    })
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }.view
    }


    // 类型转换
    private fun longToString(long: Long): String {
        return SimpleDateFormat("yyyy-MM-dd").format(Date(long))
    }

    // 类型转换
    private fun stringToLong(str: String): Long {
        val date = SimpleDateFormat("yyyy-MM-dd").parse(str)
        return date.time
    }

    private fun closeKeyfocus(){
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)

        companyName.clearFocus()
        jobName.clearFocus()
        department.clearFocus()
        primaryJob.clearFocus()
    }
}