package com.promiseeight.www.ui.model

data class MeetingUiModel(
    val hostName : String,
    val joinedUserCount : Long,
    val meetingId : Long, // pk
    val meetingName : String,
    val meetingStatus : String,
    val minimumAlertMembers : Long,
    val confirmedDate : String? = null,
    val confirmedPlace : String? = null,
    val confirmedTime : String? = null,
    val votingUserCount : Int
)