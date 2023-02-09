package com.natiqhaciyef.coffeshop.ui.adapter.behavior

import com.natiqhaciyef.coffeshop.data.model.CategoryModel

interface CategoryClickListener {
    fun setOnCategorySelected(category: CategoryModel)
}