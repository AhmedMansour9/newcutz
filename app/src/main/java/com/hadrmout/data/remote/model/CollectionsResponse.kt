package com.hadrmout.data.remote.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class CollectionsResponse(
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
        @SerializedName("description")
        var description: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("image")
        var image: String?,
        @SerializedName("products")
        var products: List<ProductsResponse.Data>,
        @SerializedName("title")
        var title: String?
    ) : Parcelable {
        @SuppressLint("ParcelCreator")
        @Parcelize
        data class Product(
            @SerializedName("addtions")
            var addtions: List<Addtion?>?,
            @SerializedName("AvgRate")
            var avgRate: Int?,
            @SerializedName("BBQ")
            var bBQ: String?,
            @SerializedName("country")
            var country: String?,
            @SerializedName("country_flag")
            var countryFlag: String?,
            @SerializedName("currency")
            var currency: String?,
            @SerializedName("description")
            var description: String?,
            @SerializedName("discount")
            var discount: Int?,
            @SerializedName("EliteProduct")
            var eliteProduct: String?,
            @SerializedName("FinalPrice")
            var finalPrice: Int?,
            @SerializedName("id")
            var id: Int?,
            @SerializedName("image")
            var image: String?,
            @SerializedName("images")
            var images: List<Image?>?,
            @SerializedName("instructions")
            var instructions: List<Instruction?>?,
            @SerializedName("IsFavoirte")
            var isFavoirte: Int?,
            @SerializedName("MainPrice")
            var mainPrice: Int?,
            @SerializedName("rates")
            var rates: List<Rate?>?,
            @SerializedName("serve_number")
            var serveNumber: Int?,
            @SerializedName("short_description")
            var shortDescription: String?,
            @SerializedName("stock")
            var stock: Int?,
            @SerializedName("subCategory_id")
            var subCategoryId: Int?,
            @SerializedName("title")
            var title: String?
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
            data class Image(
                @SerializedName("id")
                var id: Int?,
                @SerializedName("image")
                var image: String?,
                @SerializedName("product_id")
                var productId: Int?
            ) : Parcelable

            @SuppressLint("ParcelCreator")
            @Parcelize
            data class Instruction(
                @SerializedName("description")
                var description: String?,
                @SerializedName("id")
                var id: Int?,
                @SerializedName("image")
                var image: String?,
                @SerializedName("title")
                var title: String?
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
}