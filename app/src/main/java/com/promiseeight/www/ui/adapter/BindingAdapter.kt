package com.promiseeight.www.ui.adapter

import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.promiseeight.www.R
import com.promiseeight.www.ui.model.enums.DateUiState
import com.promiseeight.www.ui.model.enums.MeetingYaksogi

@BindingAdapter("dateCircle")
fun ImageView.setDateCircle(dateUiState: DateUiState) {
    Glide.with(this.context)
        .load(
            when (dateUiState) {
                DateUiState.SELECTED,
                DateUiState.SELECTED_END,
                DateUiState.SELECTED_START,
                DateUiState.GRAD_SELECT_END,
                DateUiState.GRAD_SELECT_START,
                DateUiState.SELECTED_SATURDAY_START,
                DateUiState.SELECTED_SUNDAY_END -> AppCompatResources.getDrawable(context,R.drawable.circle_green)
                else -> null
            }
        )
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

@BindingAdapter("dateLeftRect")
fun ImageView.setLeftRect(dateUiState: DateUiState) {
    Glide.with(this.context)
        .load(
            when (dateUiState) {
                DateUiState.PASS,
                DateUiState.PASS_END,
                DateUiState.SELECTED_END -> AppCompatResources.getDrawable(context,R.drawable.rectangle_green35)
                DateUiState.SELECTED_SUNDAY_END,
                DateUiState.PASS_START-> AppCompatResources.getDrawable(context, R.drawable.rectangle_green35_radius_6_left)
                else -> null
            }
        )
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

@BindingAdapter("dateRightRect")
fun ImageView.setRightRect(dateUiState: DateUiState) {
    Glide.with(this.context)
        .load(
            when (dateUiState) {
                DateUiState.PASS,
                DateUiState.PASS_START,
                DateUiState.SELECTED_START -> AppCompatResources.getDrawable(context,R.drawable.rectangle_green35)
                DateUiState.SELECTED_SATURDAY_START,
                DateUiState.PASS_END -> AppCompatResources.getDrawable(context, R.drawable.rectangle_green35_radius_6_right)
                else -> null
            }
        )
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

@BindingAdapter("meetingYaksogi")
fun ImageView.setMeetingYaksogi(meetingYaksogi: MeetingYaksogi) {
    Glide.with(this.context)
        .load(
            when (meetingYaksogi) {
                MeetingYaksogi.PLAY -> AppCompatResources.getDrawable(context, R.drawable.img_yaksogi_picnic)
                MeetingYaksogi.EAT -> AppCompatResources.getDrawable(context, R.drawable.img_yaksogi_eating)
                MeetingYaksogi.REST -> AppCompatResources.getDrawable(context, R.drawable.img_yaksogi_rest)
                MeetingYaksogi.WORK -> AppCompatResources.getDrawable(context, R.drawable.img_yaksogi_working)
                else -> null
            }
        )
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}