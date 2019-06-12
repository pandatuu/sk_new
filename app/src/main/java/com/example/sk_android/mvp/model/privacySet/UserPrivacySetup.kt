package com.example.sk_android.mvp.model.privacySet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

enum class OpenType {
    PUBLIC,
    PRIVATE
}

@Parcelize
data class UserPrivacySetup(
    val userId: UUID,
    val attributes: PrivacyAttributes,
    var greeting: Boolean,
    var greetingId: UUID,
    var openType: OpenType,
    val remind: Boolean,
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable
