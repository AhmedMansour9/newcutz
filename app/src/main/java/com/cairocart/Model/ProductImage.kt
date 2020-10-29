package com.cairocart.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductImage(
    var id: Int,
    var image: String,
    var product_id: Int
): Parcelable