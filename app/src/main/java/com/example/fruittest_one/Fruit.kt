package com.example.fruittest_one

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Fruit")
data class Fruit(

    @PrimaryKey(autoGenerate = true)
    var fruitID:Int?=null,
    @ColumnInfo(name = "name")
    var fruitName:String?,
    @ColumnInfo(name = "type")
    var fruitType:String

)