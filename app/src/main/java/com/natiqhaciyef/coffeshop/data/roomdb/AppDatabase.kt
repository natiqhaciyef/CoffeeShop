package com.natiqhaciyef.coffeshop.data.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.natiqhaciyef.coffeshop.data.model.CartCoffeeModel
import com.natiqhaciyef.coffeshop.data.model.CoffeeModel

@Database(entities = [CoffeeModel::class, CartCoffeeModel::class], version = 3)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getDao(): AppDao
}
