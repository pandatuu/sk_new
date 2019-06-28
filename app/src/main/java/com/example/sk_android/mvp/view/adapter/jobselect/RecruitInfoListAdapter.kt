package com.example.sk_android.mvp.view.adapter.jobselect

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.custom.layout.roundImageView
import com.example.sk_android.mvp.model.jobselect.RecruitInfo
import com.pingerx.imagego.core.strategy.loadCircle
import com.pingerx.imagego.core.strategy.loadImage
import org.jetbrains.anko.*

class RecruitInfoListAdapter(
    private val context: RecyclerView,
    private val recruitInfo: MutableList<RecruitInfo>,
    private val listener: (RecruitInfo, Int) -> Unit,
    private val communicateListener: (RecruitInfo) -> Unit,
    private val isCollectionListener: (RecruitInfo, Int, Boolean) -> Unit
) : RecyclerView.Adapter<RecruitInfoListAdapter.ViewHolder>() {


    val collected = 1
    val noCollected = 2


    //添加数据
    fun addRecruitInfoList(list: List<RecruitInfo>) {
        recruitInfo.addAll(list)
        notifyDataSetChanged()
    }


    //改变搜藏状态
    fun UpdatePositionCollectiont(index: Int, isCollection: Boolean, collectionId: String) {
        if (index != null && index != -1) {
            recruitInfo.get(index).isCollection = isCollection
            recruitInfo.get(index).collectionId = collectionId
            notifyDataSetChanged()
        }
    }


    override fun getItemViewType(position: Int): Int {

        var collection = recruitInfo.get(position).isCollection
        if (collection) {
            return collected
        } else {
            return noCollected
        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        lateinit var company: TextView
        lateinit var jobName: TextView
        lateinit var salaryType: TextView
        lateinit var salaryMinToMax: TextView
        lateinit var address: TextView
        lateinit var workingExperience: TextView
        lateinit var educationalBackground: TextView
        lateinit var isNew: ImageView
        lateinit var topShow: LinearLayout
        lateinit var labelShow: LinearLayout
        lateinit var bottomLine: TextView
        lateinit var bottomDate: TextView
        lateinit var canteen: ImageView
        lateinit var club: ImageView
        lateinit var socialInsurance: ImageView
        lateinit var traffic: ImageView
        lateinit var userPositionName: TextView
        lateinit var avatarURL: ImageView
        lateinit var communicate: LinearLayout
        lateinit var isCollection: ImageView
        lateinit var isCollectionContainer: LinearLayout

        var view = with(parent.context) {
            relativeLayout {
                verticalLayout {
                    backgroundResource = R.drawable.box_shadow
                    topShow = linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
//                                    if(type==GRAY){
                            backgroundResource = R.drawable.button_radius_border_no_top
//                                    }else if(type==NORMAL){
                            backgroundResource = R.drawable.button_radius_border_no_top
//                                    }

                            gravity = Gravity.CENTER_VERTICAL
                            salaryType = textView {
                                backgroundResource = R.drawable.circle_border_white
                                textSize = 10f
                                textColor = Color.WHITE
                                text = "年"
                                gravity = Gravity.CENTER
                            }.lparams {
                                leftMargin = dip(8)
                                height = dip(19)
                                width = dip(19)
                            }

                            salaryMinToMax = textView {
                                textSize = 12f
                                textColor = Color.WHITE
                                text = "600台~800台"
                                gravity = Gravity.CENTER
                            }.lparams {
                                leftMargin = dip(8)
                                height = dip(19)
                                rightMargin = dip(8)

                            }
                            minimumWidth = dip(130)
                        }.lparams {
                            leftMargin = dip(10)
                            height = matchParent
                            width = wrapContent
                        }


                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.BOTTOM
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL

                                linearLayout {
                                    gravity = Gravity.BOTTOM or Gravity.LEFT

                                    canteen = imageView {
                                        imageResource = R.mipmap.icon_canbu_home
                                        visibility = View.GONE

                                    }
                                }.lparams {
                                    width = dip(0)
                                    weight = 1f
                                    height= matchParent
                                }


                                linearLayout {
                                    gravity = Gravity.BOTTOM or Gravity.LEFT

                                    club = imageView {
                                        imageResource = R.mipmap.icon_coffee_home
                                        visibility = View.GONE
                                    }
                                }.lparams {
                                    width = dip(0)
                                    weight = 1f
                                    height= matchParent
                                }
                                linearLayout {
                                    gravity = Gravity.BOTTOM or Gravity.LEFT
                                    socialInsurance = imageView {
                                        imageResource = R.mipmap.icon_fl_home
                                        visibility = View.GONE
                                    }
                                }.lparams {
                                    width = dip(0)
                                    weight = 1f
                                    height= matchParent
                                }

                                linearLayout {
                                    gravity = Gravity.BOTTOM or Gravity.LEFT
                                    traffic = imageView {
                                        imageResource = R.mipmap.icon_cb_home
                                        visibility = View.GONE
                                    }
                                }.lparams {
                                    width = dip(0)
                                    weight = 1f
                                    height= matchParent
                                }
                            }.lparams {
                                height = matchParent
                                width = 0
                                weight = 1f
                                bottomMargin=dip(5)

                            }
                            linearLayout {
                                gravity = Gravity.BOTTOM
                                isNew = imageView {
                                    imageResource = R.mipmap.icon_new_home
                                }.lparams {

                                    //                                width = dip(23)
//                                height = dip(15)

                                    bottomMargin = dip(5)

                                }
                            }.lparams {
                                height = matchParent
                                width= wrapContent
                            }
                        }.lparams {
                            height = matchParent
                            width = 0
                            weight = 1f
                            rightMargin = dip(10)
                            leftMargin = dip(8)
                        }


                    }.lparams {
                        height = dip(35)
                        width = matchParent
                    }

                    //公司名  字体较小  灰色
                    company = textView {
                        gravity = Gravity.CENTER_VERTICAL
                        textColorResource = R.color.companyNameGray
                        textSize = 13f
                        text = "公司名称"
                    }.lparams {
                        leftMargin = dip(20)
                        topMargin = dip(20)
                    }

                    //职位名称  字体较大 黑色
                    jobName = textView {
                        gravity = Gravity.CENTER_VERTICAL
                        textColorResource = R.color.normalTextColor
                        textSize = 16f
                        text = "职位名称"
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                    }.lparams {
                        leftMargin = dip(20)
                        topMargin = dip(4)
                    }

                    labelShow = linearLayout {
                        orientation = LinearLayout.HORIZONTAL

                        address = textView {
                            //                            if(type==GRAY){
                            backgroundResource = R.drawable.label_gray_border
                            textColorResource = R.color.grayCD
//                            }else if(type==NORMAL){
                            backgroundResource = R.drawable.label_theme_bule_border
                            textColorResource = R.color.blue0097D6
//                            }

                            textSize = 11f
                            text = "東京"
                            gravity = Gravity.CENTER_VERTICAL
                            leftPadding = dip(7)
                            rightPadding = dip(7)
                        }.lparams {
                            height = matchParent
                        }

                        workingExperience = textView {
                            //if(type==GRAY){
                            backgroundResource = R.drawable.label_gray_border
                            textColorResource = R.color.grayCD
                            // }else if(type==NORMAL){
                            backgroundResource = R.drawable.label_theme_bule_border
                            textColorResource = R.color.blue0097D6
                            // }
                            textSize = 11f
                            text = "1～3"
                            gravity = Gravity.CENTER_VERTICAL
                            leftPadding = dip(7)
                            rightPadding = dip(7)
                        }.lparams {
                            height = matchParent
                            leftMargin = dip(5)
                        }

                        educationalBackground = textView {
                            //  if(type==GRAY){
                            backgroundResource = R.drawable.label_gray_border
                            textColorResource = R.color.grayCD
                            //  }else if(type==NORMAL){
                            backgroundResource = R.drawable.label_theme_bule_border
                            textColorResource = R.color.blue0097D6
                            // }
                            textSize = 11f
                            text = "大卒"
                            gravity = Gravity.CENTER_VERTICAL
                            leftPadding = dip(7)
                            rightPadding = dip(7)
                        }.lparams {
                            height = matchParent
                            leftMargin = dip(5)
                        }

                    }.lparams {
                        height = dip(18)
                        topMargin = dip(8)
                        leftMargin = dip(20)
                    }


                    relativeLayout {
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER_VERTICAL
                            avatarURL = imageView {
                                //                                imageResource = R.mipmap.icon_tx_home
                            }.lparams {
                                width = dip(28)
                                height = dip(28)
                            }
                            userPositionName = textView {
                                textColorResource = R.color.gray5c
                                textSize = 11f
                                text = ""
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams {
                                leftMargin = dip(10)
                            }

                            imageView {
                                imageResource = R.mipmap.icon_gold_home
                                visibility = View.GONE
                            }.lparams {
                                leftMargin = dip(10)
                            }
                            communicate = linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                imageView {
                                    imageResource = R.mipmap.ico_conversation
                                }.lparams {
                                    leftMargin = dip(10)
                                    rightMargin = dip(10)
                                    width = dip(15)
                                    height = dip(15)

                                }
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                            }
                        }.lparams {
                            height = matchParent
                            alignParentLeft()

                        }
                        isCollectionContainer = linearLayout {
                            gravity = Gravity.CENTER
                            isCollection = imageView {
                                if (viewType == collected) {
                                    imageResource = R.mipmap.icon_zan_h_home

                                } else if (viewType == noCollected) {
                                    imageResource = R.mipmap.icon_zan_n_home
                                }
                            }
                        }.lparams {
                            width = dip(30)
                            height = matchParent
                            alignParentRight()
                            centerVertically()
                        }
                    }.lparams {
                        height = dip(30)
                        width = matchParent
                        rightMargin = dip(17)
                        leftMargin = dip(20)
                        topMargin = dip(15)
                        bottomMargin = dip(10)
                    }

                    bottomLine = textView {
                        backgroundColorResource = R.color.grayEBEAEB
                    }.lparams {
                        height = dip(1)
                        width = matchParent
                        leftMargin = dip(11)
                        rightMargin = dip(11)
                        topMargin = dip(0)

                    }

                    bottomDate = textView {
                        text = "3月19日 17:05"
                        textSize = 13f
                        textColorResource = R.color.gray99
                        gravity = Gravity.CENTER_VERTICAL

                    }.lparams {
                        height = dip(37)
                        width = matchParent
                        leftMargin = dip(20)
                        rightMargin = dip(20)
                    }

                }.lparams() {
                    width = matchParent
                    height = wrapContent
                    topMargin = dip(5)
                }
            }

        }
        return ViewHolder(
            view,
            company,
            jobName,
            salaryType,
            salaryMinToMax,
            address,
            workingExperience,
            educationalBackground,
            isNew,
            bottomLine,
            bottomDate,
            canteen,
            club,
            socialInsurance,
            traffic,
            userPositionName,
            avatarURL,
            communicate,
            isCollection,
            isCollectionContainer
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //薪资类型
        var salaryType = recruitInfo[position].salaryType
        if (salaryType != null) {
            holder.salaryType.visibility = View.VISIBLE
            holder.salaryType.text = salaryType.toString()
        } else {
            holder.salaryType.visibility = View.GONE
        }

        //薪资范围
        var showSalaryMinToMax = recruitInfo[position].showSalaryMinToMax
        if (showSalaryMinToMax != null) {
            holder.salaryMinToMax.visibility = View.VISIBLE
            holder.salaryMinToMax.text = showSalaryMinToMax
        } else {
            holder.salaryMinToMax.visibility = View.GONE
        }

        //工作经验
        var workingExperience = recruitInfo[position].workingExperience
        if (workingExperience != null) {
            holder.workingExperience.visibility = View.VISIBLE
            holder.workingExperience.text = workingExperience.toString()
        } else {
            holder.workingExperience.visibility = View.GONE
        }

        //教育背景
        var educationalBackground = recruitInfo[position].educationalBackground
        if (educationalBackground != null && !educationalBackground.equals("")) {
            holder.educationalBackground.visibility = View.VISIBLE
            holder.educationalBackground.text = educationalBackground.toString()
        } else {
            holder.educationalBackground.visibility = View.GONE
        }

        //地点
        var address = recruitInfo[position].address
        if (address != null && !address.equals("")) {
            holder.address.visibility = View.VISIBLE
            holder.address.text = address
        } else {
            holder.address.visibility = View.GONE
        }

        //是否是最新
        var isNew = recruitInfo[position].isNew
        if (isNew) {
            holder.isNew.visibility = View.VISIBLE
        } else {
            holder.isNew.visibility = View.INVISIBLE
        }

        //显示地底部吗
        var bottomShow = recruitInfo[position].bottomShow
        if (bottomShow) {
            holder.bottomLine.visibility = View.VISIBLE
            holder.bottomDate.visibility = View.VISIBLE
        } else {
            holder.bottomLine.visibility = View.GONE
            holder.bottomDate.visibility = View.GONE
        }

        //职位名称
        var jobName = recruitInfo[position].name
        if (jobName != null) {
            holder.jobName.text = jobName
        }

        //公司名称
        var companyName = recruitInfo[position].companyName
        if (companyName != null) {
            holder.company.text = companyName
        }

        //福利
        //有食堂吗
        if (recruitInfo[position].haveCanteen) {
            holder.canteen.visibility = View.VISIBLE
        }

        //有俱乐部吗
        if (recruitInfo[position].haveClub) {
            holder.club.visibility = View.VISIBLE
        }

        //有社保吗
        if (recruitInfo[position].haveSocialInsurance) {
            holder.socialInsurance.visibility = View.VISIBLE
        }

        //有交通补助吗
        if (recruitInfo[position].haveTraffic) {
            holder.traffic.visibility = View.VISIBLE
        }

        //用户的职位名称
        holder.userPositionName.text = recruitInfo[position].userName + "." + recruitInfo[position].userPositionName

        var collectionFlag = false
        //是否搜藏
        if (recruitInfo[position].isCollection) {
            collectionFlag = true
        }


        //用户头像
        if (recruitInfo[position].avatarURL != null && !recruitInfo[position].avatarURL.equals("")) {

            if (!holder.avatarURL.isSelected) {
                var imageUri = recruitInfo[position].avatarURL
                loadCircle(
                    imageUri,
                    //                 "https://sk-user-head.s3.ap-northeast-1.amazonaws.com/c32bf618-25c1-48e5-ab60-ae671c195a2c",
                    holder.avatarURL
                )
            }
            holder.avatarURL.isSelected = true

        }




        holder.bindItem(
            recruitInfo[position],
            position,
            listener,
            communicateListener,
            isCollectionListener,
            collectionFlag
        )
        holder.setIsRecyclable(false);

    }


    override fun getItemCount(): Int = recruitInfo.size

    class ViewHolder(
        view: View,
        val company: TextView,
        val jobName: TextView,
        val salaryType: TextView,
        val salaryMinToMax: TextView,
        val address: TextView,
        val workingExperience: TextView,
        val educationalBackground: TextView,
        val isNew: ImageView,
        val bottomLine: TextView,
        val bottomDate: TextView,
        val canteen: ImageView,
        val club: ImageView,
        val socialInsurance: ImageView,
        val traffic: ImageView,
        val userPositionName: TextView,
        val avatarURL: ImageView,
        val communicate: LinearLayout,
        val isCollection: ImageView,
        val isCollectionContainer: LinearLayout

    ) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem(
            recruitInfo: RecruitInfo,
            position: Int,
            listener: (RecruitInfo, Int) -> Unit,
            communicateListener: (RecruitInfo) -> Unit,
            isCollectionListener: (RecruitInfo, Int, Boolean) -> Unit,
            collectionFlag: Boolean
        ) {
            var flag = collectionFlag
            //主体点击
            itemView.setOnClickListener {
                listener(recruitInfo, position)
            }
            //点击聊天
            communicate.setOnClickListener {
                communicateListener(recruitInfo)
            }
            //点击搜藏/取消搜藏
            isCollectionContainer.setOnClickListener {
                if (flag) {
                    flag = false
                } else {
                    flag = true
                }

                isCollectionListener(recruitInfo, position, flag)
            }
        }
    }


}