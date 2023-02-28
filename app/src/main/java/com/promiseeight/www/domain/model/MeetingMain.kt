package com.promiseeight.www.domain.model

data class MeetingMain (
    val confirmedDate: String?,
    val confirmedPlace: String?,
    val confirmedTime: String?,
    val hostName: String,
    val joinedUserCount: Int,
    val meetingId: Int,
    val meetingName: String,
    val meetingStatus: String,
    val minimumAlertMembers: Int,
    val votingUserCount: Int,
    val yaksokiType: String

)