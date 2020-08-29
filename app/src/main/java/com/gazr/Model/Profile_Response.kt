package com.gazr.Model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Profile_Response(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("success")
    var success: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("city")
        var city: City?,
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("customer_address")
        var customerAddress: String?,
        @SerializedName("customer_appartment_number")
        var customerAppartmentNumber: String?,
        @SerializedName("customer_floor_number")
        var customerFloorNumber: String?,
        @SerializedName("customer_home_number")
        var customerHomeNumber: String?,
        @SerializedName("customer_postal_code")
        var customerPostalCode: String?,
        @SerializedName("customer_street")
        var customerStreet: String?,
        @SerializedName("email")
        var email: String?,
        @SerializedName("full_name")
        var fullName: String?,
        @SerializedName("gender")
        var gender: String?,
        @SerializedName("gift_id")
        var giftId: String?,
        @SerializedName("image")
        var image: String?,
        @SerializedName("is_gift_used")
        var isGiftUsed: String?,
        @SerializedName("phone")
        var phone: String?,
        @SerializedName("promocode")
        var promocode: String?,
        @SerializedName("state")
        var state: State?,
        @SerializedName("status")
        var status: String?,
        @SerializedName("total_wallet")
        var totalWallet: Int?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class City(
            @SerializedName("id")
            var id: Int?,
            @SerializedName("info_receive_point")
            var infoReceivePoint: String?,
            @SerializedName("name")
            var name: String?
        ) : Parcelable

        @SuppressLint("ParcelCreator")
        @Parcelize
        data class State(
            @SerializedName("id")
            var id: Int?,
            @SerializedName("name")
            var name: String?,
            @SerializedName("price")
            var price: Int?
        ) : Parcelable
    }
}