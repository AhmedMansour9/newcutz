package com.cairocart.Model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SliderHome_Model(
    @SerializedName("data")
    val `data`: List<Slider_Home>,
    @SerializedName("error")
    val error: String,
    @SerializedName("status")
    val status: Boolean
)
{
    @Keep
    data class Slider_Home(
        @SerializedName("image")
        val image: String
    )
}