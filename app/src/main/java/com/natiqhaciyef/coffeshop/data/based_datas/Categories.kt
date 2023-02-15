package com.natiqhaciyef.coffeshop.data.based_datas

import com.natiqhaciyef.coffeshop.data.model.CategoryModel

object Categories {
    val list = mutableListOf(
        CategoryModel("All",true),
        CategoryModel("Hot Drinks", false),
        CategoryModel("Cold Drinks", false),
        CategoryModel("Bakery",false),
        CategoryModel("Desert", false)
    )
}