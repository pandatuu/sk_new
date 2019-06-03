package com.example.sk_android.mvp.model.myhelpfeedback

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
data class HelpModel(
    val id: UUID,
    val title: String,
    val content: String,
    val parentId: UUID?,
    val attributes: Map<String, Serializable>,
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable
