package com.promiseeight.www.data.model.response

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.domain.model.MeetingMainList

data class MeetingMainListResponse(
    @SerializedName("meetingIngList")
    val meetingIngList: List<MeetingMainResponse>,
    @SerializedName("meetingEndList")
    val meetingEndList: List<MeetingMainResponse>

)

fun MeetingMainListResponse .toMeetingMainList() = MeetingMainList(
    meetingIngList = meetingIngList.map {
        it.toMeetingMain()
    },
    meetingEndList = meetingEndList.map {
        it.toMeetingMain()
    }
)