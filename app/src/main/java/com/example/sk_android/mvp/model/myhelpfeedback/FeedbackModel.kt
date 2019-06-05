package com.example.sk_android.mvp.model.myhelpfeedback

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
data class FeedbackModel(
    val id: UUID,
    val userId: UUID,
    val type: String,
    val content: String,
    val attachments: MutableList<String>,
    val processState: String,
    val processUserId: UUID,
    val processReply: String,
    val attributes: Map<String, Serializable>,
    val createdAt: Long,
    val updatedAt: Long,
    val delete: Boolean
): Parcelable