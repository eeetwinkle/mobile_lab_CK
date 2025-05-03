package com.example.mylabs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityStart : AppCompatActivity() {

    private var selectedType: ActivityType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val recyclerView = findViewById<RecyclerView>(R.id.activity_type_recycler)

        val activityTypes = listOf(
            ActivityType("Велосипед", R.drawable.ic_bike),
            ActivityType("Бег", R.drawable.ic_run),
            ActivityType("Шаг", R.drawable.ic_walk),
        )

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.adapter = ActivityTypeAdapter(activityTypes) { selected ->
            selectedType = selected
        }

        findViewById<Button>(R.id.start_button).setOnClickListener {
            if (selectedType != null) {
                val intent = Intent(this, ActivityNewActivity::class.java)
                intent.putExtra("ACTIVITY_TYPE", selectedType!!.name)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Выберите тип активности", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

data class ActivityType(val name: String, val iconRes: Int)

class ActivityTypeAdapter(
    private val items: List<ActivityType>,
    private val onItemSelected: (ActivityType) -> Unit
) : RecyclerView.Adapter<ActivityTypeAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.activity_icon)
        val text: TextView = itemView.findViewById(R.id.activity_text)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val previousPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)
                    onItemSelected(items[position])
                }
            }
        }
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

        holder.itemView.isSelected = (position == selectedPosition)
    }

    override fun getItemCount(): Int = items.size
}


