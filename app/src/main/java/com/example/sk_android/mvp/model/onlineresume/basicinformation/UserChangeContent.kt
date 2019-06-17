package com.example.sk_android.mvp.model.onlineresume.basicinformation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
class UserChangeContent (
    var attributes: BasicAttribute,
    var auditReply: String,
    var auditState: AuditState,
    var auditUserId: UUID,
    var avatarURL: String,
    var birthday: Long,
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
): Parcelable