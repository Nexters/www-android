package com.promiseeight.www.domain.model

data class MeetingCondition(
    val meetingName : String,
    val userName : String,
    val minimumAlertMembers : Long,
    val startDate : String,
    val endDate : String,
    val promiseTimeList : List<UserPromiseTime>,
    val promisePlaceList : List<String>
)
