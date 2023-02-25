package com.promiseeight.www.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.R
import com.promiseeight.www.databinding.ItemTimeBinding
import com.promiseeight.www.ui.model.TimeUiModel

class TimeAdapter(
    private val onClick : (TimeUiModel) -> Unit
) : ListAdapter<TimeUiModel, TimeAdapter.TimeViewHolder>(TimeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        return TimeViewHolder(
            ItemTimeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        ){
            onClick(currentList[it])
        }
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class TimeViewHolder(
        private val binding : ItemTimeBinding,
        private val onClicked : (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClicked(adapterPosition)
            }
        }

        fun bind(time : TimeUiModel) {
            binding.ivTime.setImageDrawable(if(time.disabled){
                AppCompatResources.getDrawable(binding.root.context,R.drawable.rectangle_meeting_info_date_disable)
            } else if(time.selected){
                AppCompatResources.getDrawable(binding.root.context,R.drawable.rectangle_meeting_info_date_check)
            } else {
                AppCompatResources.getDrawable(binding.root.context,R.drawable.rectangle_meeting_info_date_normal)
            })
            binding.root.isClickable = !time.disabled
        }
    }

    private class TimeDiffCallback : DiffUtil.ItemCallback<TimeUiModel>() {
        override fun areItemsTheSame(
            oldItem: TimeUiModel,
            newItem: TimeUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TimeUiModel,
            newItem: TimeUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}