package com.example.sk_android.mvp.model.privacySet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class BlackCompanyAdd(
    var address: String?,
    var model: BlackCompanyModel,
var isTrueChecked: Boolean?
) : Parcelable