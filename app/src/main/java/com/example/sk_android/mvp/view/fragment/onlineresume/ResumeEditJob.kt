package com.example.sk_android.mvp.view.fragment.onlineresume

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.model.onlineresume.jobexperience.JobExperienceModel
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import java.text.SimpleDateFormat
import java.util.*

class ResumeEditJob : Fragment() {

    interface JobFrag {
        fun JobClick(jobId: String)
        fun addJobClick()
    }

    private var mList: MutableList<JobExperienceModel>? = null
    private lateinit var jobFrag: JobFrag

    companion object {
        fun newInstance(list: MutableList<JobExperienceModel>?): ResumeEditJob {
            val frag = ResumeEditJob()
            frag.mList = list
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        jobFrag = activity as JobFrag
        return creatV()
    }

    fun creatV(): View {
        return UI {
            verticalLayout {
                //就職経験
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    verticalLayout {
                        relativeLayout {
                            textView {
                                text = "就職経験"
                                textSize = 16f
                                textColor = Color.parseColor("#FF202020")
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                centerVertically()
                                alignParentLeft()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(60)
                        }

                        if (mList != null) {
                            for (item in mList!!) {
                                relativeLayout {
                                    linearLayout {
                                        orientation = LinearLayout.VERTICAL
                                        relativeLayout {
                                            relativeLayout {
                                                textView {
                                                    text = item.organizationName
                                                    textSize = 14f
                                                    textColor = Color.parseColor("#FF202020")
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                }
                                                textView {
                                                    text = "${longToString(item.startDate)} - ${longToString(item.endDate)}"
                                                    textSize = 12f
                                                    textColor = Color.parseColor("#FF999999")
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                    alignParentRight()
                                                    rightMargin = dip(20)
                                                }
                                                toolbar {
                                                    navigationIconResource = R.mipmap.icon_go_position
                                                    onClick {
                                                        jobFrag.JobClick(item.id.toString())
                                                    }
                                                }.lparams {
                                                    width = dip(22)
                                                    height = dip(22)
                                                    alignParentRight()
                                                }
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                alignParentLeft()
                                                topMargin = dip(20)
                                            }
                                            textView {
                                                text = item.position
                                                textSize = 10f
                                                textColor = Color.parseColor("#FF999999")
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                topMargin = dip(40)
                                                alignParentLeft()
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = wrapContent
                                        }
                                        linearLayout {
                                            orientation = LinearLayout.VERTICAL
                                            textView {
                                                text = item.responsibility
                                                textSize = 12f
                                                textColor = Color.parseColor("#FF333333")
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = wrapContent
                                            topMargin = dip(10)
                                        }
                                    }.lparams {
                                        width = matchParent
                                        height = matchParent
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                }
                            }
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            relativeLayout {
                                backgroundResource = R.drawable.four_radius_grey_button
                                textView {
                                    text = "就職経験を追加する"
                                    textSize = 16f
                                    textColor = Color.parseColor("#FF202020")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    centerInParent()
                                }
                                onClick {
                                    jobFrag.addJobClick()
                                }
                            }.lparams {
                                width = matchParent
                                height = dip(50)
                                centerInParent()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
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
    }


    // 类型转换
    private fun longToString(long: Long): String {
        val str = SimpleDateFormat("yyyy/MM/dd").format(Date(long))
        return str
    }
}