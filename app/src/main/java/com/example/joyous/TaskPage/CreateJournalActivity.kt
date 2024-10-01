package com.example.joyous

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.joyous.utils.LocalStorage
import java.text.SimpleDateFormat
import java.util.*

class CreateJournalActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextContent: EditText
    private lateinit var buttonSave: Button
    private lateinit var localStorage: LocalStorage
    private lateinit var savedJournalsListView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_journal)

        // Initialize local storage
        localStorage = LocalStorage(this)

        // Initialize ListView for saved journals
        savedJournalsListView = findViewById(R.id.savedJournalsListView)

        // Get saved journals from LocalStorage
        val savedJournals = localStorage.getAllJournals()

        // Check if there are saved journals
        if (savedJournals.isNotEmpty()) {
            // Set up the adapter to display saved journal titles
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, savedJournals)
            savedJournalsListView.adapter = adapter

            // Set up a click listener to view journal content when a title is clicked
            savedJournalsListView.setOnItemClickListener { _, _, position, _ ->
                val selectedJournalTitle = savedJournals[position]
                val journalContent = localStorage.getJournal(selectedJournalTitle)

                // Show journal content in a toast (you can modify this to show it in a new activity or dialog)
                Toast.makeText(this, "Content: $journalContent", Toast.LENGTH_LONG).show()
            }
        } else {
            // If no journals are saved, show a message
            Toast.makeText(this, "No saved journals", Toast.LENGTH_SHORT).show()
        }


        editTextTitle = findViewById(R.id.editTextJournalTitle)
        editTextContent = findViewById(R.id.editTextJournalContent)
        buttonSave = findViewById(R.id.buttonSaveJournal)

        buttonSave.setOnClickListener {
            saveJournal()
        }
    }

    private fun saveJournal() {
        val title = editTextTitle.text.toString().trim()
        val content = editTextContent.text.toString().trim()

        // Check if title or content is empty
        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show()
            return
        }
        if (content.isEmpty()) {
            Toast.makeText(this, "Please write something in the journal", Toast.LENGTH_SHORT).show()
            return
        }

        // Format the title for saving (remove spaces)
        val formattedTitle = title.replace("\\s+".toRegex(), "")
        val dateTime = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val journalKey = formattedTitle+"_"+dateTime
        // Save to LocalStorage
       localStorage = LocalStorage(this)
        val success = localStorage.saveJournal(journalKey, title, content)

        // Show toast and handle redirection based on success/failure
        if (success) {
            Toast.makeText(this, "Journal saved successfully", Toast.LENGTH_SHORT).show()
            // Redirect to the previous page (simulating back button press)
            onBackPressed()
        } else {
            Toast.makeText(this, "Failed to save journal", Toast.LENGTH_SHORT).show()
        }
    }
}
