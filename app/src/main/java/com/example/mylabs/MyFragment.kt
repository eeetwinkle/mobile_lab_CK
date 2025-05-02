package com.example.mylabs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class MyFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = ActivitiesAdapter { activity ->
            val intent = android.content.Intent(requireContext(), ActivityDetailActivity::class.java).apply {
                putExtra("ACTIVITY_ID", activity.id)
                putExtra("SOURCE_FRAGMENT", "USER")
            }
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        loadMyActivities(adapter)

        val startButton = view.findViewById<ImageView>(R.id.start_activity)
        startButton.setOnClickListener {
            val intent = Intent(requireContext(), ActivityStartActivity::class.java)
            startActivity(intent)
        }

    }

    private fun loadMyActivities(adapter: ActivitiesAdapter) {
        val items = mutableListOf<ActivityItem>()

        items.add(ActivityItem.DateHeader("Вчера"))
        items.add(ActivityItem.Activity(1, "14.32 км", "2 часа 46 минут", "Серфинг", "14 часов назад", "", true))

        items.add(ActivityItem.DateHeader("Май 2022 года"))
        items.add(ActivityItem.Activity(3, "1 000 м", "60 минут", "Велосипед", "29.05.2022", "", true))

        adapter.submitList(items)
    }
}
