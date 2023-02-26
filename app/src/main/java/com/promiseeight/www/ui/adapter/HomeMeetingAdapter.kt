package com.promiseeight.www.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.databinding.ItemHomeMeetingBinding
import com.promiseeight.www.ui.model.MeetingUiModel

class HomeMeetingAdapter(
    private val onClick : (MeetingUiModel) -> Unit
) : ListAdapter<MeetingUiModel,HomeMeetingAdapter.HomeMeetingViewHolder>(HomeMeetingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMeetingViewHolder {
        return HomeMeetingViewHolder(
            binding = ItemHomeMeetingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        ){ position ->
            onClick(currentList[position])
        }
    }

    override fun onBindViewHolder(holder: HomeMeetingViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class HomeMeetingViewHolder(
        private val binding : ItemHomeMeetingBinding,
        private val onClicked : (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClicked(adapterPosition)
            }
        }

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