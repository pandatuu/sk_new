package com.example.sk_android.mvp.model.jobSelect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class JobSearchResult(val name:String, val des:String) : Parcelable
