package com.example.sk_android.mvp.model.onlineresume.eduexperience

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class EduExperienceModel(
    var id: UUID,
    var userId: UUID,
    var resumeId: UUID,
    var schoolId: UUID,
    var startDate: Long,
    var endDate: Long,
    var schoolName: String,
    var major: String,
    var educationalBackground: EduBack,
    var attributes: EduExperienceAttributes,
    var createdAt: Long,
    var updatedAt: Long
) : Parcelable

enum class EduBack {
    MIDDLE_SCHOOL, HIGH_SCHOOL, SHORT_TERM_COLLEGE, BACHELOR, MASTER, DOCTOR
}