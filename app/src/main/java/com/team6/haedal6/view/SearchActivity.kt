package com.team6.haedal6.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.team6.haedal6.databinding.ActivitySearchBinding
import com.team6.haedal6.adapter.keyword.KeywordAdapter
import com.team6.haedal6.adapter.search.SearchAdapter
import com.team6.haedal6.model.Location
import com.team6.haedal6.model.Seasons
import com.team6.haedal6.viewmodel.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), OnSearchItemClickListener, OnKeywordItemClickListener {
    private lateinit var binding: ActivitySearchBinding

    // ViewModel instances
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView for search results
        val searchAdapter = SearchAdapter(this)
        binding.searchResultView.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        // Set up RecyclerView for keyword history
        val keywordAdapter = KeywordAdapter(this)
        binding.keywordHistoryView.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = keywordAdapter
        }

        // Observe search results
        searchViewModel.locations.observe(this) { locations ->
            searchAdapter.submitList(locations)
            binding.searchResultView.visibility = if (locations.isEmpty()) View.GONE else View.VISIBLE
            binding.emptyView.visibility = if (locations.isEmpty()) View.VISIBLE else View.GONE
        }

        // Observe pin coordinates based on selected seasons
        searchViewModel.coordinate.observe(this) { coordinate ->
            // Use the coordinates to display pins on a map (implement this logic here)
        }

        // Observe errors
        searchViewModel.error.observe(this) { errorMessage ->
            // Show error message (e.g., Toast)
        }

        // Set up the EditText for search input
        binding.searchTextInput.doAfterTextChanged {
            val keyword = it.toString()
            if (keyword.isNotEmpty()) {
                searchViewModel.searchLocationData(keyword)
            }
        }

        // Set up checkbox listeners for season selection
        binding.seasonSpring.setOnCheckedChangeListener { _, isChecked ->
            updateSelectedSeasons(isChecked)
        }
        binding.seasonSummer.setOnCheckedChangeListener { _, isChecked ->
            updateSelectedSeasons(isChecked)
        }
        binding.seasonFall.setOnCheckedChangeListener { _, isChecked ->
            updateSelectedSeasons(isChecked)
        }
        binding.seasonWinter.setOnCheckedChangeListener { _, isChecked ->
            updateSelectedSeasons(isChecked)
        }

        // Cancel button for clearing the search input
        binding.deleteTextInput.setOnClickListener {
            binding.searchTextInput.text.clear()
        }
    }

    private fun updateSelectedSeasons(isChecked: Boolean) {
        val selectedSeasons = Seasons(
            spring = binding.seasonSpring.isChecked,
            summer = binding.seasonSummer.isChecked,
            fall = binding.seasonFall.isChecked,
            winter = binding.seasonWinter.isChecked
        )

        // Fetch the pins for the selected seasons
        searchViewModel.fetchPinsForSelectedSeasons(selectedSeasons)
    }

    override fun onSearchItemClick(location: Location) {
        // Handle search result click
        val resultIntent = Intent().apply {
            putExtra("place_name", location.place)
            putExtra("road_address_name", location.address)
            putExtra("latitude", location.latitude)
            putExtra("longitude", location.longitude)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun onKeywordItemClick(keyword: String) {
        // Handle keyword click
        binding.searchTextInput.setText(keyword)
        searchViewModel.searchLocationData(keyword)
    }

    override fun onKeywordItemDeleteClick(keyword: String) {
        // Handle keyword delete click
        // Implement keyword deletion logic here
    }
}
