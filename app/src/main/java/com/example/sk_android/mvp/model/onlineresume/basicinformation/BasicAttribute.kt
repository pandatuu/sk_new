package com.example.sk_android.mvp.model.onlineresume.basicinformation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BasicAttribute(
    var userSkill : String,
    var jobSkill : String,
    var iCanDo : String
): Parcelable