package com.example.sk_android.mvp.model.onlineresume

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class UserBasicInformation(
    var attributes: BasicAttribute,
    var auditReply: String,
    var auditState: AuditState,
    var auditUserId: UUID,
    var avatarURL: String,
    var birthday: Long,
    var changedContent: UserChangeContent?,
    var createdAt: Long,
    var deletedAt: Long,
    var disabledAt: Long,
    var displayName: String,
    var educationalBackground: Educa,
    var email: String,
    var firstName: String,
    var gender: Sex,
    var id: UUID,
    var lastName: String,
    var line: String,
    var phone: String,
    var roles: ArrayList<String>,
    var updatedAt: Long,
    var videoThumbnailURL: String,
    var videoURL: String,
    var workingStartDate: Long
) : Parcelable

enum class Sex {
    MALE,
    FEMALE
}

enum class Educa {
    HIGH_SCHOOL,
    MIDDLE_SCHOOL,
    SPECIALITY,
    SHORT_TERM,
    BACHELOR,
    MASTER,
    DOCTOR
}

enum class AuditState {
    PASSED,
    PENDING,
    FAILED
}