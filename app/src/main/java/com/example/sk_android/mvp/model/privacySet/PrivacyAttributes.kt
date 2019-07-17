package com.example.sk_android.mvp.model.privacySet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
data class PrivacyAttributes(
    var isResume: Boolean,
    var companyName: Boolean,
    var causeText : String?
) : Parcelable