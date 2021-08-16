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
    private val adapter = MyAdapter(fList = mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add = findViewById(R.id.add)
        rec = findViewById(R.id.rec)

        add.setOnClickListener { startActivity(Intent(this, AddTest::class.java)) }

        fDao = Room.databaseBuilder(applicationContext, AppDataBase::class.java, "fruit.db")
            .allowMainThreadQueries()
            .build()
            .getFruitDao()

        val dAll = findViewById<Button>(R.id.dAll)
        dAll.setOnClickListener {
            fDao.deleteAll()
            adapter.setNewData(fDao.getAllFruits())

        }

        val fList: MutableList<Fruit> = mutableListOf()

//        fList.addAll(list)
        fDao.insertAll(fList)

        rec.layoutManager = LinearLayoutManager(this)

        rec.adapter = adapter
        adapter.listener = object : MyAdapter.Listener {
            override fun onItemClick(index: Int) {

            }

            override fun onDelClick(index: Int, obj: Fruit) {
                fDao.delete(obj)
                fDao.getAllFruits()
                adapter.setNewData(fDao.getAllFruits())
            }

        }
        adapter.setNewData(fDao.getAllFruits())

    }

    override fun onResume() {
        super.onResume()
        adapter.setNewData(fDao.getAllFruits())
    }

}

class MyAdapter(private var fList: MutableList<Fruit>) : RecyclerView.Adapter<MyViewHolder>() {

    var listener: Listener? = null

    interface Listener {
        fun onItemClick(index: Int)
        fun onDelClick(index: Int,obj:Fruit)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    fun setNewData(ls: List<Fruit>) {
        fList.clear()
        fList.addAll(ls)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return fList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fruit = fList[position]
        holder.fruitID.text = fruit.fruitID.toString()
        holder.fruitName.text = fruit.fruitName
        holder.fruitType.text = fruit.fruitType
        holder.itemView.setOnClickListener {
//            listener?.onItemClick()
//            onItemClick?.invoke(viewType)
//            val position = viewHolder.adapterPosition
//            val fruit = fList[position]
//            Toast.makeText(parent.context, "点击了 ${fruit.fruitName}", Toast.LENGTH_SHORT).show()
            listener?.onItemClick(position)

        }
        holder.delFruit.setOnClickListener {
            listener?.onDelClick(position,fruit)
        }
    }

}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val fruitID: TextView = itemView.findViewById(R.id.id)
    val fruitName: TextView = itemView.findViewById(R.id.name)
    val fruitType: TextView = itemView.findViewById(R.id.type)
    val delFruit: Button = itemView.findViewById(R.id.del)
}