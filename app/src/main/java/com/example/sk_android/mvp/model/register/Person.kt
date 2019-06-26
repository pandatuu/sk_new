package com.example.sk_android.mvp.model.register

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.TypeParceler
import java.io.Serializable

@Parcelize
data class Person(
    var attributes: Map<String,Serializable>,
    var avatarUrl:String,
    var birthday:String,
    var displayName:String,
    var educationalBackground:String,
    var email:String,
    var firstName:String,
    var gender:String,
    var lastName:String,
    var line:String,
    var phone:String,
    var videoThumbnailUrl:String,
    var videoUrl:String,
    var workingStartDate:String,
    var workStatus:String
): Parcelable