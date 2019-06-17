package com.example.sk_android.mvp.model.onlineresume.projectexprience

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ProjectExperienceAttributes(
    var department : String,
    var jobType: String
): Parcelable