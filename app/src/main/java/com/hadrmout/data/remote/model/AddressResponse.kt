package com.hadrmout.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class AddressResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("city")
        var city: City?,
        @SerializedName("floor_number")
        var floorNumber: String?,
        @SerializedName("frirstName")
        var frirstName: String?,
        @SerializedName("home_number")
        var homeNumber: String?,
        @SerializedName("id")
        var id: Int,

        @SerializedName("lastName")
        var lastName: String?,
        @SerializedName("phone")
        var phone: String?,

        @SerializedName("street")
        var street: String?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class City(
            @SerializedName("id")
            var id: Int?,
            @SerializedName("state")
            var state: List<State>?,
            @SerializedName("title")
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
                var price: Int?,
                @SerializedName("title")
                var title: String?
            ) : Parcelable
        }
    }
}