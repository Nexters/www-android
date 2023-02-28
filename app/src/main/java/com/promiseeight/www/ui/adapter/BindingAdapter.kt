package com.promiseeight.www.ui.adapter

import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.promiseeight.www.R
import com.promiseeight.www.ui.model.enums.DateUiState

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