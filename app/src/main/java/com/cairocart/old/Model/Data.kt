package com.cairocart.old.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    var `data`: List<DataX>,
    var meta: Meta
): Parcelable