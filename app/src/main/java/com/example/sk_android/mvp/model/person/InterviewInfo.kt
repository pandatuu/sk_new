package com.example.sk_android.mvp.model.person

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//面试信息
@Parcelize
data class InterviewInfo(
    //记录Id
    val id:String,
    val companyName:String?,
    //
    val companyLogo:String,
    //
    val InterviewType:String,
    //
    val positionName:String,
    //
    val showSalaryMinToMax:String,
    //状态  1,没完成  2,完成了
    val dataType:Int,
    //截止时间
    val distanceToDeadlineStr:String,
    //开始具体时间
    val startTimeStr:String,
    //开始日期
    val startDateStr:String,
    //开始日期标记
    val startflag:String


) : Parcelable

