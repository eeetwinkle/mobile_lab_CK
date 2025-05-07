package com.example.mylabs
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

enum class ActivityType(val displayName: String) {
    RUNNING("Бег"),
    BICYCLE("Велосипед"),
    WALKING("Шаг")
}

@Entity(tableName = "activities")
@TypeConverters(ActivityTypeConverter::class)
data class Activity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val user: String,
    val type: ActivityType,
    val startTime: Long = System.currentTimeMillis(),
    val endTime: Long = System.currentTimeMillis() + 100000
)