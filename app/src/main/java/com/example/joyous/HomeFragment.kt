package com.example.joyous

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var greetingText: TextView
    private lateinit var viewPager: ViewPager2
    private lateinit var prevButton: ImageButton
    private lateinit var nextButton: ImageButton
    private val handler = Handler(Looper.getMainLooper())
    private val quotes = listOf(
        "'Researchers have found that meditation helps to counter habituation—the tendency to stop paying attention to new information in our environment.'",
        "Distractions are everywhere. Notice what takes your attention, acknowledge it,and then let it go.'",
        "'We can’t always change what’s happening around us, but we can change what happens within us.'",
        "'Researchers affirm that regular journaling strengthens immune cells, anxiety and stress levels, thus acting as a management tool to reduce the impact on your physical health.'",
        "'Meditation means letting go of our baggage, letting go of all the pre-rehearsed stories and inner-dialogue that we’ve grown so attached to'",
       "'It is better to conquer yourself than to win a thousand battles. Then the victory is yours. It cannot be taken from you.'",
        "'Mindfulness is about being fully awake in our lives. It is about perceiving the exquisite vividness of each moment.'",
       "'Breathing in, I calm body and mind. Breathing out, I smile. Dwelling in the present moment, I know this is the only moment.'"
        // Add more quotes here
    )

    private val slideInterval: Long = 7000 // Interval for auto-slide in milliseconds
    private val updateInterval: Long = 60000 // Interval for updating greeting in milliseconds

    private val autoSlideRunnable = object : Runnable {
        override fun run() {
            val nextItem = (viewPager.currentItem + 1) % quotes.size
            viewPager.setCurrentItem(nextItem, true)
            handler.postDelayed(this, slideInterval)
        }
    }

    private val updateGreetingRunnable = object : Runnable {
        override fun run() {
            greetingText.text = getGreeting() // Update greeting
            Log.d("HomeFragment", "Greeting updated: ${greetingText.text}") // Debug log
            handler.postDelayed(this, updateInterval) // Run every minute
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        greetingText = view.findViewById(R.id.greeting_text)
        viewPager = view.findViewById(R.id.quotes_view_pager)
        prevButton = view.findViewById(R.id.prev_quote_button)
        nextButton = view.findViewById(R.id.next_quote_button)

        // Initialize ViewPager2 with QuotesAdapter
        val adapter = QuotesAdapter(quotes)
        viewPager.adapter = adapter

        // Handle previous button click
        prevButton.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem > 0) {
                viewPager.setCurrentItem(currentItem - 1, true)
            } else {
                viewPager.setCurrentItem(quotes.size - 1, true) // Go to last quote
            }
            restartAutoSlide() // Restart auto-slide on user interaction
        }

        // Handle next button click
        nextButton.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < quotes.size - 1) {
                viewPager.setCurrentItem(currentItem + 1, true)
            } else {
                viewPager.setCurrentItem(0, true) // Go to first quote
            }
            restartAutoSlide() // Restart auto-slide on user interaction
        }

        // Start the automatic sliding timer
        handler.postDelayed(autoSlideRunnable, slideInterval)

        // Start the runnable to update greeting in real-time
        handler.post(updateGreetingRunnable)
    }

    override fun onStart() {
        super.onStart()
        handler.post(updateGreetingRunnable) // Start updates when fragment is visible
        handler.postDelayed(autoSlideRunnable, slideInterval) // Start auto-slide when fragment is visible
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(updateGreetingRunnable) // Stop updates when fragment is not visible
        handler.removeCallbacks(autoSlideRunnable) // Stop auto-slide when fragment is not visible
    }

    private fun getGreeting(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 2..11 -> "Good Morning"
            in 12..16 -> "Good Afternoon"
            in 17..20 -> "Good Evening"
            else -> "Good Night" // Covers hours 21:00 - 1:59
        }
    }

    private fun restartAutoSlide() {
        handler.removeCallbacks(autoSlideRunnable) // Remove existing callback
        handler.postDelayed(autoSlideRunnable, slideInterval) // Post a new callback
    }
}
