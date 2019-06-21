package com.example.sk_android.mvp.model.mysystemsetup

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
data class UserSystemSetup(
    val userId: UUID,
    val attributes: Map<String, Serializable>,
    var greeting: Boolean,
    var greetingId: UUID,
    var openType: String,
    val remind: Boolean,
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable