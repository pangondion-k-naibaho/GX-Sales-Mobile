package com.gxsales.client.model.dataclass.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataProfile(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String?= "",

    @field:SerializedName("email")
    val email: String?= ""
): Parcelable