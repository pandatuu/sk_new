package com.example.sk_android.mvp.model.resume

import android.os.Parcelable
import com.taobao.accs.ErrorCode
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class InterView (
    var id:String?,
    var type:String,
    var userId:String,
    var recruitUserId:String?,
    var recruitOrganizationId:String,
    var recruitOrganizationName:String?,
    var recruitPositionId:String,
    var recruitPositionName:String?,
    var appointedStartTime:Int,
    var appointedEndTime:Int,
    var startTime:Int?,
    var endTime:Int?,
    var acceptDeadline:Int,
    var attributes: Map<String, Serializable>
    ) : Parcelable
