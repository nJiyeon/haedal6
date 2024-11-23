package com.team6.haedal6.api

import com.team6.haedal6.model.Coordinate
import com.team6.haedal6.model.Place
import com.team6.haedal6.model.Seasons
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MyApiService {
    @POST("/api/getPlaces")
    suspend fun getPlaces(
        @Body targetSeasons: Seasons
    ):Response<Coordinate>
    @GET("/api/getPlaceDetail")
    suspend fun getPlaceDetailInfo(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<Place>
}