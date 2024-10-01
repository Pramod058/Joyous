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


    // Save a journal entry
    fun saveJournal(journalKey:String, title: String, content: String): Boolean {
        val formattedTitle = journalKey.replace(" ", "") + "_" + System.currentTimeMillis().toString()
        val journalData = title + "|" + content // Store both title and content separated by "|"
        return try {
            editor.putString(formattedTitle, journalData) // Save journal content using title_datetime as the key
            editor.apply()
            true // Success
        } catch (e: Exception) {
            e.printStackTrace()
            false // Failure
        }
    }

//    // Retrieve a journal entry
//    fun getJournal(key: String): String? {
//        return sharedPreferences.getString(key, null)
//    }

    // Retrieve the journal entry (title and content)
    fun getJournal(key: String): String? {
        val journalData = sharedPreferences.getString(key, null)
        return if (journalData != null) {
//            val parts = journalData.split("|")
//            if (parts.size == 2) {
//                Pair(parts[0], parts[1]) // Return the title and content as a pair
//            } else {
//                null // Invalid data
//            }
            return journalData
        } else {
            null // No data found
        }
    }

    // Retrieve all saved journal titles
    fun getAllJournals(): List<String> {
        return sharedPreferences.all.keys.toList() // Returns list of all journal titles
    }

    // Remove a journal entry
    fun removeJournal(key: String) {
        editor.remove(key) // Remove the journal entry by key
        editor.apply()
    }
}
