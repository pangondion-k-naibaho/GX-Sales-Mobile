package com.gxsales.client.model.dataclass.response.Lead

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChannelLeadResponse(
    @field:SerializedName("id")
    val id: String?,

    @field:SerializedName("name")
    val name: String,
): Parcelable