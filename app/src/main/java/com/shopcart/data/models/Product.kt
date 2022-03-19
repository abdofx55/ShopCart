package com.shopcart.data.models

data class Product(
    val id: String,
    val category: String? = null,
    val name: String,
    val description: String? = null,
    var quantity: Int? = null,
    val price: Double,
    val offer: String? = null,
    val photosUrl: ArrayList<String>? = null,
    var isFavorite: Boolean = false

)



