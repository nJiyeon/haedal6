package com.team6.haedal6.adapter.keyword

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.team6.haedal6.R
import com.team6.haedal6.view.OnKeywordItemClickListener

class KeywordAdapter(private val onKeywordItemClickListener: OnKeywordItemClickListener) :
    ListAdapter<String, KeywordAdapter.KeywordViewHolder>(
        object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
            override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_keyword, parent, false)
        return KeywordViewHolder(view)
    }

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        holder.bindViewHolder(getItem(position), onKeywordItemClickListener)
    }

    class KeywordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val keywordTextView: TextView = view.findViewById(R.id.keyword)
        private val removeIconView: ImageView = view.findViewById(R.id.remove_icon)

        fun bindViewHolder(keyword: String, onKeywordItemClickListener: OnKeywordItemClickListener) {
            keywordTextView.text = keyword
            keywordTextView.setOnClickListener {
                onKeywordItemClickListener.onKeywordItemClick(keyword)
            }
            removeIconView.setOnClickListener {
                onKeywordItemClickListener.onKeywordItemDeleteClick(keyword)
            }
        }
    }
}