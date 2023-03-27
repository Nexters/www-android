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
import org.joda.time.DateTime

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

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] == DateChangeState.SELECT_TO_SELECT && getItemViewType(position) == DAY_VIEW_TYPE) {
                (holder as CalendarDayViewHolder).bindDateStateSelectToSelect(getItem(position))
            } else if (payloads[0] == DateChangeState.NOT_SELECT_TO_SELECT && getItemViewType(
                    position
                ) == DAY_VIEW_TYPE
            ) {
                (holder as CalendarDayViewHolder).bindDateState(getItem(position))

            }
        }
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
            if (calendar.dateState == null || calendar.dateState == DateUiState.INITIAL) {
                binding.ivDayRectangleLeft.setImageResource(0)
                binding.ivDayCircle.setImageResource(0)
                binding.ivDayRectangleRight.setImageResource(0)
            }
            binding.calendar = calendar
            binding.tvDay.setTextColor(
                if (calendar.dateState == DateUiState.SELECTED_SUNDAY_END || calendar.dateState == DateUiState.SELECTED ||
                    calendar.dateState == DateUiState.SELECTED_START || calendar.dateState == DateUiState.SELECTED_END ||
                    calendar.dateState == DateUiState.SELECTED_SATURDAY_START || calendar.dateState == DateUiState.SELECTED_BOTH
                ) ContextCompat.getColor(binding.root.context, R.color.www_white)
                else if(calendar.dateTime.dayOfYear == DateTime.now().dayOfYear) ContextCompat.getColor(binding.root.context, R.color.www_black)
                else if (calendar.dateTime.isBeforeNow) ContextCompat.getColor(binding.root.context, R.color.gray_350)
                else ContextCompat.getColor(binding.root.context, R.color.www_black)
            )
            calendar.dateState?.let {
                binding.ivDayCircle.setDateCircle(dateUiState = it)
                binding.ivDayRectangleLeft.setLeftRect(dateUiState = it)
                binding.ivDayRectangleRight.setRightRect(dateUiState = it)
            }
        }

        fun bindDateState(calendar: CalendarUiModel) {
            binding.tvDay.setTextColor(if (calendar.dateState == DateUiState.SELECTED_SUNDAY_END || calendar.dateState == DateUiState.SELECTED ||
                calendar.dateState == DateUiState.SELECTED_START || calendar.dateState == DateUiState.SELECTED_END ||
                calendar.dateState == DateUiState.SELECTED_SATURDAY_START || calendar.dateState == DateUiState.SELECTED_BOTH
            ) ContextCompat.getColor(binding.root.context, R.color.www_white)
            else ContextCompat.getColor(binding.root.context, R.color.www_black))

            calendar.dateState?.let {
                binding.ivDayCircle.setDateCircle(dateUiState = it)
                binding.ivDayRectangleLeft.setLeftRect(dateUiState = it)
                binding.ivDayRectangleRight.setRightRect(dateUiState = it)
            }
        }

        fun bindDateStateSelectToSelect(calendar: CalendarUiModel) {
            calendar.dateState?.let {
                binding.ivDayRectangleLeft.setLeftRect(dateUiState = it)
                binding.ivDayRectangleRight.setRightRect(dateUiState = it)
            }
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

        override fun getChangePayload(oldItem: CalendarUiModel, newItem: CalendarUiModel): Any? {
            return if (oldItem.dateState == DateUiState.SELECTED && (newItem.dateState == DateUiState.SELECTED_START ||
                        newItem.dateState == DateUiState.SELECTED_SATURDAY_START)
            ) DateChangeState.SELECT_TO_SELECT
            else if ((oldItem.dateState != newItem.dateState)) DateChangeState.NOT_SELECT_TO_SELECT
            else null
        }
    }

    companion object {
        const val MONTH_VIEW_TYPE = 0
        const val DAY_VIEW_TYPE = 1
    }
}

enum class DateChangeState {
    SELECT_TO_SELECT,
    NOT_SELECT_TO_SELECT,

}