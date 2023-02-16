package com.promiseeight.www.data.model.request

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.domain.model.MeetingCondition

data class MeetingCreateRequest(
    @SerializedName("meetingName") val meetingName: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("minimumAlertMembers") val minimumAlertMembers: Long,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String,
    @SerializedName("promiseDateTimeList") val promiseDateTimeList: List<UserPromiseTimeRequest>,
    @SerializedName("promisePlaceList") val promisePlaceList: List<String>
)

fun MeetingCondition.toMeetingCreateRequest() = MeetingCreateRequest(
    meetingName = meetingName,
    userName = userName,
    minimumAlertMembers = minimumAlertMembers,
    startDate = startDate,
    endDate = endDate,
    promiseDateTimeList = promiseInfoList.map {
        it.toUserPromiseTimeRequest()
    },
    promisePlaceList = promisePlaceList
)