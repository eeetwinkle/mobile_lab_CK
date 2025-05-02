package com.example.mylabs

sealed class ActivityItem {
    data class DateHeader(val date: String) : ActivityItem()
    data class Activity(
        val id: Int,
        val length: String,
        val time: String,
        val name: String,
        val when_was: String,
        val user_name: String,
        val isMyActivity: Boolean
    ) : ActivityItem()
}