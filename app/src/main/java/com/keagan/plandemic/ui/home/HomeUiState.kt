package com.keagan.plandemic.ui.home

data class HomeUiState(
    val loading: Boolean = false,
    val quote: String = "",
    val author: String = "",
    val streak: Int = 0,
    val doneToday: Boolean = false,
    val error: String? = null
)
