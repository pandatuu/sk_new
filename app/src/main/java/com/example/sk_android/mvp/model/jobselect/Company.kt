package com.example.sk_android.mvp.model.jobselect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Company(
    val name:String,
    val logo:String,
    val cityName:String,
    val countyName:String,
    val streetName:String,
    val financing:String,
    val companySize:String,
    val companyType:String,
    val video:Boolean






















) : Parcelable