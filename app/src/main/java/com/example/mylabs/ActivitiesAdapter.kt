package com.example.mylabs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActivitiesAdapter(
    private val onItemClick: (ActivityItem.Activity) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<ActivityItem> = emptyList()

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ActivityItem.DateHeader -> TYPE_HEADER
            is ActivityItem.Activity -> TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> {
                val view = inflater.inflate(R.layout.item_date_header, parent, false)
                DateHeaderViewHolder(view)
            }
            TYPE_ITEM -> {
                val view = inflater.inflate(R.layout.item_activity, parent, false)
                ActivityViewHolder(view, onItemClick)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ActivityItem.DateHeader -> (holder as DateHeaderViewHolder).bind(item)
            is ActivityItem.Activity -> (holder as ActivityViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<ActivityItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    class DateHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val dateText: TextView = view.findViewById(R.id.dateText)

        fun bind(item: ActivityItem.DateHeader) {
            dateText.text = item.date
        }
    }

    class ActivityViewHolder(
        view: View,
        private val onItemClick: (ActivityItem.Activity) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val lengthText: TextView = view.findViewById(R.id.length)
        private val timeText: TextView = view.findViewById(R.id.time)
        private val nameText: TextView = view.findViewById(R.id.activity_name)
        private val whenWasText: TextView = view.findViewById(R.id.when_was)
        private val userName: TextView = view.findViewById(R.id.user_name)

        fun bind(item: ActivityItem.Activity) {
            lengthText.text = item.length
            timeText.text = item.time
            nameText.text = item.name
            whenWasText.text = item.when_was
            userName.text = item.user_name

            itemView.setOnClickListener { onItemClick(item) }
        }
    }
}