package com.promiseeight.www.ui.model

import com.promiseeight.www.domain.model.MeetingMainList

data class MeetingMainListUiModel(
    val meetingIngList: List<MeetingUiModel>,
    val meetingEndList: List<MeetingUiModel>
)

fun MeetingMainList.toMeetingMainListUiModel() = MeetingMainListUiModel(
    meetingIngList = meetingIngList.map {
        it.toMeetingUiModel()
    },
    meetingEndList = meetingEndList.map {
        it.toMeetingUiModel()
    }
)
