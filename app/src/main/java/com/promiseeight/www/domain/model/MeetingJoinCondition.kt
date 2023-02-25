package com.promiseeight.www.domain.model

data class MeetingJoinCondition(
    val nickname: String,
    val promisePlaceList: List<String>,
    val userPromiseTimeList: List<UserPromiseTime>
)