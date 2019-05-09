package com.example.sk_android.mvp.model.jobSelect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SelectedItem(val name:String, val selected:Boolean=false) : Parcelable

