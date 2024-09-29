package com.example.joyous

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class TaskAdapter(
    context: Context,
    private val taskItems: List<String>,
    private val fragment: TaskFragment
) : ArrayAdapter<String>(context, R.layout.list_item_tasks, taskItems) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_tasks, parent, false)

        val textViewTaskName: TextView = view.findViewById(R.id.taskText)
        val imageViewTask: ImageView = view.findViewById(R.id.taskImage)

        val taskName = taskItems[position]
        textViewTaskName.text = taskName

        when (taskName) {
            "Guided Meditation" -> imageViewTask.setImageResource(R.drawable.meditation_banner)  // Use your actual image resources
            "Journal" -> imageViewTask.setImageResource(R.drawable.ic_launcher_foreground)

        }

        return view
    }
}
