package com.promiseeight.www.ui.model

import com.promiseeight.www.domain.model.PromiseTime
import org.joda.time.DateTime

data class TimeUiModel(
    val id : Long,
    val date : DateTime,
    val time : PromiseTime,
    val selected : Boolean,
    val disabled : Boolean
)