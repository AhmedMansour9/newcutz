package com.cutz.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
class RequestCreateOrder (
    @SerializedName("city_id")
    var city_id: String?,
    @SerializedName("state_id")
    var state_id: String?,
    @SerializedName("code")
    var code: String?,
    @SerializedName("customer_name")
    var customer_name: String?,
    @SerializedName("customer_phone")
    var customer_phone: String?,
    @SerializedName("customer_city")
    var customer_city: String?,
    @SerializedName("customer_region")
    var customer_region: String?,
    @SerializedName("customer_street")
    var customer_street: String?,
    @SerializedName("customer_home_number")
    var customer_home_number: String?,
    @SerializedName("customer_floor_number")
    var customer_floor_number: String?,
    @SerializedName("customer_email")
    var customer_email: String?,
    @SerializedName("customer_address")
    var customer_address: String?,
    @SerializedName("payment_method")
    var payment_method: String?,
    @SerializedName("device_type")
    var device_type: String?

): Parcelable
