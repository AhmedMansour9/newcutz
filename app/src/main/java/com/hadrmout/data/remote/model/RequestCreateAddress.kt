package com.hadrmout.data.remote.model

import com.google.gson.annotations.SerializedName

class RequestCreateAddress(
    @SerializedName("frirstName")
    var frirstName: String?,
    @SerializedName("lastName")
    var lastName: String?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("city_id")
    var city_id: String?,
    @SerializedName("state_id")
    var state_id: String?,
    @SerializedName("home_number")
    var customer_home_number: String?,
    @SerializedName("floor_number")
    var customer_floor_number: String?,
    @SerializedName("street")
    var customer_address: String?,
    @SerializedName("title")
    var title: String?,

)