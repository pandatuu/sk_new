package com.example.sk_android.mvp.model.jobselect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class City(var name:String, var id :String,var selected:Boolean) : Parcelable