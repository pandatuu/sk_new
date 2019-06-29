package com.example.sk_android.mvp.model.mysystemsetup

import android.os.Parcelable
import com.example.sk_android.mvp.model.privacySet.PrivacyAttributes
import com.google.gson.JsonObject
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.TypeParceler
import java.io.Serializable
import java.util.*

@Parcelize
data class UserSystemSetup(
    val userId: UUID,
    val attributes: PrivacyAttributes,
    var greeting: Boolean,
    var greetingId: UUID,
    var openType: String,
    val remind: Boolean,
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable