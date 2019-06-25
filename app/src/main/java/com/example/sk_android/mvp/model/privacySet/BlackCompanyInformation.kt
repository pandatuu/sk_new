package com.example.sk_android.mvp.model.privacySet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class BlackCompanyInformation(
    val id:UUID,
    var address: String,
    var model: BlackCompanyModel
) : Parcelable