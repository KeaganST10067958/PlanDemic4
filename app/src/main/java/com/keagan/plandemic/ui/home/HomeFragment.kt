package com.keagan.plandemic.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.keagan.plandemic.R
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtQuote  = view.findViewById<TextView>(R.id.txtQuote)
        val txtAuthor = view.findViewById<TextView>(R.id.txtAuthor)
        val txtStreak = view.findViewById<TextView>(R.id.txtStreak)
        val txtError  = view.findViewById<TextView>(R.id.txtError)
        val btnTick   = view.findViewById<Button>(R.id.btnTick)

        // Collect StateFlows from the ViewModel
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.quote.collect { q ->
                        txtQuote.text = q ?: "Loading quote..."
                    }
                }
                launch {
                    viewModel.author.collect { a ->
                        txtAuthor.text = a ?: ""
                    }
                }
                launch {
                    viewModel.streak.collect { s ->
                        // If you want to avoid the lint warning, add a string resource like:
                        // <string name="streak_fmt">Streak: %1$d</string>
                        // and use getString(R.string.streak_fmt, s)
                        txtStreak.text = "Streak: $s"
                    }
                }
                launch {
                    viewModel.error.collect { e ->
                        txtError.text = e ?: ""
                    }
                }
                launch {
                    viewModel.loading.collect { isLoading ->
                        btnTick.isEnabled = !isLoading
                    }
                }
            }
        }

        // Actions
        btnTick.setOnClickListener {
            viewModel.doneToday()
        }
        // Optional: long-press to refresh quote + streak
        btnTick.setOnLongClickListener {
            viewModel.refreshAll()
            true
        }
    }
}
