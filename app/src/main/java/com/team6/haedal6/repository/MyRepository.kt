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

    suspend fun fetchPlaceDetails(targetSeasons: Seasons): Result<Place> {
        return try {
            // 1. 먼저 getPlaces 호출
            val placeResult = getPlaces(targetSeasons)

            // 2. getPlaces 호출이 성공적이면 그 결과로 getPlaceDetailInfo 호출
            if (placeResult.isSuccess) {
                val coordinate = placeResult.getOrNull() // Coordinate 객체를 받아옴

                // 3. Coordinate에서 위도와 경도를 추출
                val latitude = coordinate?.latitude ?: throw Exception("Latitude not found")
                val longitude = coordinate?.longitude ?: throw Exception("Longitude not found")

                // 4. getPlaceDetailInfo 호출
                val placeDetailResult = getPlaceDetailInfo(latitude, longitude)

                // 5. 결과 반환
                if (placeDetailResult.isSuccess) {
                    placeDetailResult
                } else {
                    Result.failure(Exception("Failed to get place details"))
                }
            } else {
                Result.failure(Exception("Failed to get places"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
