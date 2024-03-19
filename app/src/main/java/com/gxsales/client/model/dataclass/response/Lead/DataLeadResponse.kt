package com.gxsales.client.model.dataclass.response.Lead

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class DataLeadResponse(
    @field:SerializedName("id")
    val id: Int?,

    @field:SerializedName("number")
    val number: String?,

    @field:SerializedName("fullName")
    val fullName: String?,

    @field:SerializedName("email")
    val email: String?,

    @field:SerializedName("phone")
    val phone: String?,

    @field:SerializedName("address")
    val address: String?,

    @field:SerializedName("latitude")
    val latitude: String?,

    @field:SerializedName("longitude")
    val longitude: String?,

    @field:SerializedName("companyName")
    val companyName: String?,

    @field: SerializedName("gender")
    val gender: String?,

    @field: SerializedName("IDNumber")
    val IDNumber: String?,

    @field:SerializedName("IDNumberPhoto")
    val IDNumberPhoto: String?,

    @field:SerializedName("generalNotes")
    val generalNotes: String?,

    @field:SerializedName("branchOffice")
    val branchOffice: BranchOfficeResponse?,

    @field:SerializedName("status")
    val status: StatusLeadResponse?,

    @field:SerializedName("probability")
    val probability: ProbabilityLeadResponse?,

    @field: SerializedName("type")
    val type: TypeLeadResponse?,

    @field:SerializedName("channel")
    val channel: ChannelLeadResponse?,

    @field:SerializedName("media")
    val media: MediaLeadResponse?,

    @field:SerializedName("source")
    val source: SourceLeadResponse?,

    @field:SerializedName("createdAt")
    val createdAt: String?,

):Parcelable