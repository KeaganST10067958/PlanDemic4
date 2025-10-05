package com.keagan.plandemic.data.remote

import com.keagan.plandemic.data.remote.dto.StreakDto
import retrofit2.http.GET
import retrofit2.http.POST

interface StreakApi {
    @GET("api/streak")
    suspend fun getStreak(): StreakDto

    @POST("api/streak/tick")
    suspend fun tick(): StreakDto
}
