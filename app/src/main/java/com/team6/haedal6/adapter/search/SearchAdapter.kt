package com.team6.haedal6.adapter.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.team6.haedal6.R
import com.team6.haedal6.model.Location
import com.team6.haedal6.view.OnSearchItemClickListener

class SearchAdapter(
    private val onSearchItemClickListener: OnSearchItemClickListener
) : ListAdapter<Location, SearchAdapter.SearchViewHolder>(
    object : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location) =
            oldItem.place == newItem.place

        override fun areContentsTheSame(oldItem: Location, newItem: Location) =
            oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindViewHolder(item, onSearchItemClickListener)
    }

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemList: RelativeLayout = view.findViewById(R.id.item_list)
        private val place: TextView = view.findViewById(R.id.place_name)
        private val address: TextView = view.findViewById(R.id.address)
        private val category: TextView = view.findViewById(R.id.category)

        fun bindViewHolder(location: Location, onSearchItemClickListener: OnSearchItemClickListener) {
            place.text = location.place
            address.text = location.address
            category.text = location.category

            itemList.setOnClickListener {
                onSearchItemClickListener.onSearchItemClick(location)
            }
        }
    }
}