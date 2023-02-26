package com.promiseeight.www.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.promiseeight.www.R
import com.promiseeight.www.databinding.ItemCalendarDayBinding
import com.promiseeight.www.databinding.ItemCalendarMonthBinding
import com.promiseeight.www.ui.model.CalendarUiModel
import com.promiseeight.www.ui.model.enums.DateUiState

class CalendarAdapter(
    private val onClick: (CalendarUiModel) -> Unit
) :
    ListAdapter<CalendarUiModel, RecyclerView.ViewHolder>(CalendarDiffCallback()) {

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isMonth) MONTH_VIEW_TYPE else DAY_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MONTH_VIEW_TYPE) CalendarMonthViewHolder(
            binding = ItemCalendarMonthBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        ) else CalendarDayViewHolder(
            binding = ItemCalendarDayBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        ) { position ->
            onClick(currentList[position])
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MONTH_VIEW_TYPE) (holder as CalendarMonthViewHolder).bind(
            getItem(
                position
            )
        )
        else (holder as CalendarDayViewHolder).bind(getItem(position))
    }

    class CalendarMonthViewHolder(
        val binding: ItemCalendarMonthBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(calendar: CalendarUiModel) {
            binding.calendar = calendar
        }
    }

    class CalendarDayViewHolder(
        val binding: ItemCalendarDayBinding,
        val onClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClicked(adapterPosition)
            }
        }

        fun bind(calendar: CalendarUiModel) {
            if(calendar.dateState == null || calendar.dateState == DateUiState.INITIAL ) {
                binding.ivDayRectangleLeft.setImageResource(0)
                binding.ivDayCircle.setImageResource(0)
                binding.ivDayRectangleRight.setImageResource(0)
            }

            binding.calendar = calendar
            binding.tvDay.setTextColor(
                if(calendar.dateState == DateUiState.SELECTED_SUNDAY_END || calendar.dateState == DateUiState.SELECTED ||
                     calendar.dateState == DateUiState.SELECTED_START || calendar.dateState == DateUiState.SELECTED_END ||
                    calendar.dateState == DateUiState.SELECTED_SATURDAY_START) ContextCompat.getColor(binding.root.context, R.color.www_white)
                else if(calendar.isCurrentMonth == false) ContextCompat.getColor(binding.root.context, R.color.gray_350)
                else ContextCompat.getColor(binding.root.context, R.color.www_black)
            )

        }
    }

    private class CalendarDiffCallback : DiffUtil.ItemCallback<CalendarUiModel>() {
        override fun areItemsTheSame(
            oldItem: CalendarUiModel,
            newItem: CalendarUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CalendarUiModel,
            newItem: CalendarUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val MONTH_VIEW_TYPE = 0
        const val DAY_VIEW_TYPE = 1
    }
}