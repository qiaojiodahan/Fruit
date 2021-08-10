package com.example.fruittest_one

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FruitDao:BaseDao<Fruit>{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(element: Fruit)

    @Query("select * from Fruit")
    fun getAllFruits():MutableList<Fruit>

    @Query("select * from Fruit where fruitID = :fruitID")
    fun getFruit(fruitID:Int):Fruit

    @Query("select * from Fruit order by fruitID desc")
    fun getAllByDateDesc():MutableList<Fruit>

    @Query("delete from Fruit")
    fun deleteAll()

}