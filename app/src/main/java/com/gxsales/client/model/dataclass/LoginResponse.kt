package com.gxsales.client.model.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse(
    @field:SerializedName("status")
    val status: String?= "",

    @field:SerializedName("token")
    val token: String?= "",

    @field:SerializedName("type")
    val type: String?= "",

    @field:SerializedName("message")
    val message: String?= ""
): Parcelable