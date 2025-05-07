package com.example.mylabs


import android.content.Context
import androidx.room.Room
import com.example.mylabsz.MyDatabase


object Depends {
    lateinit var context: Context
    lateinit var db: MyDatabase

    fun initDatabase() {
        db = Room.databaseBuilder(
            context,
            MyDatabase::class.java,
            "myDB"
        ).fallbackToDestructiveMigration()
            .build()
    }
}