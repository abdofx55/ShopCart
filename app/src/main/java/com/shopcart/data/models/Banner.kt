package com.shopcart.data.models

class Banner {
    var header: String? = null
    var body: String? = null
    var photoUrl: String? = null

    constructor()
    constructor(header: String?, body: String?, photoUrl: String?) {
        this.header = header
        this.body = body
        this.photoUrl = photoUrl
    }
}