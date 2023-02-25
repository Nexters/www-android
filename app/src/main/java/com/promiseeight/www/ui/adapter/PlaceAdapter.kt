package com.promiseeight.www.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.databinding.ItemPlaceBinding
import com.promiseeight.www.databinding.ItemRankPlaceBinding
import com.promiseeight.www.ui.model.PlaceRankUiModel
import com.promiseeight.www.ui.model.PlaceUiModel

class PlaceAdapter(
    private val onClick : (PlaceRankUiModel) -> Unit
): ListAdapter<PlaceRankUiModel, PlaceAdapter.VotingViewHolder>(PlaceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VotingViewHolder {
        return VotingViewHolder(
            ItemPlaceBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )){ position ->
            onClick(currentList[position])
        }
    }

    override fun onBindViewHolder(holder: VotingViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    class VotingViewHolder(
        private val binding: ItemPlaceBinding,
        private val onClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClicked(adapterPosition)
            }
        }

        fun bind(place: PlaceRankUiModel) {
            binding.place = place

        }
    }

    private class PlaceDiffCallback : DiffUtil.ItemCallback<PlaceRankUiModel>() {
        override fun areItemsTheSame(
            oldItem: PlaceRankUiModel,
            newItem: PlaceRankUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PlaceRankUiModel,
            newItem: PlaceRankUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}