package com.example.mylabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityStartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_activity_start)

        val recyclerView = findViewById<RecyclerView>(R.id.activity_type_recycler)
        val activityTypes = listOf(
            ActivityType("Велосипед", R.drawable.ic_bike),
            ActivityType("Бег", R.drawable.ic_run),
            ActivityType("Шаг", R.drawable.ic_walk),
        )

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ActivityTypeAdapter(activityTypes)

        findViewById<Button>(R.id.start_button).setOnClickListener {

        }
    }
}

data class ActivityType(val name: String, val iconRes: Int)

class ActivityTypeAdapter(private val items: List<ActivityType>) :
    RecyclerView.Adapter<ActivityTypeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon = itemView.findViewById<ImageView>(R.id.activity_icon)
        val text = itemView.findViewById<TextView>(R.id.activity_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity_type, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.icon.setImageResource(item.iconRes)
        holder.text.text = item.name
    }

    override fun getItemCount() = items.size
}