package com.cairocart.Model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class SendRegisterRequest_Model(
    @SerializedName("customer")
    var customer: Customer?,
    @SerializedName("password")
    var password: String?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Customer(
        @SerializedName("email")
        var email: String?,
//        @SerializedName("extension_attributes")
//        var extensionAttributes: ExtensionAttributes?,
        @SerializedName("firstname")
        var firstname: String?,
        @SerializedName("lastname")
        var lastname: String?
    ) : Parcelable {
//        @SuppressLint("ParcelCreator")
//        @Parcelize
//        data class ExtensionAttributes(
//            @SerializedName("is_subscribed")
//            var isSubscribed: Boolean?
//        ) : Parcelable
    }
}
