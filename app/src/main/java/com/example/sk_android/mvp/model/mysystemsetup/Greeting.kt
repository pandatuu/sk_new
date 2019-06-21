package com.example.sk_android.mvp.model.mysystemsetup

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Greeting(
    val content: String,
    val createdAt: Long,
    val id: UUID,
    val updatedAt: Long
    ): Parcelable