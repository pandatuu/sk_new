package com.example.sk_android.mvp.model.jobselect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class JobWantedModel(val title:String, val address:String, val salary:String, val industry:String, val industrySon:String) : Parcelable

