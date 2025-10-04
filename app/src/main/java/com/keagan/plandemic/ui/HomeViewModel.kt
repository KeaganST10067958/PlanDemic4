package com.keagan.plandemic.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keagan.plandemic.data.HomeRepository
import com.keagan.plandemic.data.remote.dto.QuoteDto
import com.keagan.plandemic.data.remote.dto.StreakDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val loading: Boolean = false,
    val quote: QuoteDto? = null,
    val streak: StreakDto? = null,
    val error: String? = null
)

class HomeViewModel : ViewModel() {
    private val repo = HomeRepository()

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state

    fun loadAll() {
        _state.value = _state.value.copy(loading = true, error = null)
        viewModelScope.launch {
            try {
                val quote = repo.fetchQuote()
                val streak = repo.fetchStreak()
                _state.value = HomeUiState(loading = false, quote = quote, streak = streak)
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false, error = e.message ?: "Error")
            }
        }
    }

    fun tickStreak() {
        _state.value = _state.value.copy(loading = true, error = null)
        viewModelScope.launch {
            try {
                val updated = repo.tickStreak()
                _state.value = _state.value.copy(loading = false, streak = updated)
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false, error = e.message)
            }
        }
    }
}
