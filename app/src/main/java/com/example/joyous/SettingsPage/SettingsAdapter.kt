package com.example.joyous.SettingsPage

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Switch
import android.widget.TextView
import com.example.joyous.R
import com.google.android.material.snackbar.Snackbar

class SettingsAdapter(
    context: Context,
    private val settingsItems: List<String>,
    private val fragment: SettingFragment
) : ArrayAdapter<String>(context, R.layout.list_item_setting, settingsItems) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_setting, parent, false)

        val textViewSettingName: TextView = view.findViewById(R.id.itemText)
        val switchNotification: Switch = view.findViewById(R.id.switchNotification)

        val settingName = settingsItems[position]
        textViewSettingName.text = settingName

        when (settingName) {
            "Notification" -> {
                switchNotification.visibility = View.VISIBLE
                switchNotification.setOnCheckedChangeListener { _, isChecked ->
                    val message = if (isChecked) "Notification is ON" else "Notification is OFF"
                    Snackbar.make(fragment.requireView(), message, Snackbar.LENGTH_SHORT).show()
                }
            }
            else -> {
                switchNotification.visibility = View.GONE
                view.setOnClickListener {
                    when (settingName) {
                        "Saves" -> {
                            val intent = Intent(context, savedJournal::class.java)
                            context.startActivity(intent)
                        }
                        "Set Reminder" -> {
                            val intent = Intent(context, setReminder::class.java)
                            context.startActivity(intent)
                        }
                    }
                }
            }
        }
        return view
    }
}
