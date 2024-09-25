// utils/LocalStorage.kt
package com.example.joyous.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.joyous.models.StressData

class LocalStorage(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("StressData", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    // Save a single stress data entry
    fun saveData(data: StressData) {
        val currentData = getData().toMutableList()
        currentData.add(data) // Add new data

        // Serialize the list to a string
        val serializedData = currentData.joinToString(separator = "|") { "${it.date},${it.value}" }
        editor.putString("stress_data", serializedData) // Save serialized string in SharedPreferences
        editor.apply()
    }

    // Retrieve all stress data entries
    fun getData(): List<StressData> {
        val serializedData = sharedPreferences.getString("stress_data", null)
        return if (serializedData != null) {
            serializedData.split("|").map {
                val parts = it.split(",")
                StressData(parts[0], parts[1].toFloat()) // Convert back to StressData
            }
        } else {
            emptyList() // Return empty list if no data found
        }
    }

    // Remove the last data entry
    fun removeLastData() {
        val currentData = getData().toMutableList()
        if (currentData.isNotEmpty()) {
            currentData.removeAt(currentData.size - 1) // Remove last entry

            // Serialize the updated list
            val serializedData = currentData.joinToString(separator = "|") { "${it.date},${it.value}" }
            editor.putString("stress_data", serializedData) // Update SharedPreferences
            editor.apply()
        }
    }
}
