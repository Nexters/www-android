package com.promiseeight.www.data.model.request

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.domain.model.MeetingJoinCondition

data class JoinMeetingRequest(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("promisePlaceList") val promisePlaceList: List<String>,
    @SerializedName("userPromiseTimeList") val userPromiseTimeList: List<UserPromiseTimeRequest>
)

fun MeetingJoinCondition.toJoinMeetingRequest() = JoinMeetingRequest(
    nickname = nickname,
    promisePlaceList = promisePlaceList,
    userPromiseTimeList = userPromiseTimeList.map {
        it.toUserPromiseTimeRequest()
    }
)