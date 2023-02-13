package com.promiseeight.www.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.databinding.ItemCandidateBinding
import com.promiseeight.www.ui.model.CandidateUiModel

class CandidateAdapter(
    private val onClick: ((CandidateUiModel) -> Unit) = { }
) :
    ListAdapter<CandidateUiModel, CandidateAdapter.CandidateViewHolder>(CandidateDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        return CandidateViewHolder(
            binding = ItemCandidateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
        ) { position ->
            onClick(currentList[position])
        }
    }

    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CandidateViewHolder(
        val binding: ItemCandidateBinding,
        val onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.ivItemClose.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        fun bind(candidate: CandidateUiModel) {
            binding.candidate = candidate
        }
    }

    private class CandidateDiffCallback : DiffUtil.ItemCallback<CandidateUiModel>() {
        override fun areItemsTheSame(
            oldItem: CandidateUiModel,
            newItem: CandidateUiModel
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: CandidateUiModel,
            newItem: CandidateUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}