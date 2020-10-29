package com.cairocart.Model


 data class CatModel(
    val id: Int,
    val image: String?,
    val isActive: Boolean,
    val level: Int,
    val name: String,
    val productCount: Int,
    val parentId: Int,
    val isExpanded: Boolean = false
)

