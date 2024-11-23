package com.team6.haedal6.repository

import com.team6.haedal6.api.MyApiService
import com.team6.haedal6.model.Coordinate
import com.team6.haedal6.model.Place
import com.team6.haedal6.model.Seasons
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyRepository @Inject constructor(
    private val myApiService: MyApiService
) {

    // getPlaces 함수 정의
    suspend fun getPlaces(targetSeasons: Seasons): Result<Coordinate> {
        return try {
            // API 호출
            val response = myApiService.getPlaces(targetSeasons)

            // 응답 처리
            if (response.isSuccessful) {
                // 성공적인 응답일 경우, Coordinate 객체 반환
                Result.success(response.body()!!)
            } else {
                // 실패한 경우, 에러 메시지 반환
                Result.failure(Exception("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            // 예외 처리
            Result.failure(e)
        }
    }
    suspend fun getPlaceDetailInfo(latitude: Double, longitude: Double): Result<Place> {
        return try {
            // API 호출
            val response = myApiService.getPlaceDetailInfo(latitude, longitude)

            // 응답 처리
            if (response.isSuccessful) {
                // 성공적인 응답일 경우, Place 객체 반환
                Result.success(response.body()!!)
            } else {
                // 실패한 경우, 에러 메시지 반환
                Result.failure(Exception("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            // 예외 처리
            Result.failure(e)
        }
    }
}
