package com.example.sk_android.mvp.model.jobselect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SelectedItem(val name:String, var selected:Boolean=false,val value:String) : Parcelable

