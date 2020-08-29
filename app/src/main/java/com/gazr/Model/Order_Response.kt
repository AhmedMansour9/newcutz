package com.gazr.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Order_Response(
    @SerializedName("data")
    val data: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
) : Parcelable
