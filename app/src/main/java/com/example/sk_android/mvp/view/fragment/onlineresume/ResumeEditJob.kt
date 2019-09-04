package com.example.sk_android.mvp.view.fragment.onlineresume

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import click
import com.example.sk_android.R
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.model.onlineresume.jobexperience.JobExperienceModel
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import withTrigger
import java.text.SimpleDateFormat
import java.util.*

class ResumeEditJob : Fragment() {

    interface JobFrag {
        fun jobClick(jobId: String)
        fun addJobClick()
    }

    private lateinit var jobFrag: JobFrag
    private lateinit var linea: LinearLayout

    companion object {
        var myResult: ArrayList<JobExperienceModel> = arrayListOf()
        fun newInstance(): ResumeEditJob {
            return ResumeEditJob()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        jobFrag = activity as JobFrag
        return creatV()
    }

    @SuppressLint("SetTextI18n", "RtlHardcoded")
    fun creatV(): View {
        val view = UI {
            verticalLayout {
                //就職経験
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    verticalLayout {
                        relativeLayout {
                            textView {
                                text = "職務経歴"
                                textSize = 16f
                                textColor = Color.parseColor("#FF202020")
                                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                centerVertically()
                                alignParentLeft()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(50)
                        }


                        linea = linearLayout {
                            orientation = LinearLayout.VERTICAL
                        }.lparams(matchParent, wrapContent){

                        }
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            relativeLayout {
                                backgroundResource = R.drawable.four_radius_grey_button
                                textView {
                                    text = "職務経歴を追加する"
                                    textSize = 16f
                                    textColor = Color.parseColor("#FF202020")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    centerInParent()
                                }
                                this.withTrigger().click {
                                    jobFrag.addJobClick()
                                }
                            }.lparams {
                                width = matchParent
                                height = dip(50)
                                gravity = Gravity.TOP
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(65)
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                }
            }
        }.view
        initView(1)
        return view
    }

    @SuppressLint("SetTextI18n", "RtlHardcoded")
    fun initView(from: Int) {
        if(from == 1){
            val application: App? = App.getInstance()
            application?.setResumeEditJob(this)
        }
        if ((myResult.size == 0)) {
            //第一次进入
        } else {
            linea.removeAllViews()
            for(item in myResult){
                val childView = UI {
                    linearLayout {
                        relativeLayout {
                            linearLayout {
                                orientation = LinearLayout.VERTICAL
                                relativeLayout {
                                    linearLayout {
                                        orientation = LinearLayout.HORIZONTAL
                                        textView {
                                            text = item.organizationName
                                            ellipsize = TextUtils.TruncateAt.END
                                            maxLines = 1
                                            textSize = 14f
                                            textColor = Color.parseColor("#FF202020")
                                        }.lparams {
                                            width = 0
                                            weight = 1f
                                            height = wrapContent
                                        }
                                        textView {
                                            text =
                                                "${longToString(item.startDate!!)} - ${longToString(item.endDate!!)}"
                                            textSize = 12f
                                            textColor = Color.parseColor("#FF999999")
                                        }.lparams {
                                            width = 0
                                            weight = 1f
                                            height = wrapContent
                                            gravity = Gravity.RIGHT
                                            leftMargin = dip(20)
                                            rightMargin = dip(20)
                                        }
                                    }.lparams {
                                        width = matchParent
                                        height = wrapContent
                                        alignParentLeft()
                                    }
                                    textView {
                                        text = item.position
                                        textSize = 10f
                                        textColor = Color.parseColor("#FF999999")
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        topMargin = dip(20)
                                        alignParentLeft()
                                    }

                                    imageView {
                                        imageResource = R.mipmap.icon_go_position
                                        this.withTrigger().click {
                                            jobFrag.jobClick(item.id.toString())
                                        }
                                    }.lparams {
                                        width = dip(6)
                                        height = dip(11)
                                        alignParentRight()
                                        centerVertically()
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                }
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    textView {
                                        text = item.responsibility
                                        ellipsize = TextUtils.TruncateAt.END
                                        maxLines = 3
                                        textSize = 12f
                                        textColor = Color.parseColor("#FF333333")
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                    topMargin = dip(10)
                                }
                                this.withTrigger().click {
                                    jobFrag.jobClick(item.id.toString())
                                }
                            }.lparams {
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            bottomMargin = dip(20)
                        }
                    }
                }.view
                linea.addView(childView)
            }
        }
    }

    // 类型转换
    @SuppressLint("SimpleDateFormat")
    private fun longToString(long: Long): String {
        return SimpleDateFormat("yyyy/MM/dd").format(Date(long))
    }

    override fun onDestroy() {
        super.onDestroy()

        val application: App? = App.getInstance()
        application!!.setResumeEditJob(null)
    }
}