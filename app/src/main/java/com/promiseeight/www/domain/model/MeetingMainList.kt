package com.promiseeight.www.domain.model

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.data.model.response.MeetingMainResponse

data class MeetingMainList (
    val meetingIngList: List<MeetingMain>,
    val meetingEndList: List<MeetingMain>
)