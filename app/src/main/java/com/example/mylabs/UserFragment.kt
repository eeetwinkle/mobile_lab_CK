package com.example.mylabs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = ActivitiesAdapter { activity ->
            val intent = Intent(requireContext(), ActivityDetailActivity::class.java).apply {
                putExtra("ACTIVITY_ID", activity.id)
                putExtra("SOURCE_FRAGMENT", "MY")
            }
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        loadUserActivities(adapter)
    }

    private fun loadUserActivities(adapter: ActivitiesAdapter) {
        val items = mutableListOf<ActivityItem>()

        items.add(ActivityItem.DateHeader("Вчера"))
        items.add(ActivityItem.Activity(1, "14.32 км", "2 часа 46 минут", "Серфинг", "14 часов назад", "@van_darkholme",false))
        items.add(ActivityItem.Activity(1, "228 м", "14 часов 48 минут", "Качели", "14 часов назад", "@techniquepasha",false))
        items.add(ActivityItem.Activity(1, "10 км", "1 час 10 минут", "Езда на кадилак", "14 часов назад", "@morgen_shtern",false))

        adapter.submitList(items)
    }
}
