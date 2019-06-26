package com.example.sk_android.mvp.model.jobselect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class JobSearchResult(val name:String,val id:String, val des:String,var type:Int) : Parcelable

