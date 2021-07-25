package com.cutzegypt.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class AboutUsResponse(
    @SerializedName("data")
    var `data`: List<Data>,
    @SerializedName("error")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("description")
        var description: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("image")
        var image: String?,

        @SerializedName("title")
        var title: String?
    ) : Parcelable
}