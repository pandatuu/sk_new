package com.example.sk_android.mvp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class JobSearchUnderSearching(val key:String, val name:String) : Parcelable

