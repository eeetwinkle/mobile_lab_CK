package com.example.mylabs
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylabsz.MyDatabase
import kotlinx.coroutines.launch


class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Depends.db

    val allActivities = db.getActivityDao().getAllActivities()

    fun addMyActivity(typeActivity: String) {
        viewModelScope.launch {
            try {
                val activityType = when (typeActivity) {
                    "Велосипед" -> ActivityType.BICYCLE
                    "Бег" -> ActivityType.RUNNING
                    "Шаг" -> ActivityType.WALKING
                    else -> throw IllegalArgumentException("Unknown activity type")
                }

                val activity = Activity(
                    user = "me",
                    type = activityType,
                    startTime = System.currentTimeMillis(),
                    endTime = System.currentTimeMillis() + 300000
                )
                db.getActivityDao().add(activity)
            } catch (e: Exception) {
                Log.e("MyViewModel", "Error adding activity", e)
            }
        }
    }
}