package com.promiseeight.www.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.databinding.ItemRankConfirmBinding
import com.promiseeight.www.databinding.ItemRankDateBinding
import com.promiseeight.www.databinding.ItemRankPlaceBinding
import com.promiseeight.www.ui.model.DateRankUiModel
import com.promiseeight.www.ui.model.PlaceRankUiModel
import com.promiseeight.www.ui.model.RankModel
import com.promiseeight.www.ui.model.RankType

class RankConfirmAdapter<T : RankModel>(
    private val onClick : (RankModel) -> Unit
): ListAdapter<T, RankConfirmAdapter.RankConfirmViewHolder>(RankConfirmDiffCallback<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankConfirmViewHolder {
        return RankConfirmViewHolder(ItemRankConfirmBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )){ position ->
            onClick(currentList[position])
        }
    }

    override fun onBindViewHolder(holder: RankConfirmViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class RankConfirmViewHolder(
        private val binding: ItemRankConfirmBinding,
        private val onClicked : (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClicked(adapterPosition)
            }
        }

        fun bind(rank: RankModel) {
            binding.vBorder.visibility = View.INVISIBLE
            binding.rank = rank

            binding.tvTitle.text = if(rank is DateRankUiModel) rank.date else (rank as PlaceRankUiModel).name
            binding.tvSubtitle.text = if(rank is DateRankUiModel) rank.time else ""
        }
    }

    private class RankConfirmDiffCallback<T : RankModel> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(
            oldItem: T,
            newItem: T
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: T,
            newItem: T
        ): Boolean {
            return oldItem == newItem
        }
    }
}