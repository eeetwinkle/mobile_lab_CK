package com.example.mylabs

import androidx.room.TypeConverter

class ActivityTypeConverter {
    @TypeConverter
    fun fromActivityType(type: ActivityType): String {
        return type.name
    }

    @TypeConverter
    fun toActivityType(name: String): ActivityType {
        return ActivityType.valueOf(name)
    }
}