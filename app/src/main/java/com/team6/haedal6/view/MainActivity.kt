package com.team6.haedal6.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraAnimation
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelLayer
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.label.LabelTextStyle
import com.team6.haedal6.R
import com.team6.haedal6.databinding.ActivityMainBinding
import com.team6.haedal6.model.Coordinate
import com.team6.haedal6.model.Location
import com.team6.haedal6.repository.location.LocationSearcher
import com.team6.haedal6.viewmodel.keyword.KeywordViewModel
import com.team6.haedal6.viewmodel.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private var mapView: MapView? = null
    private var kakaoMap: KakaoMap? = null
    private var labelLayer: LabelLayer? = null

    @Inject
    lateinit var locationSearcher: LocationSearcher

    private lateinit var errorLayout: RelativeLayout
    private lateinit var errorMessage: TextView
    private lateinit var errorDetails: TextView
    private lateinit var retryButton: ImageButton
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var bottomSheetTitle: TextView
    private lateinit var bottomSheetAddress: TextView
    private lateinit var bottomSheetImg: ImageView
    private lateinit var bottomSheetLayout: FrameLayout
    private lateinit var searchResultLauncher: ActivityResultLauncher<Intent>

    // ViewModel을 Lazy하게 제공받기
    private val keywordViewModel: KeywordViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this // 생명주기 소유자 설정
        binding.viewModel = mainViewModel // ViewModel 바인딩
        binding.keywordViewModel = keywordViewModel// Keyword ViewModel 바인딩

        // View 초기화
        initializeViews(binding)

        // ActivityResultLauncher 초기화
        searchResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            handleSearchResult(result.data)
        }

        // MapView 초기화 및 맵 라이프사이클 콜백 설정
        mapView = findViewById(R.id.map_view)
        mapView?.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                Log.d(TAG, "Map destroyed")
            }

            override fun onMapError(error: Exception) {
                Log.e(TAG, "Map error", error)
                showErrorScreen(error)
            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(map: KakaoMap) {
                kakaoMap = map
                labelLayer = kakaoMap?.labelManager?.layer
                Log.d(TAG, "Map is ready")
            }

            override fun getPosition(): LatLng {
                return LatLng.from(35.889019, 128.610257)
            }
        })

        // 검색창 클릭 시 검색 페이지로 이동
        val searchEditText = findViewById<EditText>(R.id.search_edit_text)
        searchEditText.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            searchResultLauncher.launch(intent)
        }
    }

    private fun initializeViews(binding: ActivityMainBinding) { // 바인딩을 통해 초기화
        errorLayout = binding.errorLayout
        errorMessage = binding.errorMessage
        errorDetails = binding.errorDetails
        retryButton = binding.retryButton
        retryButton.setOnClickListener { onRetryButtonClick() }

        bottomSheetLayout = binding.bottomSheetLayout
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        bottomSheetTitle = binding.bottomSheetTitle
        bottomSheetAddress = binding.bottomSheetAddress
    }

    private fun handleSearchResult(data: Intent?) {
        if (data == null) {
            showToast("검색 결과를 받아오지 못했습니다.")
            return
        }

        // 여러 개의 장소 정보를 받을 수 있다고 가정
        val latitudes = data.getDoubleArrayExtra("latitudes")
        val longitudes = data.getDoubleArrayExtra("longitudes")

        // 유효성 검사
        if (latitudes == null || longitudes == null || latitudes.size != longitudes.size) {
            showToast("검색 결과가 유효하지 않습니다.")
            return
        }

        // Coordinate 리스트 생성
        val coordinates = mutableListOf<Coordinate>()
        for (i in latitudes.indices) {
            val latitude = latitudes[i]
            val longitude = longitudes[i]

            Log.d("nJiyeon", "search result: Latitude: $latitude, Longitude: $longitude")

            // 리스트에 추가
            coordinates.add(Coordinate(latitude = latitude, longitude = longitude))
        }

        // 모든 좌표에 대해 라벨 추가
        coordinates.forEach { coordinate ->
            addLabel(listOf(coordinate)) // 각 좌표에 대해 라벨 추가
        }

        // ViewModel에 좌표 리스트 저장
        mainViewModel.saveMarkerPositions(coordinates)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        mapView?.resume()  // MapView의 resume 호출
        Log.d(TAG, "MapView resumed")
    }

    override fun onPause() {
        super.onPause()
        mapView?.pause()  // MapView의 pause 호출
        Log.d(TAG, "MapView paused")
    }

    fun showErrorScreen(error: Exception) {
        errorLayout.visibility = View.VISIBLE
        errorMessage.text = getString(R.string.map_error_message)
        errorDetails.text = error.message
        mapView?.visibility = View.GONE
    }

    private fun onRetryButtonClick() {
        errorLayout.visibility = View.GONE
        mapView?.visibility = View.VISIBLE
        mapView?.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                Log.d(TAG, "Map destroyed on retry")
            }

            override fun onMapError(error: Exception) {
                Log.e(TAG, "Map error on retry", error)
                showErrorScreen(error)
            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                this@MainActivity.kakaoMap = kakaoMap
                labelLayer = kakaoMap.labelManager?.layer
                Log.d(TAG, "Map is ready on retry")
            }
        })
    }

    private fun addLabel(coordinates: List<Coordinate>) {
        coordinates.forEach{coordinate ->
            val latitude = coordinate.latitude
            val longitude = coordinate.longitude

            val position = LatLng.from(latitude, longitude)
            val styles = kakaoMap?.labelManager?.addLabelStyles(
                LabelStyles.from(
                    LabelStyle.from(R.drawable.pin).setZoomLevel(DEFAULT_ZOOM_LEVEL),
                    LabelStyle.from(R.drawable.pin)
                        .setTextStyles(
                            LabelTextStyle.from(this, R.style.labelTextStyle)
                        )
                        .setZoomLevel(DEFAULT_ZOOM_LEVEL)
                )
            )
            labelLayer?.addLabel(
                LabelOptions.from(position).setStyles(styles)
            )
        }
    }

    private fun moveCamera(position: LatLng) {
        kakaoMap?.moveCamera(
            CameraUpdateFactory.newCenterPosition(position),
            CameraAnimation.from(CAMERA_ANIMATION_DURATION, false, false)
        )
    }

    private fun updateBottomSheet(placeName: String, roadAddressName: String) {
        bottomSheetTitle.text = placeName
        bottomSheetAddress.text = roadAddressName
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetLayout.visibility = View.VISIBLE
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val DEFAULT_ZOOM_LEVEL = 1
        private const val CAMERA_ANIMATION_DURATION = 10
    }
}
