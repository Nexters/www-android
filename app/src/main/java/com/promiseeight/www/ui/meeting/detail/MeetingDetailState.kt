package com.promiseeight.www.ui.meeting.detail

import com.promiseeight.www.domain.model.MeetingStatus

data class MeetingDetailState(
    val meetingStatus : MeetingStatus = MeetingStatus.WAITING
)

