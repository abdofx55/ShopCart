package com.shopcart.data.models

class Product {
    var id: String? = null
    var name: String? = null
    var price: Double? = null
    var photosUrl: ArrayList<String>? = null

    constructor()
    constructor(name: String?, price: Double?, photosUrl: ArrayList<String>?) {
        this.name = name
        this.price = price
        this.photosUrl = photosUrl
    }
}