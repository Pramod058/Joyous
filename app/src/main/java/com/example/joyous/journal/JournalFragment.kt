// JournalFragment.kt
package com.example.joyous.journal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.joyous.R
import com.example.joyous.utils.LocalStorage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class JournalFragment : Fragment() {

    private lateinit var journalTitleEditText: EditText
    private lateinit var journalContentEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_journal, container, false)

        journalTitleEditText = view.findViewById(R.id.journalTitleEditText)
        journalContentEditText = view.findViewById(R.id.journalContentEditText)
        saveButton = view.findViewById(R.id.saveJournalButton)

        saveButton.setOnClickListener {
            saveJournal()
        }

        return view
    }

    private fun saveJournal() {
        val title = journalTitleEditText.text.toString().trim()
        val content = journalContentEditText.text.toString().trim()
        val formattedTitle = title.replace("\\s+".toRegex(), "")
        val dateTime = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val journalKey = formattedTitle+"_"+dateTime

        if (title.isNotEmpty() && content.isNotEmpty()) {
            val localStorage = LocalStorage(requireContext())
            val success = localStorage.saveJournal(journalKey,title, content)

            if (success) {
                Toast.makeText(requireContext(), "Journal saved successfully", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed() // Navigate back after saving
            } else {
                Toast.makeText(requireContext(), "Failed to save journal", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Please fill in both fields", Toast.LENGTH_SHORT).show()
        }
    }
}
