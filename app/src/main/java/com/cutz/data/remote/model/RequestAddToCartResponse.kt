package com.cutz.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class RequestAddToCartResponse(
    @SerializedName("qty")
    var qty: Int,
    @SerializedName("product_id")
    var product_id: String,
    @SerializedName("addition_id")
    var addition_id: String?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("productWeight_id")
    var productWeight_id: String?=null
) : Parcelable
