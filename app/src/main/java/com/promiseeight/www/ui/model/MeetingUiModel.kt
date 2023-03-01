package com.promiseeight.www.ui.model

import com.promiseeight.www.domain.model.MeetingMain
import com.promiseeight.www.domain.model.MeetingStatus
import com.promiseeight.www.ui.model.enums.MeetingYaksogi

data class MeetingUiModel(
    val hostName : String,
    val joinedUserCount : Int,
    val meetingId : Long, // pk
    val meetingName : String,
    val meetingStatus : MeetingStatus,
    val minimumAlertMembers : Long,
    val confirmedDate : String?,
    val confirmedPlace : String?,
    val confirmedTime : String?,
    val votingUserCount : Int,
    val yaksogi: MeetingYaksogi,
    val dDay : String = ""
)

fun MeetingMain.toMeetingUiModel() = MeetingUiModel(
    hostName = hostName,
    joinedUserCount = joinedUserCount,
    meetingId = meetingId,
    meetingName,
    meetingStatus = MeetingStatus.valueOf(meetingStatus)
    , minimumAlertMembers, confirmedDate, confirmedPlace, confirmedTime, votingUserCount,
    yaksogi = MeetingYaksogi.valueOf(yaksokiType)
)