package com.keagan.plandemic.data.remote

import retrofit2.http.GET
import retrofit2.http.POST

data class QuoteDto(
    val quote: String,
    val author: String
)

data class StreakDto(
    val currentStreak: Int
)

interface ApiService {
    // Base URL will be http://10.0.2.2:5043/api/
    @GET("quote/random")
    suspend fun getRandomQuote(): QuoteDto

    @GET("streak/today")
    suspend fun getToday(): StreakDto

    @POST("streak/tick")
    suspend fun tick(): StreakDto
}
