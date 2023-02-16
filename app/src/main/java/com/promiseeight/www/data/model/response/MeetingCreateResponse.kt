package com.promiseeight.www.data.model.response

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.domain.model.MeetingInvitation

data class MeetingCreateResponse(
    @SerializedName("meetingCode") val meetingCode : String,
    @SerializedName("shortLink") val shortLink : String
)

fun MeetingCreateResponse.toMeetingInvitation() = MeetingInvitation(
    code = meetingCode,
    shortLink = shortLink
)