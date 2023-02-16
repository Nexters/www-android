package com.promiseeight.www.ui.model

import com.promiseeight.www.ui.model.enums.DateUiState
import org.joda.time.DateTime

data class CalendarUiModel(
    val id : Long,
    val title : String,
    val isMonth : Boolean,
    val dateTime : DateTime,
    val dateState : DateUiState? = null,
    val isCurrentMonth : Boolean? = null
)
