package com.gxsales.client.model.dataclass.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileResponse(
    @field:SerializedName("status")
    val status: String?= "",

    @field:SerializedName("message")
    val message: String?= "",

    @field:SerializedName("data")
    val data: DataProfile?= null
): Parcelable