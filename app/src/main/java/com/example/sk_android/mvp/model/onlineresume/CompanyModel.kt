package com.example.sk_android.mvp.model.onlineresume

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
data class CompanyModel(
    val position: String,
    val id: UUID,
    val name: String,
    val acronym: String,
    val registryNumber: Int,
    val logo: String,
    val website: String,
    val parentId: UUID,
    val size: String,
    val financingStage: String,
    val type: String,
    val industryIds: List<String>,
    val videoUrl: String,
    val videoThumbnailUrl: String,
    val imageUrls: List<String>,
    val benifits: List<String>,
    val auditState: String,
    val auditUserId: UUID,
    val auditReply: String,
    val changedContent: Map<String,Serializable>,
    val registrationType: String,
    val activatedAt: String,
    val attributes: Map<String,Serializable>,
    val createdAt: Long,
    val updatedAt: Long
): Parcelable