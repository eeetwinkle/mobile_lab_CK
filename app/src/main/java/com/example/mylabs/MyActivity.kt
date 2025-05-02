package com.example.mylabs

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MyActivity : AppCompatActivity() {

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.getStringExtra("NAVIGATE_TO")?.let { tag ->
            val fragment = when (tag) {
                "MY" -> MyFragment()
                "USER" -> UserFragment()
                else -> null
            }

            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it, tag)
                    .commit()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ActivityFragment(), "activity")
                .commit()
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_activity -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_container, ActivityFragment(), "activity")
                    transaction.commit()
                    true
                }
                R.id.menu_profile -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_container, ProfileActivity(), "profile")
                    transaction.commit()
                    true
                }
                else -> false
            }
        }
    }
}
