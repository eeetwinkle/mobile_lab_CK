package com.example.mylabs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MyFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ActivitiesAdapter
    private lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ActivitiesAdapter { activity ->
            val intent = Intent(requireContext(), ActivityDetailActivity::class.java).apply {
                putExtra("ACTIVITY_ID", activity.id)
                putExtra("SOURCE_FRAGMENT", "USER")
            }
            startActivity(intent)
        }

        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(MyViewModel::class.java)

        viewModel.allActivities.observe(viewLifecycleOwner) { newActivitiesList ->
            Log.d("MyFragment", "newActivitiesList size: ${newActivitiesList.size}")

            val filteredList = newActivitiesList.filter { it.user == "me" }
            val items = transformToActivityItems(filteredList)
            adapter.submitList(items)
        }

        val startButton = view.findViewById<ImageView>(R.id.start_activity)
        startButton.setOnClickListener {
            val intent = Intent(requireContext(), ActivityStart::class.java)
            startActivity(intent)
        }
    }

    private fun transformToActivityItems(activities: List<Activity>): List<ActivityItem> {
        if (activities.isEmpty()) return emptyList()

        val grouped = activities.sortedByDescending { it.startTime }
            .groupBy { formatDateHeader(it.startTime) }

        val result = mutableListOf<ActivityItem>()
        for ((date, items) in grouped) {
            result.add(ActivityItem.DateHeader(date))
            result.addAll(items.map {
                val randomKm = Random.nextInt(1, 11)
                val kmString = "$randomKm км"
                ActivityItem.Activity(
                    id = it.id,
                    length = kmString,
                    time = formatDuration(it.startTime, it.endTime),
                    name = it.type.displayName,
                    when_was = formatTimeAgo(it.startTime),
                    user_name = it.user,
                    isMyActivity = it.user == "me"
                )
            })
        }
        return result
    }

    private fun formatDuration(start: Long, end: Long): String {
        val durationMillis = end - start
        val minutes = durationMillis / 60000
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return if (hours > 0) "$hours ч $remainingMinutes мин" else "$minutes мин"
    }

    private fun formatDateHeader(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val todayStart = now - (now % 86400000)
        val yesterdayStart = todayStart - 86400000

        return when {
            timestamp >= todayStart -> "Сегодня"
            timestamp >= yesterdayStart -> "Вчера"
            else -> {
                val sdf = java.text.SimpleDateFormat("MMMM yyyy", java.util.Locale("ru"))
                sdf.format(java.util.Date(timestamp))
            }
        }
    }

    private fun formatTimeAgo(timestamp: Long): String {
        val diff = System.currentTimeMillis() - timestamp
        val minutes = diff / 60000
        return when {
            minutes < 60 -> "$minutes мин назад"
            minutes < 1440 -> "${minutes / 60} ч назад"
            else -> "${minutes / 1440} дн назад"
        }
    }
}


