package com.team6.haedal6.viewmodel.main

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.team6.haedal6.model.Coordinate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val sharedPreferences: SharedPreferences = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val _markerPositions = MutableLiveData<List<Coordinate>>()
    val markerPositions: LiveData<List<Coordinate>> get() = _markerPositions

    init {
        loadMarkerPositions()
    }

    // 여러 개의 Location 저장
    fun saveMarkerPositions(coordinate: List<Coordinate>) {
        val gson = Gson()
        val json = gson.toJson(coordinate)  // 리스트를 JSON으로 변환
        with(sharedPreferences.edit()) {
            putString(PREF_LOCATIONS, json)  // JSON 문자열로 저장
            apply()
        }
        _markerPositions.value = coordinate
    }

    // 저장된 위치들 불러오기
    private fun loadMarkerPositions() {
        val json = sharedPreferences.getString(PREF_LOCATIONS, null)
        if (!json.isNullOrEmpty()) {
            val gson = Gson()
            Log.d("MainViewModel", "Saving JSON: $json")
            val type = object : TypeToken<List<Coordinate>>() {}.type
            val coordinate: List<Coordinate> = gson.fromJson(json, type)  // JSON을 리스트로 변환
            _markerPositions.value =  coordinate
        } else {
            _markerPositions.value = emptyList()
        }
    }

    companion object {
        private const val PREFS_NAME = "MarkerPrefs"
        private const val PREF_LOCATIONS = "locations"
    }
}
