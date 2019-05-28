package com.example.sk_android.mvp.model.resume

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EntityVideo (var thumbPath:String, var path:String, var duration:Int) : Parcelable