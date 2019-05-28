package com.example.sk_android.mvp.model.resume

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Resume (val size:String, val name:String, val url:String, val updateData: String) : Parcelable


