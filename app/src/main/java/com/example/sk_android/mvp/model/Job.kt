package com.example.sk_android.mvp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Job(val jobType:String, val job:Array<String>) : Parcelable

