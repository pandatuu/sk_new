package com.example.sk_android.mvp.model.onlineresume.projectexprience

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
data class ProjectExperienceModel(
    var id : UUID,
    var userId: UUID?,
    var resumeId : UUID,
    var startDate : Long,
    var endDate : Long,
    var position : String,
    var projectName : String,
    var responsibility : String,
    var attributes: ProjectExperienceAttributes,
    var createdAt: Long,
    var updatedAt: Long
): Parcelable