package com.example.sk_android.mvp.model.jobselect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//招聘信息(招聘信息列表)
@Parcelize
data class RecruitInfo(
    //
    val emergency:Boolean,
    //招聘方式
    val recruitMethod:String,
    //工作经验 可选
    val workingExperience:String?,
    //工作方式类型
    val workingType:String,
    //货币类型
    val currencyType:String,
    //薪水类型
    val salaryType:String,
    //时薪Min
    val salaryHourlyMin:Int?,
    //时薪Max
    val salaryHourlyMax:Int?,
    //日薪Min
    val salaryDailyMin:Int?,
    //日薪Max
    val salaryDailyMax:Int?,
    //月薪Min
    val salaryMonthlyMin:Int?,
    //月薪Max
    val salaryMonthlyMax:Int?,
    //年薪Min
    val salaryYearlyMin:Int?,
    //年薪Max
    val salaryYearlyMax:Int?,
    //展示出来的薪水范围
    val showSalaryMinToMax:String,
    //
    val calculateSalary:Boolean,
    //教育背景 可选
    val educationalBackground:String?,
    //地点 可选
    val address:String?,
    //
    val content:String,
    //
    val state:String,
    //
    val resumeOnly:Boolean,
    //是最新的吗
    val isNew:Boolean,
    //显示底部吗
    val bottomShow:Boolean,
    //职位名称
    val name:String
) : Parcelable

