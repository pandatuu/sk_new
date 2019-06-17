package com.example.sk_android.mvp.model.onlineresume.jobexperience

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class JobExperienceModel(
    var id : UUID,
    var userId: UUID?,
    var resumeId : UUID,
    var organizationId : UUID,
    var organizationName: String,
    var startDate : Long,
    var endDate : Long,
    var position : String,
    var salary : Int,
    var responsibility : String,
    var hideOrganization : Boolean,
    var attributes: JobExperienceAttributes,
    var createdAt: Long,
    var updatedAt: Long
): Parcelable