package com.example.sk_android.mvp.model.message

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * Created by Wanhar Aderta Daeng Maro on 9/6/2018.
 * Email : wanhardaengmaro@gmail.com
 *
 */
@Parcelize
data class ChatRecordModel(val uid:String,val userName:String, val position:String,val avatar:String,val massage:String,val number:String) : Parcelable