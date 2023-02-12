package com.natiqhaciyef.coffeshop.ui.adapter.behavior

import com.natiqhaciyef.coffeshop.data.model.CoffeeModel

interface CoffeeAdapterClickListener {
    fun setOnClickListener(coffeeModel: CoffeeModel)
}