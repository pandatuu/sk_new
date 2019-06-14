package com.example.sk_android.mvp.model.register

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Education(
    var attributes: Map<String, Serializable>,
    var educationalBackground:String,
    var endDate:String,
    var major:String,
    var schoolId:String,
    var schoolName:String,
    var startDate:String
): Parcelable