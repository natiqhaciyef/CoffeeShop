package com.natiqhaciyef.coffeshop.data.roomdb

import androidx.room.*
import com.natiqhaciyef.coffeshop.data.model.CartCoffeeModel
import com.natiqhaciyef.coffeshop.data.model.CoffeeModel

@Dao
interface AppDao {
    @Query("SELECT * FROM CoffeeModel")
    suspend fun getAllCoffee(): List<CoffeeModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCoffee(coffeeModel: CoffeeModel)

    @Delete
    suspend fun deleteCoffee(coffeeModel: CoffeeModel)

    @Query("SELECT * FROM CartCoffeeModel")
    suspend fun getAllCoffeeCart(): List<CartCoffeeModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCoffeeCart(coffeeModel: CartCoffeeModel)

    @Delete
    suspend fun deleteCoffeeCart(coffeeModel: CartCoffeeModel)
}