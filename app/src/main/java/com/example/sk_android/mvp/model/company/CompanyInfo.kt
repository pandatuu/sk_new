package com.example.sk_android.mvp.model.company

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CompanyInfo(
    val id: String,
    val videoUrl: String,
    val logo: String,
    val name: String,
    var size: String,
    var financingStage: String,
    var type: String,
    var website: String,
    var benifits: MutableList<String>,
    var companyIntroduce: String,
    var address: MutableList<ArrayList<String>>,
    val imageUrls: MutableList<String>,
    var startTime: String,
    var endTime: String
) : Parcelable
