package com.natiqhaciyef.coffeshop.data.source

import com.natiqhaciyef.coffeshop.data.model.CoffeeModel
import com.natiqhaciyef.coffeshop.data.roomdb.AppDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppDataSource(val dao: AppDao) {

    suspend fun getAllCoffee() = withContext(Dispatchers.IO){
       dao.getAllCoffee()
    }

    suspend fun insertCoffee(coffeeModel: CoffeeModel) = withContext(Dispatchers.IO){
        dao.insertCoffee(coffeeModel)
    }

    suspend fun deleteCoffee(coffeeModel: CoffeeModel) = withContext(Dispatchers.IO){
        dao.deleteCoffee(coffeeModel)
    }

}