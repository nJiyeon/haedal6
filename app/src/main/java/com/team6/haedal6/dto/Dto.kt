package com.team6.haedal6.dto

import com.team6.haedal6.model.Coordinate
import com.team6.haedal6.model.Place
import com.team6.haedal6.model.Seasons
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceDetailResponse(
    @SerialName("name") val name: String?,
    @SerialName("address") val address: String?,
    @SerialName("imageUrl") val imageUrl: String?,
    @SerialName("spring") val spring: Boolean? = false,
    @SerialName("summer") val summer: Boolean? = false,
    @SerialName("fall") val fall: Boolean? = false,
    @SerialName("winter") val winter: Boolean? = false
)

@Serializable
data class PlaceRequest(
    @SerialName("spring") val spring: Boolean? = false,
    @SerialName("summer") val summer: Boolean? = false,
    @SerialName("fall") val fall: Boolean? = false,
    @SerialName("winter") val winter: Boolean? = false,
)

@Serializable
data class PlaceResponse(
    @SerialName("latitude") val latitude: Double?,
    @SerialName("longitude") val longitude: Double?
)

fun PlaceDetailResponse.toVO(): Place {
    return Place(
        name = this.name ?: "",
        address = this.address ?: "",
        imageUrl = this.imageUrl ?: "",
        spring = this.spring ?: false,
        summer = this.summer ?: false,
        fall = this.fall ?: false,
        winter = this.winter ?: false
    )
}

fun PlaceRequest.toVO(): Seasons {
    return Seasons(
        spring = this.spring ?: false,
        summer = this.summer ?: false,
        fall = this.fall ?: false,
        winter = this.winter ?: false
    )
}

fun PlaceResponse.toVO(): Coordinate {
    return Coordinate(
        latitude = this.latitude ?: 0.0,
        longitude = this.longitude ?: 0.0
    )
}
