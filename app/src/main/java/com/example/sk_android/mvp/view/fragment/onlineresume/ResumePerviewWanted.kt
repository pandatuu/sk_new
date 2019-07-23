package com.example.sk_android.mvp.view.fragment.onlineresume

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.mvp.model.onlineresume.jobWanted.JobWantedModel
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class ResumePerviewWanted : Fragment() {

    private lateinit var jobName: TextView
    private lateinit var areaText: TextView
    private var mList: MutableList<JobWantedModel>? = null
    private var jobList: MutableList<List<String>>? = null
    private var areaList: MutableList<List<String>>? = null

    companion object {
        fun newInstance(
            list: MutableList<JobWantedModel>?,
            jobName: MutableList<List<String>>?,
            areaName: MutableList<List<String>>?
        ): ResumePerviewWanted {
            val frag = ResumePerviewWanted()
            frag.mList = list
            frag.jobList = jobName
            frag.areaList = areaName
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return creatV()
    }

    private fun creatV(): View {
        return UI {
            verticalLayout {
                //希望の業種
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    verticalLayout {
                        relativeLayout {
                            textView {
                                text = "就職希望"
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
                            height = dip(50)
                        }
                        if (mList != null) {
                            for (index in mList!!.indices) {
                                relativeLayout {
                                    linearLayout {
                                        orientation = LinearLayout.HORIZONTAL
                                        jobName = textView {
                                            text = jobList!![index][0]
                                            textSize = 14f
                                            textColor = Color.parseColor("#FF202020")
                                        }.lparams {
                                            width = wrapContent
                                            height = wrapContent
                                        }
                                        textView {
                                            when (mList!![index].salaryType) {
                                                "HOURLY" -> text = isK(mList!![index].salaryHourlyMin, mList!![index].salaryHourlyMax)
                                                "DAILY" -> text = isK(mList!![index].salaryDailyMin, mList!![index].salaryDailyMax)
                                                "MONTHLY" -> text = isK(mList!![index].salaryMonthlyMin, mList!![index].salaryMonthlyMax)
                                                "YEARLY" -> text = isK(mList!![index].salaryYearlyMin, mList!![index].salaryYearlyMax)

                                            }
                                            textSize = 14f
                                            textColor = Color.parseColor("#FFFFB706")
                                        }.lparams {
                                            width = wrapContent
                                            height = wrapContent
                                            gravity = Gravity.RIGHT
                                            leftMargin = dip(10)
                                        }
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        alignParentLeft()
                                        alignParentTop()
                                    }
                                    linearLayout {
                                        orientation = LinearLayout.HORIZONTAL
                                        areaText = textView {
                                            var str = ""
                                            for (item in areaList!![index]){
                                                str += " $item "
                                            }
                                            text = str
                                            textSize = 10f
                                            textColor = Color.parseColor("#FF999999")
                                        }.lparams(wrapContent,wrapContent)
                                        if(jobList!=null && jobList!!.size>1){
                                            textView {
                                                text = jobList!![index][1]
                                                textSize = 10f
                                                textColor = Color.parseColor("#FF999999")
                                            }.lparams(wrapContent,wrapContent)
                                        }
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        topMargin = dip(20)
                                        alignParentLeft()
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                    bottomMargin = dip(20)
                                }
                            }
                        } else {
                            relativeLayout {
                                padding = dip(10)
                                val image = imageView {}.lparams(dip(50), dip(60)) { centerInParent() }
                                Glide.with(this@relativeLayout)
                                    .load(R.mipmap.turn_around)
                                    .into(image)
                            }.lparams {
                                width = matchParent
                                height = dip(60)
                                bottomMargin = dip(20)
                            }
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

    private fun isK(minSalary: Long,maxSalary: Long): String{
        if (minSalary / 1000000 > 0) {
            return "${minSalary / 1000000}台 - ${maxSalary / 1000000}台"
        } else {
            if (minSalary / 10000 > 0) {
                return "${minSalary / 10000}万 - ${maxSalary / 10000}万"
            } else {
                if (minSalary / 1000 > 0) {
                    return "${minSalary / 1000}k - ${maxSalary / 1000}k"
                } else {
                    return "${minSalary} - ${maxSalary}"
                }
            }
        }
    }

}