package com.hadrmout.data.remote.model

import android.annotation.SuppressLint
import android.os.Parcelable
import android.text.TextUtils
import android.util.Patterns
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.File

@SuppressLint("ParcelCreator")
@Parcelize
class RequestEditProdile (
    @SerializedName("email")
    var email: String = "",
    @SerializedName("full_name")
    var full_name: String = "",
    @SerializedName("gender")
    var gender: String = "male",
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("img")
    var image: File?=null,
    @SerializedName("deviceType")
    var deviceType: String = "android"
) : Parcelable {
    fun empty() =
        TextUtils.isEmpty(email) && TextUtils.isEmpty(full_name) && TextUtils.isEmpty(gender)
                 && TextUtils.isEmpty(phone) && !Patterns.EMAIL_ADDRESS.matcher(
            email
        ).matches()
}

