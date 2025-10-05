package com.keagan.plandemic.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keagan.plandemic.data.HomeRepository
import com.keagan.plandemic.data.remote.Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repo = HomeRepository(Api.provideApi())

    // ---- UI state flows ----
    private val _quote = MutableStateFlow<String?>(null)
    val quote: StateFlow<String?> = _quote

    private val _author = MutableStateFlow<String?>(null)
    val author: StateFlow<String?> = _author

    private val _streak = MutableStateFlow(0)
    val streak: StateFlow<Int> = _streak

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        refreshAll()
    }

    fun refreshAll() = viewModelScope.launch {
        _loading.value = true
        try {
            val q = repo.getRandomQuote()
            _quote.value = q.quote
            _author.value = "— ${q.author}"

            val s = repo.getTodayStreak()
            _streak.value = s.currentStreak
        } catch (e: Exception) {
            _error.value = e.message ?: "Unknown error"
        } finally {
            _loading.value = false
        }
    }

    fun doneToday() = viewModelScope.launch {
        _loading.value = true
        try {
            val s = repo.tickToday()
            _streak.value = s.currentStreak
        } catch (e: Exception) {
            _error.value = e.message ?: "Unknown error"
        } finally {
            _loading.value = false
        }
    }
}
