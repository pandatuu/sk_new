package com.example.sk_android.mvp.model

import android.os.Parcel
import android.os.Parcelable
import com.example.sk_android.mvp.model.myhelpfeedback.HelpModel
import com.google.gson.JsonObject
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.TypeParceler
import java.io.Serializable

@Parcelize
@TypeParceler<JsonObject,Parceler<JsonObject>>()
data class PagedList(
    val total: Int,
    val page: Int,
    val pageSize: Int,
    val data: MutableList<JsonObject>
): Parcelable


