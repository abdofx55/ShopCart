package com.shopcart.data.models

data class User(
    var id: String? = null,
    var email: String? = null,
    var name: String? = null,
    var gender: Int? = 0,
    var city: String? = null,
    var address: String? = null,
    var mobile: String? = null,
)