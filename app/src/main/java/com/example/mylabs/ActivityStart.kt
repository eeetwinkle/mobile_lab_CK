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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class ActivityStart : AppCompatActivity() {
    lateinit var myVM : MyViewModel
    private var selectedType: ActivityTypes? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        Depends.context = applicationContext
        Depends.initDatabase()

        val recyclerView = findViewById<RecyclerView>(R.id.activity_type_recycler)

        val activityTypes = listOf(
            ActivityTypes("Велосипед", R.drawable.ic_bike),
            ActivityTypes("Бег", R.drawable.ic_run),
            ActivityTypes("Шаг", R.drawable.ic_walk),
        )

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.adapter = ActivityTypeAdapter(activityTypes) { selected ->
            selectedType = selected
        }
        myVM = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(MyViewModel::class.java)

        findViewById<Button>(R.id.start_button).setOnClickListener {
            if (selectedType != null) {
                lifecycleScope.launch {
                    myVM.addMyActivity(selectedType!!.name)
                }
                val intent = Intent(this, ActivityNewActivity::class.java)
                intent.putExtra("ACTIVITY_TYPE", selectedType!!.name)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Выберите тип активности", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

data class ActivityTypes(val name: String, val iconRes: Int)

class ActivityTypeAdapter(
    private val items: List<ActivityTypes>,
    private val onItemSelected: (ActivityTypes) -> Unit
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


