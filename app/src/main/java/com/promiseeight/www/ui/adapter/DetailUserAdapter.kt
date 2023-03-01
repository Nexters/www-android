package com.promiseeight.www.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.databinding.ItemMeetingDetailUserBinding
import com.promiseeight.www.databinding.ItemMeetingDetailUserHeaderBinding
import com.promiseeight.www.databinding.ItemRankDateBinding
import com.promiseeight.www.databinding.ItemRankPlaceBinding
import com.promiseeight.www.ui.model.*

class DetailUserAdapter(

): ListAdapter<MeetingDetailUserUiModel, RecyclerView.ViewHolder>(DetailUserDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return if(currentList[position].isHeader) HEADER
        else USER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            HEADER -> MeetingDetailUserHeaderViewHolder(
                ItemMeetingDetailUserHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ))
            else -> MeetingDetailUserViewHolder(
                ItemMeetingDetailUserBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            HEADER -> (holder as MeetingDetailUserHeaderViewHolder).bind(getItem(position))
            else -> (holder as MeetingDetailUserViewHolder).bind(getItem(position) )
        }

    }

    class MeetingDetailUserHeaderViewHolder(
        val binding: ItemMeetingDetailUserHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meetingDetailUser: MeetingDetailUserUiModel) {
            binding.meetingDetailUser = meetingDetailUser

        }
    }

    class MeetingDetailUserViewHolder(
        val binding: ItemMeetingDetailUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meetingDetailUser : MeetingDetailUserUiModel) {
            binding.meetingDetailUser = meetingDetailUser

        }
    }

    private class DetailUserDiffCallback : DiffUtil.ItemCallback<MeetingDetailUserUiModel>() {
        override fun areItemsTheSame(
            oldItem: MeetingDetailUserUiModel,
            newItem: MeetingDetailUserUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MeetingDetailUserUiModel,
            newItem: MeetingDetailUserUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val HEADER = 0
        const val USER = 1
    }
}