package com.gxsales.client.model.dataclass.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemSummaryResponse(
    val count: Int,
    val summaryName: String,
):Parcelable