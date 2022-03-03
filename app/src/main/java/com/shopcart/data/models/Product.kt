package com.shopcart.data.models

data class Product(
    val id: String? = null,
    val category: String? = null,
    val name: String? = null,
    val description: String? = null,
    var quantity: Int? = null,
    val price: Double? = null,
    val offer: String? = null,
    val photosUrl: ArrayList<String>? = null,
    var isFavorite: Boolean? = false

)



