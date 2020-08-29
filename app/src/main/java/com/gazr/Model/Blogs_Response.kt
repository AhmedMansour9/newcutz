package com.gazr.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Blogs_Response(
    @SerializedName("data")
    var `data`: List<Data?>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("success")
    var success: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("description")
        var description: String?,
        @SerializedName("image")
        var image: String?,
        @SerializedName("short_description")
        var shortDescription: String?,
        @SerializedName("title")
        var title: String?
    ) : Parcelable
}