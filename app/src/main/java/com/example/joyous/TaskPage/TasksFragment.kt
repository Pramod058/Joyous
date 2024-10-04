package com.example.joyous

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.joyous.TaskAdapter

class TaskFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var taskAdapter: TaskAdapter
    private val taskItems = listOf("Guided Meditation", "Journal") // You can replace this with dynamic data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        listView = view.findViewById(R.id.listViewTasks)

        // Initialize and set the adapter with the fragment reference
        taskAdapter = TaskAdapter(requireContext(), taskItems, this)
        listView.adapter = taskAdapter

        return view
    }
}
