package com.keagan.plandemic.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.keagan.plandemic.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val vm: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val txtQuote = view.findViewById<TextView>(R.id.txtQuote)
        val txtAuthor = view.findViewById<TextView>(R.id.txtAuthor)
        val txtStreak = view.findViewById<TextView>(R.id.txtStreak)
        val txtError = view.findViewById<TextView>(R.id.txtError)
        val btnTick = view.findViewById<Button>(R.id.btnTick)

        btnTick.setOnClickListener { vm.tickStreak() }

        viewLifecycleOwner.lifecycleScope.launch {
            vm.state.collectLatest { s ->
                txtError.text = s.error ?: ""
                s.quote?.let {
                    txtQuote.text = "“${it.text}”"
                    txtAuthor.text = it.author?.let { a -> "— $a" } ?: ""
                }
                s.streak?.let {
                    txtStreak.text = "Streak: ${it.current} (Longest: ${it.longest})"
                }
            }
        }

        vm.loadAll()
    }
}
