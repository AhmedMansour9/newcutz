package com.hadrmout.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName

@SuppressLint("ParcelCreator")
data class AddToFavouritResponse(
    @SerializedName("data")
    var `data`: String?,
    @SerializedName("message")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
)