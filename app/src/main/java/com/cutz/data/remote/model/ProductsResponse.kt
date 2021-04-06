package com.cutz.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class ProductsResponse(
    @SerializedName("data")
    var `data`: List<Data>,
    @SerializedName("error")
    var error: String?,
    @SerializedName("status")
    var status: Boolean?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class Data(
        @SerializedName("addtions")
        var addtions: MutableList<Addtion>,
        @SerializedName("weights")
        var weights: MutableList<Weights>,
        @SerializedName("AvgRate")
        var avgRate: Double,
        @SerializedName("BBQ")
        var bBQ: String?,
        @SerializedName("expiration")
        var expiration: String?,
        @SerializedName("chilled")
        var chilled: String?,
        @SerializedName("chilled_img")
        var chilledImg: String?,
        @SerializedName("country")
        var country: String?,
        @SerializedName("country_flag")
        var countryFlag: String?,
        @SerializedName("frozenInstructions")
        var frozenInstructions: String?,
        @SerializedName("frozen")
        var frozen: String?,
        @SerializedName("frozen_img")
        var frozen_img: String?,
        @SerializedName("provenance_description")
        var provenance_description: String?,
        @SerializedName("panSearing_img")
        var panSearing_img: String?,
        @SerializedName("panSearing")
        var panSearing: String?,

        @SerializedName("currency")
        var currency: String,
        @SerializedName("description")
        var description: String,
        @SerializedName("discount")
        var discount: Int,
        @SerializedName("EliteProduct")
        var eliteProduct: String,
        @SerializedName("FinalPrice")
        var finalPrice: String,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("image")
        var image: String?,
        @SerializedName("measr_unit")
        var measr_unit: String?,

        @SerializedName("images")
        var images: MutableList<Image>?,
        @SerializedName("instructions")
        var instructions: List<Instruction>,
        @SerializedName("IsFavoirte")
        var isFavoirte: Int,
        @SerializedName("MainPrice")
        var mainPrice: String,
        @SerializedName("rates")
        var rates: MutableList<Rate>,
        @SerializedName("serve_number")
        var serveNumber: String?,
        @SerializedName("short_description")
        var shortDescription: String,
        @SerializedName("stock")
        var stock: Int,
        @SerializedName("subCategory_id")
        var subCategoryId: Int?,
        @SerializedName("title")
        var title: String
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Addtion(
            @SerializedName("currency")
            var currency: String?,
            @SerializedName("id")
            var id: Int?,
            @SerializedName("price")
            var price: Int?,
            @SerializedName("title")
            var title: String?
        ) : Parcelable

        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Weights(
            @SerializedName("id")
            var id: Int,
            @SerializedName("weight")
            var weight: String,
            @SerializedName("price")
            var price: String,
            @SerializedName("stock")
            var stock: String,
        ) : Parcelable

        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Image(
            @SerializedName("id")
            var id: Int,
            @SerializedName("image")
            var image: String,
            @SerializedName("product_id")
            var productId: Int?
        ) : Parcelable

        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Instruction(
            @SerializedName("description")
            var description: String,
            @SerializedName("id")
            var id: Int?,
            @SerializedName("image")
            var image: String?,
            @SerializedName("title")
            var title: String
        ) : Parcelable

        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Rate(
            @SerializedName("customer")
            var customer: Customer?,
            @SerializedName("feedback")
            var feedback: String?,
            @SerializedName("id")
            var id: Int?,
            @SerializedName("rate")
            var rate: Int?
        ) : Parcelable {
            @SuppressLint("ParcelCreator")
            @Parcelize
            data class Customer(
                @SerializedName("created_at")
                var createdAt: CreatedAt?,
                @SerializedName("email")
                var email: String?,
                @SerializedName("id")
                var id: Int?,
                @SerializedName("image")
                var image: String?,
                @SerializedName("name")
                var name: String?,
                @SerializedName("phone")
                var phone: String?
            ) : Parcelable {
                @SuppressLint("ParcelCreator")
                @Parcelize
                data class CreatedAt(
                    @SerializedName("date")
                    var date: String?,
                    @SerializedName("timezone")
                    var timezone: String?,
                    @SerializedName("timezone_type")
                    var timezoneType: Int?
                ) : Parcelable
            }
        }
    }
}