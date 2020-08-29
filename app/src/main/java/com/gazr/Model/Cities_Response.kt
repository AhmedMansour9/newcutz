package com.gazr.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Cities_Response(
    @SerializedName("data")
    var `data`: List<Data>,
    @SerializedName("message")
    var message: String?,
    @SerializedName("success")
    var success: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("name")
        var city: String,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("price")
        var price: String,
        @SerializedName("info_receive_point")
        var info_receive_point: String

    ) : Parcelable
}