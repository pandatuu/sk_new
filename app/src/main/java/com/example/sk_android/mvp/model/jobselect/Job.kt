package com.example.sk_android.mvp.model.jobselect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Job(val name:String,var selectedType:Int, val id:String) : Parcelable

