package com.example.joyous.SettingsPage

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.joyous.R

class setReminder : AppCompatActivity() {
    private lateinit var spinnerReminderOptions: Spinner
    private lateinit var buttonBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_reminder)

        // Initialize views
        spinnerReminderOptions = findViewById(R.id.spinnerReminderOptions)
        buttonBack = findViewById(R.id.buttonBack)

        // Set up the back button functionality
        buttonBack.setOnClickListener {
            onBackPressed()
        }

        // Set up the spinner with options
        val reminderOptions = arrayOf("Guided Meditation", "Journal")
        val adapter = ArrayAdapter(this, R.layout.spinner_item, reminderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerReminderOptions.adapter = adapter

        spinnerReminderOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Handle the selection
                val selectedOption = reminderOptions[position]
                // You can add actions based on selection
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle case when nothing is selected
            }
        }
    }
    }
