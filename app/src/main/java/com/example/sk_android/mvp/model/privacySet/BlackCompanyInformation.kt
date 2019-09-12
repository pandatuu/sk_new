package com.example.sk_android.mvp.model.privacySet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BlackCompanyInformation(
    val id: String,
    var address: String,
    var model: BlackCompanyModel
) : Parcelable