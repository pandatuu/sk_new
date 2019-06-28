package com.example.sk_android.mvp.model.message

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ChatRecordModel(
    val uid:String,
    val userName:String,
    val position:String,
    val avatar:String,
    val massage:String,
    val number:String,
    val companyName:String,
    val lastPositionId:String) : Parcelable