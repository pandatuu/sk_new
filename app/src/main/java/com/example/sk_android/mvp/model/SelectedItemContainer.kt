package com.example.sk_android.mvp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SelectedItemContainer(val containerName:String, val item:Array<SelectedItem>) : Parcelable
