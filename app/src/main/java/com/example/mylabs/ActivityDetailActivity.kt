package com.example.mylabs

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class   ActivityDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    @Suppress("MissingSuperCall")
    override fun onBackPressed() {
        val source = intent.getStringExtra("SOURCE_FRAGMENT")

        val intent = Intent(this, MyActivity::class.java).apply {
            putExtra("NAVIGATE_TO", source)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()

    }



}