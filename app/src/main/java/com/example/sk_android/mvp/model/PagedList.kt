package com.example.sk_android.mvp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class PagedList<T : Serializable>(
    val total: Int,
    val page: Int,
    val pageSize: Int,
    val data: List<T>
): Parcelable