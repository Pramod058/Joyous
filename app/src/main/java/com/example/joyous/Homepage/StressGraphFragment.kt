package com.example.joyous.Homepage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.joyous.R
import com.example.joyous.models.StressData
import com.example.joyous.utils.LocalStorage
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar // Don't forget to import Snackbar

class StressGraphFragment : Fragment() {
    private lateinit var localStorage: LocalStorage
    private var currentSliderValue: Float = 0f // Default slider value
    private lateinit var lineGraphView: LineGraphView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stress_graph, container, false)

        localStorage = LocalStorage(requireContext())
        lineGraphView = view.findViewById(R.id.lineGraphView)

        // Initialize UI components
        val seekBar = view.findViewById<SeekBar>(R.id.seekBar)
        val saveButton = view.findViewById<MaterialButton>(R.id.saveButton)
        val valueText = view.findViewById<TextView>(R.id.valueText)

        // Load initial data
        updateGraph()

        // Set up the seek bar
        seekBar.max = 10 // Set max value for 0.0 to 1.0 (scale it to 10)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("DefaultLocale")
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                currentSliderValue = progress / 10f // Convert to float between 0.0 and 1.0
                valueText.text = String.format("Value: %.1f", currentSliderValue)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // Save button action
        saveButton.setOnClickListener {
            saveData()
        }

        return view
    }

    private fun saveData() {
        val currentDate = System.currentTimeMillis().toString() // Example date; format as needed
        localStorage.saveData(StressData(currentDate, currentSliderValue))
        updateGraph()
        showSnackbar("Value submitted") // Show Snackbar only when saving data
    }

    private fun undoLastEntry() {
        localStorage.removeLastData()
        updateGraph()
        // No Snackbar message for undo action
    }

    private fun updateGraph() {
        val data = localStorage.getData()
        lineGraphView.setData(data)
    }

    private fun showSnackbar(message: String) {
        // Get the root view of the fragment
        val rootView = requireView() // This gets the current view of the fragment

        // Create the Snackbar with an action
        val snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo") {
            // Call the undo function when "Undo" is clicked
            undoLastEntry()
        }

        snackbar.show() // Show the Snackbar
    }
}
