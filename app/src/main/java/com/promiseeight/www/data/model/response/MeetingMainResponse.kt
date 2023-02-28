package com.promiseeight.www.data.model.response

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.domain.model.MeetingMain

data class MeetingMainResponse(
    @SerializedName("confirmedDate")
    val confirmedDate: String?,
    @SerializedName("confirmedPlace")
    val confirmedPlace: String?,
    @SerializedName("confirmedTime")
    val confirmedTime: String?,
    @SerializedName("hostName")
    val hostName: String,
    @SerializedName("joinedUserCount")
    val joinedUserCount: Int,
    @SerializedName("meetingId")
    val meetingId: Int,
    @SerializedName("meetingName")
    val meetingName: String,
    @SerializedName("meetingStatus")
    val meetingStatus: String,
    @SerializedName("minimumAlertMembers")
    val minimumAlertMembers: Int,
    @SerializedName("votingUserCount")
    val votingUserCount: Int,
    @SerializedName("yaksokiType")
    val yaksokiType: String
)

fun MeetingMainResponse.toMeetingMain() = MeetingMain(
    confirmedDate,
    confirmedPlace,
    confirmedTime,
    hostName,
    joinedUserCount,
    meetingId,
    meetingName,
    meetingStatus,
    minimumAlertMembers,
    votingUserCount,
    yaksokiType
)