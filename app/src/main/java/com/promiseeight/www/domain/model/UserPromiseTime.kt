package com.promiseeight.www.domain.model

import com.google.gson.annotations.SerializedName

data class UserPromiseTime(
    val promiseDate : String,
    val promiseDayOfWeek : String = "",
    val promiseTime : PromiseTime,
    val userInfoList : List<User> = emptyList(),
    val timetableId : Long = 0
)
