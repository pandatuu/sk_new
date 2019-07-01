package com.example.sk_android.mvp.view.adapter.person

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.airsaid.pickerviewlibrary.TimePickerView
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobselect.RecruitInfo
import com.example.sk_android.mvp.model.person.InterviewInfo
import com.pingerx.imagego.core.strategy.loadCircle
import com.pingerx.imagego.core.strategy.loadImage
import org.jetbrains.anko.*

class InterviewListAdapter(
    private val context: RecyclerView,
    private val datalist: MutableList<InterviewInfo>,
    private val dataType: String,
    private val listener: (InterviewInfo) -> Unit
) : RecyclerView.Adapter<InterviewListAdapter.ViewHolder>() {

    lateinit var text: TextView
    lateinit var topShow: LinearLayout


    //添加数据
    fun addRecruitInfoList(list: List<InterviewInfo>) {
        datalist.addAll(list)
        notifyDataSetChanged()
    }


    fun clearRecruitInfoList() {
        datalist.clear()
        notifyDataSetChanged()
    }



    override fun getItemViewType(position: Int): Int {

        var type = datalist.get(position).dataType
        return type


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        lateinit var companyName: TextView
        lateinit var interviewType: TextView
        lateinit var positionName: TextView
        lateinit var showSalaryMinToMax: TextView

        lateinit var  startflag: TextView
        lateinit var  startDateStr: TextView

        lateinit var notifyText: TextView


        lateinit var companyLogo: ImageView


        var view = with(parent.context) {


            relativeLayout {
                verticalLayout {
                    backgroundColor = Color.WHITE

                    verticalLayout {

                        linearLayout {

                            startflag= textView {
                                gravity = Gravity.CENTER_VERTICAL
                                text = ""
                                textSize = 12f
                                textColorResource = R.color.black20

                            }.lparams {
                                height = matchParent
                                width = wrapContent
                            }

                            startDateStr=  textView {
                                gravity = Gravity.CENTER_VERTICAL
                                text = "3月29日"
                                textSize = 12f
                                textColorResource = R.color.gray99
                            }.lparams {
                                height = matchParent
                                width = 0
                                weight = 1f
                                leftMargin = dip(10)

                            }


                            notifyText = textView {
                                gravity = Gravity.CENTER_VERTICAL
                                text = "7時間後、取り消す"
                                if (viewType == 1 || viewType == 2) {
                                    textColorResource = R.color.black20
                                    textSize = 15f
                                } else {
                                    textColorResource = R.color.gray5c
                                    textSize = 12f
                                }
                            }.lparams {
                                height = matchParent
                                width = wrapContent
                            }


                        }.lparams {
                            height = dip(37)
                            width = matchParent
                        }


                        view {
                            backgroundResource = R.color.grayEBEAEB
                        }.lparams {
                            height = dip(1)
                            width = matchParent
                        }



                        linearLayout {

                            gravity = Gravity.CENTER_VERTICAL


                            companyLogo = imageView {

                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.ico_company_default_logo)

                            }.lparams() {
                                width = dip(60)
                                height = dip(60)

                            }


                            verticalLayout {

                                companyName = textView {
                                    gravity = Gravity.CENTER_VERTICAL
                                    text = "任天堂株式会社東京本社"
                                    textSize = 16f
                                    textColorResource = R.color.black20
                                }.lparams {
                                    height = matchParent
                                    width = wrapContent
                                }

                                linearLayout {

                                    interviewType = textView {
                                        gravity = Gravity.CENTER_VERTICAL
                                        text = "面接"
                                        textSize = 13f
                                        textColorResource = R.color.gray99
                                    }.lparams {
                                        height = dip(19)
                                        width = wrapContent
                                    }

                                    positionName = textView {
                                        gravity = Gravity.CENTER_VERTICAL
                                        text = "phpエンジニア"
                                        textSize = 13f
                                        textColorResource = R.color.gray99
                                    }.lparams {
                                        height = dip(19)
                                        width = wrapContent
                                        leftMargin = dip(5)
                                    }

                                    showSalaryMinToMax = textView {
                                        gravity = Gravity.CENTER_VERTICAL
                                        text = "30万-50万"
                                        textSize = 13f
                                        textColorResource = R.color.gray99
                                    }.lparams {
                                        height = dip(19)
                                        width = wrapContent
                                        leftMargin = dip(5)

                                    }


                                }.lparams() {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(3)

                                }


                            }.lparams() {
                                width = 0
                                weight = 1f
                                height = wrapContent
                                leftMargin = dip(10)

                            }


                            linearLayout {
                                textView {
                                    gravity = Gravity.CENTER_VERTICAL
                                    if (viewType == 1 ||viewType == 2 ) {
                                        text = dataType
                                    } else {
                                        visibility = View.GONE
                                    }
                                    textSize = 12f
                                    textColorResource = R.color.gray5c
                                }.lparams {
                                    height = dip(17)
                                    width = wrapContent
                                    topMargin = dip(26)
                                }
                            }.lparams {
                                height = matchParent
                            }


                        }.lparams {
                            height = dip(0)
                            weight = 1f
                            width = matchParent
                        }


                    }.lparams() {
                        width = matchParent
                        height = matchParent
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }

                }.lparams() {
                    width = matchParent
                    height = dip(127)
                    topMargin = dip(8)
                }
            }

        }
        return ViewHolder(view, companyName, companyLogo, interviewType, positionName, showSalaryMinToMax, notifyText,startflag,startDateStr)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //右上角提示
        if (datalist.get(position).dataType == 1) {
            holder.notifyText.text = datalist.get(position).startTimeStr
        }else if (datalist.get(position).dataType == 2) {
            holder.notifyText.text = datalist.get(position).distanceToDeadlineStr
        } else {
            holder.notifyText.text = dataType
        }
        //左上角提示
        if (datalist.get(position).dataType == 1) {
            holder.startflag.text = datalist.get(position).startflag
            holder.startDateStr.text = datalist.get(position).startDateStr
        }else{
            holder.startflag.text = datalist.get(position).startDateStr
            holder.startDateStr.text = datalist.get(position).startTimeStr
        }

        //公司名
        holder.companyName.text = datalist.get(position).companyName
        //公司logo
        if (datalist.get(position).companyLogo != null && !"".equals(datalist.get(position).companyLogo)) {
            loadImage(
                datalist.get(position).companyLogo,
                //                 "https://sk-user-head.s3.ap-northeast-1.amazonaws.com/c32bf618-25c1-48e5-ab60-ae671c195a2c",
                holder.companyLogo
            )
        }
        //面试类型
        holder.interviewType.text=datalist.get(position).InterviewType
        //职位名称
        holder.positionName.text=datalist.get(position).positionName
        //薪水
        holder.showSalaryMinToMax.text=datalist.get(position).showSalaryMinToMax

//        textView.text=jobContainer[position].containerName
//
//        if(position%2==0){
//            labelShow.addView(getLabelView(NORMAL))
//            topShow.addView(getTopView(NORMAL))
//        }
//        else{
//            labelShow.addView(getLabelView(GRAY))
//            topShow.addView(getTopView(GRAY))
//        }
//
        holder.bindItem(datalist[position], position, listener, context)
    }


    override fun getItemCount(): Int = datalist.size

    class ViewHolder(
        view: View,
        val companyName: TextView,
        val companyLogo: ImageView,
        val interviewType: TextView,
        val positionName: TextView,
        val showSalaryMinToMax: TextView,
        val notifyText: TextView,
        val startflag: TextView,
        val startDateStr: TextView
        ) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem(
            interviewInfo: InterviewInfo,
            position: Int,
            listener: (InterviewInfo) -> Unit,
            context: RecyclerView
        ) {
            itemView.setOnClickListener {
                listener(interviewInfo)
            }
        }
    }


}