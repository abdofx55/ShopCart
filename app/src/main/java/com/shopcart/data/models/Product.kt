package com.shopcart.data.models

data class Product(
    val id: String = "",
    val category: String = "",
    val name: String = "",
    val description: String = "",
    var quantity: Int = 0,
    val price: Double = 0.0,
    val offer: String = "",
    val photosUrl: ArrayList<String> = ArrayList(),
    val isFeatured: Boolean = false,
    val isBestSell: Boolean = false,
    val isFavorite: Boolean = false
)



