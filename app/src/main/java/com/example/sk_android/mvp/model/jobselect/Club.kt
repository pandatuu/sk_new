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
data class Club(val image:Int, val name:String?, val desc:String?) : Parcelable