package com.promiseeight.www.data.model.response

import com.google.gson.annotations.SerializedName
import com.promiseeight.www.domain.model.MeetingMain

data class MeetingMainResponse(
    @SerializedName("confirmedDate")
    val confirmedDate: String? = null,
    @SerializedName("confirmedPlace")
    val confirmedPlace: String?  = null,
    @SerializedName("confirmedTime")
    val confirmedTime: String?  = null,
    @SerializedName("hostName")
    val hostName: String,
    @SerializedName("joinedUserCount")
    val joinedUserCount: Int,
    @SerializedName("meetingId")
    val meetingId: Long,
    @SerializedName("meetingName")
    val meetingName: String,
    @SerializedName("meetingStatus")
    val meetingStatus: String,
    @SerializedName("minimumAlertMembers")
    val minimumAlertMembers: Long,
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