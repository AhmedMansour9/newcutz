package com.hadrmout.data.remote.model


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class MyOrdersResponse(
    @SerializedName("data")
    var `data`: MutableList<Data>,
    @SerializedName("message")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(

        @SerializedName("id")
        var id: Int?,
        @SerializedName("details")
        var orderDetials: MutableList<OrderDetial>,

        @SerializedName("status")
        var status: String?,

        @SerializedName("total")
        var total: Int?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class CreatedAt(
            @SerializedName("date")
            var date: String?,
            @SerializedName("timezone")
            var timezone: String?,
            @SerializedName("timezone_type")
            var timezoneType: Int?
        ) : Parcelable

        @SuppressLint("ParcelCreator")
        @Parcelize
        data class OrderDetial(
            @SerializedName("price")
            var price: String?,
            @SerializedName("name")
            var name: String?,
            @SerializedName("image")
            var image: String?,
            @SerializedName("qty")
            var qty: String?
        ) : Parcelable

    }
}