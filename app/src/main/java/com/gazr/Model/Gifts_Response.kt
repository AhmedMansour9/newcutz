package com.gazr.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Gifts_Response(
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
        @SerializedName("gift_value")
        var giftValue: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("type")
        var type: String?
    ) : Parcelable
}