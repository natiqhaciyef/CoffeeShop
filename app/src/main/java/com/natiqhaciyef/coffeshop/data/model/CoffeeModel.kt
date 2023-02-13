package com.natiqhaciyef.coffeshop.data.model

import androidx.room.Entity
import java.io.Serializable

@Entity
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
