package com.keagan.plandemic.data.remote

import com.keagan.plandemic.data.remote.dto.QuoteDto
import retrofit2.http.GET

interface QuoteApi {
    @GET("api/quote/today")
    suspend fun getToday(): QuoteDto
}
