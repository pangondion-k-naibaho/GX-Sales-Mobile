package com.gxsales.client.model.dataclass.response.Authentication

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LogoutResponse(
    @field:SerializedName("status")
    val status: String?= "",

    @field:SerializedName("message")
    val message: String?= ""
):Parcelable