package com.example.joyous.SettingsPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.joyous.R

class SettingFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var settingsAdapter: SettingsAdapter
    private val settingsItems = listOf("Saves", "Set Reminder", "Notification")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        listView = view.findViewById(R.id.listViewSettings)

        // Initialize and set the adapter with the fragment reference
        settingsAdapter = SettingsAdapter(requireContext(), settingsItems, this)
        listView.adapter = settingsAdapter

        return view
    }
}
