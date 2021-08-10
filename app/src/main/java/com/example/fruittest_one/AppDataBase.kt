package com.example.fruittest_one

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Fruit::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getFruitDao(): FruitDao

}