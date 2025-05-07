package com.example.mylabsz

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mylabs.Activity
import com.example.mylabs.ActivityDao
import com.example.mylabs.ActivityTypeConverter

@Database(
    entities = [Activity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(ActivityTypeConverter::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getActivityDao(): ActivityDao
}