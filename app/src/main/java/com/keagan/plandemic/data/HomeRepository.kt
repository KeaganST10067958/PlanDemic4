package com.keagan.plandemic.data

import com.keagan.plandemic.data.remote.ApiClient
import com.keagan.plandemic.data.remote.QuoteApi
import com.keagan.plandemic.data.remote.StreakApi
import com.keagan.plandemic.data.remote.dto.QuoteDto
import com.keagan.plandemic.data.remote.dto.StreakDto

class HomeRepository {
    private val quoteApi = ApiClient.create(QuoteApi::class.java)
    private val streakApi = ApiClient.create(StreakApi::class.java)

    suspend fun fetchQuote(): QuoteDto = quoteApi.getToday()
    suspend fun fetchStreak(): StreakDto = streakApi.get()
    suspend fun tickStreak(): StreakDto = streakApi.tick()
}
