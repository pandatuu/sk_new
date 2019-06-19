package com.example.sk_android.mvp.model.jobselect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * Created by Wanhar Aderta Daeng Maro on 9/6/2018.
 * Email : wanhardaengmaro@gmail.com
 *
 */
@Parcelize
data class Area(val province:String,var type:Int, val city:MutableList<City>) : Parcelable