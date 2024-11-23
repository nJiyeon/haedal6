package com.team6.haedal6.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team6.haedal6.BuildConfig
import com.team6.haedal6.api.KakaoLocalApi
import com.team6.haedal6.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val api: KakaoLocalApi
) : ViewModel() {
    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>>
        get() = _locations

    fun searchLocationData(keyword: String) {
        viewModelScope.launch {
            try {
                val response = api.searchKeyword("KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}}", keyword)
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
                e.printStackTrace()
            }
        }
    }
}