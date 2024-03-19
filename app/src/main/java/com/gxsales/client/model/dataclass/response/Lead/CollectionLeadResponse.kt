package com.gxsales.client.model.dataclass.response.Lead

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionLeadResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: List<DataLeadResponse>?= null,

    @field:SerializedName("pagination")
    val pagination: PaginationCollectionLeadResponse?= null,
): Parcelable