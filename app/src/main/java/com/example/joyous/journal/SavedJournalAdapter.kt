//package com.example.joyous.journal

package com.example.joyous

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SavedJournalAdapter(
    private val context: Context,
    private val journalTitles: List<String>
) : BaseAdapter() {

    override fun getCount(): Int {
        return journalTitles.size
    }

    override fun getItem(position: Int): Any {
        return journalTitles[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.grid_item_journal, parent, false)
        val titleTextView: TextView = view.findViewById(R.id.gridItemTextView)

        titleTextView.text = journalTitles[position]

        return view
    }
}
