package com.example.sk_android.mvp.model.jobselect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SelectedItemContainer(val containerName:String, val item:MutableList<SelectedItem>) : Parcelable
