package com.promiseeight.www.domain.model

data class PromiseInfo(
    val date : String,
    val datOfWeek : String = "",
    val promiseTime : PromiseTime
)
