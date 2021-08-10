package com.example.fruittest_one

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class MainActivity : AppCompatActivity() {

    /*private val list = listOf(
        Fruit(1, "苹果", "红富士"),
        Fruit(2, "香蕉", "西贡蕉"),
        Fruit(3, "桃子", "水蜜桃"),
        Fruit(4, "榴莲", "猫山王"),
        Fruit(5, "樱桃", "黑珍珠"),
        Fruit(6, "西瓜", "8424")
    )*/

    private lateinit var add: Button
    private lateinit var rec: RecyclerView
    private lateinit var fDao: FruitDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add = findViewById(R.id.add)
        rec = findViewById(R.id.rec)

        add.setOnClickListener { startActivity(Intent(this, AddTest::class.java)) }

        fDao =Room.databaseBuilder(applicationContext, AppDataBase::class.java, "fruit.db")
                .allowMainThreadQueries()
                .build()
                .getFruitDao()

        val dAll = findViewById<Button>(R.id.dAll)
        dAll.setOnClickListener { fDao.deleteAll() }

        val fList: MutableList<Fruit> = mutableListOf()

//        fList.addAll(list)
        fDao.insertAll(fList)


        rec.layoutManager = LinearLayoutManager(this)

        rec.adapter = MyAdapter(fDao.getAllFruits())
    }

    override fun onResume() {
        super.onResume()
        rec.adapter = MyAdapter(fDao.getAllFruits())
    }

}

class MyAdapter(private var fList: MutableList<Fruit>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return fList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fruit = fList[position]
        holder.fruitID.text = fruit.fruitID.toString()
        holder.fruitName.text = fruit.fruitName
        holder.fruitType.text = fruit.fruitType
    }

}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val fruitID: TextView = itemView.findViewById(R.id.id)
    val fruitName: TextView = itemView.findViewById(R.id.name)
    val fruitType: TextView = itemView.findViewById(R.id.type)
}