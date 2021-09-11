package com.hadrmout.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class BranchesResponse(
    @SerializedName("data")
    var `data`: MutableList<Data>?,
    @SerializedName("error")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("title")
        var title: String,
        @SerializedName("address")
        var address: String,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("lat")
        var lat: Double?,
        @SerializedName("lng")
        var lng: Double?

    ) : Parcelable {
        override fun toString(): String {
            return title
        }
    }
}