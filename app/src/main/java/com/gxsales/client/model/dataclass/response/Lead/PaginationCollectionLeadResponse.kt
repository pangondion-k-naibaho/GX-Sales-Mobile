package com.gxsales.client.model.dataclass.response.Lead

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaginationCollectionLeadResponse(
    @field:SerializedName("count")
    val count: Int?,

    @field:SerializedName("currentPage")
    val currentPage: Int,

    @field:SerializedName("perPage")
    val perPage: Int,

    @field:SerializedName("total")
    val total: Int,

    @field:SerializedName("totalPages")
    val totalPages: Int,
):Parcelable