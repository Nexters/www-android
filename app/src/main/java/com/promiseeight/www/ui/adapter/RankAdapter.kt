package com.promiseeight.www.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.databinding.ItemRankDateBinding
import com.promiseeight.www.databinding.ItemRankPlaceBinding
import com.promiseeight.www.ui.model.DateRankUiModel
import com.promiseeight.www.ui.model.PlaceRankUiModel
import com.promiseeight.www.ui.model.RankModel
import com.promiseeight.www.ui.model.RankType

class RankAdapter<T : RankModel>(
    
): ListAdapter<T, RecyclerView.ViewHolder>(RankDiffCallback<T>()) {

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            RankType.DATE.ordinal -> DateRankViewHolder(
                ItemRankDateBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ))
            else -> PlaceRankViewHolder(
                ItemRankPlaceBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            RankType.DATE.ordinal -> (holder as DateRankViewHolder).bind(getItem(position) as DateRankUiModel)
            else -> (holder as PlaceRankViewHolder).bind(getItem(position) as PlaceRankUiModel)
        }

    }

    class DateRankViewHolder(
        val binding: ItemRankDateBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rank: DateRankUiModel) {
            binding.rank = rank

        }
    }

    class PlaceRankViewHolder(
        val binding: ItemRankPlaceBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rank: PlaceRankUiModel) {
            binding.rank = rank

        }
    }

    private class RankDiffCallback<T : RankModel> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(
            oldItem: T,
            newItem: T
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: T,
            newItem: T
        ): Boolean {
            return oldItem == newItem
        }
    }
}