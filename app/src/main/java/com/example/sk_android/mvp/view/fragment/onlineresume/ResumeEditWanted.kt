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
import click
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.model.jobselect.UserJobIntention
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduExperienceModel
import com.example.sk_android.mvp.view.fragment.person.PsActionBarFragment
import com.example.sk_android.utils.BaseTool
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import withTrigger
import java.util.ArrayList

class ResumeEditWanted : Fragment() {

    interface WantedFrag {
        fun wantedClick(id: UserJobIntention)
        fun addWanted()
    }

    private lateinit var want: WantedFrag
    private lateinit var jobName: TextView
    private lateinit var areaText: TextView


    companion object {
        var mList: MutableList<UserJobIntention> = mutableListOf()
        var jobList: MutableList<List<String>> = mutableListOf()
        var areaList: MutableList<List<String>> = mutableListOf()
        var myResult: ArrayList<UserJobIntention> = arrayListOf()
        fun newInstance(
            list: MutableList<UserJobIntention>?,
            jobName: MutableList<List<String>>?,
            areaName: MutableList<List<String>>?
        ): ResumeEditWanted {
            val frag = ResumeEditWanted()
//            frag.mList = list
//            frag.jobList = jobName
//            frag.areaList = areaName
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        want = activity as WantedFrag
        return creatV()
    }

    private fun creatV(): View {
        initView(1)
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
                        if (mList != null ) {
                            val size = (mList?.size ?: 1) - 1
                            for (index in 0..size) {
                                relativeLayout {
                                    linearLayout {
                                        orientation = LinearLayout.HORIZONTAL
                                        jobName = textView {
                                            if(index < jobList?.size?:0)
                                                text = jobList?.get(index)?.get(0)
                                            textSize = 14f
                                            textColor = Color.parseColor("#FF202020")
                                        }.lparams {
                                            width = wrapContent
                                            height = wrapContent
                                        }
                                        textView {
                                            val item = mList?.get(index)

                                            when (item?.salaryType) {
                                                "HOURLY" -> text =
                                                    isK(item.salaryHourlyMin, item.salaryHourlyMax)
                                                "DAILY" -> text =
                                                    isK(item.salaryDailyMin, item.salaryDailyMax)
                                                "MONTHLY" -> text = isK(
                                                    item.salaryMonthlyMin,
                                                    item.salaryMonthlyMax
                                                )
                                                "YEARLY" -> text =
                                                    isK(item.salaryYearlyMin, item.salaryYearlyMax)

                                            }
                                            textSize = 14f
                                            textColor = Color.parseColor("#FF202020")
                                        }.lparams {
                                            width = wrapContent
                                            height = wrapContent
                                            leftMargin = dip(20)
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
                                            if(index < areaList?.size?:0){
                                                var str = ""
                                                for (item in areaList?.get(index) ?: listOf()) {
                                                    str += " $item "
                                                }
                                                text = str
                                            }
                                            textSize = 10f
                                            textColor = Color.parseColor("#FF999999")
                                        }.lparams(wrapContent, wrapContent)
                                        if (!jobList.isNullOrEmpty()) {
                                            textView {
                                                if(index < jobList?.size?:0){
                                                    text = if(jobList?.get(index)?.size ?: 0 > 1){
                                                        jobList?.get(index)?.get(1)
                                                    }else{
                                                        ""
                                                    }
                                                }
                                                textSize = 10f
                                                textColor = Color.parseColor("#FF999999")
                                            }.lparams(wrapContent, wrapContent)
                                        }
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        topMargin = dip(20)
                                        alignParentLeft()
                                    }

                                    imageView {
                                        imageResource = R.mipmap.icon_go_position
                                        onClick {
                                            val obj = mList?.get(index) ?: return@onClick
                                            want.wantedClick(obj)
                                        }
                                    }.lparams {
                                        width = dip(6)
                                        height = dip(11)
                                        alignParentRight()
                                        centerVertically()
                                    }
                                    this.withTrigger().click {
                                        val obj = mList?.get(index) ?: return@click
                                        want.wantedClick(obj)
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                    bottomMargin = dip(20)
                                }
                            }
                            relativeLayout {
                                backgroundResource = R.drawable.text_view_bottom_border
                                relativeLayout {
                                    backgroundResource = R.drawable.four_radius_grey_button
                                    textView {
                                        text = "就職希望を追加する"
                                        textSize = 16f
                                        textColor = Color.parseColor("#FF202020")
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        centerInParent()
                                    }
                                    this.withTrigger().click {
                                        want.addWanted()
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
    fun initView(from: Int) {
        if(from == 1){
            val application: App? = App.getInstance()
            application?.setResumeEditWanted(this)
        }
        if ((PsActionBarFragment.myResult.size == 0)) {
            //第一次进入
        } else {
//            var mList: MutableList<UserJobIntention>? = null
//            var jobList: MutableList<List<String>>? = null
//            var areaList: MutableList<List<String>>? = null

            for (item in myResult){
                val jobArray = item.industryName[0].split("-")
                val jlist = mutableListOf<String>()
                val aList = mutableListOf<String>()
                if(jobArray.isNotEmpty()){
                    jlist.add(jobArray[1])
                    jlist.add(jobArray[0])
                }
                jobList.add(jlist)

                aList.add(item.areaName[0])
                areaList.add(aList)
            }

            mList =  myResult
        }
    }
    private fun isK(minSalary: Int, maxSalary: Int): String {
        return "${BaseTool().moneyToString(minSalary.toString())["result"]}円 - ${BaseTool().moneyToString(maxSalary.toString())["result"]}円"
    }

}