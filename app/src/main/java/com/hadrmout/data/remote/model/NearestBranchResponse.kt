package com.hadrmout.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class NearestBranchResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("error")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("address")
        var address: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("lat")
        var lat: Double?,
        @SerializedName("lng")
        var lng: Double?,
        @SerializedName("title")
        var title: String?
    ) : Parcelable
}