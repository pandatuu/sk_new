package com.example.sk_android.mvp.model.jobselect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Club(val image:Int, val name:String?, val desc:String?) : Parcelable