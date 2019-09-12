package com.example.sk_android.mvp.model.collection

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class CollectionModel(
    val id: String,
    val userId: UUID,
    val targetEntityId: UUID,
    val targetEntityType: String,
    val createdAt: Long
): Parcelable