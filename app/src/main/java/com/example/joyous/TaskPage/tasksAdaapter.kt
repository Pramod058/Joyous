package com.example.joyous

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.joyous.R
import com.example.joyous.journal.JournalFragment

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
        }
        val onTaskClickListener = View.OnClickListener {
            if (taskName == "Journal") {
                // Only go to CreateJournalActivity if it's Task 2
                val intent = Intent(context, CreateJournalActivity::class.java)
                context.startActivity(intent)
            } else {
                // Handle other task clicks (e.g., Task 1 or Task 3)
                // You can add specific actions for other tasks here if needed
            }
        }
//
//            "Journal" -> {
//                imageViewTask.setImageResource(R.drawable.ic_launcher_foreground)
//                val intent = Intent(context, CreateJournalActivity::class.java)
//                context.startActivity(intent)
//            }
        textViewTaskName.setOnClickListener(onTaskClickListener)
        imageViewTask.setOnClickListener(onTaskClickListener)

//        view.setOnClickListener{
//            when (taskName) {
//                "Journal" -> {
//                    // Navigate to JournalFragment when Task 2 is clicked
//                    val fragmentManager: FragmentManager = fragment.parentFragmentManager
//                    val transaction: FragmentTransaction = fragmentManager.beginTransaction()
//                    transaction.replace(R.id.fragment_container, JournalFragment())
//                    transaction.addToBackStack(null)
//                    transaction.commit()
//                }
//            }
//        }


        return view
    }
}
