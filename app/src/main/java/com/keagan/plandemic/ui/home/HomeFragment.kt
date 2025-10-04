package com.keagan.plandemic.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.keagan.plandemic.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDate

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val prefs by lazy {
        requireContext().getSharedPreferences("plandemic_prefs", 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val txtQuote   = view.findViewById<TextView>(R.id.txtQuote)
        val txtAuthor  = view.findViewById<TextView>(R.id.txtAuthor)
        val txtStreak  = view.findViewById<TextView>(R.id.txtStreak)
        val btnTick    = view.findViewById<Button>(R.id.btnTick)
        val txtError   = view.findViewById<TextView>(R.id.txtError)

        // --- Streak UI setup ---
        txtStreak.text = "Streak: ${currentStreak()}"

        btnTick.setOnClickListener {
            txtError.text = ""
            val today = LocalDate.now().toString()
            val lastDone = prefs.getString("streak_last_done", null)

            if (lastDone == today) {
                txtError.text = "You’ve already completed today ✅"
            } else {
                val newStreak = if (lastDone == null) 1 else currentStreak() + 1
                prefs.edit()
                    .putInt("streak_count", newStreak)
                    .putString("streak_last_done", today)
                    .apply()
                txtStreak.text = "Streak: $newStreak"
            }
        }

        // --- Quote fetch (local dev API; safe no-crash fallback) ---
        // Update the URL to your running API endpoint if different.
        fetchQuote(
            url = "http://10.0.2.2:5043/api/quote",
            onSuccess = { quote, author ->
                txtQuote.text = quote
                txtAuthor.text = author
            },
            onError = { msg ->
                // Keep the default "Loading quote..." text if you like,
                // or show an error message:
                txtError.text = msg
            }
        )
    }

    private fun currentStreak(): Int = prefs.getInt("streak_count", 0)

    /**
     * Very small HTTP client using HttpURLConnection (no extra deps).
     * Expects a JSON like: { "text": "...", "author": "..." }
     */
    private fun fetchQuote(
        url: String,
        onSuccess: (String, String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching {
                    val conn = (URL(url).openConnection() as HttpURLConnection).apply {
                        connectTimeout = 5000
                        readTimeout = 5000
                        requestMethod = "GET"
                    }
                    conn.inputStream.use { stream ->
                        val body = stream.bufferedReader().readText()
                        val json = JSONObject(body)
                        val quote = json.optString("text", "Stay consistent. Small steps daily.")
                        val author = json.optString("author", "")
                        quote to author
                    }
                }
            }
            result.onSuccess { (q, a) -> onSuccess(q, a) }
                .onFailure { onError("Could not load quote (is your API running?)") }
        }
    }
}
