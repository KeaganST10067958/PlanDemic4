package com.keagan.plandemic.data

import com.keagan.plandemic.data.remote.ApiService
import com.keagan.plandemic.data.remote.QuoteDto
import com.keagan.plandemic.data.remote.StreakDto

class HomeRepository(private val api: ApiService) {

    suspend fun getRandomQuote(): QuoteDto =
        api.getRandomQuote()

    suspend fun getTodayStreak(): StreakDto =
        api.getToday()

    suspend fun tickToday(): StreakDto =
        api.tick()
}
