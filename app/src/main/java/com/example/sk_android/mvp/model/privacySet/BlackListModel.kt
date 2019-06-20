package com.example.sk_android.mvp.model.privacySet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
data class BlackListModel(
    var id: UUID,
    var userId: UUID,
    var attributes: Map<String,Serializable>,
    var blackedOrganizationId: UUID,
    var createdAt: Long,
    var updatedAt: Long
) : Parcelable
