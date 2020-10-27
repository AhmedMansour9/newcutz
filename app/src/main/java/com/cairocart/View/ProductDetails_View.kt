package com.cairocart.View

import com.cairocart.Model.AllProducts_Response


interface ProductDetails_View {

    fun Details(detailsProduct: AllProducts_Response.Data.Data)

    fun DetailsProducts(detailsProduct: AllProducts_Response.Data.Data)

}