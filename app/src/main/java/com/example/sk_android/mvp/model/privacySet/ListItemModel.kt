package com.example.sk_android.mvp.model.privacySet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class ListItemModel(val companyIcon:Int, val companyName:String, val companyAddr:String, var isTrueChecked:Boolean?) : Parcelable, Serializable