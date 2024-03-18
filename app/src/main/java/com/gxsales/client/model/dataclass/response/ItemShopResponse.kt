package com.gxsales.client.model.dataclass.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemShopResponse(
    val productImage: Int,
    val productName: String,
    val productPrice: String,
    val productStock: Int,
    val productType: String,
    val productTax: String
):Parcelable