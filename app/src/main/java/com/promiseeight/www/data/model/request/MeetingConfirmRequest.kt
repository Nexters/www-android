package com.promiseeight.www.data.model.request

import com.google.gson.annotations.SerializedName

data class MeetingConfirmRequest(
    @SerializedName("meetingPlaceId") val meetingPlaceId : Long,
    @SerializedName("meetingUserTimetableId") val meetingUserTimetableId : Long
)
