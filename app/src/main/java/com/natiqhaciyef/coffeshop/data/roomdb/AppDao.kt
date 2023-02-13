package com.natiqhaciyef.coffeshop.data.roomdb

import androidx.room.*
import com.natiqhaciyef.coffeshop.data.model.CoffeeModel

@Dao
interface AppDao {
    @Query("SELECT * FROM CoffeeModel")
    suspend fun getAllCoffee(): List<CoffeeModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCoffee(coffeeModel: CoffeeModel)

    @Delete
    suspend fun deleteCoffee(coffeeModel: CoffeeModel)
}