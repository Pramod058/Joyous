package com.example.joyous.SettingsPage

import android.app.AlertDialog
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.joyous.R
import com.example.joyous.SavedJournalAdapter
import com.example.joyous.utils.LocalStorage

class savedJournal : AppCompatActivity() {
    private lateinit var gridView: GridView
    private lateinit var previewTextView: TextView
    private lateinit var journalTitle : TextView
    private lateinit var deleteButton: Button
    private lateinit var localStorage: LocalStorage

    private var selectedJournal: String? = null
    private var selectedPosition: Int? = null // Track the selected journal's position

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_journal)

        // Initialize views
        gridView = findViewById(R.id.gridViewSavedJournals)
        previewTextView = findViewById(R.id.previewTextArea)
        journalTitle = findViewById(R.id.journalTitle)
        deleteButton = findViewById(R.id.deleteButton)

        // Initialize LocalStorage
        localStorage = LocalStorage(this)

        // Load saved journals and set up the grid
        loadJournals()

        // Handle delete button click
        deleteButton.setOnClickListener {
            if (selectedJournal != null) {
                // Show a confirmation dialog
                showDeleteConfirmationDialog(selectedJournal!!)
            } else {
                Toast.makeText(this, "No journal selected to delete", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to load journals and bind the GridView
    private fun loadJournals() {
        val savedJournals = localStorage.getAllJournals()

        if (savedJournals.isNotEmpty()) {

            // Create a list of formatted titles
            val displayTitles = savedJournals.map { key ->
                val journalData = localStorage.getJournal(key)
                if (journalData != null) {
                    val content = journalData.split("|")
                    val titleOnly = content[0]
                    if (titleOnly.length > 30) titleOnly.substring(0, 30) + "..." else titleOnly // Trim the title if longer than 50 characters
                } else {
                    "Untitled"
                }
            }
            // Set up adapter
            val adapter = SavedJournalAdapter(this, displayTitles)
            gridView.adapter = adapter

            // Handle click events on GridView
            gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                selectedJournal = savedJournals[position]
                selectedPosition = position
                val parts = localStorage.getJournal(selectedJournal!!).toString().split("|")
                journalTitle.setText(parts[0])
                previewTextView.text =
                    parts[1] // Show the content in preview
            }
        } else {
            Toast.makeText(this, "No saved journals", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to delete a journal and update the UI
    private fun deleteJournal(journalName: String) {
        localStorage.removeJournal(journalName)
        Toast.makeText(this, "$journalName deleted successfully", Toast.LENGTH_SHORT).show()

        // Reload the journal list after deletion
        loadJournals()

        // Clear the preview and reset selectedJournal
        clearSelection()
    }

    // Show delete confirmation dialog
    private fun showDeleteConfirmationDialog(journalName: String) {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to delete $journalName?")
            .setPositiveButton("Yes") { dialog, _ ->
                deleteJournal(journalName)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    // Clear the selected journal and reset the preview
    private fun clearSelection() {
        selectedJournal = null
        selectedPosition = null
        previewTextView.text = "Journal preview"
        journalTitle.text = null
    }
}
