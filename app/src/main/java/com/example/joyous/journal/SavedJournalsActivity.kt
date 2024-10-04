package com.example.joyous

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.joyous.utils.LocalStorage

class SavedJournalsActivity : AppCompatActivity() {

    private lateinit var localStorage: LocalStorage
    private lateinit var gridView: GridView
    private lateinit var previewTextArea: EditText
    private lateinit var journalTitle : EditText
    private lateinit var deleteButton: Button
    private var selectedJournalTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_journal)

        // Initialize LocalStorage
        localStorage = LocalStorage(this)

        // Initialize UI elements
        gridView = findViewById(R.id.gridViewSavedJournals)
        previewTextArea = findViewById(R.id.previewTextArea)
        journalTitle = findViewById(R.id.journalTitle)
        deleteButton = findViewById(R.id.deleteButton)

        // Get all saved journal titles
        val savedJournals = localStorage.getAllJournals()

        // Set up the adapter for the GridView
        val adapter = SavedJournalAdapter(this, savedJournals)
        gridView.adapter = adapter

        // Set up item click listener for GridView
        gridView.setOnItemClickListener { _, _, position, _ ->
            selectedJournalTitle = savedJournals[position]
            val journalContent = localStorage.getJournal(selectedJournalTitle!!).toString()
            println(journalContent)
            val parts = journalContent.split("|")
            journalTitle.setText(parts[0])
            previewTextArea.setText(parts[1]) // Display selected journal content in preview
        }

        // Set up delete button listener
        deleteButton.setOnClickListener {
            selectedJournalTitle?.let { title ->
                showDeleteConfirmation(title)
            } ?: Toast.makeText(this, "No journal selected", Toast.LENGTH_SHORT).show()
        }
    }

    // Show confirmation dialog before deleting
    private fun showDeleteConfirmation(title: String) {
        AlertDialog.Builder(this)
            .setTitle("Delete Journal")
            .setMessage("Do you really want to delete this journal?")
            .setPositiveButton("Yes") { _, _ ->
                // Delete the journal from local storage
                localStorage.removeJournal(title)
                Toast.makeText(this, "Journal deleted", Toast.LENGTH_SHORT).show()
                recreate() // Refresh the activity to update the grid view
            }
            .setNegativeButton("No", null)
            .show()
    }
}
