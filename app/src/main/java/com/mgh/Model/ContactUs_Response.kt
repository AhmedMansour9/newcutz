package com.mgh.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class ContactUs_Response(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: String,
    @SerializedName("status")
    val status: Boolean
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("address")
        val address: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("map")
        val map: String,
        @SerializedName("phone_1")
        val phone1: String,
        @SerializedName("phone_2")
        val phone2: String
    ) : Parcelable
}