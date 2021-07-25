package com.cutzegypt.data.remote.model

import android.annotation.SuppressLint
import android.os.Parcelable
import android.text.TextUtils
import android.util.Patterns.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class RegisterRequest(
    @SerializedName("email")
    var email: String = "",
    @SerializedName("full_name")
    var full_name: String = "",
    @SerializedName("gender")
    var gender: String = "male",
    @SerializedName("password")
    var password: String = "",
    @SerializedName("phone")
    var phone: String = "",

    @SerializedName("deviceType")
    var deviceType: String = "android"
) : Parcelable {
    fun empty() =
        TextUtils.isEmpty(email) && TextUtils.isEmpty(full_name) && TextUtils.isEmpty(gender)
                && TextUtils.isEmpty(password)
                && TextUtils.isEmpty(phone) && !EMAIL_ADDRESS.matcher(
            email
        ).matches()

}