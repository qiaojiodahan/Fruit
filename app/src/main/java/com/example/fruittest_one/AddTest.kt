package com.example.fruittest_one

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.room.Room

class AddTest : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)



        val back = findViewById<Button>(R.id.back)
        back.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }

        val fDao: FruitDao =
            Room.databaseBuilder(applicationContext, AppDataBase::class.java, "fruit.db")
                .allowMainThreadQueries()
                .build()
                .getFruitDao()

        val send = findViewById<Button>(R.id.send)

        send.setOnClickListener {

            val fruitN = findViewById<EditText>(R.id.fruitN)
            val fName = fruitN.text.toString()

            val fruitT = findViewById<EditText>(R.id.fruitT)
            val fType = fruitT.text.toString()

            val addFruit = Fruit(fDao.getAllFruits().size+1, fName, fType)
            val fList: MutableList<Fruit> = mutableListOf()

            fList.add(addFruit)
            fDao.insertAll(fList)
            val count = fDao.insertAll(fList)

            if (count.isNotEmpty()) {
                startActivity(Intent(this, FinishTest::class.java))
            }

            fruitN.text = null
            fruitT.text = null
        }

    }

}