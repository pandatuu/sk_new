package com.example.sk_android.mvp.model.register

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Work(
    var attributes: Map<String, String>,
    var endDate:String,
    var hideOrganization:Boolean,
    var organizationId:String,
    var organizationName:String,
    var position:String,
    var responsibility:String,
    var startDate:String
): Parcelable