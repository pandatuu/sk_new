package com.example.sk_android.mvp.model.resume

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Resume (var imageUrl:Int, val id:String, val size:String, val name:String, val url:String, val updateData: String,
                   val downloadURL:String,val status:Int,val mediaId:String,val mediaUrl:String) : Parcelable


