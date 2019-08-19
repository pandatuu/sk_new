package com.example.sk_android.mvp.view.fragment.onlineresume

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import click
import com.example.sk_android.R
import com.example.sk_android.custom.layout.floatOnKeyboardLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import withTrigger
import java.text.SimpleDateFormat
import java.util.*


class AddProjectExperienceFrag : Fragment() {

    interface AddProject {
        fun startDate()
        fun endDate()
    }

    lateinit var mContext: Context
    private lateinit var addproject: AddProject

    private lateinit var projectName: EditText //项目名字
    private lateinit var position: EditText //项目中的职位
    private lateinit var startDate: TextView //开始日期
    private lateinit var endDate: TextView //结束日期
    private lateinit var projectUrl: EditText //项目链接
    private lateinit var primaryJob: EditText //项目介绍

    companion object {
        fun newInstance(context: Context): AddProjectExperienceFrag {
            val fragment = AddProjectExperienceFrag()
            fragment.mContext = context
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        addproject = activity as AddProject
        return createView()
    }

    fun getProjectExperience(): Map<String, Any>? {

        //验证项目名字非空 (项目链接可空)
        if (projectName.text.isNullOrBlank()) {
            val toast = Toast.makeText(activity!!.applicationContext, "プロジェクト名を入力してください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }
        //验证项目名字字符长度 2-30
        val nLength = projectName.text.length
        if (nLength !in 2..30) {
            val toast = Toast.makeText(activity!!.applicationContext, "プロジェクト名を2～30文字以内でご記入ください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }
        //验证项目中的职位非空
        if (position.text.isNullOrBlank()) {
            val toast = Toast.makeText(activity!!.applicationContext, "役職を入力してください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }
        //验证项目中的职位字符长度 2-30
        val pLength = position.text.length
        if (pLength !in 2..30) {
            val toast = Toast.makeText(activity!!.applicationContext, "担当プロジェクトの役職を2～30文字以内でご記入ください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }

        // 验证开始日期大于结束日期
        if (startDate.text.toString().isNotBlank() && endDate.text.toString().isNotBlank()) {
            val start = stringToLong(startDate.text.toString().trim())
            val end = stringToLong(endDate.text.toString().trim())
            if (end < start) {
                val toast = Toast.makeText(activity!!.applicationContext, "終了時間は開始時間より後に設定してください。", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                return null
            }
        } else {
            val toast = Toast.makeText(activity!!.applicationContext, "開始時間あるいは終了時間を選択してください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }

        //验证项目介绍内容非空
        if (primaryJob.text.isNullOrBlank()) {
            val toast = Toast.makeText(activity!!.applicationContext, "プロジェクト詳細を入力してください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }
        // 验证项目介绍内容不超过2000字
        val jLength = primaryJob.text.length
        if (jLength !in 2..2000) {
            val toast = Toast.makeText(activity!!.applicationContext, "プロジェクト詳細を2～2000文字以内でご記入ください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }


        return mapOf(
            "attributes" to mapOf(
                "projectUrl" to projectUrl.text.toString().trim()
            ),
            "endDate" to stringToLong(endDate.text.toString().trim()).toString(),
            "projectName" to projectName.text.toString().trim(),
            "position" to position.text.toString().trim(),
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

    private fun createView(): View? {
        return UI {
            floatOnKeyboardLayout {
                linearLayout {
                    scrollView {
                        isVerticalScrollBarEnabled = false
                        overScrollMode = View.OVER_SCROLL_NEVER
                        verticalLayout {
                            // プロジェクト名
                            relativeLayout {
                                backgroundResource = R.drawable.text_view_bottom_border
                                textView {
                                    text = "プロジェクト名"
                                    textSize = 14f
                                    textColor = Color.parseColor("#FF999999")
                                    singleLine = true
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(15)
                                }
                                projectName = editText {
                                    background = null
                                    padding = dip(1)
                                    textSize = 17f
                                    textColor = Color.parseColor("#FF333333")
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
                            // 担当役職
                            relativeLayout {
                                backgroundResource = R.drawable.text_view_bottom_border
                                textView {
                                    text = "担当役職"
                                    textSize = 14f
                                    textColor = Color.parseColor("#FF999999")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(15)
                                }
                                position = editText {
                                    background = null
                                    padding = dip(1)
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
                                height = dip(85)
                                leftMargin = dip(15)
                                rightMargin = dip(15)
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
                                            addproject.startDate()
                                        }
                                    }.lparams {
                                        width = dip(6)
                                        height = dip(11)
                                        alignParentRight()
                                        centerVertically()
                                    }
                                    this.withTrigger().click {
                                        closeKeyfocus()
                                        addproject.startDate()
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
                                            addproject.endDate()
                                        }
                                    }.lparams {
                                        width = dip(6)
                                        height = dip(11)
                                        alignParentRight()
                                        centerVertically()
                                    }
                                    this.withTrigger().click {
                                        closeKeyfocus()
                                        addproject.endDate()
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
                            // プロジェクトのリンク
                            relativeLayout {
                                backgroundResource = R.drawable.text_view_bottom_border
                                textView {
                                    text = "プロジェクトのリンク"
                                    textSize = 14f
                                    textColor = Color.parseColor("#FF999999")
                                    singleLine = true
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(15)
                                }
                                projectUrl = editText {
                                    background = null
                                    padding = dip(1)
                                    textSize = 17f
                                    textColor = Color.parseColor("#FF333333")
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
                            // プロジェクト紹介
                            relativeLayout {
                                textView {
                                    text = "プロジェクト詳細"
                                    textSize = 14f
                                    textColor = Color.parseColor("#FF999999")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(15)
                                }
                                primaryJob = editText {
                                    backgroundResource = R.drawable.area_text
                                    gravity = top
                                    padding = dip(10)
                                    setOnTouchListener(object : View.OnTouchListener {
                                        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                                            if (event!!.action == MotionEvent.ACTION_DOWN
                                                || event!!.action == MotionEvent.ACTION_MOVE
                                            ) {
                                                //按下或滑动时请求父节点不拦截子节点
                                                v!!.parent.parent.parent.requestDisallowInterceptTouchEvent(true);
                                            }
                                            if (event!!.action == MotionEvent.ACTION_UP) {
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
                            this.withTrigger().click {
                                closeKeyfocus()
                            }
                        }.lparams {
                            width = matchParent
                            height = matchParent
                        }
                        setOnScrollChangeListener(object : View.OnScrollChangeListener {
                            override fun onScrollChange(
                                v: View?,
                                scrollX: Int,
                                scrollY: Int,
                                oldScrollX: Int,
                                oldScrollY: Int
                            ) {
                                val imm =
                                    activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.hideSoftInputFromWindow(view!!.windowToken, 0)
                            }

                        })
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }

                setAboutPopupListener {
                    val focusedView = findFocus()
                    if (focusedView != null) {
                        setAnchor(focusedView)
                    }
                }
            }
        }.view
    }

    // 类型转换
    private fun longToString(long: Long): String {
        val str = SimpleDateFormat("yyyy-MM-dd").format(Date(long))
        return str
    }

    // 类型转换
    private fun stringToLong(str: String): Long {
        val date = SimpleDateFormat("yyyy-MM-dd").parse(str)
        return date.time
    }

    private fun closeKeyfocus() {
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
        projectName.clearFocus()
        position.clearFocus()
        projectUrl.clearFocus()
        primaryJob.clearFocus()
    }
}