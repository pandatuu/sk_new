package com.example.sk_android.mvp.model.person

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//面试信息
@Parcelize
data class InterviewInfo(

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
    val dataType:Int


) : Parcelable

