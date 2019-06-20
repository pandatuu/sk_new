package com.example.sk_android.mvp.model.privacySet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class BlackCompanyModel(
    var id: String,
    var name: String,
    var logo: String
) : Parcelable
