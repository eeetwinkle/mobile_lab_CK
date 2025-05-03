package com.example.mylabs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ActivityNewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_activity)

        val actionTextView = findViewById<TextView>(R.id.action_text)
        val finishButton = findViewById<ImageButton>(R.id.finish_button)

        val activityType = intent.getStringExtra("ACTIVITY_TYPE")
        actionTextView.text = activityType ?: "Неизвестно"

        finishButton.setOnClickListener {
            val intent = Intent(this, MyActivity::class.java)
            startActivity(intent)
        }
    }
}
