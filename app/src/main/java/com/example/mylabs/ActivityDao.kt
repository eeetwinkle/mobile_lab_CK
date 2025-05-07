package com.example.mylabs
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import androidx.lifecycle.LiveData


@Dao
interface ActivityDao {
    @Insert
    suspend fun add(activity: Activity)

    @Delete
    suspend fun delete(activity: Activity)

    @Update
    suspend fun update(activity: Activity)

    @Query("SELECT * FROM activities")
    fun getAllActivities(): LiveData<List<Activity>>
}