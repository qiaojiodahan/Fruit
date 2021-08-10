package com.example.fruittest_one

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class FinishTest : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        val ok = findViewById<Button>(R.id.ok)
        ok.setOnClickListener{startActivity(Intent(this, MainActivity::class.java))}
    }

}