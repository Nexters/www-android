package com.promiseeight.www.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.databinding.ItemHomeMeetingBinding

/// Meeting Model 만들어지면 String -> Meeting 으로 바뀔 예정!!
class HomeMeetingAdapter : ListAdapter<String,HomeMeetingAdapter.HomeMeetingViewHolder>(HomeMeetingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMeetingViewHolder {
        return HomeMeetingViewHolder(
            ItemHomeMeetingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeMeetingViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class HomeMeetingViewHolder(
        val binding : ItemHomeMeetingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(homeMeeting : String) {
            binding.homeMeeting = homeMeeting
        }
    }

    private class HomeMeetingDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }
}