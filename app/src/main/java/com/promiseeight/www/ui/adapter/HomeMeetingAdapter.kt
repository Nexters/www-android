package com.promiseeight.www.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.databinding.ItemHomeMeetingBinding
import com.promiseeight.www.ui.model.MeetingUiModel

class HomeMeetingAdapter : ListAdapter<MeetingUiModel,HomeMeetingAdapter.HomeMeetingViewHolder>(HomeMeetingDiffCallback()) {

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

        fun bind(homeMeeting : MeetingUiModel) {
            binding.homeMeeting = homeMeeting
        }
    }

    private class HomeMeetingDiffCallback : DiffUtil.ItemCallback<MeetingUiModel>() {
        override fun areItemsTheSame(
            oldItem: MeetingUiModel,
            newItem: MeetingUiModel
        ): Boolean {
            return oldItem.meetingId == newItem.meetingId
        }

        override fun areContentsTheSame(
            oldItem: MeetingUiModel,
            newItem: MeetingUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}