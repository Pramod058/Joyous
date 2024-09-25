package com.example.joyous

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class QuotesFragment : Fragment() {

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
    private val slideInterval: Long = 7000

    private val autoSlideRunnable = object : Runnable {
        override fun run() {
            val nextItem = (viewPager.currentItem + 1) % quotes.size
            viewPager.setCurrentItem(nextItem, true)
            handler.postDelayed(this, slideInterval)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quotes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.quotes_view_pager)
        prevButton = view.findViewById(R.id.prev_quote_button)
        nextButton = view.findViewById(R.id.next_quote_button)

        val adapter = QuotesAdapter(quotes)
        viewPager.adapter = adapter

        // Auto-slide functionality
        handler.postDelayed(autoSlideRunnable, slideInterval)

        prevButton.setOnClickListener {
            val currentItem = viewPager.currentItem
            viewPager.setCurrentItem(if (currentItem > 0) currentItem - 1 else quotes.size - 1, true)
            restartAutoSlide()
        }

        nextButton.setOnClickListener {
            val currentItem = viewPager.currentItem
            viewPager.setCurrentItem(if (currentItem < quotes.size - 1) currentItem + 1 else 0, true)
            restartAutoSlide()
        }
    }

    private fun restartAutoSlide() {
        handler.removeCallbacks(autoSlideRunnable)
        handler.postDelayed(autoSlideRunnable, slideInterval)
    }
}
