package com.hadrmout.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class CreateAddressResponse(
    @SerializedName("message")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable