package com.example.sk_android.mvp.model.onlineresume

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class JobExperienceAttributes(
    var department : String,
    var jobType: String
): Parcelable