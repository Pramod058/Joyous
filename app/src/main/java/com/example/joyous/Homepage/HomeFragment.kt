package com.example.joyous

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.joyous.Homepage.StressGraphFragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Load Greeting Fragment
        val greetingFragment = GreetingFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.greeting_container, greetingFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()


        // Load Graph and slider Fragment
        val graphFragment = StressGraphFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.graph_container, graphFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()


        // Load Quotes Fragment
        val quotesFragment = QuotesFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.quotes_container, quotesFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}
