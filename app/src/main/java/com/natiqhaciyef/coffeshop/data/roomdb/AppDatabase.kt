package com.natiqhaciyef.coffeshop.data.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.natiqhaciyef.coffeshop.data.model.CoffeeModel

@Database(entities = [CoffeeModel::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getDao(): AppDao
}
