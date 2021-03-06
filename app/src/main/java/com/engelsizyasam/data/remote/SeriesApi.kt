package com.engelsizyasam.data.remote

import com.engelsizyasam.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesApi {

    @GET("playlists")
    suspend fun getSeries(
        @Query("part") part: String = "snippet",
        @Query("pageToken") pageToken: String,
        @Query("channelId") channelId: String = "UCgfrxC70niIPNBOqQO2g-IQ",
        @Query("key") key: String = "AIzaSyCkX2pR0YevcbtLB5s6qcUFBYp3m_lfsLU"
    ): NewsResponseDto
}