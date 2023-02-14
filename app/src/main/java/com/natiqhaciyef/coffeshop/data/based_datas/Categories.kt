package com.natiqhaciyef.coffeshop.data.based_datas

import com.natiqhaciyef.coffeshop.data.model.CategoryModel

object Categories {
    val list = mutableListOf(
        CategoryModel("All",false),
        CategoryModel("Hot Drinks", true),
        CategoryModel("Ice Drinks", false),
        CategoryModel("Bakery",false),
        CategoryModel("Desert", false)
    )
}