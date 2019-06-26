package com.example.sk_android.mvp.model.jobselect

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.TypeParceler
import java.io.Serializable

@Parcelize
data class UserJobIntention (
    var areaIds: ArrayList<String>,
    var areaName: MutableList<String>,
    var attributes: Map<String,Serializable>,
    var createdAt: String,
    var currencyType: String,
    var deletedAt: String,
    var evaluation: String,
    var id: String,
    var industryIds: ArrayList<String>,
    var industryName:MutableList<String>,
    var recruitMethod: String,
    var resumeId: String,
    var salaryDailyMax: Int,
    var salaryDailyMin: Int,
    var salaryHourlyMax: Int,
    var salaryHourlyMin: Int,
    var salaryMax: Int,
    var salaryMin: Int,
    var salaryMonthlyMax: Int,
    var salaryMonthlyMin: Int,
    var salaryType: String,
    var salaryYearlyMax: Int,
    var salaryYearlyMin: Int,
    var updatedAt: String,
    var userId: String,
    var workingExperience: Int,
    var workingTypes: ArrayList<String>
): Parcelable