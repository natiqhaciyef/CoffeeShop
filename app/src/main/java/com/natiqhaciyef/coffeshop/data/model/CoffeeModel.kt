package com.natiqhaciyef.coffeshop.data.model

import java.io.Serializable

data class CoffeeModel(
    var id: Int,
    var name: String,
    var detail: String,
    var image:String,
    var price: Double,
    var size: String,
    var rating: Double,
    var category: String
): Serializable
