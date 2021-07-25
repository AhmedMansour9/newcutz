package com.cutzegypt.data.remote.model


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
    @SerializedName("productWeight")
    var addition_id: String?,
) : Parcelable
