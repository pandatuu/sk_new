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
import android.widget.Toast
import click
import com.example.sk_android.R
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.model.jobselect.UserJobIntention
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
    private lateinit var linea: LinearLayout
    var index = 0
    var isAdd = false
    var isDel = false


    companion object {
        var jobList: MutableList<List<String>> = mutableListOf()
        var areaList: MutableList<List<String>> = mutableListOf()
        var myResult: ArrayList<UserJobIntention> = arrayListOf()
        fun newInstance(): ResumeEditWanted {
            return ResumeEditWanted()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        want = activity as WantedFrag
        return creatV()
    }

    fun addIndex(){
        isAdd = true
    }
    fun delIndex(){
        isDel = true
    }

    override fun onResume() {
        super.onResume()
        if(isAdd){
            println("before index++++$index")
            index += 1
        }
        if(isDel){
            println("before index++++$index")
            index -= 1
        }
    }
    private fun creatV(): View {
        val view = UI {
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
                        linea = linearLayout {
                            orientation = LinearLayout.VERTICAL
                        }.lparams(matchParent, wrapContent)
                                                
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
                                    println("after index++++$index")
                                    if(index<3) {
                                        want.addWanted()
                                    }else{
                                        val toast = Toast.makeText(context, "自作の履歴書は最大３つまでです。古い履歴書を削除して下さい。", Toast.LENGTH_SHORT)//网路出现问题
                                        toast.setGravity(Gravity.CENTER, 0, 0)
                                        toast.show()
                                    }
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
    fun initView(from: Int) {
        if(from == 1){
            val application: App? = App.getInstance()
            application?.setResumeEditWanted(this)
        }
        if (myResult.size == 0) {
            //第一次进入
        } else {
            linea.removeAllViews()
            jobList = mutableListOf()
            areaList = mutableListOf()
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
            println("===========want=============")
            println(myResult)
            println(jobList)
            println(areaList)
            println(from)
            println("===========want=============")
            for (index in 0 until myResult.size) {
                val childView = UI { 
                    linearLayout {
                        relativeLayout {
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                jobName = textView {
                                    if(index < jobList.size)
                                        text = jobList[index][0]
                                    textSize = 14f
                                    textColor = Color.parseColor("#FF202020")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                }
                                textView {
                                    val item = myResult[index]

                                    when (item.salaryType) {
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
                                    if(index < areaList.size){
                                        var str = ""
                                        for (item in areaList[index]) {
                                            str += " $item "
                                        }
                                        text = str
                                    }
                                    textSize = 10f
                                    textColor = Color.parseColor("#FF999999")
                                }.lparams(wrapContent, wrapContent)
                                if (!jobList.isNullOrEmpty()) {
                                    textView {
                                        if(index < jobList.size){
                                            text = if(jobList[index].size > 1){
                                                jobList[index][1]
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
                                    val obj = myResult[index]
                                    want.wantedClick(obj)
                                }
                            }.lparams {
                                width = dip(6)
                                height = dip(11)
                                alignParentRight()
                                centerVertically()
                            }
                            this.withTrigger().click {
                                val obj = myResult[index]
                                want.wantedClick(obj)
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
            index = linea.childCount
        }
    }
    private fun isK(minSalary: Int, maxSalary: Int): String {
        return "${BaseTool().moneyToString(minSalary.toString())["result"]}円 - ${BaseTool().moneyToString(maxSalary.toString())["result"]}円"
    }
    override fun onDestroy() {
        super.onDestroy()

        val application: App? = App.getInstance()
        application!!.setResumeEditWanted(null)
    }
}