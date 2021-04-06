package com.cutz.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class AddToCartResponse(
    @SerializedName("data")
    var `data`: String?,
    @SerializedName("status")
    var status: Boolean,
    @SerializedName("error")
    var error: String
) : Parcelable