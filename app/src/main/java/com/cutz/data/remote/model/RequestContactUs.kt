package com.cutz.data.remote.model

import android.annotation.SuppressLint
import android.os.Parcelable
import android.text.TextUtils
import android.util.Patterns
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.File


@SuppressLint("ParcelCreator")
@Parcelize
class RequestContactUs (
    @SerializedName("email")
    var email: String = "",
    @SerializedName("name")
    var full_name: String = "",
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("message")
    var message: String = ""
    ) : Parcelable {
        fun empty() =
            TextUtils.isEmpty(email) && TextUtils.isEmpty(full_name) && TextUtils.isEmpty(message)
                    && TextUtils.isEmpty(phone) && !Patterns.EMAIL_ADDRESS.matcher(
                email
            ).matches()
}