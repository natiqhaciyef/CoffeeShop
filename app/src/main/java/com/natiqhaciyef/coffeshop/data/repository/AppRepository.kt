package com.natiqhaciyef.coffeshop.data.repository

import com.natiqhaciyef.coffeshop.data.model.CartCoffeeModel
import com.natiqhaciyef.coffeshop.data.model.CoffeeModel
import com.natiqhaciyef.coffeshop.data.source.AppDataSource

class AppRepository(val ds: AppDataSource) {

    suspend fun getAllCoffee() = ds.getAllCoffee()

    suspend fun insertCoffee(coffeeModel: CoffeeModel) = ds.insertCoffee(coffeeModel)

    suspend fun deleteCoffee(coffeeModel: CoffeeModel) = ds.deleteCoffee(coffeeModel)

    suspend fun getAllCoffeeCart() = ds.getAllCoffeeCart()

    suspend fun insertCoffeeCart(cartCoffeeModel: CartCoffeeModel) = ds.insertCoffeeCart(cartCoffeeModel)

    suspend fun deleteCoffeeCart(cartCoffeeModel: CartCoffeeModel) = ds.deleteCoffeeCart(cartCoffeeModel)
}