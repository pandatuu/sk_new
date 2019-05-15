package com.example.sk_android.mvp.model.privacySet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListItemModel(val companyIcon:Int, val companyName:String, val companyAddr:String) : Parcelable