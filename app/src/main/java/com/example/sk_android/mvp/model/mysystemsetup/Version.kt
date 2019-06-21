package com.example.sk_android.mvp.model.mysystemsetup

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
data class Version(
    val id: UUID,
    var type: String,
    var semver: String,
    var number: Int,
    val description: String,
    val imageUrls: List<String>,
    val disabled: String,
    val downloadUrl: String,
    val releaseDate: Long,
    val attributes: Map<String, Serializable>,
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable