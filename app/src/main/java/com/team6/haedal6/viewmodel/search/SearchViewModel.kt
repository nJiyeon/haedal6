package com.team6.haedal6.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team6.haedal6.model.Coordinate
import com.team6.haedal6.model.Seasons
import com.team6.haedal6.repository.MyRepository
import com.team6.haedal6.api.KakaoLocalApi
import com.team6.haedal6.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val kakaoApi: KakaoLocalApi,  // Inject KakaoLocalApi for searching
    private val repository: MyRepository  // Inject MyRepository for fetching pins based on seasons
) : ViewModel() {

    // LiveData to hold search results (from KakaoLocalApi)
    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> get() = _locations

    // LiveData to hold coordinates for pins (from MyApiService)
    private val _coordinate = MutableLiveData<Coordinate>()
    val coordinate: LiveData<Coordinate> get() = _coordinate

    // LiveData for error messages
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // Function to search locations using KakaoLocalApi
    fun searchLocationData(keyword: String) {
        viewModelScope.launch {
            try {
                val response = kakaoApi.searchKeyword("KakaoAK YOUR_API_KEY", keyword) // Replace with your Kakao API key
                _locations.value = response.documents.map {
                    Location(
                        place = it.placeName,
                        address = it.addressName,
                        category = it.categoryGroupName,
                        latitude = it.latitude,
                        longitude = it.longitude
                    )
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    // Function to fetch pins based on selected seasons
    fun fetchPinsForSelectedSeasons(selectedSeasons: Seasons) {
        viewModelScope.launch {
            try {
                val result = repository.getPlaces(selectedSeasons)
                result.onSuccess { coordinate ->
                    _coordinate.value = coordinate // Update the pin coordinates based on selected seasons
                }.onFailure { exception ->
                    _error.value = exception.message // Handle error
                }
            } catch (e: Exception) {
                _error.value = e.message // Handle error
            }
        }
    }
}
