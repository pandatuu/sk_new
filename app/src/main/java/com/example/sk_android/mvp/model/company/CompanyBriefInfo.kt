package com.example.sk_android.mvp.model.company

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CompanyBriefInfo(
    //公司Id
    val id:String,
    //公司名称
    val name:String,
    //公司简称
    val acronym:String,
    //公司logo路径
    val logo:String,
    //公司规模
    val size:String,
    //融资状况
    val financingStage:String,
    //公司类型
    val type:String,
    //所属行业
    val industry:String,
    //有视频吗
    val haveVideo:Boolean,
    //城市名
    val cityName:String,
    //区县名
    val countyName:String,
    //街区名
    val streetName:String























) : Parcelable