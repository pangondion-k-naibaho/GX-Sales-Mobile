package com.gxsales.client.model.dataclass.response.Lead

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BranchOfficeResponse(
    @field:SerializedName("id")
    val id: Int?,

    @field:SerializedName("name")
    val name: String?
):Parcelable