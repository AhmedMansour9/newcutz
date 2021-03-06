package com.hadrmout.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class CountriesResponse(
    @SerializedName("data")
    var `data`: MutableList<Data?>?,
    @SerializedName("message")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("states")
        var city: MutableList<City>?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("name")
        var title: String?

    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class City(
            @SerializedName("id")
            var id: Int?,
            @SerializedName("name")
            var title: String?

        ) : Parcelable {
            @SuppressLint("ParcelCreator")
            @Parcelize
            data class State(
                @SerializedName("currency")
                var currency: String?,
                @SerializedName("id")
                var id: Int?,
                @SerializedName("price")
                var price: Double,
                @SerializedName("title")
                var title: String?

            ) : Parcelable {
                override fun toString(): String {
                    return "$title"
                }
            }

            override fun toString(): String {
                return "$title"
            }
        }

        override fun toString(): String {
            return "$title"
        }
    }

}