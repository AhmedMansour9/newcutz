package com.hadrmout.data.remote.model


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class AboutUsResponse(
    @SerializedName("data")
    var `data`: Data,
    @SerializedName("message")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("description")
        var description: String?,
        @SerializedName("image")
        var image: String?,

        @SerializedName("title")
        var title: String?
    ) : Parcelable
}