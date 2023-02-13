package com.natiqhaciyef.coffeshop.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class CoffeeModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var detail: String,
    var image:String,
    var price: Double,
    var size: String,
    var rating: Double,
    var category: String
): Serializable
