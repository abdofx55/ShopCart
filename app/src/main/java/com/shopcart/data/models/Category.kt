package com.shopcart.data.models

class Category {
    var id: String? = null
    var name: String? = null
    var photoUrl: String? = null
    var colorFilter: Int? = null

    constructor()
    constructor(name: String?, photoUrl: String?, colorFilter: Int?) {
        this.name = name
        this.photoUrl = photoUrl
        this.colorFilter = colorFilter
    }
}