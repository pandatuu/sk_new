package com.example.sk_android.mvp.model

import android.os.Parcelable
import com.example.sk_android.mvp.model.myhelpfeedback.HelpModel
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class PagedList(
    val total: Int,
    val page: Int,
    val pageSize: Int,
    val data: MutableList<HelpModel>
): Parcelable