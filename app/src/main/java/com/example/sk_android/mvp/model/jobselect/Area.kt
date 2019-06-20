package com.example.sk_android.mvp.model.jobselect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Area(val province:String,var type:Int, val city:MutableList<City>) : Parcelable