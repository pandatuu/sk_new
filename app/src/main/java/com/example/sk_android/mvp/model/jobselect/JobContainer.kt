package com.example.sk_android.mvp.model.jobselect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class JobContainer(val containerName:String, val item:Array<Job>) : Parcelable

