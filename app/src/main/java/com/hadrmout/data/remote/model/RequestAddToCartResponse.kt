package com.hadrmout.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class RequestAddToCartResponse(
    @SerializedName("qty")
    var qty: Int,
    @SerializedName("weight_id")
    var weight_id: String?=null,
    @SerializedName("additions")
    var addition_id: MutableList<Int>?=null,
) : Parcelable
