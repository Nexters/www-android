package com.promiseeight.www.ui.model

import com.promiseeight.www.domain.model.MeetingDetail
import com.promiseeight.www.domain.model.PlaceVote
import com.promiseeight.www.domain.model.UserPromisePlace
import com.promiseeight.www.domain.model.UserPromiseTime
import com.promiseeight.www.domain.model.MeetingStatus
import com.promiseeight.www.ui.model.enums.MeetingYaksogi

data class MeetingDetailUiModel(
    val hostName: String,
    val isHost: Boolean,
    val isJoined: Boolean,
    val joinedUserCount: Long,
    val joinedUserList : List<MeetingDetailUserUiModel>,
    val meetingCode: String,
    val meetingId: Long,
    val meetingName: String,
    val meetingStatus: MeetingStatus,
    val minimumAlertMembers: Long,
    val shortLink: String,
    val confirmedDate: String?,
    val confirmedPlace: String?,
    val confirmedTime: String?,
    val currentUserName: String?,
    val userPromiseDateTimeList: List<UserPromiseTime>,
    val userPromisePlaceList: List<UserPromisePlace>?,
    val userVoteList: List<PlaceVote>?,
    val votingUserCount: Int,
    val userVoted: Boolean = false,
    val yaksogi: MeetingYaksogi,
)

fun MeetingDetail.toMeetingDetailUiModel() = MeetingDetailUiModel(
    hostName = hostName,
    isHost = isHost,
    isJoined = isJoined,
    joinedUserCount = joinedUserCount,
    joinedUserList = joinedUserInfoList.map {
        it.toMeetingDetailUserUiModel()
    },
    meetingCode = meetingCode,
    meetingId = meetingId,
    meetingName = meetingName,
    meetingStatus = MeetingStatus.valueOf(meetingStatus),
    minimumAlertMembers = minimumAlertMembers,
    shortLink = shortLink,
    confirmedDate = confirmedDate,
    confirmedPlace = confirmedPlace,
    confirmedTime = confirmedTime,
    currentUserName = currentUserName,
    userPromiseDateTimeList = userPromiseDateTimeList,
    userPromisePlaceList = userPromisePlaceList?.map {
        it.copy(
            voted = getPlaceVoted(it.promisePlace)
        )
    },
    userVoteList = userVoteList,
    votingUserCount = votingUserCount,
    userVoted = getUserVoted(),
    yaksogi = MeetingYaksogi.valueOf(yaksogi),
)

fun MeetingDetail.getUserVoted(): Boolean {
    userVoteList?.forEach {
        if (it.userNameList.contains(currentUserName)) return true
    }
    return false
}

fun MeetingDetail.getPlaceVoted(placeName: String): Boolean {
    userVoteList?.forEach {
        if (it.placeName == placeName && it.userNameList.contains(currentUserName)) return true
    }
    return false
}

